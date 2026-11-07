# Spring Boot + Swagger + H2 + Vercel

Projeto de experimento com **Java 21**, **Spring Boot 4.1.2**, **Swagger UI (springdoc 3.2.0)**, **H2 em memória**, **Maven**, **Dockerfile** e configuração do Container Runtime da **Vercel**.

## Requisitos

- Java 21 e Maven 3.9+ para rodar na sua máquina, **ou** Docker.
- Para deploy: conta na Vercel e repositório no GitHub.

## Rodar localmente com Maven

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

O perfil `local` habilita o console H2. A aplicação estará disponível em `http://localhost:8080`.

### URLs para testes locais

| Recurso | URL |
| --- | --- |
| Página inicial (redireciona para Swagger) | http://localhost:8080/ |
| Swagger UI | http://localhost:8080/swagger-ui.html |
| OpenAPI JSON | http://localhost:8080/v3/api-docs |
| Listar produtos | http://localhost:8080/api/produtos |
| Health check | http://localhost:8080/api/health |
| Console H2 (somente perfil local) | http://localhost:8080/h2-console |

### Acessar o console H2

- JDBC URL: `jdbc:h2:mem:demo`
- User Name: `sa`
- Password: *(deixe vazio)*

No console, teste `SELECT * FROM PRODUTOS;`.

## Rodar com Docker

```bash
docker build -t springboot-demo .
docker run --rm -p 8080:80 springboot-demo
```

Abra `http://localhost:8080/swagger-ui.html`. O console H2 fica desativado no Docker por segurança. Para **testes locais somente**, você pode usar `docker run --rm -p 8080:80 -e SPRING_PROFILES_ACTIVE=local springboot-demo`.

## Testar os endpoints

O banco inicia com três produtos: Teclado, Mouse e Monitor.

**GET - listar produtos:**

```bash
curl http://localhost:8080/api/produtos
```

**POST - criar produto:**

```bash
curl -X POST http://localhost:8080/api/produtos \
  -H "Content-Type: application/json" \
  -d '{"nome":"Webcam","preco":199.90}'
```

**GET por ID:** `GET /api/produtos/1`

**PUT:** `PUT /api/produtos/1` com JSON `{"nome":"Novo nome","preco":120.00}`

**DELETE:** `DELETE /api/produtos/1`

Você também pode executar todos os endpoints pelo botão **Try it out** no Swagger UI.

## Deploy na Vercel

1. Envie o conteúdo desta pasta para um repositório GitHub (o `Dockerfile` e o `vercel.json` devem ficar na raiz do repositório).
2. Na Vercel, use **Add New > Project** e importe o repositório.
3. Confira se o framework está definido como **Container** (o arquivo `vercel.json` já força essa seleção).
4. Clique em **Deploy**.
5. Depois, acesse `https://SEU-PROJETO.vercel.app/swagger-ui.html` ou `https://SEU-PROJETO.vercel.app/api/health`.

O Dockerfile já usa `PORT=80`, que é a porta padrão do Container Runtime da Vercel. Se definir a variável `PORT` no dashboard, a aplicação acompanhará essa configuração via `server.port=${PORT:8080}`.

Opcional, via CLI, a partir da raiz do projeto:

```bash
npx vercel --prod
```

### Atenção: H2 e Vercel

**Este projeto é apenas para experimentos.** O H2 está configurado `mem:demo`, portanto os registros existem somente dentro da instância Java atual. Quando a instância reiniciar, for encerrada ou escalada, as alterações serão perdidas. Instâncias diferentes **não compartilham o mesmo banco**. Os três produtos de exemplo são recriados a cada inicialização. Se precisar guardar dados de verdade na Vercel, troque H2 por PostgreSQL hospedado em um serviço persistente.

**O servidor também não fica sempre ligado.** A Vercel escala as instâncias para zero quando estão ociosas; uma solicitação seguinte inicia uma nova instância. Há cotas e limites de uso aplicáveis ao plano Hobby.

**Segurança:** a API do experimento não tem autenticação; não use dados reais. O console H2 é desativado fora do perfil local.

## Estrutura

```text
springboot-swagger-h2-vercel/
├── Dockerfile
├── vercel.json
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/br/com/exemplo/demo/
│   │   │   ├── DemoApplication.java
│   │   │   ├── config/DadosIniciaisConfig.java
│   │   │   ├── controller/{Home,Health,Produto}Controller.java
│   │   │   ├── dto/ProdutoRequest.java
│   │   │   ├── model/Produto.java
│   │   │   └── repository/ProdutoRepository.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application-local.properties
│   └── test/java/br/com/exemplo/demo/DemoApplicationTests.java
└── README.md
```

## Referências

- Vercel Container Runtime: https://vercel.com/docs/functions/runtimes/container
- Vercel Dockerfile: https://vercel.com/blog/dockerfile-on-vercel
- Springdoc: https://springdoc.org/getting-started.html
