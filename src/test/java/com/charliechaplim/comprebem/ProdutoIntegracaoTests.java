package com.charliechaplim.comprebem;

import com.charliechaplim.comprebem.dto.ProdutoRequestDTO;
import com.charliechaplim.comprebem.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProdutoIntegracaoTests {
    @Autowired ProdutoService service;

    @Test
    void devePersistirQuantidadeNoCadastroEAtualizacao() {
        var criar = new ProdutoRequestDTO("Teste estoque", "Teste", new BigDecimal("15.50"),
                "Testes", null, 7);
        var salvo = service.save(criar);
        try {
            assertEquals(7, service.findById(salvo.produtoId()).quantidade().intValue());
            var editar = new ProdutoRequestDTO("Teste estoque atualizado", "Teste", new BigDecimal("18.50"),
                    "Testes", null, 12);
            assertEquals(12, service.update(salvo.produtoId(), editar).quantidade().intValue());
            var semQuantidade = new ProdutoRequestDTO("Teste legado", "Teste", new BigDecimal("20.00"),
                    "Testes", null, null);
            assertEquals(12, service.update(salvo.produtoId(), semQuantidade).quantidade().intValue());
        } finally {
            service.delete(salvo.produtoId());
        }
    }
}
