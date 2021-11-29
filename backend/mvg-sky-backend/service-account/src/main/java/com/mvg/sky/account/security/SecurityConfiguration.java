package com.mvg.sky.account.security;

import com.mvg.sky.account.exception.handler.AccessDeniedHandlerImpl;
import com.mvg.sky.account.exception.handler.UnAuthorizationHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @NonNull
    private final UserDetailsService userDetailsService;
    @NonNull
    private final AccessDeniedHandlerImpl accessDeniedHandlerImpl;
    @NonNull
    private final UnAuthorizationHandler unAuthorizationHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
            .disable();

        http.cors();

        http.formLogin().disable();

        http.exceptionHandling().
            accessDeniedHandler(accessDeniedHandlerImpl);
        http.exceptionHandling()
            .authenticationEntryPoint(unAuthorizationHandler);

        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
            .antMatchers("/accounts/document/**", "/accounts/swagger-ui/**", "/accounts/api-docs/**")
            .permitAll();
        http.authorizeRequests()
            .antMatchers("/accounts/login")
            .permitAll();
        http.authorizeRequests()
            .anyRequest().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
