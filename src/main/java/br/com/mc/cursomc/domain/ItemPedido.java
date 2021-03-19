package br.com.mc.cursomc.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemPedido implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@EmbeddedId
	private ItemPedidoPK id = new ItemPedidoPK();
	
	private double desconto;
	private Integer quantidade;
	private double preco;
	
	
	
	public ItemPedido() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public ItemPedido(Pedido pedido, Produto produto, double desconto, Integer quantidade, double preco) {
		super();
		this.id.setPedido(pedido);
		this.id.setProduto(produto);
		this.desconto = desconto;
		this.quantidade = quantidade;
		this.preco = preco;
	}

	@JsonIgnore
	public Pedido getPedido() {
		return this.id.getPedido();
	}
	
	public void setPedido(Pedido pedido) {
		this.id.setPedido(pedido);
	}

	public Produto getProduto() {
		return this.id.getProduto();
	}
	
	public void setProduto(Produto produto) {
		this.id.setProduto(produto);
	}

	public ItemPedidoPK getId() {
		return id;
	}
	public void setId(ItemPedidoPK id) {
		this.id = id;
	}
	public double getDesconto() {
		return desconto;
	}
	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	public double getSubTotal() {
		return this.getQuantidade() * (this.getPreco() - this.getDesconto());
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		StringBuilder builder = new StringBuilder();
		builder.append(getProduto().getNome());
		builder.append(", quantidade: ");
		builder.append(this.getQuantidade());
		builder.append(", preço unitário: ");
		builder.append(nf.format(this.getPreco()));
		builder.append(", subtotal: ");
		builder.append(nf.format(this.getSubTotal()));
		builder.append("\n");
		return builder.toString();
	}
	
	
}
