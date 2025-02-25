import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
public class MovieCollection {
    private ArrayList<Movie> movies;
    private Scanner scan = new Scanner(System.in);
    private final String MOVIE_DATA_PATH = "src/movies_data.csv";

    public MovieCollection() {
        movies = new ArrayList<>();
        loadMovies();
        for (int i = 0; i < 20; i++) {
            System.out.println(movies.get(i).getTitle());
        }
        sortMovies(movies);
        for (int i = 0; i < 20; i++) {
            System.out.println(movies.get(i).getTitle());
        }

    }
    
    private void printMovieInfo(Movie movie) {
        System.out.println("Title: " + movie.getTitle() + "\n" + "Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Cast: " + movie.stringCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User Rating: " + movie.getUserRating());
    }
    private Movie parseMovie(String line) {
        String temp = line;
        String title = temp.substring(0, temp.indexOf(","));
        temp = temp.substring(temp.indexOf(",") + 1);
        String castUnparsed = temp.substring(0, temp.indexOf(","));
        temp = temp.substring(temp.indexOf(",") + 1);
        String director = temp.substring(0, temp.indexOf(","));
        temp = temp.substring(temp.indexOf(",") + 1);
        String overview = temp.substring(0, temp.indexOf(","));
        temp = temp.substring(temp.indexOf(",") + 1);
        int runtime = Integer.parseInt(temp.substring(0, temp.indexOf(",")));
        temp = temp.substring(temp.indexOf(",") + 1);
        double userRating = Double.parseDouble(temp);
        String[] castArray = castUnparsed.split("\\|", 100);
        ArrayList<String> cast = new ArrayList<>(Arrays.asList(castArray));
        return new Movie(title, cast, director, overview, runtime, userRating);
    }
    private void loadMovies() {
        File f = new File(MOVIE_DATA_PATH);
        try {
            Scanner fileScanner = new Scanner(f);
            fileScanner.nextLine();
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.startsWith("title,cast,director")) {
                    continue; // skip the beginning because it's the only unparseable line
                } else {
                movies.add(parseMovie(line));
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Movies data file not found!");
        }
    }
    
    private void sortMovies(ArrayList<Movie> movies){
        for (int i = 1; i < movies.size(); i++) {  // iterate through list starting at index 1
            Movie temp = movies.get(i);
            int k = i;
            while (k > 0 && movies.get(k - 1).getTitle().compareTo(temp.getTitle()) > 0) {
                movies.set(k, movies.get(k - 1));
                k--;
            }
            movies.set(k, temp);
        }
    }
    
    private void sortActors(ArrayList<String> actors) {
        for (int i = 1; i < actors.size(); i++) {
            String temp = actors.get(i);
        
            int k = i;
            while (k > 0 && actors.get(k - 1).compareTo(temp) > 0) {
                actors.set(k, actors.get(k - 1));
                k--;
            }
            actors.set(k, temp);
        }
    }
         

    public void start(){
        System.out.println("Welcome to the movie collection!");
        String menuOption = "";
        
        while (!menuOption.equals("q")) {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (c)ast");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scan.nextLine();

            if (menuOption.equals("t")) {
                searchTitles();
            } else if (menuOption.equals("c")) {
                searchCast();
            } else if (menuOption.equals("q")) {
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }

    public void searchTitles(){
        System.out.print("Enter a title search term: ");
        String term = scan.nextLine();
        ArrayList<Movie> titleList = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++){
            if (movies.get(i).getTitle().toLowerCase().contains(term.toLowerCase())) {
                titleList.add(movies.get(i));
            }
        }
        if (titleList.size() == 0) {
            System.out.println("No movie titles match that search term!");
            return;
        }
        for (int k = 0; k < titleList.size(); k++){
            System.out.println((k+1) + ". " + titleList.get(k).getTitle());
        }
        System.out.print("What movie would you like to learn more about: ");
        int movie = scan.nextInt();
        movie -= 1;
        printMovieInfo(titleList.get(movie));
        System.out.println("** Press enter to return to menu **");
        scan.nextLine();
    }

    public void searchCast(){
        System.out.print("Enter a person to search for (first or last name): ");
        String term = scan.nextLine();
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++){
            for (String name : movies.get(i).getCast()) {
                if (name.toLowerCase().contains(term.toLowerCase())) {
                    boolean add = true;
                    for (String listName : names) {
                        if (name.equals(listName)) {
                            add = false;
                            break;
                        }
                    }
                    if (add) {
                        names.add(name);
                    }
                }
            }
        }
        if (names.size() == 0) {
            System.out.println("No results match your search");
            return;
        }
        sortActors(names);
        for (int i = 0; i < names.size(); i++) {
            System.out.println((i + 1) + ". " + names.get(i));
        }
        System.out.print("Which would you like to see all movies for?: ");
        int movie = scan.nextInt();
        scan.nextLine();
        movie -= 1;
        // Print list of all movie for a given actor
        ArrayList<Movie> actorMovies = new ArrayList<>();
        for (int k = 0; k < movies.size(); k++){
            for (String actorName : movies.get(k).getCast()) {
                if (actorName.contains(names.get(movie))) {
                    actorMovies.add(movies.get(k));
                    break;
                }
            }
        }
        for (int j = 0; j < actorMovies.size(); j++){
            System.out.println((j+1) + ". " + actorMovies.get(j).getTitle());
        }
        System.out.println("What movie would you like to learn more about?");
        int num = scan.nextInt();
        scan.nextLine();
        num -= 1;
        printMovieInfo(actorMovies.get(num));
        System.out.println("** Press enter to return to menu **");
        scan.nextLine();
        
    //    for (int k = 0; k < searchList.size(); k++){
    //        System.out.println((k + 1) + ". " + searchList.get(k).getTitle());
    //    }
    }
    
}