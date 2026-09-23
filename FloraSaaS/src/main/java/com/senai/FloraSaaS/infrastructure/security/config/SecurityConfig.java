package com.senai.FloraSaaS.infrastructure.security.config;

import com.senai.FloraSaaS.infrastructure.security.jwt_auth.JwtAuthEntryPoint;
import com.senai.FloraSaaS.infrastructure.security.jwt_auth.JwtAuthenticationFilter;
import com.senai.FloraSaaS.infrastructure.security.service.UsuarioDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UsuarioDetailsService usuarioDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(
                        AbstractHttpConfigurer::disable
                )

                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthEntryPoint))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",
                                "/swagger-ui/**",
                                //"/swagger-ui.html",   // A página HTML principal
                                "/v3/api-docs/**"
                        ).permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/usuario").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/usuario/*").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/usuario/*").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/usuario/*").hasAnyRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/auth/me").hasAnyRole("USUARIO","ADMIN","CLIENTE") // todo -> AVISO quiqui: o /me puxa as informações do usuario logado, caso voce queira eu faço um mais especifico

                        .requestMatchers(HttpMethod.POST, "/api/cliente").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/cliente/**").hasAnyRole("ADMIN","CLIENTE")
                        .requestMatchers(HttpMethod.PUT, "/api/cliente/**").hasAnyRole("ADMIN","CLIENTE")
                        .requestMatchers(HttpMethod.PATCH, "/api/cliente/**").hasAnyRole("ADMIN","CLIENTE")
                        .requestMatchers(HttpMethod.DELETE, "/api/cliente/**").hasAnyRole("ADMIN")

                        // todo -> incluir requestMatchers para Ambientes, etc
                        // todo -> rever as roles permitidas para cada endpoint e os requestMatchers correspondentes

                        .requestMatchers(HttpMethod.GET, "/api/ambiente/*").hasAnyRole("ADMIN","CLIENTE")
                        .requestMatchers(HttpMethod.POST, "/api/ambiente/**").hasAnyRole("ADMIN","CLIENTE")
                        .requestMatchers(HttpMethod.PUT, "/api/ambiente/**").hasAnyRole("ADMIN","CLIENTE")
                        .requestMatchers(HttpMethod.PATCH, "/api/ambiente/**").hasAnyRole("ADMIN","CLIENTE")
                        .requestMatchers(HttpMethod.DELETE, "/api/ambiente/**").hasAnyRole("ADMIN","CLIENTE")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(usuarioDetailsService);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173")); // origem do frontend, PS: peguei do professor, ainda não temos frontend
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L); // cache preflight

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

