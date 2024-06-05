# Estágio de construção
FROM maven:3.8.5-openjdk-17-slim AS build

# Defina o diretório de trabalho no contêiner
WORKDIR /app

# Copie o arquivo pom.xml e o código-fonte
COPY pom.xml .
COPY src ./src

# Baixe as dependências e compile o aplicativo
RUN mvn clean package -DskipTests

# Estágio final
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho no contêiner
WORKDIR /app

# Copie o JAR do estágio de construção
COPY --from=build /app/target/*.jar app.jar

# Defina a variável de ambiente PORT
ENV PORT 8080

# Exponha a porta 8080
EXPOSE 8080

# Comando para executar a aplicação
CMD ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]
