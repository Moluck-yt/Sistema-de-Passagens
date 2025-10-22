#!/bin/bash
set -e

# Script para substituir variáveis de ambiente no persistence.xml

echo "Configurando persistence.xml com variáveis de ambiente..."

PERSISTENCE_FILE="/usr/local/tomcat/webapps/passagens-rodoviarias/WEB-INF/classes/META-INF/persistence.xml"

# Aguardar o WAR ser extraído
echo "Aguardando deploy do WAR..."
timeout=60
while [ ! -f "$PERSISTENCE_FILE" ] && [ $timeout -gt 0 ]; do
    sleep 1
    timeout=$((timeout-1))
done

if [ -f "$PERSISTENCE_FILE" ]; then
    echo "Substituindo variáveis no persistence.xml..."

    # Substituir variáveis de ambiente
    sed -i "s/\${DB_HOST:localhost}/${DB_HOST:-localhost}/g" "$PERSISTENCE_FILE"
    sed -i "s/\${DB_PORT:3306}/${DB_PORT:-3306}/g" "$PERSISTENCE_FILE"
    sed -i "s/\${DB_NAME:passagens_rodoviarias}/${DB_NAME:-passagens_rodoviarias}/g" "$PERSISTENCE_FILE"
    sed -i "s/\${DB_USER:root}/${DB_USER:-root}/g" "$PERSISTENCE_FILE"
    sed -i "s/\${DB_PASSWORD:rootmoluck}/${DB_PASSWORD:-rootmoluck}/g" "$PERSISTENCE_FILE"

    echo "Variáveis substituídas com sucesso!"
    echo "DB_HOST=${DB_HOST:-localhost}"
    echo "DB_PORT=${DB_PORT:-3306}"
    echo "DB_NAME=${DB_NAME:-passagens_rodoviarias}"
else
    echo "AVISO: persistence.xml não encontrado. Usando configurações padrão."
fi

# Iniciar Tomcat
echo "Iniciando Tomcat..."
exec catalina.sh run
