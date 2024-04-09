import java.io.*;

public class TextDatabase {

    private String databaseFileName;

    public TextDatabase() {
        databaseFileName = null;
    }

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

    public void close() {
        System.out.println("Database closed.");
        databaseFileName = null;
    }

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
