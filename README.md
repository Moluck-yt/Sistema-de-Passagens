# 🚌 Sistema de Passagens Rodoviárias

> Sistema web desenvolvido em Java EE para gerenciamento completo de vendas de passagens rodoviárias

[![Java](https://img.shields.io/badge/Java-11-orange.svg)](https://www.oracle.com/java/)
[![Jakarta EE](https://img.shields.io/badge/Jakarta%20EE-9+-blue.svg)](https://jakarta.ee/)
[![Docker](https://img.shields.io/badge/Docker-Ready-brightgreen.svg)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-Academic-yellow.svg)]()

## 📋 Sumário

- [Sobre o Projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Arquitetura](#arquitetura)
- [Como Executar](#como-executar)
  - [Opção 1: Docker (RECOMENDADO)](#opção-1-docker-recomendado)
  - [Opção 2: Instalação Manual](#opção-2-instalação-manual)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Modelo de Dados](#modelo-de-dados)
- [Regras de Negócio](#regras-de-negócio)
- [Capturas de Tela](#capturas-de-tela)
- [Equipe](#equipe)

---

## 📖 Sobre o Projeto

O **Sistema de Passagens Rodoviárias** é uma aplicação web desenvolvida como Projeto Integrador do curso de Desenvolvimento de Aplicações. O sistema permite o gerenciamento completo de vendas de passagens, incluindo cadastro de cidades, veículos, controle de poltronas e relatórios gerenciais.

### 🎯 Objetivos

- Gerenciar cadastros de cidades, veículos e usuários
- Controlar venda de passagens com validação de disponibilidade
- Gerar relatórios de faturamento e rotas
- Garantir integridade dos dados e regras de negócio
- Fornecer interface amigável e responsiva

---

## ✨ Funcionalidades

### 🔐 Sistema de Autenticação
- Login e logout de usuários
- Controle de sessão
- Proteção de rotas

### 🏙️ Gerenciamento de Cidades
- ✅ Cadastrar, editar, consultar e excluir cidades
- ✅ Validação de código IBGE único
- ✅ Campos: Nome, Código IBGE, UF

### 🚌 Gerenciamento de Veículos
- ✅ Cadastrar, editar, consultar e excluir veículos
- ✅ Validação de placa e número únicos
- ✅ Campos: Número, Placa, Motorista, Modelo, Data de Compra, Quantidade de Poltronas

### 🎫 Venda de Passagens
- ✅ Venda com validação automática de disponibilidade
- ✅ Verificação de poltrona já vendida
- ✅ Validação de limite de poltronas do veículo
- ✅ Controle de data e horário de saída
- ✅ Campos: Veículo, Poltrona, Data/Hora de Saída, Origem, Destino, Valor

### 📊 Relatórios Gerenciais

#### 1. Faturamento por Período
- Consulta de passagens vendidas em intervalo de datas
- Cálculo automático de faturamento total
- Listagem detalhada de vendas

#### 2. Passagens por Roteiro
- Consulta por cidade de origem e destino
- Visualização de todas as vendas do roteiro
- Análise de rotas mais utilizadas

---

## 🛠️ Tecnologias Utilizadas

### Backend
| Tecnologia | Versão | Descrição |
|-----------|--------|-----------|
| **Java** | 11 | Linguagem de programação |
| **Jakarta EE** | 9+ | Platform enterprise |
| **JSF (Mojarra)** | 3.0.3 | Framework MVC |
| **Hibernate** | 6.1.7 | ORM (Mapeamento objeto-relacional) |
| **JPA** | 3.1.0 | Persistência de dados |
| **CDI (Weld)** | 4.0.3 | Injeção de dependências |
| **Bean Validation** | 3.0.2 | Validação de dados |

### Frontend
| Tecnologia | Versão | Descrição |
|-----------|--------|-----------|
| **PrimeFaces** | 13.0.0 (jakarta) | Componentes UI |
| **OmniFaces** | 4.0 | Utilitários JSF |
| **HTML5/CSS3** | - | Estrutura e estilo |
| **Font Awesome** | - | Ícones |

### Banco de Dados
| Tecnologia | Versão | Descrição |
|-----------|--------|-----------|
| **MySQL** | 8.0 | Banco de dados relacional |
| **HikariCP** | 5.0.1 | Connection pool |

### Build & Deploy
| Tecnologia | Versão | Descrição |
|-----------|--------|-----------|
| **Maven** | 3.9+ | Gerenciamento de dependências |
| **Tomcat** | 10.1 | Servidor de aplicação |
| **Docker** | 20.10+ | Containerização |
| **Docker Compose** | 1.29+ | Orquestração de containers |

---

## 🏗️ Arquitetura

O projeto segue os padrões **MVC (Model-View-Controller)** e **Multi-Tier Architecture**:

```
┌─────────────────────────────────────────────────┐
│                   FRONTEND                      │
│         (JSF/XHTML + PrimeFaces)               │
└─────────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────────┐
│                 CONTROLLER                      │
│            (Managed Beans - CDI)               │
└─────────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────────┐
│               BUSINESS LOGIC                    │
│           (Validações e Regras)                │
└─────────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────────┐
│              DATA ACCESS (DAO)                  │
│            (GenericDAO + Specific DAOs)        │
└─────────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────────┐
│               PERSISTENCE                       │
│            (JPA/Hibernate + MySQL)             │
└─────────────────────────────────────────────────┘
```

### Padrões de Projeto Implementados

- **DAO (Data Access Object)**: Isolamento da lógica de acesso a dados
- **Generic DAO**: Reutilização de código CRUD
- **Template Method**: Template de layout (template.xhtml)
- **Dependency Injection**: CDI para gerenciamento de dependências
- **MVC**: Separação clara de responsabilidades

---

## 🚀 Como Executar

### Pré-requisitos

- **Docker** e **Docker Compose** instalados *(para Opção 1)*
- **JDK 11+**, **Maven 3.6+**, **MySQL 8+** e **Tomcat 10+** *(para Opção 2)*

---

### Opção 1: Docker (RECOMENDADO) 🐳

A forma mais rápida e fácil de executar o projeto!

#### 1️⃣ Usando MySQL do Docker existente

Se você já tem MySQL rodando em Docker:

```bash
# 1. Clone/baixe o projeto
cd /caminho/para/Cairu

# 2. Certifique-se que o MySQL está rodando
docker ps | grep mysql

# 3. Execute o script de build
./docker-build.sh
```

#### 2️⃣ Usando MySQL standalone (sem MySQL Docker)

Se você NÃO tem MySQL em Docker, use o docker-compose completo:

```bash
# 1. Use o docker-compose com MySQL incluso
# (arquivo fornecido separadamente em docker-compose-full.yml)
docker-compose -f docker-compose-full.yml up -d --build

# 2. Aguarde a inicialização (~60 segundos)
docker-compose logs -f
```

#### 3️⃣ Acessar a Aplicação

Aguarde a inicialização e acesse:

```
http://localhost:8080/passagens-rodoviarias/
```

**Credenciais:**
- **Login:** `admin`
- **Senha:** `123456`

#### 📝 Comandos Docker Úteis

```bash
# Ver logs da aplicação
docker logs -f passagens-app

# Parar a aplicação
docker stop passagens-app

# Reiniciar a aplicação
docker restart passagens-app

# Remover container
docker rm -f passagens-app

# Acessar bash do container
docker exec -it passagens-app bash
```

---

### Opção 2: Instalação Manual 🔧

Para ambiente de desenvolvimento ou sem Docker:

#### 1️⃣ Configurar MySQL

```sql
-- Criar banco de dados
CREATE DATABASE passagens_rodoviarias;

-- Criar usuário
USE passagens_rodoviarias;

INSERT INTO usuarios (nome, cargo, login, senha, email)
VALUES ('Administrador', 'Gerente', 'admin', '123456', 'admin@passagens.com');
```

#### 2️⃣ Configurar persistence.xml

Edite `src/main/resources/META-INF/persistence.xml`:

```xml
<property name="jakarta.persistence.jdbc.url"
          value="jdbc:mysql://localhost:3306/passagens_rodoviarias?..."/>
<property name="jakarta.persistence.jdbc.user" value="root"/>
<property name="jakarta.persistence.jdbc.password" value="SUA_SENHA"/>
```

#### 3️⃣ Build com Maven

```bash
# Limpar e compilar
mvn clean package

# O WAR será gerado em: target/passagens-rodoviarias.war
```

#### 4️⃣ Deploy no Tomcat

**Opção A - Copiar manualmente:**
```bash
sudo cp target/passagens-rodoviarias.war /var/lib/tomcat10/webapps/
sudo systemctl restart tomcat10
```

**Opção B - Manager do Tomcat:**
1. Acesse: http://localhost:8080/manager
2. Faça upload do WAR gerado

#### 5️⃣ Acessar

```
http://localhost:8080/passagens-rodoviarias/
```

---

## 📁 Estrutura do Projeto

```
Cairu/
├── src/
│   ├── main/
│   │   ├── java/br/com/cairu/
│   │   │   ├── model/              # Entidades JPA
│   │   │   │   ├── Usuario.java
│   │   │   │   ├── Cidade.java
│   │   │   │   ├── Veiculo.java
│   │   │   │   └── Passagem.java
│   │   │   ├── dao/                # Data Access Objects
│   │   │   │   ├── GenericDAO.java
│   │   │   │   ├── UsuarioDAO.java
│   │   │   │   ├── CidadeDAO.java
│   │   │   │   ├── VeiculoDAO.java
│   │   │   │   └── PassagemDAO.java
│   │   │   ├── bean/               # Managed Beans (Controllers)
│   │   │   │   ├── LoginBean.java
│   │   │   │   ├── CidadeBean.java
│   │   │   │   ├── VeiculoBean.java
│   │   │   │   ├── PassagemBean.java
│   │   │   │   └── RelatorioBean.java
│   │   │   └── util/               # Utilitários
│   │   │       └── JPAUtil.java
│   │   ├── resources/
│   │   │   └── META-INF/
│   │   │       └── persistence.xml
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   ├── templates/
│   │       │   │   └── template.xhtml
│   │       │   ├── web.xml
│   │       │   ├── faces-config.xml
│   │       │   └── beans.xml
│   │       ├── pages/
│   │       │   ├── cidades/
│   │       │   │   └── cidades.xhtml
│   │       │   ├── veiculos/
│   │       │   │   └── veiculos.xhtml
│   │       │   ├── passagens/
│   │       │   │   └── passagens.xhtml
│   │       │   └── relatorios/
│   │       │       ├── faturamento.xhtml
│   │       │       └── roteiro.xhtml
│   │       ├── resources/
│   │       │   └── css/
│   │       │       └── style.css
│   │       ├── login.xhtml
│   │       └── index.xhtml
│   └── test/java/                  # Testes (vazio)
├── Dockerfile                      # Imagem Docker da aplicação
├── docker-compose.yml              # Orquestração Docker
├── docker-build.sh                 # Script automatizado de deploy
├── docker-stop.sh                  # Script para parar containers
├── init-db/                        # Scripts SQL iniciais
│   └── 01-init.sql
├── pom.xml                         # Configuração Maven
├── README.md                       # Este arquivo
└── README-DOCKER.md                # Documentação Docker detalhada
```

---

## 💾 Modelo de Dados

### Diagrama Entidade-Relacionamento

```
┌─────────────────┐
│    Usuario      │
├─────────────────┤
│ id (PK)         │
│ nome            │
│ cargo           │
│ login (UNIQUE)  │
│ senha           │
│ email (UNIQUE)  │
└─────────────────┘

┌─────────────────┐
│    Cidade       │
├─────────────────┤
│ id (PK)         │◄─┐
│ nomeCidade      │  │
│ idCidade (UK)   │  │
│ uf              │  │
└─────────────────┘  │
                     │
┌─────────────────┐  │
│    Veiculo      │  │
├─────────────────┤  │
│ id (PK)         │◄─┼─┐
│ numero (UNIQUE) │  │ │
│ placa (UNIQUE)  │  │ │
│ motorista       │  │ │
│ modelo          │  │ │
│ dataCompra      │  │ │
│ qtdPoltronas    │  │ │
└─────────────────┘  │ │
                     │ │
┌──────────────────────┐ │
│      Passagem        │ │
├──────────────────────┤ │
│ idPassagem (PK)      │ │
│ veiculo_id (FK) ─────┼─┘
│ poltrona             │
│ dataSaida            │
│ horaSaida            │
│ cidadeOrigem_id (FK) ┼─┘
│ cidadeDestino_id (FK)┼─┘
│ valorPassagem        │
│ vendida              │
│ dataVenda            │
└──────────────────────┘
```

### Tabelas

#### usuarios
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | BIGINT (PK, AI) | Identificador único |
| nome | VARCHAR(100) | Nome completo |
| cargo | VARCHAR(50) | Cargo do usuário |
| login | VARCHAR(50) UNIQUE | Login de acesso |
| senha | VARCHAR(255) | Senha (texto plano) |
| email | VARCHAR(100) UNIQUE | Email |

#### cidades
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | BIGINT (PK, AI) | Identificador único |
| nomeCidade | VARCHAR(100) | Nome da cidade |
| idCidade | VARCHAR(10) UNIQUE | Código IBGE |
| uf | VARCHAR(2) | Estado (UF) |

#### veiculos
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | BIGINT (PK, AI) | Identificador único |
| numero | VARCHAR(20) UNIQUE | Número do veículo |
| placa | VARCHAR(10) UNIQUE | Placa |
| motorista | VARCHAR(100) | Nome do motorista |
| modelo | VARCHAR(50) | Modelo do ônibus |
| dataCompra | DATE | Data de aquisição |
| qtdPoltronas | INTEGER | Total de poltronas |

#### passagens
| Campo | Tipo | Descrição |
|-------|------|-----------|
| idPassagem | BIGINT (PK, AI) | Identificador único |
| veiculo_id | BIGINT (FK) | Veículo da viagem |
| poltrona | INTEGER | Número da poltrona |
| dataSaida | DATE | Data da viagem |
| horaSaida | VARCHAR(10) | Horário de saída |
| cidadeOrigem_id | BIGINT (FK) | Cidade de partida |
| cidadeDestino_id | BIGINT (FK) | Cidade de chegada |
| valorPassagem | DECIMAL(10,2) | Valor da passagem |
| vendida | BOOLEAN | Status de venda |
| dataVenda | TIMESTAMP | Data/hora da venda |

---

## ⚙️ Regras de Negócio

### Validações de Passagens

✅ **Poltrona Válida**
- Número da poltrona deve estar entre 1 e a quantidade de poltronas do veículo
- Implementado em: `PassagemBean.validarPassagem()` (linha 119-131)

✅ **Poltrona Não Vendida**
- Não pode vender a mesma poltrona duas vezes para o mesmo veículo/data/hora
- Implementado em: `PassagemDAO.isPoltronaJaVendida()` (linha 20-39)

✅ **Cidades Diferentes**
- Cidade de origem e destino devem ser diferentes
- Implementado em: `PassagemBean.validarPassagem()` (linha 114-117)

✅ **Dados Obrigatórios**
- Veículo, cidades, poltrona, data e horário são obrigatórios
- Validado via Bean Validation e no Managed Bean

### Validações de Cadastros

✅ **Unicidade**
- Login e email de usuários devem ser únicos
- Placa e número de veículos devem ser únicos
- Código IBGE de cidades deve ser único
- Garantido via constraints do banco + validações DAO

✅ **Autenticação**
- Usuário deve estar autenticado para acessar o sistema
- Verificado via `LoginBean.verificarSessao()` em todas as páginas protegidas

---

## 📸 Capturas de Tela

### Tela de Login
Interface moderna com validação de campos

### Dashboard
Cards clicáveis para acesso rápido às funcionalidades

### Gerenciamento de Passagens
Formulário com validação em tempo real e tabela de listagem

### Relatórios
Filtros por período e roteiro com cálculo automático de valores

---

## 👥 Equipe

**Equipe 1 - Projeto Integrador 2025**

| Nome | Papel |
|------|-------|
| **Rodrigo** | Desenvolvedor |
| **Gabriel Kauan** | Desenvolvedor |
| **Guilherme Teixeira** | Desenvolvedor |

**Professor Orientador:** Andre Guimarães Portugal
**Instituição:** Faculdade Cairu
**Disciplina:** Desenvolvimento de Aplicações
**Ano:** 2025

---

## 📝 Observações Importantes

### Segurança
⚠️ **ATENÇÃO:** Este projeto é acadêmico. Para uso em produção, implemente:
- Hash de senhas (BCrypt/PBKDF2)
- HTTPS/SSL
- Controle de permissões (roles)
- Proteção contra SQL Injection adicional
- Tratamento de exceções mais robusto

### Melhorias Futuras
- [ ] Implementar hash de senhas
- [ ] Adicionar sistema de roles/permissões
- [ ] Exportação de relatórios em PDF (JasperReports)
- [ ] Dashboard com gráficos
- [ ] API REST para integração
- [ ] Testes unitários e de integração
- [ ] Paginação nas listagens
- [ ] Busca avançada
- [ ] Sistema de reservas

---

## 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos como parte do Projeto Integrador da Faculdade Cairu.

---

## 📞 Contato

Para dúvidas sobre o projeto, entre em contato com a equipe ou com o professor orientador.

---

<div align="center">

**Desenvolvido com ❤️ pela Equipe 1**

*Faculdade Cairu - 2025*

</div>
