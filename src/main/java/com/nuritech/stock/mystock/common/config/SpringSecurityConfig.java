package com.nuritech.stock.mystock.common.config;

import com.nuritech.stock.mystock.common.exception.ExceptionHandlerFilter;
import com.nuritech.stock.mystock.common.security.RestAuthenticationEntryPoint;
import com.nuritech.stock.mystock.common.security.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    //JwtRequestFilter가 호출되기 전에 ExceptionHandlerFilter이 호출되도록 필터를 등록
    @Autowired
    private ExceptionHandlerFilter exceptionHandlerFilter;

    @Autowired
    private TokenAuthenticationFilter jwtRequestFilter;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /*
     * Security Filter 적용을 무시한다.
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**")
                .antMatchers("/css/**")
                .antMatchers("/img/**")
                .antMatchers("/js/**")
                .antMatchers("/api/v1/login")
                .antMatchers("/api/v1/user")
                .antMatchers("/signUp")
                .antMatchers("/api/v1/oauth/refresh")
                .antMatchers("/h2-console/**")
                .antMatchers("/index")
        ;
    }

    /*
     * HttpSecurity객체는 현재 로그인한 사용자가 적절한 역할과 연결돼 있는지 확인하는 서블릿 필터를 생성한다.
     * ant 패턴식 설명
     * 1) ? -> 단일 문자와 일치한다.
     * 2) * ->/를 제외하는 0자 이상의 문자와 일치한다. (ex "/events*" == "/events","/events123")
     * 3) ** ->경로의 0개 이상의 디렉터리와 일치한다. (ex "/events/**" == "/events","/events/","/events/1","/events/1/form?test=1")
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // We don't need CSRF for this example
        httpSecurity
                .cors().disable()
                .csrf().disable()
                .httpBasic().disable()
                // 모든 리소스에 대해 permitAll(인증절차없이 접근가능)
                // anyRequest().authenticated()
                .authorizeRequests()
                    .antMatchers("/**")
                    .permitAll()
                    .anyRequest().authenticated()
                    .and()
                // make sure we use stateless session; session won't be used to
                // store user's state.
                .exceptionHandling()
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .and()
                // 상태없는 세션을 이용하며, 세션에 사용자의 상태를 저장하지 않는다.
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .headers().frameOptions().disable()
                ;

        // 모든 요청에 토큰을 검증하는 필터를 추가한다.
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(exceptionHandlerFilter, TokenAuthenticationFilter.class);
    }
}