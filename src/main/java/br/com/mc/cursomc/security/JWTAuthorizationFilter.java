package br.com.mc.cursomc.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private JWTUtils ju;
	
	private UserDetailsService uds;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtils ju, UserDetailsService uds) {
		super(authenticationManager);
		this.ju = ju;
		this.uds = uds;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String hauth = request.getHeader("Authorization");
		System.out.println("AuthorizationFilter: Authorization: " + hauth);
		
		if (hauth != null && hauth.startsWith("Bearer ")) {
			UsernamePasswordAuthenticationToken token = getAuthentication(request, hauth.substring(7));
			System.out.println("AuthorizationFilter: token: " + token);
			if (token != null) {
				SecurityContextHolder.getContext().setAuthentication(token);
			}
		}
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, String token) {
		if (ju.tokenValido(token)) {
			System.out.println("AuthorizationFilter: token valido");
			String username = ju.getUsername(token);
			System.out.println("AuthorizationFilter: username: " + username);
			UserDetails user = uds.loadUserByUsername(username);
			System.out.println("AuthorizationFilter: user: " + user);
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		return null;
	}
}
