package br.com.mc.cursomc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mc.cursomc.domain.Endereco;

@Repository
public interface EnderecoDAO extends JpaRepository<Endereco, Integer> {

}
