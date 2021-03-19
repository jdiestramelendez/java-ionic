package br.com.mc.cursomc.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mc.cursomc.dao.ItemPedidoDAO;
import br.com.mc.cursomc.dao.PagamentoDAO;
import br.com.mc.cursomc.dao.PedidoDAO;
import br.com.mc.cursomc.domain.ItemPedido;
import br.com.mc.cursomc.domain.PagamentoBoleto;
import br.com.mc.cursomc.domain.Pedido;
import br.com.mc.cursomc.domain.enums.EstadoPagamento;
import br.com.mc.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoDAO dao;
	
	@Autowired
	private PagamentoDAO pagdao;
	
	@Autowired
	private BoletoService bolserv;
	
	@Autowired
	private ClienteService cliserv;
	
	@Autowired
	private ProdutoService prodserv;
	
	@Autowired
	private ItemPedidoDAO itemdao;
	
	@Autowired
	private EmailService emailserv;
	
	
	
	public Pedido buscar(Integer id) {
		 Optional<Pedido> obj = dao.findById(id);
		 return obj.orElseThrow(() -> new ObjectNotFoundException(
		  "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	public List<Pedido> todos() {
		return dao.findAll();
	}
	
	public Pedido criar(Pedido ped) {
		return dao.save(ped);
	}

	@Transactional
	public Pedido insert(@Valid Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.setCliente(cliserv.find(pedido.getCliente().getId()));
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		if (pedido.getPagamento() instanceof PagamentoBoleto) {
			PagamentoBoleto pagbol = (PagamentoBoleto) pedido.getPagamento();
			bolserv.preencherPagamentoBoleto(pagbol);
		}
		
		pedido = dao.save(pedido);
		pagdao.save(pedido.getPagamento());
		
		for (ItemPedido item : pedido.getItens()) {
			item.setDesconto(0);
			item.setProduto(prodserv.buscar(item.getProduto().getId()));
			item.setPreco(item.getProduto().getPreco());
			item.setPedido(pedido);
		}
		
		itemdao.saveAll(pedido.getItens());
		System.out.println("Inseriu Pedido: " + pedido);
		emailserv.sendOrderConfirmationHtmlEmail(pedido);
		return pedido;
	}
}
