package br.com.mc.cursomc.domain.enums;

public enum TipoCliente {
	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private Integer codigo;
	private String descricao;

	TipoCliente(int i, String s) {
		this.codigo = i;
		this.descricao = s;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static TipoCliente getTipoCliente(Integer cod) {
		if (cod == null) return null;
		for (TipoCliente tc : TipoCliente.values()) {
			if (tc.getCodigo().equals(cod)) return tc;
		}
		throw new IllegalArgumentException("Código do tipo de cliente inválido: " + null);
	}
}
