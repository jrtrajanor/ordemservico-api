package com.jr.ordemservico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.jr.ordemservico.domain.util.Constantes;
import com.jr.ordemservico.domain.util.security.JwtTokenAuthorizationOncePerRequestFilter;
import com.jr.ordemservico.domain.util.security.JwtUnAuthorizedResponseAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private JwtUnAuthorizedResponseAuthenticationEntryPoint jwtUnAuthorizedResponseAuthenticationEntryPoint;

    @Autowired
    private JwtTokenAuthorizationOncePerRequestFilter jwtAuthenticationTokenFilter;

    @Value("${jwt.get.token.uri}")
    private String authenticationPath;
    

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
       
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(jwtUnAuthorizedResponseAuthenticationEntryPoint).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            .antMatchers("/", "/index", "/login", "/about", "/signin", "/logout", "/resources/**", "/webjars/**").permitAll()
    		.antMatchers("/home").hasAnyRole(Constantes.PERMISSAO_ADMINISTRADOR, Constantes.PERMISSAO_USUARIO)
    		.antMatchers("/usuarios/**").hasAnyRole(Constantes.PERMISSAO_ADMINISTRADOR, Constantes.PERMISSAO_USUARIO)
    		.antMatchers("/clientes/**").hasAnyRole(Constantes.PERMISSAO_ADMINISTRADOR, Constantes.PERMISSAO_USUARIO)
    		.antMatchers("/ordens-servico/**").hasAnyRole(Constantes.PERMISSAO_ADMINISTRADOR, Constantes.PERMISSAO_USUARIO)
    		
    		.antMatchers("/items/**").hasAnyRole(Constantes.PERMISSAO_ADMINISTRADOR, Constantes.PERMISSAO_USUARIO)
    		    		
            .and()
            .logout()
            .logoutUrl("/j_spring_security_logout"); 
            //.anyRequest().authenticated();

       httpSecurity
            .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        
        httpSecurity
            .headers()
            .frameOptions().sameOrigin()  //H2 Console Needs this setting
            .cacheControl(); //disable caching
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity
            .ignoring()
            .antMatchers(
                HttpMethod.POST,
                authenticationPath
            )
            .antMatchers(HttpMethod.OPTIONS, "/**")
            
            .and()
            .ignoring()
            .antMatchers(
                HttpMethod.GET,
                "/" //Other Stuff You want to Ignore
            )
            .and()
            .ignoring()
            .antMatchers("/h2-console/**/**");//Should not be in Production!
    }

    //@Bean
    //public AuthenticationManager customAuthenticationManager() throws Exception {
    //    return authenticationManager();
    //}
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
    
}