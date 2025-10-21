#!/bin/bash

# Script para parar a aplicação Docker
# Autor: Equipe 1 - Projeto Integrador

echo "========================================="
echo "  Parando Sistema de Passagens"
echo "========================================="
echo ""

# Parar containers
echo "Parando containers..."
docker-compose down

echo ""
echo "Containers parados com sucesso!"
echo ""
echo "Para remover volumes (APAGA DADOS DO BANCO):"
echo "  docker-compose down -v"
echo ""
