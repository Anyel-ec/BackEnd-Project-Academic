package pe.edu.unamba.academic.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pe.edu.unamba.academic.security.config.UserDetailsServiceImpl;
import pe.edu.unamba.academic.security.jwt.JwtEntryPoint;
import pe.edu.unamba.academic.security.jwt.JwtTokenFilter;

@AllArgsConstructor
@Slf4j
@Configuration
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final JwtEntryPoint jwtEntryPoint;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenFilter jwtTokenFilter;
    private AuthenticationManager authenticationManager;

    protected static final String[] AUTH_WHITELIST = {
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/h2-console/**",
            "api/v1/auth/login",
            "/api/v1/test/validateToken",
            "/api/v1/**",
            "/test/",
            "/api/correo/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
        authenticationManager = builder.build();
        http.authenticationManager(authenticationManager);

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());

        // Allow H2 console to load properly by configuring headers
        http.headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin) // Allow H2 console in iframe
        );
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(AUTH_WHITELIST)
                .permitAll()
                .anyRequest().authenticated()); //ojo

        http.exceptionHandling(exc -> exc.authenticationEntryPoint(jwtEntryPoint));
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
