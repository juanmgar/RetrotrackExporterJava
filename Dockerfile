# Usa una imagen base con JDK 17
FROM eclipse-temurin:17-jdk-alpine

# Crea un directorio para el app
WORKDIR /app

# Copia el jar generado
COPY target/RetrotrackExporterJava-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar

# Copia el archivo WSDL al contenedor
COPY src/main/resources/user.wsdl /app/user.wsdl

# Comando por defecto (sobrescribible desde docker run)
ENTRYPOINT ["java", "-jar", "app.jar"]
