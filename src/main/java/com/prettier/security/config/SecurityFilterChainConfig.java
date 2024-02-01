package com.prettier.security.config;

import com.prettier.security.exception.CustomAuthenticationFailureHandler;
import com.prettier.security.jwt.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationEntryPoint authEntryPointJwt;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    public SecurityFilterChainConfig(JWTAuthenticationFilter jwtAuthenticationFilter,
                                     AuthenticationProvider authenticationProvider,
                                     @Qualifier("delegatedAuthEntryPoint") AuthenticationEntryPoint authEntryPointJwt,
                                     CustomAuthenticationFailureHandler customAuthenticationFailureHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
        this.authEntryPointJwt = authEntryPointJwt;
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors(Customizer.withDefaults())
                .formLogin()
                .failureHandler(customAuthenticationFailureHandler).and()
                .authorizeHttpRequests()
                .requestMatchers(
                        HttpMethod.POST,
                        "/api/v1.0/users",
                        "/api/v1.0/auth/**"
                )
                .permitAll()
                .requestMatchers(
                        HttpMethod.GET,
                        "/ping",
                        "/api/v1.0/users/*/profile-image"
                ).permitAll()
                .requestMatchers(HttpMethod.GET, "/actuator/**")
                .permitAll()
                .requestMatchers(AUTH_WHITE_LIST)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPointJwt);
        return http.build();

////                .cors().and()
//                .csrf().disable()
//                .cors(Customizer.withDefaults())
//                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
//                .and()
//                .formLogin()
//                //.loginPage("/api/v1.0/auth/{language}/login")
//                .failureHandler(customAuthenticationFailureHandler).and()
//                .authorizeHttpRequests()
//                //.requestMatchers(HttpMethod.GET,"/api/**").permitAll()
//                .requestMatchers("/api/v1.0/auth/**").permitAll()
//                .requestMatchers(AUTH_WHITE_LIST).permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//        http.headers().frameOptions().sameOrigin();
//        http.httpBasic(Customizer.withDefaults());
//        return http.build();
    }

    private static final String AUTH_WHITE_LIST[] = {
            "/",
            "/index*",
            "/static/**",
            "/*.js",
            "/*.json",
            "/css/**",
            "/favicon.ico",
            "/contact-Messages/**",
            "/api/v1.0/auth/{language}/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger*/**"
    };
}
