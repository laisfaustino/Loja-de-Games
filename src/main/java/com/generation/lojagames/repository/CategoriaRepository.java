package com.generation.lojagames.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.lojagames.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

<<<<<<< HEAD
	public List<Categoria> findAllByTipoContainingIgnoreCase(String tipo);
	
=======
	List<Categoria> findAllByTipoContainingIgnoreCase(@Param("tipo") String tipo);
	List<Categoria> findAllByPrecoContainingIgnoreCase(@Param("preco") String preco);
>>>>>>> ed9fa9624c0f7e4bd25d2cd1608d571758296fdc
}
