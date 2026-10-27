package com.charliechaplim.comprebem.entity;

import com.charliechaplim.comprebem.dto.ProdutoRequestDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_PRODUTO")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column
    private String categoria;

    @Column(length = 500)
    private String descricao;

    @Column
    private String imagem;

    @Column(nullable = false)
    private Integer quantidade = 0;

    public static Produto toEntity(ProdutoRequestDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setCategoria(dto.categoria());
        produto.setImagem(dto.imagem());
        produto.setQuantidade(dto.quantidade() == null ? 0 : dto.quantidade());
        return produto;
    }
}
