package com.nuritech.stock.mystock.common.exception;

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
        } catch (Exception ex){
            log.error("exception handler filter");
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,response,ex);

        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex){
        response.setStatus(status.value());
        response.setContentType("application/json");

        ErrorResponse errorResponse = new ErrorResponse(status.value(), ex.getClass().getSimpleName(), ex.getMessage());
        try{
            String json = errorResponse.convertToJson();
            log.debug(">>> ExceptionHandlerFilter:errorResponse:json={}", json);
            response.getWriter().write(json);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
