package com.generation.lojagames.controller;

import java.math.BigDecimal;

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

import com.generation.lojagames.model.Produto;
import com.generation.lojagames.repository.CategoriaRepository;
import com.generation.lojagames.repository.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

	
	@Autowired
    private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	
	@GetMapping
	public ResponseEntity<List<Produto>> getAll(){
		return ResponseEntity.ok(produtoRepository.findAll());

		// SELECT * FROM tb_postagens;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id){
		
		return produtoRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
		
		// SELECT * FROM tb_postagens WHERE id = ?;
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome){
		
		return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
		
		// SELECT * FROM tb_postagens WHERE titulo LIKE "%titulo%";
	}
	
	@PostMapping
	public ResponseEntity<Produto> post(@Valid @RequestBody Produto produto){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(produtoRepository.save(produto));
		
		/* INSERT INTO tb_postagens (data, titulo, texto) 
		 VALUES (?, ?, ?)*/
	}
	
	@PutMapping
	public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto){
		
		return produtoRepository.findById(produto.getId())
				.map(resposta -> ResponseEntity.ok(produtoRepository.save(produto)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
		
		/* UPDATE tb_postagens SET titulo = ?, texto = ?, data = ?
		 * WHERE id = id*/
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		
		Optional<Produto> produto = produtoRepository.findById(id);
		
		if (produto.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		produtoRepository.deleteById(id);
		
		/* DELETE FROM tb_postagens WHERE id = id*/
	}

	public CategoriaRepository getCategoriaRepository() {
		return categoriaRepository;
	}

	public void setTemaRepository(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}
	
	@GetMapping("/preco_maior/{preco}")
	public ResponseEntity<List<Produto>> getPrecoMaiorQue(@PathVariable BigDecimal preco){ 
		return ResponseEntity.ok(produtoRepository.findAllByPrecoGreaterThanOrderByPreco(preco));
	}
	
	// Consulta pelo preço menor do que o preço digitado em ordem decrescente
	
	@GetMapping("/preco_menor/{preco}")
	public ResponseEntity<List<Produto>> getPrecoMenorQue(@PathVariable BigDecimal preco){ 
		return ResponseEntity.ok(produtoRepository.findAllByPrecoLessThanOrderByPrecoDesc(preco));
	}


}
