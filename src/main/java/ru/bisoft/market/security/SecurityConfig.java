package ru.bisoft.market.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class SecurityConfig {

        CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowCredentials(true);
                //configuration.setAllowedOrigins(Arrays.asList("*"));
                configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://192.168.23.178:3000", "http://172.16.1.4:8090"));
                configuration.setAllowedMethods(Arrays.asList("*"));
                configuration.setAllowedHeaders(Arrays.asList("*"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

        @Bean
        public SecurityFilterChain config(HttpSecurity http) throws Exception {
                return http
                                .authorizeHttpRequests(cst -> cst
                                                .requestMatchers("/api/**").permitAll()
                                                .requestMatchers("/**").permitAll())
                                .formLogin(cst -> cst
                                                .defaultSuccessUrl("/")
                                                .permitAll())
                                .cors(cfg -> cfg.configurationSource(corsConfigurationSource()))
                                .csrf(cfg -> cfg.disable())
                                .build();
        }

        @Bean
        public UserDetailsManager userDetailsManager(PasswordEncoder passwordEncoder) {
                log.info(passwordEncoder.encode("user"));
                log.info(passwordEncoder.encode("admin"));
                UserDetails user = User
                                .withUsername("user")
                                .password(passwordEncoder.encode("user"))
                                .roles("USER")
                                .build();

                UserDetails admin = User
                                .withUsername("admin")
                                .password(passwordEncoder.encode("admin"))
                                .roles("ADMIN")
                                .build();
                return new InMemoryUserDetailsManager(user, admin);
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }
}
