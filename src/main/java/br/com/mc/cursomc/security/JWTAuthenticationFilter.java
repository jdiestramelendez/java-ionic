package br.com.mc.cursomc.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mc.cursomc.dto.CredenciaisDTO;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private JWTUtils ju;
	
	private AuthenticationManager authman;
	
	
	public JWTAuthenticationFilter(AuthenticationManager authman, JWTUtils ju) {
		super();
    	setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
		this.ju = ju;
		this.authman = authman;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			CredenciaisDTO cdto = new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class);
			System.out.println("AuthentcationFilter: Recebeu: " + cdto.getEmail());
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(cdto.getEmail(), cdto.getSenha(), new ArrayList<>());
			System.out.println("AuthentcationFilter: token: " + token);
			Authentication auth = authman.authenticate(token);
			System.out.println("AuthentcationFilter: auth: " + auth);
			return auth;
		} catch (IOException e) {
			System.out.println("AuthentcationFilter: Erro: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("AuthentcationFilter: principal: " + authResult.getName() + ", " + authResult.getPrincipal());
		String username = ((UserSS) authResult.getPrincipal()).getUsername();
		String token = ju.generateToken(username);
		response.addHeader("Authorization", "Bearer " + token);
	}

	
	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {
		 
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json"); 
            response.getWriter().append(json());
        }
        
        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Não autorizado\", "
                + "\"message\": \"Email ou senha inválidos\", "
                + "\"path\": \"/login\"}";
        }
    }
	
}
