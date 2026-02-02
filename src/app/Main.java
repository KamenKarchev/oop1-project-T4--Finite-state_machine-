package app;

import cli.command.CommandHandler;

import java.util.Scanner;

/**
 * <h2>Входна точка на приложението</h2>
 *
 * <p>
 * {@code Main} стартира интерактивния CLI режим. Програмата чете редове от стандартния вход и ги
 * предава на {@link cli.command.CommandHandler}, който парсва и изпълнява командите.
 * </p>
 *
 * <p>
 * Цикълът приключва когато {@link cli.command.CommandHandler#handleLine(String)} върне {@code false}
 * (което се случва при командата {@code exit}).
 * </p>
 */
public class Main {
    /**
     * Стартира CLI цикъла.
     *
     * @param args аргументи от командния ред (в момента не се използват)
     */
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
