
# 📅 Cronograma para MVP - API Encurtador de URLs

## ✅ Dia 1: Preparação e Configuração
- [ ] **Setup do projeto**
  - Criar repositorio no github
  - Criar projeto Spring Boot (Java 21)
  - Adicionar dependências (Spring Web, Spring Data JPA, MariaDB, Swagger)
- [ ] **Configuração do banco**
  - Criar banco MariaDB local
  - Ajustar `application.properties` (datasource, JPA)
- [ ] **Documentação inicial**
  - Configurar Swagger/OpenAPI

---

## ✅ Dia 2: Modelagem e Serviço Core
- [ ] **Modelagem**
  - Criar entidade `ShortenedUrl` (id, shortCode, originalUrl, createdAt, expiresAt, clickCount, isActive)
  - Criar repositório JPA
- [ ] **Serviço de geração de código**
  - Implementar algoritmo Base62 (0-9, A-Z, a-z)
  - Garantir unicidade no banco

---

## ✅ Dia 3: Endpoints Principais
- [ ] **POST /api/shorten**
  - Receber URL + customCode (opcional) + expiresIn (opcional)
  - Validar URL (regex + protocolo)
  - Persistir no banco
  - Retornar URL encurtada completa
- [ ] **GET /{shortCode}**
  - Buscar URL original
  - Redirecionar (HTTP 301)
  - Incrementar contador de cliques
  - Tratar erros (404 inexistente, 410 expirado)

---

## ✅ Dia 4: Validações e Testes
- [ ] **Validações**
  - Prevenir duplicação de códigos curtos
  - Tratar customCode (verificar disponibilidade)
- [ ] **Testes**
  - Testes unitários (serviço Base62, validações)
  - Testes de integração (MockMvc para endpoints)

---

## ✅ Dia 5: Ajustes e Documentação Final
- [ ] Revisão geral do código
- [ ] Ajustar Swagger com exemplos
- [ ] Preparar README com instruções de execução
- [ ] Planejar próximos passos (Analytics, Gestão, Segurança)

---

### ⏱ Estimativa Total
- **Tempo aproximado:** 15h (distribuídas em 5 blocos de 3h)
- **Entrega:** MVP funcional com encurtamento e redirecionamento


