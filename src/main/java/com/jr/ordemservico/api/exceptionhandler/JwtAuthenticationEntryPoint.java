package com.jr.ordemservico.api.exceptionhandler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException { 
            
    		response.setContentType( MediaType.APPLICATION_JSON_VALUE);
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Acesso negado!");

    	
    }
    

}