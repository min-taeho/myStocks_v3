package com.nuritech.stock.mystock.user.web;

import com.nuritech.stock.mystock.common.exception.ApiException;
import com.nuritech.stock.mystock.common.exception.ExceptionCode;
import com.nuritech.stock.mystock.common.util.JwtUtil;
import com.nuritech.stock.mystock.user.ApiTokenType;
import com.nuritech.stock.mystock.user.dto.*;
import com.nuritech.stock.mystock.user.service.UserService;
import com.nuritech.stock.mystock.user.dto.SignUpRequestDto;
import com.nuritech.stock.mystock.user.dto.SignUpResponseDto;
import com.nuritech.stock.mystock.user.dto.UserResponseDto;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    /**
     * 사용자 등록 : 사용자 정보를 등록한다.
     *
     * @param request
     * @return
     */
    @PostMapping({"/signUp", "/api/user", "api/v1/user"})
    public SignUpResponseDto signUp(@RequestBody SignUpRequestDto request) {
        SignUpResponseDto response = new SignUpResponseDto();

        log.debug(">>>> UserApiController::signUp..");
        try {
            userService.save(request);
            response.setResponse("success");
            response.setMessage("회원가입을 성공적으로 완료했습니다.");
        } catch (Exception e) {
            //response.setResponse("failed");
            //response.setMessage("회원가입을 하는 도중 오류가 발생했습니다.");
            //response.setData(e.toString());
            throw e;
        }
        return response;
    }

    @GetMapping("/api/v1/user/list")
    @ResponseStatus(value = HttpStatus.OK)
    public List<UserResponseDto> getUserList() {
        return userService.getUserList();
    }

    /**
     * 사용자 로그인 : 사용자 인증을 수행한다.
     * 인증을 통해 access token과 refresh token을 발급받을 수 있고,
     * 유효한 token을 보유하고 있다면, 인증 절차 없이 API를 사용할 수 있다.
     * token으로 인증을 대체하기 때문이다.
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/authenticate", "api/v1/login"}, method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody SignInRequestDto dto) throws Exception {

        Long userId = userService.signIn(dto);
        /*
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(reqeust.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails, ApiTokenType.ACCESS_TOKEN);
        */
        SignInResponseDto signInResponse = userService.findFirstByUserId(userId);
        return ResponseEntity.ok(signInResponse);
    }

    /**
     * 테스트를 위한 메소드
     * @return
     */
    @GetMapping("/contents")
    public String contents() { // 회원 추가
        return "welcome to hell";
    }


}