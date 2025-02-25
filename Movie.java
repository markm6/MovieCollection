import java.util.ArrayList;

public class Movie {
    private String title;
    private ArrayList<String> cast;
    private String director;
    private String overview;
    private int runtime;
    private double userRating;
    public Movie(String title, ArrayList<String> cast, String director, String overview, int runtime, double userRating) {
        this.title = title;
        this.cast = cast;
        this.director = director;
        this.runtime = runtime;
        this.overview = overview;
        this.userRating = userRating;
    }
    public ArrayList<String> getCast() {
        return cast;
    }
    public String getDirector() {
        return director;
    }
    public String getOverview() {
        return overview;
    }
    public int getRuntime() {
        return runtime;
    }
    public String getTitle() {
        return title;
    }
    public double getUserRating() {
        return userRating;
    }
    public String stringCast() {
        String c = "" + cast;
        return c.substring(1, c.length() - 1);
    }
}
