# Planejamento para API de Encurtador de URLs

## 📋 **Épicos Principais**

### 1. **Épico: Gerenciamento de URLs**
#### Histórias de Usuário:
- **HU01**: Como usuário, quero encurtar uma URL longa para obter uma URL curta
- **HU02**: Como usuário, quero que minha URL encurtada redirecione para a URL original
- **HU03**: Como usuário, quero personalizar o código da URL encurtada (opcional)
- **HU04**: Como usuário, quero definir data de expiração para minha URL (opcional)

#### Tarefas Técnicas:
- [ ] Modelar entidade URL no banco de dados
- [ ] Implementar endpoint POST `/api/shorten`
- [ ] Implementar endpoint GET `/{shortCode}`
- [ ] Criar serviço de geração de códigos curtos
- [ ] Implementar validação de URLs

### 2. **Épico: Analytics e Métricas**
#### Histórias de Usuário:
- **HU05**: Como usuário, quero visualizar estatísticas de acesso das minhas URLs
- **HU06**: Como usuário, quero ver de onde vêm os acessos (geolocalização)

#### Tarefas Técnicas:
- [ ] Criar tabela de analytics/estatísticas
- [ ] Implementar tracking de cliques
- [ ] Criar endpoint GET `/api/urls/{shortCode}/stats`
- [ ] Implementar coleta de dados do usuário (IP, user-agent)

### 3. **Épico: Gestão de URLs**
#### Histórias de Usuário:
- **HU07**: Como usuário, quero listar minhas URLs encurtadas
- **HU08**: Como usuário, quero desativar uma URL encurtada
- **HU09**: Como usuário, quero editar o destino de uma URL encurtada

#### Tarefas Técnicas:
- [ ] Implementar endpoint GET `/api/urls`
- [ ] Implementar endpoint PUT `/api/urls/{shortCode}`
- [ ] Implementar endpoint DELETE `/api/urls/{shortCode}`

### 4. **Épico: Segurança e Validação**
#### Histórias de Usuário:
- **HU10**: Como usuário, quero que URLs maliciosas sejam bloqueadas
- **HU11**: Como administrador, quero limitar taxa de requisições por usuário

#### Tarefas Técnicas:
- [ ] Implementar validação contra URLs maliciosas
- [ ] Configurar rate limiting
- [ ] Implementar sanitização de URLs
- [ ] Criar blacklist de domínios

## 🚀 **Sprint 1: MVP (Minimum Viable Product)**

### Objetivo: Funcionalidade básica de encurtamento

**Tarefas Prioritárias:**
1. **Configuração do Projeto**
   - [ ] Setup do ambiente de desenvolvimento
   - [ ] Configuração do banco de dados
   - [ ] Estrutura básica da API

2. **Funcionalidades Core**
   - [ ] Modelo de dados para URLs
   - [ ] Endpoint de encurtamento (`POST /api/shorten`)
   - [ ] Endpoint de redirecionamento (`GET /{shortCode}`)
   - [ ] Geração de código curto único

3. **Validações Básicas**
   - [ ] Validar formato da URL
   - [ ] Verificar se URL é acessível
   - [ ] Prevenir duplicação de códigos curtos

## 🗄️ **Modelo de Dados Sugerido**

```sql
-- Tabela principal de URLs
CREATE TABLE shortened_urls (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    short_code VARCHAR(10) UNIQUE NOT NULL,
    original_url TEXT NOT NULL,
    custom_code BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NULL,
    click_count BIGINT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE
);

-- Tabela de estatísticas
CREATE TABLE url_analytics (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    short_code VARCHAR(10) NOT NULL,
    accessed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ip_address VARCHAR(45),
    user_agent TEXT,
    referrer VARCHAR(500),
    country VARCHAR(100)
);
```

## 🔧 **Endpoints da API**

### **POST /api/shorten**
```json
{
  "url": "https://exemplo.com/pagina-muito-longa",
  "customCode": "meulink", // opcional
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

## 📊 **Critérios de Aceitação**

### Para HU01 (Encurtar URL):
- [ ] URL válida retorna código curto
- [ ] URL inválida retorna erro 400
- [ ] Código curto é único
- [ ] Resposta inclui URL encurtada completa

### Para HU02 (Redirecionar):
- [ ] Código válido redireciona para URL original
- [ ] Código inválido retorna 404
- [ ] URLs expiradas retornam 410
- [ ] Contador de cliques é incrementado

## 🛠️ **Tecnologias Sugeridas**

- **Backend**: Node.js/Express, Python/FastAPI, ou Java/Spring Boot
- **Banco**: PostgreSQL ou MySQL
- **Cache**: Redis (para performance no redirecionamento)
- **Deploy**: Docker + servidor cloud

Quer que eu detalhe alguma parte específica ou ajuste alguma coisa nesse planejamento?
