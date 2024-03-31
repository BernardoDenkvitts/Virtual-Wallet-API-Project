package com.virtualwallet.demo.Configuration.Security;

import com.virtualwallet.demo.Model.User.Roles;
import com.virtualwallet.demo.Repository.UserRepository;
import jakarta.ws.rs.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
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


@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    private final SecurityFilter securityFilter;
    @Autowired
    UserRepository userRepository;
    public SecurityConfigurations(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder enconderPassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        String transactionEndpoint = "/v1/transaction/**";
        String userEndpoint = "/v1/user/*";
        String authEndpoint = "/v1/auth/**";
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionMng -> sessionMng.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((it) ->
                        it.requestMatchers(authEndpoint)
                                .permitAll()

                                .requestMatchers(HttpMethod.POST, HttpMethod.GET, transactionEndpoint)
                                    .hasAnyRole(Roles.USER.name(), Roles.ADMIN.name())

                                .requestMatchers(HttpMethod.GET, userEndpoint)
                                    .hasAnyRole(Roles.USER.name(), Roles.ADMIN.name())

                                .anyRequest()
                                .authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
