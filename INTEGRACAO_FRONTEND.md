# Compatibilidade de estoque e Expo Web

Este patch é complementar à integração do aplicativo React Native/Expo disponível em `CharlieChaplim/loja-compre-bem`.

## Alterações

- `ProdutoRequestDTO`: campo opcional `quantidade` (inteiro >= 0) e validações básicas de nome/preço.
- `ProdutoResponseDTO`: expõe `quantidade` dos produtos existentes e recém-criados.
- `Produto.toEntity`: persiste `quantidade` no POST, usando zero caso o cliente legado não informe o campo.
- `ProdutoService.update`: persiste a quantidade no PUT; se ausente, preserva o estoque anterior.
- `CorsConfig`: libera somente as origens locais de desenvolvimento mais comuns (`localhost`, loopback e faixas LAN) para Expo Web. Antes de expor a API na internet, substitua essas origens por domínios oficiais e implante autenticação.
- `ProdutoIntegracaoTests`: testa cadastro, edição de estoque e compatibilidade de PUT sem campo quantidade.

## Como rodar

```powershell
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

A API permanece em `http://localhost:8080`. A base H2 continua no local original `./data/db-api` e **não precisa ser apagada**.

Exemplo de POST em `http://localhost:8080/api/v1/produtos`:

```json
{
  "nome": "Produto de teste",
  "preco": 39.90,
  "quantidade": 7,
  "descricao": "Criado no aplicativo",
  "categoria": "Testes",
  "imagem": null
}
```

No GET, além dos campos existentes, a resposta contém `"quantidade": 7`.

## Atividade: lista e detalhe diretamente da API

- `GET /api/v1/produtos/filtro?page=0&size=20`: retorna a primeira pagina sem aplicar filtro.
- `GET /api/v1/produtos/filtro?filtro=CADEIRA&page=0&size=20`: filtra por nome sem diferenciar letras maiusculas e minusculas.
- `GET /api/v1/produtos/{id}` e `DELETE /api/v1/produtos/{id}` usam `@PathVariable("id")`.
- `spring.data.web.pageable.serialization-mode=via_dto` entrega `{ "content": [], "page": { "size": 20, "number": 0, "totalElements": 60, "totalPages": 3 } }` (exemplo com 60 itens).
- `mvnw.cmd`: retirado o escape `^` que estava sendo enviado indevidamente ao PowerShell, bloqueando o download do Maven.

Validacao manual: abra `http://localhost:8080/api/v1/produtos/filtro?filtro=cAdEiRa` e `http://localhost:8080/api/v1/produtos/filtro` depois de iniciar a API.
