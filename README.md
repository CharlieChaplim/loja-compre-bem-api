# Loja Compre Bem API — Paginação de Produtos

API REST em **Java 21, Spring Boot 4.1.1, Spring Data JPA, H2 e Springdoc OpenAPI (Swagger UI)**. Projeto adaptado da base fornecida para a atividade da turma.

## Funcionalidades

- `GET /api/v1/produtos`: catálogo paginado, página 0 e 20 itens por padrão, limite de 50 itens por página.
- `GET /api/v1/produtos?page=2`: terceira página (páginas começam em 0).
- `GET /api/v1/produtos?size=500`: respeita tamanho máximo de 50.
- `GET /api/v1/produtos?busca=CADEIRA`: filtra pelo nome sem diferenciar maiúsculas de minúsculas; aceita paginação junto.
- `GET /api/v1/produtos/{id}`: devolve produto por UUID ou **HTTP 404** quando não encontrado.
- Operações `POST`, `PUT` e `DELETE` de produtos mantidas.
- Cadastro automático de 60 produtos quando o banco está vazio, sem repetir produtos a cada reinicialização.

## Como executar (Windows)

**Pré-requisitos**: Java JDK 21 (`java -version`) e internet na primeira execução (o projeto inclui um script que obtém o Maven quando necessário).

1. Extraia o arquivo ZIP e abra um terminal PowerShell ou CMD **dentro da pasta `loja-compre-bem-api`**, onde está o `pom.xml`.
2. Rode:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

3. Abra: **http://localhost:8080/swagger-ui.html**

Em Linux ou macOS:

```sh
chmod +x mvnw
./mvnw spring-boot:run
```

### Banco de dados

O H2 persiste em `data/db-api.mv.db`, ignorado pelo Git. Para apresentar exatamente **60 produtos**, inicie com banco vazio: pare a API e remova `data/db-api.mv.db` e `data/db-api.trace.db` se existirem; ao reiniciar, o sistema popula 60 registros. Se já existir um banco, a carga completa até pelo menos 60 sem apagar os registros existentes.

## Demonstração no Swagger

Acesse `http://localhost:8080/swagger-ui.html`, expanda `GET /api/v1/produtos`, clique **Try it out** e **Execute**. Deixe parâmetros em branco quando quiser testar os valores padrão.

| Cenário | Requisição | Resultado esperado no banco limpo |
|---|---|---|
| 1. Padrão | `/api/v1/produtos` | `content` com 20 itens, `totalElements: 60`, `totalPages: 3`, `size: 20` |
| 2. Última página | `/api/v1/produtos?page=2` | `number: 2`, 20 itens, `last: true` |
| 3. Limite máximo | `/api/v1/produtos?size=500` | `size: 50` e 50 itens no `content` |
| 4. Busca | `/api/v1/produtos?busca=CADEIRA` | Apenas nomes contendo `cadeira`, qualquer combinação de maiúsculas/minúsculas |
| 5. ID existente | `/api/v1/produtos/{id}` | Copie um `produtoId` retornado no cenário 1: HTTP 200 |
| 6. ID inexistente | `/api/v1/produtos/00000000-0000-0000-0000-000000000000` | HTTP 404 |

A paginação é feita **no banco** pelo Spring Data `Pageable` e retorna metadados como `content`, `totalElements`, `totalPages`, `number` e `size`. A ordenação por nome e UUID estabiliza o resultado.

## Testes

```powershell
.\mvnw.cmd test
```

Os testes verificam a existência de pelo menos 60 produtos, tamanho padrão e limite de páginas, filtro case-insensitive, ID válido e erro 404 para ID inexistente.

## Publicar no GitHub (primeira vez)

Crie um repositório vazio chamado **`loja-compre-bem-api`** em https://github.com/new na sua conta. Não selecione README, `.gitignore` nem licença na criação (já há arquivos correspondentes no projeto).

Abra o PowerShell **na pasta extraída que contém `pom.xml`** e execute:

```powershell
git init
git add .
git commit -m "Implementa paginacao e busca de produtos"
git branch -M main
git remote add origin https://github.com/CharlieChaplim/loja-compre-bem-api.git
git push -u origin main
```

O Git pode solicitar autenticação em uma janela do navegador. Se pedir nome/e-mail na primeira execução, configure `git config --global user.name "Seu Nome"` e `git config --global user.email "seu-email@exemplo.com"` e repita o commit.

Para mudanças futuras, use `git add .`, `git commit -m "Descreva a mudanca"` e `git push`.

## Organização do projeto

- `controller/ProdutoController.java`: parâmetros de consulta, IDs e rotas REST.
- `service/ProdutoService.java`: regras de paginação, limites, busca e 404.
- `repository/ProdutoRepository.java`: consultas Spring Data JPA.
- `config/ProdutoDataLoader.java`: carga inicial de 60 produtos.
- `entity/Produto.java` e `dto/`: modelo de dados e objetos de transferência.
- `src/test/`: testes de comportamento.
