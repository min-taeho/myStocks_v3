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
     * ??????????????? ????????????.
     * @param request ??????????????? ???????????? DTO
     * @return ???????????? ????????? PK
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
     * ????????? ?????? ???????????? ????????????, token??? ????????????.
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
     * authenticationManager??? ?????? ????????????(authenticate)
     *
     * @param authId
     * @param password
     */
    private void authenticate(String authId, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authId, password));
    }

    /**
     * ?????? ??????
     * ??????????????? ?????? accessToken??? refreshToken??? ?????? null??????,
     * ???????????? ?????? null??? ?????????.
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
     * ??????????????? ???????????? ????????? ????????????.
     * @param userDetails
     * @return
     * @throws Exception
     */
    @Transactional
    public Collection<? extends GrantedAuthority> getAuthorities(UserDetails userDetails) throws Exception {
        return userDetails.getAuthorities();
    }

    /**
     * ???????????? token ????????? ????????????.
     * @param userId
     * @return
     */
    public SignInResponseDto findFirstByUserId(Long userId) {
        ApiTokenEntity entity = apiTokenRepository.findFirstByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("???????????? ?????? token ?????????."));
        return new SignInResponseDto(entity);
    }

    /**
     * refresh ????????? ????????? access ?????? ????????? ??????
     *
     * @param userId
     * @param refreshToken
     * @return
     */
    @Transactional
    public SignInResponseDto reIssueToken(Long userId, String refreshToken) {

        // 1) token ???????????? ??????
        if (jwtUtil.isTokenExpired(refreshToken))
            throw new ApiException(ExceptionCode.EXPIRED_TOKEN);

        // 2) DB?????? ????????????
        String savedRefreshToken = this.findUserTokenByUserId(userId).getRefreshToken();
        if (StringUtils.compare(refreshToken, savedRefreshToken) != 0) {
            throw new ApiException(ExceptionCode.USER_NOT_FOUND);
        }

        // 3) access token ??????
        String authId = this.findUserByUserId(userId).getAuthId();
        String newAccessToken = jwtUtil.generateToken(authId, ApiTokenType.ACCESS_TOKEN);

        // 4) refresh token??? 7??? ????????? refresh token??? ??????
        // TODO : refresh ?????? ????????? ?????? ?????? ????????? ?????? ??????
        String newRefreshToken = refreshToken;
        if (jwtUtil.isTokenWithin7daysExpires(refreshToken)) {
            newRefreshToken = jwtUtil.generateToken(authId, ApiTokenType.REFRESH_TOKEN);
        }

        // 5) ???????????? DB??????
        this.issueToken(userId, authId, newAccessToken, newRefreshToken);

        return new SignInResponseDto(userId, newAccessToken, newRefreshToken);
    }

    /**
     * ????????? ????????? DB??? ????????? token??? ???????????? ??????.
     *
     * @param userId DB?????? ???????????? ????????? ID
     * @param token ????????? ??????
     * @return true/false
     */
    public boolean compareIssuedToken(Long userId, String token, ApiTokenType type) {

        // token ?????? ??????
        SignInResponseDto issuedToken = this.findUserTokenByUserId(userId);

        if(type == ApiTokenType.ACCESS_TOKEN) return StringUtils.equals(token, issuedToken.getAccessToken());
        else return StringUtils.equals(token, issuedToken.getRefreshToken());
    }

    /**
     * ???????????? token ????????? ????????????.
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
     * UserId??? ????????? ????????? ????????????.
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
