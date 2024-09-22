//Student ID: M00774667
package task1.data;

//Import any essential packages to use in this class
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
import java.util.ArrayList;

public class DataParser {

    private String dBlock;//Data block to parse
    private boolean awarded = true;//Boolean to check if the prize was awarded
    private String yearRegex = "\\d{4}\\s*";//Regex to check the year
    private String notAwardedRegex = "Not awarded\\s*";//Regex to check if the second line says "Not awarded"
    private String secondLineRegex = "(.*?)\\s*(?:\\((b\\.\\s*(\\d{4}))\\)|\\((\\d{4}-\\d{4})\\)|\\((\\d{4}-\\d{3})\\))\\|([^\n]+)\\|([^\n]+)\\s*";//Regex for the second line of the block and splitted into groups
    private String citationLineRegex = "\"(.*?)\"";//Regex to match the third line
    private String genresLineRegex = "^[a-zA-Z ,]+$";//Regex to match the fourth line

    public DataParser(String block) {//Create a constructor
        this.dBlock = block;
    }

    //Extract the year from the first line
    public int extractYear() {
        Scanner scanner = new Scanner(this.dBlock);
        int yearToExtract = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Pattern yearPattern = Pattern.compile(this.yearRegex);
            Matcher yearMatcher = yearPattern.matcher(line); //Match the line including spaces
            if (yearMatcher.matches()) {
                Pattern onlyYearPattern = Pattern.compile("\\d{4}");
                Matcher onlyYearMatcher = onlyYearPattern.matcher(line);//Match only the numbers of the line
                if (onlyYearMatcher.find()) {
                    String number = onlyYearMatcher.group(0);
                    yearToExtract = Integer.parseInt(number);//Extract the numbers as integers and return them
                }
            }
        }
        scanner.close();
        return yearToExtract;
    }

    //Check the second line of the block to see if is not awarded
    public boolean checkIfAwarded() {
        Scanner scanner = new Scanner(this.dBlock);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Pattern notAwardedPattern = Pattern.compile(this.notAwardedRegex);
            Matcher notAwardedMatcher = notAwardedPattern.matcher(line);
            if (notAwardedMatcher.matches()) {
                this.awarded = false;//if second line matches, return false
            }
        }
        scanner.close();
        return this.awarded;
    }

    //Extract all the names of the people awarded for the year in the block
    public ArrayList<String> extractName() {
        Scanner scanner = new Scanner(this.dBlock);
        ArrayList<String> namesToExtract = new ArrayList<>();//Create an array to return all the names  
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Pattern secondLinePattern = Pattern.compile(this.secondLineRegex);
            Matcher secondLineMatcher = secondLinePattern.matcher(line);
            if (secondLineMatcher.find()) {//Match the second line, find, extract and return the group for the name in an ArrayList
                namesToExtract.add(secondLineMatcher.group(1));
            }
        }
        scanner.close();
        return namesToExtract;
    }

    //Extract the all the years of birth and possible years of death
    public ArrayList<ArrayList<String>> extractBirthDeath() {
        Scanner scanner = new Scanner(this.dBlock);
        ArrayList<ArrayList<String>> doBdoDToExtract = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Pattern secondLinePattern = Pattern.compile(this.secondLineRegex);
            Matcher secondLineMatcher = secondLinePattern.matcher(line);
            if (secondLineMatcher.find()) {
                if (secondLineMatcher.group(3) != null) {//if group 3 matches
                    ArrayList<String> dobArrayL = new ArrayList<>();
                    dobArrayL.add(secondLineMatcher.group(3));//Store year of birth
                    dobArrayL.add("----");
                    doBdoDToExtract.add(dobArrayL);
                } else if (secondLineMatcher.group(4) != null) {//if group 4 matches
                    String[] dates = secondLineMatcher.group(4).split("-");//Split and store year of birth and year of death
                    ArrayList<String> dobdodArrayL = new ArrayList<>();
                    for (String date : dates) {
                        dobdodArrayL.add(date);
                    }
                    doBdoDToExtract.add(dobdodArrayL);
                } else if (secondLineMatcher.group(5) != null) {//if group 5 matches
                    String[] dates = secondLineMatcher.group(5).split("-");//Split and store year of birth and year of death
                    ArrayList<String> dobdodArrayL = new ArrayList<>();
                    for (String date : dates) {
                        dobdodArrayL.add(date);
                    }
                    doBdoDToExtract.add(dobdodArrayL);
                }
            }
        }
        scanner.close();
        return doBdoDToExtract; //return all the ArrayLists of DoB or DoB-DoD 
    }

    //Extract all the countries
    public ArrayList<ArrayList<String>> extractCountry() {
        Scanner scanner = new Scanner(this.dBlock);
        ArrayList<ArrayList<String>> countriesToExtract = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Pattern secondLinePattern = Pattern.compile(this.secondLineRegex);
            Matcher secondLineMatcher = secondLinePattern.matcher(line);
            if (secondLineMatcher.find()) {
                String[] countries = secondLineMatcher.group(6).split(",");//Split and store all the countries 
                ArrayList<String> countriesArrayL = new ArrayList<>();
                for (String country : countries) {
                    countriesArrayL.add(country);
                }
                countriesToExtract.add(countriesArrayL);
            }
        }
        scanner.close();
        return countriesToExtract;//return all the ArrayLists of the countries
    }

    //Extract all the languages
    public ArrayList<ArrayList<String>> extractLanguage() {
        Scanner scanner = new Scanner(this.dBlock);
        ArrayList<ArrayList<String>> languagesToExtract = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Pattern secondLinePattern = Pattern.compile(this.secondLineRegex);
            Matcher secondLineMatcher = secondLinePattern.matcher(line);
            if (secondLineMatcher.find()) {
                String[] languages = secondLineMatcher.group(7).split(",");//Split and store all the languages
                ArrayList<String> languagesArrayL = new ArrayList<>();
                for (String language : languages) {
                    languagesArrayL.add(language);
                }
                languagesToExtract.add(languagesArrayL);
            }
        }
        scanner.close();
        return languagesToExtract;//return all the ArrayLists of the languages
    }

    //Extract all the citations
    public ArrayList<String> extractCitation() {
        Scanner scanner = new Scanner(this.dBlock);
        ArrayList<String> citationToExtract = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Pattern citationLinePattern = Pattern.compile(this.citationLineRegex);
            Matcher citationLineMatcher = citationLinePattern.matcher(line);
            if (citationLineMatcher.matches()) {
                citationToExtract.add(line);//Match the citation line
            }
        }
        scanner.close();
        return citationToExtract;//Return all the citations
    }

    //Extract all the genres
    public ArrayList<ArrayList<String>> extractGenres() {
        Scanner scanner = new Scanner(this.dBlock);
        ArrayList<ArrayList<String>> genresToExtract = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Pattern genresLinePattern = Pattern.compile(this.genresLineRegex);
            Matcher genresLineMatcher = genresLinePattern.matcher(line);
            if (genresLineMatcher.matches()) {
                String[] genres = line.split(",");//Spli and store al the genres
                ArrayList<String> genresArrayL = new ArrayList<>();
                for (String genre : genres) {
                    genresArrayL.add(genre);
                }
                genresToExtract.add(genresArrayL);
            }
        }
        scanner.close();
        return genresToExtract;//Return all the ArrayLists of genres
    }
}
