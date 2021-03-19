package br.com.mc.cursomc.domain.enums;

public enum EstadoPagamento {
	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");
	

	
	private Integer codigo;
	private String descricao;

	EstadoPagamento(int i, String s) {
		this.codigo = i;
		this.descricao = s;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static EstadoPagamento getEstadoPagamento(Integer cod) {
		if (cod == null) return null;
		for (EstadoPagamento tc : EstadoPagamento.values()) {
			if (tc.getCodigo().equals(cod)) return tc;
		}
		throw new IllegalArgumentException("Código do estado do pagamento inválido: " + null);
	}
}
