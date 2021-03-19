package br.com.mc.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.mc.cursomc.dao.ClienteDAO;
import br.com.mc.cursomc.domain.Cliente;
import br.com.mc.cursomc.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClienteDAO dao;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente c = dao.findByEmail(email);
		System.out.println("UserDetailService: cliente: " + c);
		if (c == null) {
			throw new UsernameNotFoundException(email);
		}
		UserSS u = new UserSS(c.getId(), c.getEmail(), c.getSenha(), c.getPerfis());
		return u;
	}

}
