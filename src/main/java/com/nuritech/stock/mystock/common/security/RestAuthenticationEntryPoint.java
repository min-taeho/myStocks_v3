package com.nuritech.stock.mystock.common.security;

import com.nuritech.stock.mystock.common.exception.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -7858869558953243875L;

    /**
     * TokenAuthenticationFilter에서 전달된 Exception을 처리
     *
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        int exception = (Integer)request.getAttribute("exception");

        log.info("class={}, method={}, exception={}",
                "RestAuthenticationEntryPoint",
                "commence",
                exception);

        if(exception == 0 || exception == ExceptionCode.UNKNOWN_ERROR.getCode()) {
            setResponse(response, ExceptionCode.UNKNOWN_ERROR);
        }
        //잘못된 타입의 토큰인 경우
        else if( exception == ExceptionCode.WRONG_TYPE_TOKEN.getCode() ) {
            setResponse(response, ExceptionCode.WRONG_TYPE_TOKEN);
        }
        //토큰 만료된 경우
        else if(exception == ExceptionCode.EXPIRED_TOKEN.getCode() ) {
            setResponse(response, ExceptionCode.EXPIRED_TOKEN);
        }
        //지원되지 않는 토큰인 경우
        else if(exception == ExceptionCode.UNSUPPORTED_TOKEN.getCode() ) {
            setResponse(response, ExceptionCode.UNSUPPORTED_TOKEN);
        }
        else {
            setResponse(response, ExceptionCode.ACCESS_DENIED);
        }
    }

    /**
     * Response 출력, 한글 출력을 위해 getWriter() 사용
     *
     * @param response
     * @param code
     * @throws IOException
     */
    private void setResponse(HttpServletResponse response, ExceptionCode code) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseJson = new JSONObject();
        responseJson.put("message", code.getMessage());
        responseJson.put("code", code.getCode());

        response.getWriter().print(responseJson);
    }

}