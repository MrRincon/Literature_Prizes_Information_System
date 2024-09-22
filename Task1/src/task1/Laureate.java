//Student ID: M00774667
package task1;

//Import any essential packages to use in this class
import java.util.ArrayList;
import java.util.Arrays;

public class Laureate {

    private String name;
    private ArrayList<String> birth_death;
    private ArrayList<String> nations;
    private ArrayList<String> languages;
    private String citation;
    private ArrayList<String> genres;

    public Laureate(String name, ArrayList<String> birth_death, ArrayList<String> nations, ArrayList<String> languages, String citation, ArrayList<String> genres) {//Create a constructor
        this.name = name;
        this.birth_death = birth_death;
        this.nations = nations;
        this.languages = languages;
        this.citation = citation;
        this.genres = genres;
    }

    //Getter methods to return the attributes
    public String getName() {
        return name;
    }

    public ArrayList<String> getBirth_death() {
        return birth_death;
    }

    public ArrayList<String> getNations() {
        return nations;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }

    public String getCitation() {
        return citation;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    //toString method to return the Laureate attributes
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("| %-23s| %-6s| %-6s| %-21s| %-21s|%n", this.name, this.birth_death.get(0), this.birth_death.get(1), this.languages.get(0), this.genres.get(0)));
        int maxLoop = Math.max(genres.size(), languages.size()); // Grab the max list.
        for (int i = 1; i < maxLoop; i++) {
            sb.append(String.format("| %-23s| %-6s| %-6s|", "", "", ""));
            // Check if there's another language to append.
            if (i < languages.size()) {
                sb.append(String.format(" %-21s|", languages.get(i)));
            } else {
                sb.append(String.format(" %-21s|", ""));
            }
            // Check if there's another genre to append.
            if (i < genres.size()) {
                sb.append(String.format("%-22s|%n", genres.get(i)));
            } else {
                sb.append(String.format("%-22s|%n", ""));
            }
        }
        sb.append("----------------------------------------------------------------------------------------\n");
        sb.append(String.format("| %-35s%-15s%-35s|%n", "", "Citation:", ""));
        sb.append(framedCitation());
        sb.append("----------------------------------------------------------------------------------------\n");
        return sb.toString();
    }
    
    //Method to format and frame the citation
    private StringBuilder framedCitation() {
        int totalLineWidth = 87;
        //Split citation into words and store them in an ArrayList
        ArrayList<String> wordsList = new ArrayList<>(Arrays.asList(citation.split(" ")));
        StringBuilder currentLineBuilder = new StringBuilder(), fullCitationBuilder = new StringBuilder();
        int citationTextWidth = totalLineWidth - 20;
        int sidePadding;
        //Iterate over each word in the wordList
        for (String currentWord : wordsList) {
            //Check if currentWord exceeds the citationTextWidth 
            if ((currentLineBuilder.length() + currentWord.length() + 1) > citationTextWidth) {
                sidePadding = (totalLineWidth - currentLineBuilder.length()) / 2; 
                //Append the formatted line to fullCitationBuilder
                if ((sidePadding * 2) + currentLineBuilder.length() != totalLineWidth) {
                    fullCitationBuilder.append(String.format("|%" + sidePadding + "s%s%" + (sidePadding + 1) + "s|%n", "", currentLineBuilder.toString().trim(), ""));
                } else {
                    fullCitationBuilder.append(String.format("|%" + sidePadding + "s%s%" + sidePadding + "s|%n", "", currentLineBuilder.toString().trim(), ""));
                }
                //Clear currentLine for the next line
                currentLineBuilder.setLength(0);
            }
            //Append currentWord to currentLineBuilder
            currentLineBuilder.append(currentWord).append(" ");
        }
        //If currentLineBuilder has remaining words, add them to fullCitationBuilder
        if (currentLineBuilder.length() > 0) {
            sidePadding = (totalLineWidth - currentLineBuilder.length()) / 2;
            if ((sidePadding * 2) + currentLineBuilder.length() != 87) {
                fullCitationBuilder.append(String.format("|%" + sidePadding + "s%s%" + (sidePadding + 1) + "s|%n", "", currentLineBuilder.toString().trim(), ""));
            } else {
                fullCitationBuilder.append(String.format("|%" + sidePadding + "s%s%" + sidePadding + "s|%n", "", currentLineBuilder.toString().trim(), ""));
            }
        }
        return fullCitationBuilder;
    }
}
