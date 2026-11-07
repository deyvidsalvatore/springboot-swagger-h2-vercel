package br.com.exemplo.demo.controller;

import br.com.exemplo.demo.dto.ProdutoRequest;
import br.com.exemplo.demo.model.Produto;
import br.com.exemplo.demo.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "Operações CRUD de produtos")
public class ProdutoController {

    private final ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Lista todos os produtos")
    public List<Produto> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um produto pelo ID")
    public Produto buscar(@PathVariable Long id) {
        return buscarOu404(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria um produto")
    public Produto criar(@Valid @RequestBody ProdutoRequest request) {
        return repository.save(new Produto(request.nome(), request.preco()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um produto")
    public Produto atualizar(@PathVariable Long id,
                             @Valid @RequestBody ProdutoRequest request) {
        Produto produto = buscarOu404(id);
        produto.setNome(request.nome());
        produto.setPreco(request.preco());
        return repository.save(produto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui um produto")
    public void excluir(@PathVariable Long id) {
        Produto produto = buscarOu404(id);
        repository.delete(produto);
    }

    private Produto buscarOu404(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }
}
