# Usa una imagen base con JDK 17
FROM eclipse-temurin:17-jdk-alpine

# Instala herramientas necesarias
RUN apk --no-cache add ca-certificates

# Copia el certificado autofirmado al sistema
COPY src/main/resources/localhost.crt /usr/local/share/ca-certificates/localhost.crt
RUN update-ca-certificates

# Copia el certificado también para Java y lo importa en el truststore
COPY localhost.crt /tmp/localhost.crt
RUN keytool -importcert -noprompt \
    -alias localcert \
    -file /tmp/localhost.crt \
    -keystore $JAVA_HOME/lib/security/cacerts \
    -storepass changeit

# Crea un directorio para el app
WORKDIR /app

# Copia el jar generado
COPY target/RetrotrackExporterJava-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar

# Crea la ruta esperada por el código
RUN mkdir -p /home/runner/work/RetrotrackExporterJava/RetrotrackExporterJava/src/main/resources

# Copia el WSDL al path esperado
COPY src/main/resources/user.wsdl /home/runner/work/RetrotrackExporterJava/RetrotrackExporterJava/src/main/resources/user.wsdl

# Comando por defecto (sobrescribible desde docker run)
ENTRYPOINT ["java", "-jar", "app.jar"]
