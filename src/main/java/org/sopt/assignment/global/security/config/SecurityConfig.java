package org.sopt.assignment.global.security.config;

import lombok.RequiredArgsConstructor;
import org.sopt.assignment.global.constants.Constants;
import org.sopt.assignment.global.security.exception.CustomAccessDeniedHandler;
import org.sopt.assignment.global.security.exception.CustomAuthenticationEntryPointerHandler;
import org.sopt.assignment.global.security.filter.JwtAuthenticationFilter;
import org.sopt.assignment.global.security.filter.JwtExceptionFilter;
import org.sopt.assignment.global.security.manager.JwtAuthenticationManager;
import org.sopt.assignment.global.security.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationEntryPointerHandler customAuthenticationEntryPointerHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtAuthenticationManager jwtAuthenticationManager;
    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(new PortRequestMatcher(8080))

                .csrf(AbstractHttpConfigurer::disable)

                .httpBasic(AbstractHttpConfigurer::disable)

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .formLogin(AbstractHttpConfigurer::disable)


                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers(Constants.NO_NEED_AUTH.toArray(String[]::new)).permitAll()
                                .anyRequest().authenticated())

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPointerHandler)
                        .accessDeniedHandler(customAccessDeniedHandler)
                )

                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtAuthenticationManager, jwtUtil), UsernamePasswordAuthenticationFilter.class
                )

                .addFilterBefore(
                        new JwtExceptionFilter(), JwtAuthenticationFilter.class
                )

                .getOrBuild();
    }
}
