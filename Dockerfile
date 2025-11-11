# Multi-stage build para otimizar o tamanho da imagem

# Stage 1: Build da aplicação com Maven
FROM maven:3.9-eclipse-temurin-11 AS builder

WORKDIR /app

# Copiar pom.xml primeiro para aproveitar cache do Docker
COPY pom.xml .

# Baixar dependências (cache layer)
RUN mvn dependency:go-offline -B

# Copiar código fonte
COPY src ./src

# Build da aplicação
RUN mvn clean package -DskipTests

# Stage 2: Imagem runtime com Tomcat
FROM tomcat:10.1-jdk11

# Remover aplicações default do Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Instalar unzip para manipular o WAR
RUN apt-get update && apt-get install -y unzip && rm -rf /var/lib/apt/lists/*

# Copiar o WAR da aplicação do estágio de build
COPY --from=builder /app/target/passagens-rodoviarias.war /tmp/passagens-rodoviarias.war

# Extrair WAR, modificar persistence.xml e reempacotar
WORKDIR /tmp
RUN unzip -q passagens-rodoviarias.war -d passagens-rodoviarias && \
    cd passagens-rodoviarias/WEB-INF/classes/META-INF && \
    sed -i 's/\${DB_HOST:localhost}/mysql-container/g' persistence.xml && \
    sed -i 's/\${DB_PORT:3306}/3306/g' persistence.xml && \
    sed -i 's/\${DB_NAME:passagens_rodoviarias}/passagens_rodoviarias/g' persistence.xml && \
    sed -i 's/\${DB_USER:root}/root/g' persistence.xml && \
    sed -i 's/\${DB_PASSWORD:rootmoluck}/rootmoluck/g' persistence.xml && \
    cd /tmp/passagens-rodoviarias && \
    jar -cf /usr/local/tomcat/webapps/passagens-rodoviarias.war * && \
    cd /tmp && rm -rf passagens-rodoviarias passagens-rodoviarias.war

# Voltar para o diretório do Tomcat
WORKDIR /usr/local/tomcat

# Criar diretório ROOT e página de redirecionamento
RUN mkdir -p /usr/local/tomcat/webapps/ROOT && \
    echo '<!DOCTYPE html>' > /usr/local/tomcat/webapps/ROOT/index.html && \
    echo '<html>' >> /usr/local/tomcat/webapps/ROOT/index.html && \
    echo '<head>' >> /usr/local/tomcat/webapps/ROOT/index.html && \
    echo '    <meta charset="UTF-8">' >> /usr/local/tomcat/webapps/ROOT/index.html && \
    echo '    <meta http-equiv="refresh" content="0; url=/passagens-rodoviarias/">' >> /usr/local/tomcat/webapps/ROOT/index.html && \
    echo '    <title>Redirecionando...</title>' >> /usr/local/tomcat/webapps/ROOT/index.html && \
    echo '</head>' >> /usr/local/tomcat/webapps/ROOT/index.html && \
    echo '<body>' >> /usr/local/tomcat/webapps/ROOT/index.html && \
    echo '    <p>Redirecionando para o Sistema de Passagens Rodoviárias...</p>' >> /usr/local/tomcat/webapps/ROOT/index.html && \
    echo '    <p>Se não for redirecionado automaticamente, <a href="/passagens-rodoviarias/">clique aqui</a>.</p>' >> /usr/local/tomcat/webapps/ROOT/index.html && \
    echo '</body>' >> /usr/local/tomcat/webapps/ROOT/index.html && \
    echo '</html>' >> /usr/local/tomcat/webapps/ROOT/index.html

# Expor porta 8080
EXPOSE 8080

# Configurar variáveis de ambiente para JVM
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseG1GC"

# Label com metadados
LABEL maintainer="Equipe 1 - Projeto Integrador"
LABEL description="Sistema de Passagens Rodoviárias - Java EE com JSF"
LABEL version="1.0"

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/passagens-rodoviarias/ || exit 1

# Comando para iniciar o Tomcat
CMD ["catalina.sh", "run"]
