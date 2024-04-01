import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Menu.initializeCommands();

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            Menu.printMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            Menu.executeCommand(choice);
        } while (choice != 4);
        scanner.close();
    }
}