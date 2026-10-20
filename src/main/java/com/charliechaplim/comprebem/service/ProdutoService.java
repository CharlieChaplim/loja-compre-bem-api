package com.charliechaplim.comprebem.service;

import com.charliechaplim.comprebem.dto.ProdutoRequestDTO;
import com.charliechaplim.comprebem.dto.ProdutoResponseDTO;
import com.charliechaplim.comprebem.entity.Produto;
import com.charliechaplim.comprebem.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public Page<ProdutoResponseDTO> findAll(int page, int size, String busca) {
        int paginaSegura = Math.max(0, page);
        int tamanhoSeguro = Math.min(50, Math.max(1, size));
        // Ordenacao estavel evita itens 'pularem' de pagina entre consultas.
        PageRequest pageable = PageRequest.of(paginaSegura, tamanhoSeguro,
                Sort.by("nome").ascending().and(Sort.by("id").ascending()));
        Page<Produto> produtos = (busca == null || busca.isBlank())
                ? produtoRepository.findAll(pageable)
                : produtoRepository.findByNomeContainingIgnoreCase(busca.trim(), pageable);
        return produtos.map(ProdutoResponseDTO::toDto);
    }

    public ProdutoResponseDTO findById(UUID id) {
        return ProdutoResponseDTO.toDto(findRequired(id));
    }

    private Produto findRequired(UUID id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Produto nao encontrado: " + id));
    }

    public ProdutoResponseDTO save(ProdutoRequestDTO dto) {
        return ProdutoResponseDTO.toDto(produtoRepository.save(Produto.toEntity(dto)));
    }

    public ProdutoResponseDTO update(UUID produtoId, ProdutoRequestDTO dto) {
        Produto existente = findRequired(produtoId);
        existente.setNome(dto.nome());
        existente.setDescricao(dto.descricao());
        existente.setPreco(dto.preco());
        existente.setCategoria(dto.categoria());
        existente.setImagem(dto.imagem());
        return ProdutoResponseDTO.toDto(produtoRepository.save(existente));
    }

    public void delete(UUID produtoId) {
        findRequired(produtoId);
        produtoRepository.deleteById(produtoId);
    }
}
