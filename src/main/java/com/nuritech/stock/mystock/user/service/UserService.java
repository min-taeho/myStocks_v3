package com.nuritech.stock.mystock.user.service;

import com.nuritech.stock.mystock.common.exception.ApiException;
import com.nuritech.stock.mystock.common.exception.ExceptionCode;
import com.nuritech.stock.mystock.common.util.JwtUtil;
import com.nuritech.stock.mystock.user.ApiTokenType;
import com.nuritech.stock.mystock.user.Authority;
import com.nuritech.stock.mystock.user.domain.ApiTokenEntity;
import com.nuritech.stock.mystock.user.domain.ApiTokenRepository;
import com.nuritech.stock.mystock.user.domain.UserEntity;
import com.nuritech.stock.mystock.user.domain.UserRepository;
import com.nuritech.stock.mystock.user.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ApiTokenRepository apiTokenRepository;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @Transactional
    public List<UserResponseDto> getUserList() throws UsernameNotFoundException {
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 회원정보를 저장한다.
     * @param request 회원정보가 들어있는 DTO
     * @return 저장되는 회원의 PK
     */
    @Transactional
    public Long save(SignUpRequestDto request) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(">>> pw="+encoder.encode(request.getPassword()));
        UserEntity userEntity = new UserEntity(request.getAuthId(), encoder.encode(request.getPassword()), Authority.USER);
        userRepository.save(userEntity);
        return userEntity.getUserId();
    }

    /**
     * 로그인 하는 사용자를 인증하고, token을 발급한다.
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional
    public Long signIn(SignInRequestDto request) throws Exception {
        try {
            UserEntity userEntity = userRepository.findFirstByAuthId(request.getAuthId())
                    .orElseThrow(() -> new ApiException(ExceptionCode.USER_NOT_FOUND));

            authenticate(request.getAuthId(), request.getPassword());
            issueToken(userEntity.getUserId(), userEntity.getAuthId(), null, null);

            return userEntity.getUserId();
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    /**
     * authenticationManager를 통해 인증처리(authenticate)
     *
     * @param authId
     * @param password
     */
    private void authenticate(String authId, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authId, password));
    }

    /**
     * 토큰 발급
     * 최초발급인 경우 accessToken과 refreshToken의 값이 null이고,
     * 재발급인 경우 null이 아니다.
     *
     * @param userId
     * @param authId
     * @param accessToken
     * @param refreshToken
     */
    private void issueToken(Long userId, String authId, String accessToken, String refreshToken) {
        apiTokenRepository.removeByUserId(userId);

        accessToken = StringUtils.isEmpty(accessToken) ? jwtUtil.generateToken(authId, ApiTokenType.ACCESS_TOKEN) : accessToken;
        refreshToken = StringUtils.isEmpty(refreshToken) ? jwtUtil.generateToken(authId, ApiTokenType.REFRESH_TOKEN) : refreshToken;

        ApiTokenEntity entity = ApiTokenEntity.builder()
                .userId(userId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        apiTokenRepository.save(entity);
    }

    /**
     * 로그인하는 사용자의 권한을 조회한다.
     * @param userDetails
     * @return
     * @throws Exception
     */
    @Transactional
    public Collection<? extends GrantedAuthority> getAuthorities(UserDetails userDetails) throws Exception {
        return userDetails.getAuthorities();
    }

    /**
     * 사용자의 token 정보를 조회한다.
     * @param userId
     * @return
     */
    public SignInResponseDto findFirstByUserId(Long userId) {
        ApiTokenEntity entity = apiTokenRepository.findFirstByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 token 입니다."));
        return new SignInResponseDto(entity);
    }

    /**
     * refresh 토큰을 이용해 access 토큰 재발급 처리
     *
     * @param userId
     * @param refreshToken
     * @return
     */
    @Transactional
    public SignInResponseDto reIssueToken(Long userId, String refreshToken) {

        // 1) token 기간만료 검사
        if (jwtUtil.isTokenExpired(refreshToken))
            throw new ApiException(ExceptionCode.EXPIRED_TOKEN);

        // 2) DB값과 일치여부
        String savedRefreshToken = this.findUserTokenByUserId(userId).getRefreshToken();
        if (StringUtils.compare(refreshToken, savedRefreshToken) != 0) {
            throw new ApiException(ExceptionCode.USER_NOT_FOUND);
        }

        // 3) access token 발급
        String authId = this.findUserByUserId(userId).getAuthId();
        String newAccessToken = jwtUtil.generateToken(authId, ApiTokenType.ACCESS_TOKEN);

        // 4) refresh token이 7일 이내면 refresh token도 발급
        // TODO : refresh 토큰 재발급 기간 설정 변수화 수정 필요
        String newRefreshToken = refreshToken;
        if (jwtUtil.isTokenWithin7daysExpires(refreshToken)) {
            newRefreshToken = jwtUtil.generateToken(authId, ApiTokenType.REFRESH_TOKEN);
        }

        // 5) 신규토큰 DB저장
        this.issueToken(userId, authId, newAccessToken, newRefreshToken);

        return new SignInResponseDto(userId, newAccessToken, newRefreshToken);
    }

    /**
     * 제촐된 토큰이 DB에 저장된 token과 일치여부 확인.
     *
     * @param userId DB에서 관리하는 사용자 ID
     * @param token 제출된 토큰
     * @return true/false
     */
    public boolean compareIssuedToken(Long userId, String token, ApiTokenType type) {

        // token 정보 조회
        SignInResponseDto issuedToken = this.findUserTokenByUserId(userId);

        if(type == ApiTokenType.ACCESS_TOKEN) return StringUtils.equals(token, issuedToken.getAccessToken());
        else return StringUtils.equals(token, issuedToken.getRefreshToken());
    }

    /**
     * 사용자의 token 정보를 조회한다.
     *
     * @param userId
     * @return
     */
    public SignInResponseDto findUserTokenByUserId(Long userId) {
        ApiTokenEntity entity = apiTokenRepository.findFirstByUserId(userId)
                .orElseThrow(() -> new ApiException(ExceptionCode.USER_NOT_FOUND));
        return new SignInResponseDto(entity);
    }

    /**
     * UserId로 사용자 정보를 조회한다.
     *
     * @param userId
     * @return
     */
    public UserResponseDto findUserByUserId(Long userId) {
        UserEntity entity = userRepository.findFirstByUserId(userId)
                .orElseThrow(() -> new ApiException(ExceptionCode.USER_NOT_FOUND));
        return new UserResponseDto(entity);
    }


}
