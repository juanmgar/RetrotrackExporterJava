package retrotrackexporterjava.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserGameCollection {
    public int id;
    public int gameId;
    public String user;

    @Override
    public String toString() {
        return id + "," + gameId + "," + user;
    }
}
