package org.sopt.assignment.global.config;

import lombok.RequiredArgsConstructor;
import org.sopt.assignment.global.constants.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

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
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPointHandler)
                )

                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtUtil, jwtAuthenticationManager), LogoutFilter.class
                )

                .addFilterBefore(
                        new JwtExceptionFilter(), JwtAuthenticationFilter.class
                )

                .getOrBuild();
    }
}
