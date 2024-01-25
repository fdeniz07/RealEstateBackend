package com.prettier.security.config;

import com.prettier.repository.RoleRepository;
import com.prettier.security.jwt.AuthEntryPointJwt;
import com.prettier.security.jwt.AuthTokenFilter;
import com.prettier.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserDetailsServiceImpl userService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthEntryPointJwt unauthorizedHandler;


    // add our security configuration here...

//   @Bean
//public InMemoryUserDetailsManager userDetailsManager(){
//
//       Role role = roleRepository.findByNameEquals("ADMIN");
//       Set<Role> roleSet = new HashSet<>();
//
//       UserDetails john = User.builder()
//               .userName("john")
//               .passwordHash("{noop}test123")
//               .roles(roleSet)
//               .build();
//   }

//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//
//        //olusan obje üzerinden Encoder ve UserDetailService'i tanistirmaliyiz
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//
//        return authProvider;
//    }

    @Bean //method seviye - singleton
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {

        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


//        @Bean
//        public SecurityFilterChain securityFilterChain(HttpSecurity http,
//                RoleRepository roleRepository) throws Exception {


        //Http isteklerine göre yetkilendirme kurallarimiz

//        http.authorizeHttpRequests(configurer ->
//                configurer
//                        .requestMatchers(HttpMethod.GET, "/api/v1.0/users").hasRole("CUSTOMER")
//                        .requestMatchers(HttpMethod.GET, "/api/v1.0/users/**").hasRole("CUSTOMER")
//                        .requestMatchers(HttpMethod.POST, "/api/v1.0/users").hasRole("MANAGER")
//                        .requestMatchers(HttpMethod.PUT, "/api/v1.0/users").hasRole("MANAGER")
//                        .requestMatchers(HttpMethod.DELETE, "/api/v1.0/users").hasRole("ADMIN")
//        );

        // use HTTP Basic Authentication
        //http.httpBasic(Customizer.withDefaults());
//
//        // disable Cross Site Request Forgery (CSRF)
//        // in general, not required for sateteless REST APIs that use POST,PUT,DELETE and/or PATCH

        http.csrf(AbstractHttpConfigurer::disable) //csrf.disable() etmemmizin sebebi, update metotlarimiz düzgün calismayabilir
                .authorizeHttpRequests(request -> request.requestMatchers("/api/v1.0/auth/**")
                        .permitAll()
//                        .requestMatchers("/api/v1.0/admins").hasAnyAuthority(roleRepository.findByNameEquals("ADMIN").getName())
//                        .requestMatchers("/api/v1.0/users").hasAnyAuthority(roleRepository.findByNameEquals("ADMIN").getName())
//                        .requestMatchers("/api/v1.0/managers").hasAnyAuthority(roleRepository.findByNameEquals("MANAGER").getName())
//                        .requestMatchers("/api/v1.0/customers").hasAnyAuthority(roleRepository.findByNameEquals("CUSTOMER").getName())
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                );
//        return http.build();


        http.cors(cors -> cors.disable()); //cors --> tarayici tarafli güvenlik protokolleri- server baska bir sunucuda, Db baska bir sunucuda, frontend baska tarafta olabilir
        http.authenticationProvider(authenticationProvider());
         http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//
        return http.build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    private static final String AUTH_WHITE_LIST[] = {
            "/",
            "/index*",
            "/static/**",
            "/*.js",
            "/*.json",
            "/contactMessages/save",
            "/auth/login",
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
