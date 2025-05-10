package retrotrackexporterjava.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {
    public int id;
    public String title;
    public String platform;
    public String genre;
    public String screenshotUrl;
    public String description;

    @Override
    public String toString() {
        return id + "," + title + "," + platform + "," + genre + "," + screenshotUrl + "," + description;
    }
}
