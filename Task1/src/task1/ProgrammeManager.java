//Student ID: M00774667
package task1;

//Import any essential packages to use in this class
import task1.data.DataLoader;
import java.util.Scanner;

public class ProgrammeManager {

    public void start() {
        //Create a DataLoader obj to read the data
        DataLoader loadFile = DataLoader.getLoaderInstance();
        loadFile.readLines();
        //Create a LoadMenu obj to manage the menu
        LoadMenu menu = LoadMenu.getLoaderInstance();
        menu.mainMenu();//Display main menu
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice != 0) {
            try {
                System.out.print("Enter choice > ");//Input choice for main menu
                choice = Integer.parseInt(scanner.nextLine());
                //If the menu choice is false print error message
                if (!menu.processChoice(choice, loadFile.getYearObjs())) {
                    System.out.println("Invalid choice. Please enter a valid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.(No spaces or special characters)");
            }
            menu.mainMenu();
        }
        scanner.close();
    }
}
