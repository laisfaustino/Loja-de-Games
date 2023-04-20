package com.generation.lojagames.controller;

import java.util.List;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.lojagames.model.Categoria;
import com.generation.lojagames.repository.CategoriaRepository;
import com.generation.lojagames.repository.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

	@Autowired
    private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
    
    @GetMapping
    public ResponseEntity<List<Categoria>> getAll(){
        return ResponseEntity.ok(categoriaRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getById(@PathVariable Long id){
        return categoriaRepository.findById(id)
            .map(resposta -> ResponseEntity.ok(resposta))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @GetMapping("/tipo/{tipo}")
	public ResponseEntity<List<Categoria>> getByTitulo(@PathVariable String tipo){
		
		return ResponseEntity.ok(categoriaRepository.findAllByTipoContainingIgnoreCase(tipo));
		
		// SELECT * FROM tb_postagens WHERE titulo LIKE "%titulo%";
    }
 
	
    @PostMapping
	public ResponseEntity<Categoria> post(@Valid @RequestBody Categoria categoria){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(categoriaRepository.save(categoria));
		
		/* INSERT INTO tb_postagens (data, titulo, texto) 
		 VALUES (?, ?, ?)*/
	}
	
    @PutMapping
	public ResponseEntity<Categoria> put(@Valid @RequestBody Categoria categoria){
		
		return produtoRepository.findById(categoria.getId())
				.map(resposta -> ResponseEntity.ok(categoriaRepository.save(categoria)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
		
		/* UPDATE tb_postagens SET titulo = ?, texto = ?, data = ?
		 * WHERE id = id*/
	}
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		
		if (categoria.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		categoriaRepository.deleteById(id);
		
		/* DELETE FROM tb_postagens WHERE id = id*/
	}

	public ProdutoRepository getprodutoRepository() {
		return getprodutoRepository();
	}

	public void setTemaRepository(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
}
