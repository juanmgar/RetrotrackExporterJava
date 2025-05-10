package retrotrackexporterjava.service;

import retrotrackexporterjava.soap.*;

public class SoapClient {
    public static String login(String username, String password) {
        UserManagementWSService service = new UserManagementWSService();
        IUserManagementWS port = service.getUserManagementWSPort();
        String jwt = port.login(username, password);
        if (jwt.contains("incorrectos")) {
            throw new RuntimeException("Login fallido: " + jwt);
        }
        return jwt;
    }
}
