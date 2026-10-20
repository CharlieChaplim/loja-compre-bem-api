package com.charliechaplim.comprebem.config;

import com.charliechaplim.comprebem.entity.Produto;
import com.charliechaplim.comprebem.repository.ProdutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ProdutoDataLoader {

    // 60 itens do catalogo para demonstrar tres paginas de 20 itens.
    private static final String[] NOMES = {
        "Cadeira de Escritorio", "Cadeira Gamer", "Cadeira de Madeira",
        "Cadeira Dobrável", "Cadeira Estofada", "Mesa de Jantar",
        "Mesa de Centro", "Mesa de Escritorio", "Sofa de Dois Lugares",
        "Estante de Livros",

        "Geladeira Frost Free", "Fogao de Quatro Bocas", "Micro-ondas Digital",
        "Liquidificador", "Batedeira Planetaria", "Maquina de Lavar",
        "Aspirador de Po", "Ventilador de Mesa", "Cafeteira Eletrica",
        "Sanduicheira",

        "Notebook", "Mouse Sem Fio", "Teclado Mecanico",
        "Monitor LED", "Headset Bluetooth", "Caixa de Som",
        "Webcam Full HD", "Carregador USB-C", "Tablet",
        "Hub USB",

        "Luminaria de Mesa", "Abajur Moderno", "Vaso de Ceramica",
        "Quadro Decorativo", "Tapete Antiderrapante", "Espelho Redondo",
        "Relogio de Parede", "Almofada Decorativa", "Cortina Blackout",
        "Prateleira de Parede",

        "Panela de Pressao", "Frigideira Antiaderente", "Jogo de Talheres",
        "Conjunto de Pratos", "Copo de Vidro", "Garrafa Termica",
        "Assadeira Retangular", "Tigela de Inox", "Escorredor de Louca",
        "Forma de Bolo",

        "Agenda Executiva", "Caderno Universitario", "Caneta Esferografica",
        "Lapiseira", "Pasta Organizadora", "Suporte para Notebook",
        "Organizador de Mesa", "Grampeador", "Calculadora de Mesa",
        "Bloco de Anotacoes"
    };

    private static final String[] CATEGORIAS = {
        "Moveis", "Eletrodomesticos", "Eletronicos",
        "Decoracao", "Cozinha", "Papelaria"
    };

    @Bean
    CommandLineRunner popularProdutos(ProdutoRepository repository) {
        return args -> {
            long totalInicial = repository.count();
            if (totalInicial >= 60) {
                return; // Preserva dados que ja estiverem no banco.
            }

            List<Produto> novos = new ArrayList<>();
            for (int i = 0; i < NOMES.length && totalInicial + novos.size() < 60; i++) {
                if (repository.existsByNomeIgnoreCase(NOMES[i])) {
                    continue;
                }
                Produto produto = new Produto();
                produto.setNome(NOMES[i]);
                produto.setCategoria(CATEGORIAS[i / 10]);
                produto.setDescricao("Produto do catalogo Compre Bem: " + NOMES[i]);
                produto.setPreco(BigDecimal.valueOf(25L + i * 13L));
                produto.setQuantidade(10 + i);
                novos.add(produto);
            }

            int extra = 1;
            while (totalInicial + novos.size() < 60) {
                String nome = "Produto Adicional " + extra++;
                if (repository.existsByNomeIgnoreCase(nome)) {
                    continue;
                }
                Produto produto = new Produto();
                produto.setNome(nome);
                produto.setCategoria("Diversos");
                produto.setDescricao("Produto para completar o catalogo inicial.");
                produto.setPreco(BigDecimal.valueOf(39.90));
                produto.setQuantidade(10);
                novos.add(produto);
            }
            repository.saveAll(novos);
        };
    }
}
