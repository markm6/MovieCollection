import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
public class MovieCollection {
    private ArrayList<Movie> movies;
    private Scanner scan = new Scanner(System.in);
    private final String MOVIE_DATA_PATH = "src/movies_data.csv";

    public MovieCollection() {
        movies = new ArrayList<>();
        loadMovies();
        sortMovies(movies);
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
        String[] cast = castUnparsed.split("\\|", 100);
        return new Movie(title, cast, director, overview, runtime, userRating);
    }
    private void loadMovies() {
        File f = new File(MOVIE_DATA_PATH);
        try {
            Scanner fileScanner = new Scanner(f);
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
        
        for (int i = 0; i < movies.size()-1; i++){
            int min = 0;
            for (int k = i+1; k < movies.size(); k++){
                if (movies.get(k).getTitle().compareTo(movies.get(i).getTitle()) < 0){
                    min = k;
                }
            }
            
            Movie temp = movies.get(i);
            movies.set(i, movies.get(min));
            movies.set(min,  temp);
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
            if (movies.get(i).getTitle().contains(term)) {
                titleList.add(movies.get(i));
            }
        }
        System.out.print("What movie would you like to learn more about: ");
        int movie = scan.nextInt();
        movie -= 1;
        System.out.println("Title: " + titleList.get(movie).getTitle() + "\n" + "Runtime: " + titleList.get(movie).getRuntime());
        System.out.println("Cast:" + titleList.get(movie).getCast());
        System.out.println("Overview: " + titleList.get(movie).getOverview());
        System.out.println("User Rating: " + titleList.get(movie).getUserRating());
        System.out.println("** Press enter to return to menu **");
        scan.nextLine();
    }

    public void searchCast(){
        System.out.print("Enter a person to search for (first or last name): ");
        String term = scan.nextLine();
        ArrayList<Movie> searchList = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++){
            for (String name : movies.get(i).getCast()) {
                if (name.contains(term)) {
                    searchList.add(movies.get(i));
                    break;
                }
            }
        }
        System.out.print("Which would you like to see all for?: ");
        int movie = scan.nextInt();
        movie -= 1;
        // Print list of all movie for a given actor
        System.out.println("Title: " + searchList.get(movie).getTitle() + "\n" + "Runtime: " + searchList.get(movie).getRuntime());
        System.out.println("Cast:" + searchList.get(movie).getCast());
        System.out.println("Overview: " + searchList.get(movie).getOverview());
        System.out.println("User Rating: " + searchList.get(movie).getUserRating());
        System.out.println("** Press enter to return to menu **");
        scan.nextLine();
        
        for (int k = 0; k < searchList.size(); k++){
            System.out.println((k + 1) + ". " + searchList.get(k).getTitle());
        }
    }
    
}