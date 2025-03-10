package com.medMais.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
	
	@Autowired
    private SecurityFilter securityFilter;
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return 
              http.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                	req.requestMatchers("/admin", "/admin/**").hasAnyAuthority("ADMIN");
                    req.requestMatchers("/paciente/register").permitAll();
                    req.requestMatchers("/pessoa/login").permitAll();
                    req.requestMatchers("/auth/**").permitAll();
                    req.requestMatchers("/medico/especialidade").permitAll();
                    req.requestMatchers("/medico/**").hasAnyAuthority("MEDICO");
                    req.requestMatchers("/pessoa/atualizar","/endereco/**","/historicodoenca/**","/consulta/**").hasAnyAuthority("ADMIN","PACIENTE","MEDICO");
                    req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**","/historicotransacoes/**").permitAll();
                    req.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
