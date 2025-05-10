package retrotrackexporterjava.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GameSession {
    public int id;
    public int gameId;
    public String playerId;
    public String playedAt;
    public int minutesPlayed;

    @Override
    public String toString() {
        return id + "," + gameId + "," + playerId + "," + playedAt + "," + minutesPlayed;
    }
}
