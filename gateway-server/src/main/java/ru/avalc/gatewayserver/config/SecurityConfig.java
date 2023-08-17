package ru.avalc.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author Alexei Valchuk, 15.08.2023, email: a.valchukav@gmail.com
 */

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        httpSecurity.authorizeExchange(authorizeExchangeSpec ->
                authorizeExchangeSpec
                        .anyExchange().authenticated()).oauth2ResourceServer().jwt();

        httpSecurity.csrf().disable();

        return httpSecurity.build();
    }
}
