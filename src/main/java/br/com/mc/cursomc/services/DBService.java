package br.com.mc.cursomc.services;

import java.util.Arrays;
import java.util.Date;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.mc.cursomc.dao.CategoriaDAO;
import br.com.mc.cursomc.dao.CidadeDAO;
import br.com.mc.cursomc.dao.ClienteDAO;
import br.com.mc.cursomc.dao.EnderecoDAO;
import br.com.mc.cursomc.dao.EstadoDAO;
import br.com.mc.cursomc.dao.ItemPedidoDAO;
import br.com.mc.cursomc.dao.PagamentoDAO;
import br.com.mc.cursomc.dao.PedidoDAO;
import br.com.mc.cursomc.dao.ProdutoDAO;
import br.com.mc.cursomc.domain.Categoria;
import br.com.mc.cursomc.domain.Cidade;
import br.com.mc.cursomc.domain.Cliente;
import br.com.mc.cursomc.domain.Endereco;
import br.com.mc.cursomc.domain.Estado;
import br.com.mc.cursomc.domain.ItemPedido;
import br.com.mc.cursomc.domain.Pagamento;
import br.com.mc.cursomc.domain.PagamentoBoleto;
import br.com.mc.cursomc.domain.PagamentoCartao;
import br.com.mc.cursomc.domain.Pedido;
import br.com.mc.cursomc.domain.Produto;
import br.com.mc.cursomc.domain.enums.EstadoPagamento;
import br.com.mc.cursomc.domain.enums.PerfilCliente;
import br.com.mc.cursomc.domain.enums.TipoCliente;
import br.com.mc.cursomc.utils.TestUtils;

@Service
public class DBService {

	@Autowired
	CategoriaDAO catdao;
	@Autowired
	ProdutoDAO proddao;
	@Autowired
	CidadeDAO ciddao;
	@Autowired
	EstadoDAO estdao;
	@Autowired
	EnderecoDAO enddao;
	@Autowired
	ClienteDAO clidao;
	@Autowired
	PedidoDAO peddao;
	@Autowired
	PagamentoDAO pagdao;
	@Autowired
	ItemPedidoDAO itemdao;
	
	@Autowired
	private BCryptPasswordEncoder passEnc;
	

