package com.nuritech.stock.mystock.common.security;

import com.nuritech.stock.mystock.common.exception.ApiException;
import com.nuritech.stock.mystock.common.exception.ExceptionCode;
import com.nuritech.stock.mystock.common.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.SignatureException;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    /**
     * TokenAuthenticationFilter 제외 URL 관리
     */
    /*
    private static final String[] EXCLUDE_PATHS = {
            "/api/v1/login", "/authenticate",
            "/signUp", "/api/user", "api/v1/user",
            "/refreshToken", "api/v1/oauth/refresh"
    };
     */

    /**
     * 필터 제외 URL 제외 처리
     *
     * @param request
     * @return true/false
     * @throws ServletException
     */
    /*
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {
        String path = request.getRequestURI();

        log.info("class={}, method={}, path={}, return{}",
                "TokenAuthenticationFilter",
                "shouldNotFilter",
                path,
                Arrays.asList(EXCLUDE_PATHS).contains(path));

        return Arrays.asList(EXCLUDE_PATHS).contains(path);
    }
     */

    /**
     * 토큰 인증 처리 필터
     *
     * AccessDeniedException을 발생시키면 AuthenticationEntryPoint에서 Exception을 처리
     *
     * 여기 필터에서 처리하던 토큰 인증을 interceptor로 이전
     * 사실상 여기 인증 필터에 기능은 없으나,
     * 향후 필터에서 처리할 기능이 필요한 경우에 사용하기 위해 형태만 남겨둔다.
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(request, response);
    }
}