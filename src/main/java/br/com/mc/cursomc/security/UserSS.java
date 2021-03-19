package br.com.mc.cursomc.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.mc.cursomc.domain.enums.PerfilCliente;

public class UserSS implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private Integer id;

	private String email;
	private String senha;
	private Collection<? extends GrantedAuthority> authorities;

	
	
	public UserSS() {
		super();
	}

	public UserSS(Integer id, String email, String senha, Collection<PerfilCliente> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.authorities = perfis.stream().map(p -> new SimpleGrantedAuthority(p.getDescricao())).collect(Collectors.toList());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
