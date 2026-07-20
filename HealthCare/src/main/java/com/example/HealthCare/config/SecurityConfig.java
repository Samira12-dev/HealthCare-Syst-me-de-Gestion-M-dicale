package com.example.HealthCare.config;

import com.example.HealthCare.filter.JwtFilter;
import com.example.HealthCare.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.http.HttpStatus;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // ✅ Correct AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(
                List.of("http://localhost:5178")
        );

        config.setAllowedMethods(
                List.of("*")
        );

        config.setAllowedHeaders(
                List.of("*")
        );

        config.setAllowCredentials(true);


        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> {})
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(ex -> ex
                        // No/invalid/expired token -> 401, not 403
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized: " + authException.getMessage()))
                        // Authenticated but wrong role -> 403
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.sendError(HttpStatus.FORBIDDEN.value(), "Forbidden: " + accessDeniedException.getMessage()))
                )
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()

                        // Swagger
                        .requestMatchers(
                                "/api-docs/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-ui/index.html",
                                "/swagger-resources/**",
                                "/webjars/**",   "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()

                        //admin
                        .requestMatchers(HttpMethod.GET,    "/api/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,   "/api/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,    "/api/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")

                        //medecin & admin
                        .requestMatchers(HttpMethod.GET, "/api/medecins/**").hasAnyRole("MEDECIN","ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/medecins/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/medecins/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/medecins/**").hasRole("ADMIN")

                        //patient
                        .requestMatchers(HttpMethod.GET,    "/api/patients/me").hasAnyRole("PATIENT", "MEDECIN", "ADMIN")
                        .requestMatchers(HttpMethod.GET,    "/api/patients").hasAnyRole("MEDECIN", "ADMIN")
                        .requestMatchers(HttpMethod.GET,    "/api/patients/**").hasAnyRole("MEDECIN", "ADMIN")
                        .requestMatchers(HttpMethod.POST,   "/api/patients/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,    "/api/patients/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/patients/**").hasRole("ADMIN")

                        //rendez_vous
                        .requestMatchers(HttpMethod.GET,    "/api/rendez_vous/me").hasAnyRole("PATIENT", "MEDECIN", "ADMIN")
                        .requestMatchers(HttpMethod.POST,   "/api/rendez_vous/**").hasAnyRole("PATIENT", "MEDECIN", "ADMIN")
                        .requestMatchers(HttpMethod.GET,    "/api/rendez_vous/**").hasAnyRole("MEDECIN", "ADMIN")
                        .requestMatchers(HttpMethod.PUT,    "/api/rendez_vous/**").hasAnyRole("MEDECIN", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/rendez_vous/**").hasAnyRole("MEDECIN", "ADMIN")

                        // dossier medical
                        .requestMatchers(HttpMethod.GET,    "/api/dossier/me").hasAnyRole("PATIENT", "MEDECIN", "ADMIN")
                        .requestMatchers(HttpMethod.GET,    "/api/dossier/**").hasAnyRole("MEDECIN", "ADMIN")
                        .requestMatchers(HttpMethod.POST,   "/api/dossier/**").hasAnyRole("MEDECIN", "ADMIN")
                        .requestMatchers(HttpMethod.PUT,    "/api/dossier/**").hasAnyRole("MEDECIN", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/dossier/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}