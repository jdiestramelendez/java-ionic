package br.com.mc.cursomc.domain;

import java.io.Serializable;

public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private String descricao;
	private double preco;
	
	public ProdutoDTO() {
		super();
	}

	public ProdutoDTO(Produto prod) {
		this.nome = prod.getNome();
		this.id = prod.getId();
		this.descricao = prod.getDescricao();
		this.preco = prod.getPreco();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	
	
}
