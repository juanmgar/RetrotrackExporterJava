package retrotrackexporterjava.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import retrotrackexporterjava.model.*;

import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.MediaType;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;

public class RestClient {
    private static final String BASE_URL = "https://localhost:9095/rest/api/";

    public static List<Game> getGames(String jwt) throws Exception {
        String json = callApi("Games", jwt);
        return parseList(json, Game.class);
    }

    public static List<UserGameCollection> getUserGameCollections(String jwt) throws Exception {
        String json = callApi("UserGameCollections", jwt);
        return parseList(json, UserGameCollection.class);
    }

    public static List<GameSession> getGameSessions(String jwt) throws Exception {
        String json = callApi("GameSessions", jwt);
        return parseList(json, GameSession.class);
    }

    private static String callApi(String path, String jwt) {
        //Client client = ClientBuilder.newClient();
        Client client = getUnsafeClient();
        return client.target(BASE_URL + path)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + jwt)
                .get(String.class);
    }

    private static <T> List<T> parseList(String json, Class<T> clazz) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
        return mapper.readValue(json, listType);
    }

    public static Client getUnsafeClient() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{ new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs, String string) {}
                public void checkServerTrusted(X509Certificate[] xcs, String string) {}
                public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
            } }, new SecureRandom());

            return ClientBuilder.newBuilder()
                    .sslContext(sslContext)
                    .hostnameVerifier((s, sslSession) -> true)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
