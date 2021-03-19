package br.com.mc.cursomc.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.mc.cursomc.domain.Categoria;
import br.com.mc.cursomc.domain.Produto;

@Repository
public interface ProdutoDAO extends JpaRepository<Produto, Integer> {
	//@Transactional(readOnly = true)
	//@Query("SELECT DISTINCT prod FROM Produto prod INNER JOIN prod.categorias cat WHERE prod.nome like %:nome% AND cat IN :categorias")
	//Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pr);
	
	@Transactional(readOnly = true)
	Page<Produto> findDistinctByNomeContainingIgnoreCaseAndCategoriasIn(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pr);
	
}
