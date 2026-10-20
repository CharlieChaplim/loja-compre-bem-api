package com.charliechaplim.comprebem.repository;

import com.charliechaplim.comprebem.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
    // Spring Data JPA gera busca por nome sem diferenciar maiusculas de minusculas.
    Page<Produto> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    boolean existsByNomeIgnoreCase(String nome);
}
