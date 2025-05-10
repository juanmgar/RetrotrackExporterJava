package retrotrackexporterjava;

import retrotrackexporterjava.model.*;
import retrotrackexporterjava.service.*;

import java.io.FileWriter;
import java.util.List;

public class RetrotrackExporter {
    public static void main(String[] args) throws Exception {

        // Para permitir el uso de https sin un certificado firmado, he deshabilitado la validación.
        SSLUtil.disableCertificateValidation();
        String email = null;
        for (int i = 0; i < args.length; i++) {
            if ("--email".equals(args[i]) && i + 1 < args.length) {
                email = args[i + 1];
                break;
            }
        }
        if (email == null) {
            System.err.println("Falta el argumento --email usuario@correo.com");
            System.exit(1);
        }

        String jwt = SoapClient.login("admin", "a1234");
        List<Game> games = RestClient.getGames(jwt);
        List<UserGameCollection> collections = RestClient.getUserGameCollections(jwt);
        List<GameSession> sessions = RestClient.getGameSessions(jwt);

        generateCsv("games.csv", games);
        generateCsv("collections.csv", collections);
        generateCsv("sessions.csv", sessions);

        String[] archivos = {"games.csv", "collections.csv", "sessions.csv"};
        EmailSender.sendEmail(email, "Export RetroTrack", "Adjunto los backups de RetroTrack.", archivos);

        System.out.println("Archivos generados y envío a " + email);
    }

    static void generateCsv(String filename, List<?> data) throws Exception {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Datos exportados:\n");
            for (Object obj : data) {
                writer.write(obj.toString() + "\n");
            }
        }
    }
}
