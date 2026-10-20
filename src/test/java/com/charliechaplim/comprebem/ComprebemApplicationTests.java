package com.charliechaplim.comprebem;

import com.charliechaplim.comprebem.repository.ProdutoRepository;
import com.charliechaplim.comprebem.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ComprebemApplicationTests {
    @Autowired ProdutoRepository repository;
    @Autowired ProdutoService service;

    @Test
    void deveCriarPeloMenosSessentaProdutos() {
        assertTrue(repository.count() >= 60);
    }

    @Test
    void devePaginarComVintePorPadrao() {
        var pagina = service.findAll(0, 20, null);
        assertEquals(20, pagina.getSize());
        assertEquals(20, pagina.getNumberOfElements());
        assertTrue(pagina.getTotalElements() >= 60);
    }

    @Test
    void deveLimitarSizeEmCinquenta() {
        var pagina = service.findAll(0, 500, null);
        assertEquals(50, pagina.getSize());
        assertEquals(50, pagina.getNumberOfElements());
    }

    @Test
    void deveFiltrarSemDiferenciarMaiusculasEMinusculas() {
        var pagina = service.findAll(0, 20, "cAdEiRa");
        assertFalse(pagina.isEmpty());
        assertTrue(pagina.getContent().stream()
                .allMatch(item -> item.nome().toLowerCase().contains("cadeira")));
    }

    @Test
    void deveRetornar404ParaIdInexistente() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> service.findById(UUID.randomUUID()));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void deveConsultarProdutoExistente() {
        var produto = repository.findAll().get(0);
        assertEquals(produto.getId(), service.findById(produto.getId()).produtoId());
    }
}
