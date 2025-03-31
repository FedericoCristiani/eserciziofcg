# Usa un'immagine di Maven per la build
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Imposta la cartella di lavoro
WORKDIR /app

# Copia il progetto
COPY . .

# Costruisce il JAR
RUN mvn clean package -DskipTests

# Usa un'immagine leggera di JDK per eseguire il JAR
FROM openjdk:17-jdk-slim

# Imposta la cartella di lavoro
WORKDIR /app

# Copia il JAR dalla fase di build
COPY --from=build /app/target/*.jar eserciziofcg-0.0.1.jar

COPY scripts/wait-for-it.sh /app/wait-for-it.sh

RUN chmod +x /app/wait-for-it.sh

EXPOSE 8080

# Comando per avviare l'applicazione
ENTRYPOINT ["/bin/sh", "-c", "/app/wait-for-it.sh mariadb:3306 -- java -jar eserciziofcg-0.0.1.jar"]

#ENTRYPOINT ["java", "-jar", "eserciziofcg-0.0.1.jar"]