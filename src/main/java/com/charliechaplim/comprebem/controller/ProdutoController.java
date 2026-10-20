package com.charliechaplim.comprebem.controller;

import com.charliechaplim.comprebem.dto.ProdutoRequestDTO;
import com.charliechaplim.comprebem.dto.ProdutoResponseDTO;
import com.charliechaplim.comprebem.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    private final ProdutoService produtoService;

    @Operation(summary = "Listar produtos paginados, com busca opcional por nome")
    @GetMapping
    public Page<ProdutoResponseDTO> findAll(
            @Parameter(description = "Pagina iniciando em 0. Ex.: page=2 e a terceira pagina")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Produtos por pagina: padrao 20, maximo 50")
            @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Trecho do nome, sem diferenciar maiusculas e minusculas")
            @RequestParam(required = false) String busca) {
        return produtoService.findAll(page, size, busca);
    }

    @Operation(summary = "Consultar produto pelo UUID")
    @ApiResponse(responseCode = "200", description = "Produto encontrado")
    @ApiResponse(responseCode = "404", description = "Produto nao encontrado")
    @GetMapping("/{id}")
    public ProdutoResponseDTO findById(@PathVariable UUID id) {
        return produtoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponseDTO save(@Valid @RequestBody ProdutoRequestDTO produto) {
        return produtoService.save(produto);
    }

    @PutMapping("/{produtoId}")
    public ProdutoResponseDTO update(@PathVariable UUID produtoId,
                                     @Valid @RequestBody ProdutoRequestDTO produto) {
        return produtoService.update(produtoId, produto);
    }

    @DeleteMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID produtoId) {
        produtoService.delete(produtoId);
    }
}
