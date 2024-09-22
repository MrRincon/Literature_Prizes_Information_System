//Student ID: M00774667
package task1;

//Import any essential packages to use in this class
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.TreeSet;

public class LoadMenu {

    private static LoadMenu instance;

    //Private constructor to disallow creation of other object instances of LoadMenu outside of this class 
    private LoadMenu() {
    }

    //Returns a new LoadMenu if one does not exist, or the previously created object if it does exist
    public static LoadMenu getLoaderInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new LoadMenu();
            return instance;
        }
    }

    //Method to print the menu
    public void mainMenu() {
        System.out.printf("%-22s%n", "----------------------");
        System.out.printf("%-22s%n", "Literature Prize Menu ");
        System.out.printf("%-22s%n", "----------------------");
        System.out.printf("%-22s%n", "List ................1");
        System.out.printf("%-22s%n", "Select ..............2");
        System.out.printf("%-22s%n", "Search ..............3");
        System.out.printf("%-22s%n", "Exit.................0");
        System.out.printf("%-22s%n", "----------------------");
    }

    //Method to process the menu choice
    public boolean processChoice(int choice, ArrayList<LiteraturePrize> allYearObjs) {
        switch (choice) {
            case 1: 
                listPrizeWinners(allYearObjs);
                return true;
            case 2:
                selectYear(allYearObjs);
                return true;
            case 3:
                searchByGenres(allYearObjs);
                return true;
            case 0:
                System.out.println("Exiting...");
                System.exit(0);
            default:
                return false;
        }
    }

    //Method to find and display all the prize winners for the years requested 
    public void listPrizeWinners(ArrayList<LiteraturePrize> allYearObjs) {
        int startYear = 0;
        int endYear = 0;
        boolean validInput = false;
        Scanner inputScan = new Scanner(System.in);
        do {
            try {
                //Start year and end year user inputs
                System.out.print("Enter start year > ");
                startYear = inputScan.nextInt();
                System.out.print("Enter end year > ");
                endYear = inputScan.nextInt();
                //Check to limit the search to the years stored only
                if (startYear < allYearObjs.get(0).getYear()
                        || endYear > allYearObjs.get(allYearObjs.size() - 1).getYear()
                        || startYear > endYear) {
                    System.out.println("Invalid year range.");
                } else {
                    //Display prize winners header
                    System.out.printf("------------------------------------------------------------------------------\n");
                    System.out.printf("| %4s | %-68s|\n", "Year", "Prize winners (and associated nations)");
                    System.out.printf("------------------------------------------------------------------------------\n");
                    //Loop through the year range
                    for (int year = startYear; year <= endYear; year++) {
                        //Then loop through all the prize objs in each year 
                        for (LiteraturePrize prize : allYearObjs) {
                            /*Print the year of the prize and the name of the author with the author's 
                            nationaliy if that matches to the year in range*/
                            if (prize.getYear() == year) {
                                String prizeWinners = prize.toString();
                                //If prize winners is empty, print the not awarded message. If not empty, print prize winner
                                System.out.printf("| %-4d | %-68s|\n", year, prizeWinners.isEmpty() ? "NOT AWARDED" : prizeWinners);
                            }
                        }
                    }
                    System.out.println("------------------------------------------------------------------------------");
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid year");
                inputScan.nextLine();
            }
        } while (!validInput);
    }

    //Method to select the year and display the prize winner for that year 
    public void selectYear(ArrayList<LiteraturePrize> allYearObjs) {
        Scanner inputScan = new Scanner(System.in);
        int selectedYear = 0;
        boolean validInput = false;
        do {
            try {
                //Year input 
                System.out.print("Enter year of prize > ");
                selectedYear = inputScan.nextInt();
                //Check if selected year is stored
                if (selectedYear < allYearObjs.get(0).getYear()
                        || selectedYear > allYearObjs.get(allYearObjs.size() - 1).getYear()) {
                    System.out.println("Information for this year not found in the system");
                    break;
                } else {
                    //Find the selected year in the list of LiteraturePrize objects
                    for (LiteraturePrize prize : allYearObjs) {
                        if (prize.getYear() == selectedYear) {
                            //Check if there are laureates winners for the prize
                            if (!prize.getLaureates().isEmpty()) {
                                //Display a header for the prize information
                                System.out.printf("----------------------------------------------------------------------------------------\n");
                                System.out.printf("| %-23s| %-6s| %-6s| %-21s| %-21s|%n", "Winner(s)", "Born", "Died", "Language(s)", "Genre(s)");
                                System.out.printf("----------------------------------------------------------------------------------------\n");
                                //Display the information for each laureate that was awarded
                                prize.getLaureates().forEach(laureate -> {
                                    System.out.print(laureate.toString());
                                });
                            } else {
                                System.out.println("Prize had no winner(s) this year");
                            }
                        }
                    }
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. please enter a valid year");
                inputScan.nextLine();
            }
        } while (!validInput);
    }

    //Method to search and display prize winners by genre
    public void searchByGenres(ArrayList<LiteraturePrize> allYearObjs) {
        Scanner input = new Scanner(System.in);
        String searchedGenre;
        //Check if the input is not empty or if it is only empty spaces
        do {
            System.out.print("Search genre > ");
            searchedGenre = input.nextLine().trim().toLowerCase();
        } while (searchedGenre.isEmpty());
        //Display the header for the search results
        System.out.printf("----------------------------------------------------------------------------------------------------------------------------\n");
        System.out.printf("| %-30s | %-80s | %-4s |%n", "Name", "Genres", "Year");
        System.out.printf("----------------------------------------------------------------------------------------------------------------------------\n");
        boolean noMatchesFound = true;
        TreeSet<String> ts = new TreeSet<>();
        //Loop through all the literature prizes obj and the Laureates for each prize
        for (LiteraturePrize prize : allYearObjs) {
            for (Laureate laureate : prize.getLaureates()) {
                boolean genreMatched = false;
                StringBuilder matchedGenres = new StringBuilder();
                //Loop through all the genres of the Laureate and check if the any genre matches with the search
                for (String genre : laureate.getGenres()) {
                    if (genre.toLowerCase().contains(searchedGenre)) {
                        genreMatched = true;
                        //If a word containing the search input is found, replace the search input part to be uppercase
                        matchedGenres.append(genre.replaceAll("(?i)" + searchedGenre, searchedGenre.toUpperCase())).append(",");
                    } else {
                        matchedGenres.append(genre).append(",");
                    }
                }
                //If a match was found for the searched genre
                if (genreMatched) {
                    noMatchesFound = false;
                    matchedGenres.setLength(matchedGenres.length() - 1); // Remove comma
                    //Display laureate's name, genres, and the year of the prize
                    ts.add(String.format("| %-30s | %-80s | %-4d |%n", laureate.getName(), matchedGenres.toString(), prize.getYear()));
                }
            }
        }
        ts.forEach(item -> {
            System.out.print(item);
        });
        if (noMatchesFound) {
            System.out.printf("| %-120s |%n", "No winners found matching the genre search.");
        }
        System.out.printf("----------------------------------------------------------------------------------------------------------------------------\n");
    }
}