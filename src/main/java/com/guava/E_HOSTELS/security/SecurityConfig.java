package com.guava.E_HOSTELS.security;

import com.guava.E_HOSTELS.users.landlord.Landlord;
import com.guava.E_HOSTELS.users.staff.Staff;
import com.guava.E_HOSTELS.users.tenant.Tenant;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


        private final PasswordEncoder passwordEncoder;

        private final CustomUserDetailsService customUserDetailsService;
        private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

        @Autowired
        public SecurityConfig(PasswordEncoderConfig passwordEncoderConfig, CustomUserDetailsService customUserDetailsService, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
            this.passwordEncoder = passwordEncoderConfig.passwordEncoder();
            this.customUserDetailsService = customUserDetailsService;
            this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        }


        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http )throws Exception {

//            registry.addMapping("/**")
//                    .allowedOrigins("http://localhost:4200")
//                    .allowedMethods("GET","POST","PUT","DELETE","OPTIONS");

            return http
                    .csrf().disable()
                    .formLogin(form->form
                            .loginPage("/login").permitAll()
                            .successHandler(this.customAuthenticationSuccessHandler))
                    .authorizeHttpRequests(
                            auth->auth    // there is an issue with registering the landlord
                                    .requestMatchers("/images/**","/static/**","/register/tenant","/save/tenant").permitAll()

                                    .requestMatchers("/register/landlord", "/save/landlord").hasAnyRole("STAFF", "DIRECTOR")

                                    .requestMatchers( "/register/staff", "/register/director","/director/**").hasRole("DIRECTOR")
                                    .requestMatchers("/save/staff", "/save/director").hasRole("DIRECTOR")
                                    .requestMatchers("/landlord/**").hasRole("LANDLORD")
                                    .requestMatchers("/tenant/**").hasRole("TENANT")
                                    .requestMatchers("/staff/**").hasRole("STAFF")
                                    .anyRequest().authenticated()
                                    .and()
                                    .authenticationProvider(this.authenticationProvider()))
                    .build();
        }

        @Bean
        AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
            return configuration.getAuthenticationManager();
        }

        @Bean
        AuthenticationProvider authenticationProvider(){
            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setPasswordEncoder(this.passwordEncoder);
            authenticationProvider.setUserDetailsService(this.customUserDetailsService);
            return authenticationProvider;
        }
    }

