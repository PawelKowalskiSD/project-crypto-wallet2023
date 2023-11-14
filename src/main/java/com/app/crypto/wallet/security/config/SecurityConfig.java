package com.app.crypto.wallet.security.config;

import com.app.crypto.wallet.exceptions.UserNotFoundException;
import com.app.crypto.wallet.repository.UserRepository;
import com.app.crypto.wallet.security.jwt.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.io.PrintWriter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserRepository userRepository;
    private final JwtFilter jwtFilter;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        httpSecurity.authorizeHttpRequests()
                .requestMatchers(
                        "/auth/**",
                        "/v2/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/webjars/**"
                )
                .permitAll()
                .requestMatchers(HttpMethod.GET, "/users/{userId}" ).hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/{userId}" ).hasAnyAuthority("USER", "ADMIN")
                .requestMatchers("/users/edits").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers("/users").hasAnyAuthority("ADMIN")
                .requestMatchers("/coins/**").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers("/wallets/**").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers("/roles/**").hasAnyAuthority("ADMIN")
                .anyRequest()
                .authenticated();
        httpSecurity
                .exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                    // Tworzymy niestandardową treść JSON z informacją o błędzie
                    String jsonResponse = "{\"error\": \"Access Denied\", \"message\": \"You do not have permission to access this resource\"}";

                    // Wysyłamy odpowiedź JSON z niestandardową treścią
                    PrintWriter writer = response.getWriter();
                    writer.print(jsonResponse);
                    writer.flush();
                });
        httpSecurity
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity
                .logout()
                .logoutUrl("/auth/log-out")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder getBcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authorizationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            try {
                return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
