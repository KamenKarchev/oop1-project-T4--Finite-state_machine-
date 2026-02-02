package io;

import java.io.*;

/**
 * <h2>Наследен (legacy) текстов „database“ клас</h2>
 *
 * <p>
 * Този клас е по-стар placeholder за файлови операции (open/close/save/saveAs) чрез текстов файл.
 * В актуалната архитектура на проекта файловата система за автомати е реализирана чрез XML
 * компоненти ({@link io.MachineXmlStore} и {@link io.RegistryFileSession}).
 * </p>
 *
 * <p>
 * Запазен е основно за съвместимост/референция и не е критичен за текущата функционалност.
 * </p>
 */
public class TextDatabase {

    private String databaseFileName;

    /**
     * Създава нова „сесия“ без отворен файл.
     */
    public TextDatabase() {
        databaseFileName = null;
    }

    /**
     * Отваря текстов файл и отпечатва съдържанието му на конзолата.
     *
     * @param fileName име/път към файл
     */
    public void open(String fileName) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("Database opened successfully.");
            databaseFileName = fileName;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing reader: " + e.getMessage());
            }
        }
    }

    /**
     * Затваря текущата „сесия“ (забравя името на файла).
     */
    public void close() {
        System.out.println("Database closed.");
        databaseFileName = null;
    }

    /**
     * Записва примерни данни към текущия файл (ако има).
     */
    public void save() {
        if (databaseFileName != null) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(databaseFileName, true))) {
                writer.println("Some data to save...");
                System.out.println("Database saved successfully.");
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Database is not opened yet. Please open or save as.");
        }
    }

    /**
     * Записва примерни данни към нов файл и го прави текущ.
     *
     * @param fileName име/път към файл
     */
    public void saveAs(String fileName) {
        BufferedReader reader = null;
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(fileName));
            writer.println("Some data to save...");
            writer.close();
            System.out.println("Database saved as: " + fileName);
            databaseFileName = fileName;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing reader: " + e.getMessage());
            }
        }
    }
}