	public void initTestDB() throws Exception {

		TestUtils.logDestacado("DBService.initTestDatabase");
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Ferramentas");
		Categoria cat4 = new Categoria(null, "Malas");
		Categoria cat5 = new Categoria(null, "Acessórios");
		Categoria cat6 = new Categoria(null, "Celulares");
		Categoria cat7 = new Categoria(null, "Programas");
		Categoria cat8 = new Categoria(null, "Móveis");
		Categoria cat9 = new Categoria(null, "Limpeza");
		Categoria cat10 = new Categoria(null, "Decoração");
		
		Produto p1 = new Produto(null, "Computador", "Computador Desktop completo", 2599.99);
		addCategorias(p1, cat1);
		Produto p2 = new Produto(null, "Impressora", "Impressora jato de tinta", 349.99);
		addCategorias(p2, cat1, cat2);
		Produto p3 = new Produto(null, "Mouse", "Mouse ótico com fio, USB", 24.99);
		addCategorias(p3, cat1);
		Produto p4 = new Produto(null, "Monitor 22'", "Monitor Full HD 22'", 299.99);
		addCategorias(p4, cat1);
		Produto p5 = new Produto(null, "Cadeira de Escritório", "Cadeira de escritório com ajuste de altura e inclinação", 249.99);
		addCategorias(p5, cat2, cat8, cat10);
		Produto p6 = new Produto(null, "Mochila Notebook", "Mochila em couro para notebook e acessórios", 99.99);
		addCategorias(p6, cat4, cat5, cat1);
		Produto p7 = new Produto(null, "Luminária LED USB", "Luminária LED USB", 39.99);
		addCategorias(p7, cat1, cat5, cat10);
		
		catdao.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8, cat9, cat10));
		proddao.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7));
		
		TreeMap<String, String> estados = new TreeMap<>();
		estados.put("AC", "Acre");
		estados.put("AL", "Alagoas");
		estados.put("AP", "Amapá");
		estados.put("AM", "Amazonas");
		estados.put("BA", "Bahia");
		estados.put("CE", "Ceará");
		estados.put("DF", "Distrito Federal");
		estados.put("ES", "Espírito Santo");
		estados.put("GO", "Goiás");
		estados.put("MA", "Maranhão");
		estados.put("MT", "Mato Grosso");
		estados.put("MS", "Mato Grosso do Sul");
		estados.put("MG", "Minas Gerais");
		estados.put("PA", "Pará");
		estados.put("PB", "Paraíba");
		estados.put("PR", "Paraná");
		estados.put("PE", "Pernambuco");
		estados.put("PI", "Piauí");
		estados.put("RJ", "Rio de Janeiro");
		estados.put("RN", "Rio Grande do Norte");
		estados.put("RS", "Rio Grande do Sul");
		estados.put("RO", "Rondônia");
		estados.put("RR", "Roraima");
		estados.put("SC", "Santa Catarina");
		estados.put("SP", "São Paulo");
		estados.put("SE", "Sergipe");
		estados.put("TO", "Tocantins");
		

		TreeMap<String, Estado> estInst = new TreeMap<>();
		
		for (String uf : estados.keySet()) {
			Estado est = new Estado(null, uf, estados.get(uf));
			estInst.put(uf, est);
		}

		Cidade cid1 = new Cidade(null, "São Paulo", estInst.get("SP"));
		Cidade cid2 = new Cidade(null, "Campinas", estInst.get("SP"));
		Cidade cid3 = new Cidade(null, "Santos", estInst.get("SP"));
		Cidade cid4 = new Cidade(null, "Araraquara", estInst.get("SP"));
		estInst.get("SP").getCidades().addAll(Arrays.asList(cid1, cid2, cid3, cid4));

		Cidade cid5 = new Cidade(null, "Belo Horizonte", estInst.get("MG"));
		Cidade cid6 = new Cidade(null, "Juiz de Fora", estInst.get("MG"));
		Cidade cid7 = new Cidade(null, "Três Corações", estInst.get("MG"));
		Cidade cid8 = new Cidade(null, "Uberlândia", estInst.get("MG"));
		estInst.get("MG").getCidades().addAll(Arrays.asList(cid5, cid6, cid7, cid8));
		
		Cidade cid9 = new Cidade(null, "Rio de Janeiro", estInst.get("RJ"));
		Cidade cid10 = new Cidade(null, "Niterói", estInst.get("RJ"));
		Cidade cid11 = new Cidade(null, "São Gonçalo", estInst.get("RJ"));
		Cidade cid12 = new Cidade(null, "Nova Iguaçu", estInst.get("RJ"));
		estInst.get("RJ").getCidades().addAll(Arrays.asList(cid9, cid10, cid11, cid12));
		
		estdao.saveAll(estInst.values());
		ciddao.saveAll(estInst.get("SP").getCidades());
		ciddao.saveAll(estInst.get("MG").getCidades());
		ciddao.saveAll(estInst.get("RJ").getCidades());
		
		
		Cliente cli1 = new Cliente(null, "José Antônio Santos", "jas@hotmail.com", "11111111111", TipoCliente.PESSOAFISICA, passEnc.encode("1234"));
		cli1.getTelefones().addAll(Arrays.asList("(11) 99999-9999", "(11) 2323-2323"));

		Endereco end1 = new Endereco(null, "Rua Um", "1", null, "Bairro 1", "01111-001", cid11, cli1);
		Endereco end2 = new Endereco(null, "Rua Dois", "2", null, "Bairro 2", "02222-002", cid12, cli1);
		
		cli1.getEnderecos().add(end1);
		cli1.getEnderecos().add(end2);
		
		
		Cliente cli2 = new Cliente(null, "Marcos Correia", "mc021970@bol.com.br", "22222222222", TipoCliente.PESSOAFISICA, passEnc.encode("1q2w3e4r"));
		cli2.getTelefones().addAll(Arrays.asList("(11) 98888-8888", "(11) 2222-2222"));
		cli2.addPerfil(PerfilCliente.ADMIN);
		Endereco end3 = new Endereco(null, "Rua Três", "3", null, "Bairro 3", "03333-003", cid1, cli2);
		
		cli2.getEnderecos().add(end3);
		
		clidao.saveAll(Arrays.asList(cli1, cli2));
		enddao.saveAll(Arrays.asList(end1, end2, end3));
		
		
		Pedido ped1 = new Pedido(null, new Date(), cli1, end2);
		Pagamento pag1 = new PagamentoBoleto(null, EstadoPagamento.PENDENTE, ped1, new Date(System.currentTimeMillis() + 24*60*60*1000), null);
		ped1.setPagamento(pag1); 
		
		
		Pedido ped2 = new Pedido(null, new Date(System.currentTimeMillis() - 2*60*60*1000), cli1, end2);
		Pagamento pag2 = new PagamentoCartao(null, EstadoPagamento.QUITADO, ped2, 5);
		ped2.setPagamento(pag2);
		
		cli1.getPedidos().add(ped1);
		cli1.getPedidos().add(ped2);
		
		peddao.saveAll(Arrays.asList(ped1, ped2));
		pagdao.saveAll(Arrays.asList(pag1, pag2));
		
		
		ItemPedido item1 = new ItemPedido(ped1, p1, 0, 1, p1.getPreco());
		ItemPedido item2 = new ItemPedido(ped1, p2, 0.1, 1, p2.getPreco());
		ItemPedido item3 = new ItemPedido(ped1, p3, 0, 2, p3.getPreco());
		
		ItemPedido item4 = new ItemPedido(ped2, p2, 0.05, 2, p2.getPreco());
		
		ped1.getItens().addAll(Arrays.asList(item1, item2, item3));
		ped2.getItens().addAll(Arrays.asList(item4));
		System.out.println("Ped1 Total: " + ped1.getTotal());
		System.out.println("Ped2 Total: " + ped2.getTotal());
		
		p1.getItens().add(item1);
		p2.getItens().addAll(Arrays.asList(item2, item4));
		p3.getItens().add(item3);
		
		System.out.println(ped1);
		System.out.println(ped2);
		
		itemdao.saveAll(Arrays.asList(item1, item2, item3, item4));
	}
	

	
	public void addCategorias(Produto p, Categoria... cats) {
		for (Categoria c : cats) {
			p.getCategorias().add(c);
			c.getProdutos().add(p);
		}
	}

}
