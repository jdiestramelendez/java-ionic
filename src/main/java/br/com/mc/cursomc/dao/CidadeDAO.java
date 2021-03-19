package br.com.mc.cursomc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mc.cursomc.domain.Cidade;

@Repository
public interface CidadeDAO extends JpaRepository<Cidade, Integer> {

}
