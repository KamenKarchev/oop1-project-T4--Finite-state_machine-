package app;

import cli.command.CommandHandler;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CommandHandler handler = CommandHandler.getInstance();
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.print("> ");
            String line = scanner.nextLine();
            try {
                running = handler.handleLine(line);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
