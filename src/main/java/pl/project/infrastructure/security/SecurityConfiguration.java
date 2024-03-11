package pl.project.infrastructure.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pl.project.api.controller.addresses.HomeAddresses;

import java.util.Map;

@Configuration
public class SecurityConfiguration {

    private  final String AUTHORITY_ALL = "ALL";
    private  final String AUTHORITY_CUSTOMER = "CUSTOMER";
    private  final String AUTHORITY_RESTAURANT_OWNER = "RESTAURANT_OWNER";
    private  final String AUTHORITY_DELIVERY_MAN = "DELIVERY_MAN";
    private  final String AUTHORITY_REST_API = "REST_API";
    private  final String AUTHORITY_ADMIN = "ADMIN";

    private final Map<String, String[]> requestMatcherPatterns = Map.of(
            AUTHORITY_ALL, new String[] {"/", "/login", "/error", "/registration/**"},
            AUTHORITY_CUSTOMER, new String[] {"/customer/**"},
            AUTHORITY_RESTAURANT_OWNER, new String[] {"/restaurant_owner/**", "/images/**"},
            AUTHORITY_DELIVERY_MAN, new String[] {"/delivery_man/**"},
            AUTHORITY_REST_API, new String[] {"/rest_api/**"},
            AUTHORITY_ADMIN, new String[] {"/admin/**"}
    );

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService
    ){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authManager(
            HttpSecurity http,
            AuthenticationProvider authenticationProvider
    ) throws Exception{
        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider)
                .build();
    }

    @Bean
    @ConditionalOnProperty(value = "spring.security.enabled", havingValue = "true", matchIfMissing = true)
    public SecurityFilterChain securityEnabled(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(requestMatcherPatterns.get(AUTHORITY_ALL)).permitAll()
                        .requestMatchers(requestMatcherPatterns.get(AUTHORITY_CUSTOMER)).hasAnyAuthority(AUTHORITY_CUSTOMER)
                        .requestMatchers(requestMatcherPatterns.get(AUTHORITY_RESTAURANT_OWNER)).hasAnyAuthority(AUTHORITY_RESTAURANT_OWNER)
                        .requestMatchers(requestMatcherPatterns.get(AUTHORITY_DELIVERY_MAN)).hasAnyAuthority(AUTHORITY_DELIVERY_MAN)
                        .requestMatchers(requestMatcherPatterns.get(AUTHORITY_REST_API)).hasAnyAuthority(AUTHORITY_REST_API)
                        .requestMatchers(requestMatcherPatterns.get(AUTHORITY_ADMIN)).hasAnyAuthority(AUTHORITY_ADMIN)
                )
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .logout(logout -> logout
                        .logoutSuccessUrl(HomeAddresses.HOME)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .build();
    }
}
