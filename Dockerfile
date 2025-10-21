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

# Copiar o WAR da aplicação do estágio de build
COPY --from=builder /app/target/passagens-rodoviarias.war /usr/local/tomcat/webapps/passagens-rodoviarias.war

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
