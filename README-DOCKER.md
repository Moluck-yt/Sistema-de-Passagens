# Sistema de Passagens Rodoviárias - Docker

Guia completo para executar a aplicação usando Docker.

## 📋 Pré-requisitos

- **Docker** instalado (versão 20.10 ou superior)
- **Docker Compose** instalado (versão 1.29 ou superior)
- **Portas disponíveis**: 8080 (aplicação) e 3306 (MySQL)

### Verificar instalação do Docker

```bash
docker --version
docker-compose --version
```

## 🚀 Quick Start - Início Rápido

### Opção 1: Usando o script automatizado (RECOMENDADO)

```bash
./docker-build.sh
```

Este script faz tudo automaticamente:
- Para containers existentes
- Constrói a imagem Docker
- Inicia MySQL e Tomcat
- Aguarda a aplicação ficar pronta
- Mostra o status dos containers

### Opção 2: Comandos manuais

```bash
# Build e iniciar
docker-compose up -d --build

# Verificar logs
docker-compose logs -f
```

## 🌐 Acessar a Aplicação

Após o deploy, acesse:

**URL:** http://localhost:8080/passagens-rodoviarias/

**Credenciais padrão:**
- Login: `admin`
- Senha: `123456`

## 🐳 Arquitetura Docker

A aplicação usa **2 containers**:

### 1. MySQL Container (`passagens-mysql`)
- **Imagem:** mysql:8.0
- **Porta:** 3306
- **Banco de dados:** passagens_rodoviarias
- **Volume:** Dados persistidos em `passagens_mysql_data`

### 2. Aplicação Container (`passagens-app`)
- **Imagem:** Custom (build multi-stage)
- **Porta:** 8080
- **Base:** Tomcat 10.1 + JDK 11
- **Build:** Maven 3.9

## 📦 Volumes

```bash
# Listar volumes
docker volume ls | grep passagens

# Inspecionar volume do MySQL
docker volume inspect passagens_mysql_data
```

**⚠️ ATENÇÃO:** Os dados do banco são persistidos no volume `passagens_mysql_data`. Para apagar os dados, remova o volume:

```bash
docker-compose down -v  # REMOVE TODOS OS DADOS!
```

## 📝 Comandos Úteis

### Gerenciamento de Containers

```bash
# Iniciar containers
docker-compose up -d

# Parar containers (mantém dados)
docker-compose down

# Parar e remover volumes (APAGA DADOS)
docker-compose down -v

# Reiniciar apenas a aplicação
docker-compose restart app

# Reiniciar apenas o MySQL
docker-compose restart mysql
```

### Logs e Debugging

```bash
# Ver logs de todos os containers
docker-compose logs -f

# Logs apenas da aplicação
docker-compose logs -f app

# Logs apenas do MySQL
docker-compose logs -f mysql

# Últimas 100 linhas
docker-compose logs --tail=100 app
```

### Status e Informações

```bash
# Status dos containers
docker-compose ps

# Processos dentro do container
docker-compose top

# Uso de recursos
docker stats passagens-app passagens-mysql

# Informações da rede
docker network inspect passagens-network
```

### Executar Comandos nos Containers

```bash
# Acessar bash da aplicação
docker exec -it passagens-app bash

# Acessar MySQL
docker exec -it passagens-mysql mysql -uroot -prootmoluck passagens_rodoviarias

# Ver estrutura do banco
docker exec passagens-mysql mysql -uroot -prootmoluck passagens_rodoviarias -e "SHOW TABLES;"

# Listar usuários cadastrados
docker exec passagens-mysql mysql -uroot -prootmoluck passagens_rodoviarias -e "SELECT id, nome, login FROM usuarios;"
```

### Rebuild e Cache

```bash
# Build sem cache (força rebuild completo)
docker-compose build --no-cache

# Rebuild e reiniciar
docker-compose up -d --build

# Remover imagens antigas não utilizadas
docker image prune -a
```

## 🔧 Configurações Avançadas

### Variáveis de Ambiente

Edite o arquivo `docker-compose.yml` para personalizar:

