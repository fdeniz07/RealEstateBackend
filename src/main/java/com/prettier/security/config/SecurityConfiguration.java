package com.prettier.security.config;

import com.prettier.security.exception.AuthEntryPointJwt;
import com.prettier.security.exception.CustomAuthenticationFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final AuthEntryPointJwt unauthorizedHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .formLogin()
                //.loginPage("/api/v1.0/auth/{language}/login")
                .failureHandler(customAuthenticationFailureHandler).and()
                .authorizeHttpRequests()
                //.requestMatchers(HttpMethod.GET,"/api/**").permitAll()
                .requestMatchers("/api/v1.0/auth/**").permitAll()
                .requestMatchers(AUTH_WHITE_LIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers().frameOptions().sameOrigin();
        http.httpBasic(Customizer.withDefaults());
        return http.build();
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

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**") // tüm URL'leri kapsayacagini söyledik
                        .allowedOrigins("*") // Tüm kaynaklara izin veriliyor
                        .allowedHeaders("*") //Tüm header'lara izin verilecegini söyledik
                        .allowedMethods("*"); //Bütün HTTP metodlarina izin verildi
            }
        };
    }
}
