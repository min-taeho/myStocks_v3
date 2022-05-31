package com.nuritech.stock.mystock.common.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            log.debug("doFilterInternal::request.getRequestURI::{}", request.getRequestURI());
            filterChain.doFilter(request,response);

        } catch (SecurityException | MalformedJwtException | DecodingException e) {
            /*
             * ExceptionTranslationFilter
                    * 인증과 권환 체크에서 발생하는 AuthenticationException과 AccessDeniedException에 대한 Exception을 체크하는 필터
                    * 에러 발생한다면 ExceptionTranslationFilter는 해당에러가 AuthenticationException인지 AccessDeniedException인지를
                    * 체크하고 AccessDeniedException라면 AuthenticationTrustResolver를 통해 익명사용자인지 리멤버미 사용자인지를 확인
                    * 익명사용자이거나 리멤버미 사용자라면 AuthenticationEntryPoint 에게 넘겨 인증을 받고오도록 한다.
             * 반대로 인증을 했지만 권한이 없는 사용자라면 AccessDeniedHandler 로 넘겨 권한 실패 페이지로 넘긴다.
                    * 각각 401 (인증실패)  ,  403(권한없음) 에러에 대응한다.
                    *
             * validateAuthorizationHeader 와 doAuthenticate 에서 발생하는 IllegalArgumentException 또는 NullPointException등에
             * 대응하기 위해 사용자정의 Exception으로 처리하고 AuthenticationEntryPoint 에 넘기기 위해 AccessDeniedException을
             * 발생시킨다.
              */
            //request.setAttribute("exception", ExceptionCode.WRONG_TYPE_TOKEN.getCode());
            //throw new ApiException(ExceptionCode.WRONG_TYPE_TOKEN);
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, e, ExceptionCode.WRONG_TYPE_TOKEN.getCode());
        } catch (ExpiredJwtException | SignatureException e) {
            //request.setAttribute("exception", ExceptionCode.EXPIRED_TOKEN.getCode());
            //throw new ApiException(ExceptionCode.EXPIRED_TOKEN);
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, e, ExceptionCode.EXPIRED_TOKEN.getCode());
        } catch (UnsupportedJwtException e) {
            //request.setAttribute("exception", ExceptionCode.UNSUPPORTED_TOKEN.getCode());
            //throw new ApiException(ExceptionCode.UNSUPPORTED_TOKEN);
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, e, ExceptionCode.UNSUPPORTED_TOKEN.getCode());
        } catch (IllegalArgumentException e) {
            //request.setAttribute("exception", ExceptionCode.WRONG_TYPE_TOKEN.getCode());
            //throw new ApiException(ExceptionCode.WRONG_TYPE_TOKEN);
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, e, ExceptionCode.WRONG_TYPE_TOKEN.getCode());
        } catch (ApiException e) {
            //throw e;
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, e, e.getError().getCode());
        } catch (Exception e) {
            log.error("================================================");
            log.error("ExceptionHandlerFilter - doFilterInternal() 오류발생");
            log.error("Exception Message : {}", e.getMessage());
            log.error("Exception ExceptionCode : {}", request.getAttribute("exception"));
            log.error("Exception StackTrace : {");
            e.printStackTrace();
            log.error("}");
            log.error("================================================");
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, e, 500);
        }
    }

    public void setErrorResponse(HttpStatus status,
                                 HttpServletResponse response,
                                 Throwable ex,
                                 int exceptionCode){
        response.setStatus(status.value());
        response.setContentType("application/json");

        //ErrorResponse errorResponse = new ErrorResponse(status.value(), ex.getClass().getSimpleName(), ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(exceptionCode, ex.getClass().getSimpleName(), ex.getMessage());
        try{
            String json = errorResponse.convertToJson();
            log.debug(">>> ExceptionHandlerFilter:errorResponse:json={}", json);
            response.getWriter().write(json);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
