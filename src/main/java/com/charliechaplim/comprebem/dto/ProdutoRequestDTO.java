package com.charliechaplim.comprebem.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProdutoRequestDTO(
        @NotBlank String nome,
        String descricao,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal preco,
        String categoria,
        String imagem,
        @Min(0) Integer quantidade
) {}
