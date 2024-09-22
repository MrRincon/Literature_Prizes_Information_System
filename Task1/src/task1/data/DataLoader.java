//Student ID: M00774667
package task1.data;

//Import any essential packages to use in this class
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
//Import Laureate and LiteraturePrize classes to create the objects
import task1.Laureate;
import task1.LiteraturePrize;

public class DataLoader {

    private static DataLoader instance;//Static variable referring to the single created instance of DataLoader
    private final String filePath = System.getProperty("user.dir");
    private final String ipFile = filePath + File.separator + "literature-prizes.txt";
    private final ArrayList<LiteraturePrize> allYears;

    //Private constructor to disallow creation of other object instances of DataLoader outside of this class
    private DataLoader() {
        this.allYears = new ArrayList<>();
    }

    //Returns a new DataLoader if one does not exist or the previously created object if it does exist
    public static DataLoader getLoaderInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new DataLoader();
            return instance;
        }
    }

    //Read the lines from the data file and parses the data
    public void readLines() {
        File file = new File(ipFile);
        try {
            Scanner scanner = new Scanner(file);
            //Splits the data into blocks when found with this delimiter
            scanner.useDelimiter("-----");
            while (scanner.hasNext()) {
                String block = scanner.next();
                //Creates a new block obj to be parsed
                DataParser parseData = new DataParser(block);
                ArrayList<Laureate> awardedTo = new ArrayList<>();
                //Checks in the block if the prize for that year has been awarded
                if (parseData.checkIfAwarded()) {
                    for (int i = 0; i < (parseData.extractName().size()); i++) {
                        //If awarded, loop through all the elements in the ArrayLists to get the Laureates values to create a Laureate obj
                        Laureate laureate = new Laureate(
                                parseData.extractName().get(i),
                                parseData.extractBirthDeath().get(i),
                                parseData.extractCountry().get(i),
                                parseData.extractLanguage().get(i),
                                parseData.extractCitation().get(i),
                                parseData.extractGenres().get(i)
                        );
                        //Add the Laureate obj found to an ArrayList
                        awardedTo.add(laureate);
                    }
                    //Create LiteraturePrize obj using the year for that block and the ArrayList of Laureate objs
                    LiteraturePrize NewPrize = new LiteraturePrize(
                            parseData.extractYear(),
                            awardedTo
                    );
                    //Add LiteraturePrize to an ArrayList to get all the LiteraturePrize objs
                    this.allYears.add(NewPrize);
                } else {
                    //If not awarded, Create LiteraturePrize obj using the year for that block and an empty ArrayList
                    LiteraturePrize NewPrize = new LiteraturePrize(
                            parseData.extractYear(),
                            awardedTo
                    );
                    this.allYears.add(NewPrize);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    //Getter method to get all the LiteraturePrize objs
    public ArrayList<LiteraturePrize> getYearObjs() {
        return this.allYears;
    }
}
