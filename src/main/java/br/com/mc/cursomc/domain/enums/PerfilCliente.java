package br.com.mc.cursomc.domain.enums;

public enum PerfilCliente {

	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");
	
	private Integer codigo;
	private String descricao;

	PerfilCliente(int i, String s) {
		this.codigo = i;
		this.descricao = s;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static PerfilCliente getPerfilCliente(Integer cod) {
		if (cod == null) return null;
		for (PerfilCliente pc : PerfilCliente.values()) {
			if (pc.getCodigo().equals(cod)) return pc;
		}
		throw new IllegalArgumentException("Código do perfil de cliente inválido: " + null);
	}
}
