# TeamTrack

O TeamTrack é um aplicativo de gerenciamento de projetos para manter o rastreio de seus clientes e atividades.

![TeamTrack App](teamtrack_01.png)

## Funcionamento geral da aplicação

Para a aplicação funcionar localmente, é preciso seguir alguns passos importantes. O detalhe desses passos será dado adiante, mas tem-se uma visão geral a seguir: 

1. Primeiramente, defina um banco de dados com nome "teamtrack" no PostgreSQL, com senha própria
2. Suba o servidor backend em `http://localhost:8080`
3. Com isso, é possível fazer requisições para os endpoints da API: /clientes, /projetos, /atividades
4. Sugestão de requisições realizadas por meio do app Insomnia ou Postman
5. Suba também o servidor frontend em `http://localhost:5173`
6. A tela da aplicação será mostrada conforme a imagem acima: a diferença é que serão mostrados os projetos, clientes e atividades conforme definido pelo usuário nas requisições com a API. A imagem acima mostra um exemplo. 


## Autor

Barbara Calderon, software developer.

- [Github](https://www.github.com/barbaracalderon)
- [Linkedin](https://www.linkedin.com/in/barbaracalderondev)
- [Twitter](https://www.x.com/bederoni)

## Tecnologias usadas

- **Backend**: Java, JDK17, Spring Boot, Spring Data JPA, Spring Web, DevTools, Lombok, Jakarta Validation API, PostgreSQL, JUnit, Mockito.
- **Frontend**: Vue.js, Bootstrap, Pinia, HTML5, CSS3, Vite

Também foi utilizado o Insomnia para requisições ao servidor Backend e povoamento do banco de dados TeamTrack.


## Instruções de Configuração

### Backend

1. Clone o repositório:

   ```bash
   git clone git@github.com:barbaracalderon/teamtrack.git
   ```

2. Crie um banco de dados no PostgreSQL com nome "teamtrack"

3. Configure o arquivo application.properties

```properties
spring.application.name=teamtrack
server.port=8080
spring.datasource.url=jdbc:postgresql://localhost:5432/teamtrack
spring.datasource.username=postgres
spring.datasource.password=[sua senha aqui]
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

4. Execute a aplicação

```bash
./mvnw spring-boot:run
```

![TeamTrack App](teamtrack_02.png)

O servidor será iniciado em http://localhost:8080/

5. Passo-a-passo de uso pelo Insomnia

Recomenda-se o uso do Isomnia para realizar as requisições aos endpoints da API e povoar o banco de dados com as informações que se deseja. Neste projeto também encontra-se um arquivo do Insomnia com os dados que foram utilizados na construção do projeto: "Insomnia_teamtrackjson".

![TeamTrack App](teamtrack_03.png)
![TeamTrack App](teamtrack_04.png)

6. Endpoints da API

| Método | Endpoint                  | Descrição                                            |
|--------|---------------------------|------------------------------------------------------|
| POST   | /clientes                 | Cria um novo cliente                                 |
| GET    | /clientes                 | Lista todos os clientes                              |
| POST   | /projetos                 | Cria um novo projeto                                 |
| GET    | /projetos                 | Lista todos os projetos                              |
| GET    | /projetos/em_aberto       | Lista todos os projetos em aberto                    |
| POST   | /atividades               | Cria uma nova atividade associada a um projeto       |
| GET    | /atividades               | Lista todas as atividades                             |


7. Logs

A aplicação conta com aplicação de Logs (`@slf4j`) para visualização de `infos` e `errors` no terminal.

8. Testes

Rode os testes com o comando:

```bash
mvn test
```
São 24 testes totais. O resultado é verificado abaixo:

![TeamTrack App](teamtrack_05.png)

### Frontend

A parte frontend do projeto encontra-se no diretório "frontend". 

1. Entre no diretório frontend, ainda com o servidor backend ativo
2. Execute o comando para subir

```bash
npm run dev
```

A aplicação será inicializada em `http://localhost:5174/`

3. Abra o navegador até o endereço citado acima e utilize a aplicação

Você verá a aplicação dinâmica e responsiva, sendo atualizada ao mesmo tempo que novos dados são inseridos no servidor backend. Foi utilizado o "accordion" para visualização dos dados, de modo que temos o nome do projeto, o nome do cliente relacionado ao projeto e também as atividades daquele projeto. Algumas imagens da aplicação ativa e funcionando abaixo.

![TeamTrack App](teamtrack_06.png)

![TeamTrack App](teamtrack_07.png)

![TeamTrack App](teamtrack_08.png)

![TeamTrack App](teamtrack_09.png)

![TeamTrack App](teamtrack_10.png)


## Considerações finais

O TeamTrack foi desenvolvido inteiramente por mim, Barbara Calderon, com o objetivo de facilitar o gerenciamento de projetos, clientes e tarefas, oferecendo uma interface intuitiva e funcionalidades robustas. A combinação do Spring Boot no backend e do Vue.js no frontend proporciona uma aplicação eficiente e de fácil manutenção. Esperamos que este sistema atenda às suas necessidades e facilite o acompanhamento de suas atividades diárias.