```yaml
environment:
  DB_HOST: mysql
  DB_PORT: 3306
  DB_NAME: passagens_rodoviarias
  DB_USER: root
  DB_PASSWORD: rootmoluck  # ⚠️ Mudar em produção!
  JAVA_OPTS: "-Xms256m -Xmx512m"  # Ajustar memória JVM
```

### Alterar Portas

Para usar portas diferentes, edite `docker-compose.yml`:

```yaml
services:
  app:
    ports:
      - "9090:8080"  # Aplicação na porta 9090

  mysql:
    ports:
      - "3307:3306"  # MySQL na porta 3307
```

### Script de Inicialização do Banco

O arquivo `init-db/01-init.sql` é executado automaticamente na primeira criação do banco.

Para adicionar mais dados iniciais, crie novos arquivos `.sql` na pasta `init-db/`:

```bash
# Exemplo: init-db/02-seed-data.sql
INSERT INTO cidades (nomeCidade, idCidade, uf) VALUES
('Porto Alegre', '4314902', 'RS'),
('Curitiba', '4106902', 'PR');
```

## 🏗️ Estrutura de Arquivos Docker

```
Cairu/
├── Dockerfile              # Definição da imagem da aplicação
├── docker-compose.yml      # Orquestração dos containers
├── .dockerignore          # Arquivos ignorados no build
├── docker-build.sh        # Script de build automatizado
├── docker-stop.sh         # Script para parar containers
├── init-db/               # Scripts SQL de inicialização
│   └── 01-init.sql       # Dados iniciais
└── README-DOCKER.md       # Esta documentação
```

## 🐛 Troubleshooting

### Aplicação não inicia

```bash
# Verificar logs
docker-compose logs app

# Verificar se o MySQL está pronto
docker-compose logs mysql | grep "ready for connections"

# Reiniciar com logs visíveis
docker-compose down
docker-compose up
```

### Erro de conexão com banco de dados

```bash
# Verificar se os containers estão na mesma rede
docker network inspect passagens-network

# Testar conexão do container app para mysql
docker exec passagens-app ping mysql -c 3
```

### Porta já em uso

```bash
# Verificar o que está usando a porta 8080
sudo lsof -i :8080

# Matar processo (cuidado!)
sudo kill -9 <PID>

# Ou alterar a porta no docker-compose.yml
```

### Banco de dados não inicializa

```bash
# Remover volume e recriar
docker-compose down -v
docker-compose up -d

# Verificar permissões do volume
docker volume inspect passagens_mysql_data
```

### Memória insuficiente

Editar `docker-compose.yml`:

```yaml
services:
  app:
    deploy:
      resources:
        limits:
          memory: 1G
        reservations:
          memory: 512M
```

## 📊 Monitoramento

### Health Checks

Os containers têm health checks configurados:

```bash
# Ver status de saúde
docker inspect --format='{{.State.Health.Status}}' passagens-app
docker inspect --format='{{.State.Health.Status}}' passagens-mysql
```

### Recursos

```bash
# Monitor em tempo real
docker stats

# Uso de disco
docker system df

# Limpar recursos não utilizados
docker system prune -a
```

## 🚢 Deploy em Produção

**⚠️ IMPORTANTE:** Antes de fazer deploy em produção:

1. **Mudar senhas padrão** no `docker-compose.yml`
2. **Configurar HTTPS** (nginx reverse proxy)
3. **Backups regulares** do volume MySQL
4. **Usar secrets** do Docker Swarm ou Kubernetes
5. **Monitoramento** com Prometheus/Grafana
6. **Logs centralizados** (ELK Stack)

### Exemplo de Backup

```bash
# Backup do banco
docker exec passagens-mysql mysqldump -uroot -prootmoluck passagens_rodoviarias > backup.sql

# Restaurar backup
docker exec -i passagens-mysql mysql -uroot -prootmoluck passagens_rodoviarias < backup.sql
```

## 🆘 Suporte

**Equipe 1 - Projeto Integrador**
- Rodrigo
- Gabriel Kauan
- Guilherme Teixeira

**Professor:** Andre Guimarães Portugal

## 📄 Licença

Projeto Acadêmico - Faculdade Cairu - 2025
