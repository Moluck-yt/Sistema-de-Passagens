#!/bin/bash

# Script para build e deploy da aplicação com Docker
# Autor: Equipe 1 - Projeto Integrador

set -e  # Parar em caso de erro

echo "========================================="
echo "  Build e Deploy - Sistema de Passagens"
echo "========================================="
echo ""

# Cores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Função para imprimir mensagens coloridas
print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

print_info() {
    echo -e "${YELLOW}ℹ $1${NC}"
}

# 1. Parar containers existentes (se houver)
print_info "Parando containers existentes..."
docker-compose down 2>/dev/null || true
print_success "Containers parados"

# 2. Limpar volumes antigos (opcional - comentado por segurança)
# print_info "Removendo volumes antigos..."
# docker volume rm passagens_mysql_data 2>/dev/null || true

# 3. Build da aplicação
print_info "Construindo imagem Docker..."
docker-compose build --no-cache
print_success "Imagem construída com sucesso"

# 4. Iniciar containers
print_info "Iniciando containers..."
docker-compose up -d
print_success "Containers iniciados"

# 5. Aguardar inicialização
print_info "Aguardando inicialização dos serviços..."
echo ""
echo "Aguardando MySQL..."
sleep 10

echo "Aguardando aplicação..."
for i in {1..30}; do
    if curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/passagens-rodoviarias/ | grep -q "200\|302"; then
        print_success "Aplicação disponível!"
        break
    fi
    echo -n "."
    sleep 2
done
echo ""

# 6. Mostrar status dos containers
print_info "Status dos containers:"
docker-compose ps

echo ""
echo "========================================="
print_success "Deploy concluído com sucesso!"
echo "========================================="
echo ""
echo "Acesse a aplicação em: http://localhost:8080/passagens-rodoviarias/"
echo ""
echo "Credenciais de acesso:"
echo "  Login: admin"
echo "  Senha: 123456"
echo ""
echo "Comandos úteis:"
echo "  - Ver logs da aplicação:  docker-compose logs -f app"
echo "  - Ver logs do MySQL:      docker-compose logs -f mysql"
echo "  - Parar containers:       docker-compose down"
echo "  - Reiniciar:              docker-compose restart"
echo ""
