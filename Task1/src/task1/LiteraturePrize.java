//Student ID: M00774667
package task1;

//Import any essential packages to use in this class
import java.util.ArrayList;

public class LiteraturePrize {

    private int year;
    private ArrayList<Laureate> laureates;

    public LiteraturePrize(int year, ArrayList<Laureate> lauretes) {//Create a constructor
        this.year = year;
        this.laureates = lauretes;
    }

    //Getter methods to return the attributes
    public int getYear() {
        return year;
    }

    public ArrayList<Laureate> getLaureates() {
        return laureates;
    }

    //toString method to return a String with the Laureate names and the nations of this Laureate
    public String toString() {
        if (laureates.isEmpty()) {
            return "";
        }
        int x = 0;
        StringBuilder builder = new StringBuilder();
        for (Laureate laureate : laureates) {
            //Check if there is more than one laureate to print a comma
            if(x > 0){
                builder.append(", " + laureate.getName() + " " + laureate.getNations());
            } else {
                builder.append(laureate.getName() + " " + laureate.getNations());
            }
            x++;
        }
        return builder.toString();
    }
}
