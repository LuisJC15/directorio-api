# Etapa 1 — Construcción con Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copiar pom y dependencias para cache
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código
COPY src ./src

# Compilar y empaquetar sin tests
RUN mvn clean package -DskipTests


# Etapa 2 — Imagen final (run time)
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copiar el JAR desde la etapa 1
COPY --from=builder /app/target/*.jar app.jar

# Exponer puerto 8080
EXPOSE 8080

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]
