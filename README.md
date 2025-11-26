# 🔗 URLGZ 🔗
---
🔗
Api responsavel por encurtar urls longas. Realiza o encurtamento da url, garantindo que ela seja a mais curta possivel, e redireciona para url original sempre que for requisitado a url curta. 
🔗

### 💻 tecnologias utilizadas

* **linguagem:** java 21
* **framework:** spring boot 3.x
* **gerenciador de dependências:** Gradle
* **banco de dados:** MariaDb 
* **persistência:** spring data jpa / hibernate
* **Testes:** JUnit 5, Mockito (com 'org.mockito:mockito-core:5.+')
* **documentação:** springdoc openapi (swagger ui)
* **Container:** Docker
### ⚙️ pré-requisitos

para executar a api localmente, você precisará ter instalado:

1.  **java development kit (jdk):** versão 21 ou superior.
    ```bash
    java -version
    ```
2.  **Gradlew:** Gerenciador de dependencias.

### 🚀 instalação e execução

1.  **clonar o repositório:**
    ```bash
    git clone [https://github.com/renansalves/urlgz.git](https://github.com/renansalves/urlgz.git)
    cd api-pessoas
    ```

2.  **Configurar Banco de Dados (Opcional):**
    O projeto utiliza **MariaDB**, que pode ser alterado para outros bancos. Para usar o PostgreSQL ou outro banco de sua preferencia, edite o arquivo `src/main/resources/application.yml` e configure as credenciais:

    ```yaml
    # Exemplo de configuração (YAML):
    spring:
      datasource:
        url: jdbc:postgresql://localhost:5432/api_pessoas_db 
        username: seu_usuario 
        password: sua_senha
    ```
3.  **compilar e empacotar (build):**
    ```bash
    ./gradlew build
    ```

4.  **executar a aplicação:**
    ```bash
    ./gradlew bootRun
    # a aplicação estará rodando em http://localhost:8080
    ```

### 🧭 uso da api (endpoints principais)

a api é acessível em `http://localhost:8080` (porta padrão).

| método | endpoint | descrição |
| :--- | :--- | :--- |
| **post** | `/api/urls` | cria um novo registro de pessoa. |
| **get** | `/api/urls/{shortCode}` | busca uma pessoa pelo id. |
| **delete** | `/api/urls/{shortCode}` | deleta uma pessoa pelo id. |

**exemplo de requisição (post /api/v1/shorten):**

### **POST /api/v1/shorten**
```json
{
  "url": "https://exemplo.com/pagina-muito-longa",
  "expiresIn": "30d" // opcional
}
```

### **GET /{shortCode}**
- Redireciona 301 para URL original

### **GET /api/urls/{shortCode}/stats**
```json
{
  "shortCode": "abc123",
  "originalUrl": "https://...",
  "createdAt": "2024-01-01T00:00:00Z",
  "totalClicks": 150,
  "clicksLast30Days": 45
}
```


### 📄 Documentação (Swagger UI)

* A documentação interativa da API, gerada automaticamente pelo Springdoc, pode ser acessada em: `http://localhost:8080/swagger-ui.html`

### 🎯 Funcionalidades e Requisitos

#### Encurtar URL:
- [ ] URL válida retorna código curto
- [ ] URL inválida retorna erro 400
- [ ] Código curto é único
- [ ] Resposta inclui URL encurtada completa

#### Redirecionar:
- [ ] Código válido redireciona para URL original
- [ ] Código inválido retorna 404
- [ ] URLs expiradas retornam 410
- [ ] Contador de cliques é incrementado

### 📝 Trabalho Futuro (TODO)

* [X] 🚧 .
* [X] 📈 .
