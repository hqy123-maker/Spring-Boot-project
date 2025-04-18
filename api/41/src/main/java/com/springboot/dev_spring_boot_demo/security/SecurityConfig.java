package com.springboot.dev_spring_boot_demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Phải khớp với cách mã hóa trong DB
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

        userDetailsManager.setUsersByUsernameQuery(
                "SELECT username, password, enabled FROM users WHERE username=?");
        userDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT username, authority FROM authorities WHERE username=?");

        return userDetailsManager;
    }




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                        configurer
                                // Cho phép truy cập không cần đăng nhập
                                .requestMatchers( "/","/home", "/about", "/products", "/health-tips",
                                        "/about-us", "/default/**", "/css/**", "/js/**", "/images/**").permitAll()

                                // Phân quyền cho các URL
                                .requestMatchers("/students/**").hasAnyRole("STUDENT", "MANAGER", "ADMIN")
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/manager/**").hasRole("MANAGER")

                                // Tất cả request khác yêu cầu đăng nhập
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form.loginPage("/login")
                                .loginProcessingUrl("/authenticateTheUser")
                                .defaultSuccessUrl("/", true)
                                .permitAll()
                )
                .logout(logout ->
                        logout.logoutSuccessUrl("/login")
                                .permitAll()
                )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
                );

        return http.build();
    }
}