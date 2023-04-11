import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


/**
 * The type Code snippet library.
 * @author Jacob Jonas
 * @version 1.0
 * @date 4-10-23
 */
public class CodeSnippetLibrary extends Component {
    private static final String ROOT_PATH = "./";
    private static final String FILE_EXTENSION = ".txt";


    /**
     * The main function of the program.
     *
     *
     * @param args Pass command line arguments to the program
     *
     * @return Void
     *
     */
    public void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Code Snippet Library Menu:");
            System.out.println("1. View existing libraries");
            System.out.println("2. Create a new library");
            System.out.println("3. Add code to a library");
            System.out.println("4. Exit");

            System.out.print("Enter your choice (1-4): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            switch (choice) {
                case 1:
                    viewExistingLibraries();
                    break;
                case 2:
                    createNewLibrary(scanner);
                    break;
                case 3:
                    addCodeToLibrary(scanner);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }



    /**
     * The viewExistingLibraries function allows the user to view the contents of a library.
     * The function first checks if there are any libraries in existence, and if not, displays an error message.
     * If there are libraries in existence, it prompts the user to enter which library they would like to view.
     * It then reads that file's contents and displays them for the user using a JTextArea object within a JFrame window.

     *
     *
     * @return The contents of a file
     *
     */
    public void viewExistingLibraries() {
        File root = new File(ROOT_PATH);
        File[] files = root.listFiles((dir, name) -> name.endsWith(FILE_EXTENSION));
        JTextArea textArea = null;
        if (files == null || files.length == 0) {
            textArea.setText("No libraries found.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Existing libraries:\n");
            for (File file : files) {
                sb.append(file.getName() + "\n");
            }
            // prompt user to view contents of a library
            String libraryName = JOptionPane.showInputDialog(this, sb.toString() + "\nEnter the name of the library to view:");
            if (libraryName != null && !libraryName.isEmpty()) {
                String fileContent = CodeSnippetLibrary.getFileContents(libraryName + FILE_EXTENSION);
                if (fileContent != null) {
                    JFrame frame = new JFrame("Library Content: " + libraryName);
                    textArea = new JTextArea(fileContent);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    frame.add(scrollPane);
                    frame.setSize(500, 500);
                    frame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to read file: " + libraryName + FILE_EXTENSION);
                }
            }
        }
    }



    /**
     * The createNewLibrary function creates a new library file with the name specified by the user.
     *
     *
     * @param scanner Read the user input
     *
     * @return Void, so the return type should be void
     *
     */
    private static void createNewLibrary(Scanner scanner) {
        System.out.print("Enter the name of the new library: ");
        String libraryName = scanner.nextLine();
        String fileName = libraryName + FILE_EXTENSION;
        File file = new File(ROOT_PATH + fileName);
        try {
            if (file.createNewFile()) {
                System.out.println("New library created: " + fileName);
            } else {
                System.out.println("Library already exists: " + fileName);
            }
        } catch (IOException e) {
            System.out.println("Failed to create library: " + fileName);
            e.printStackTrace();
        }
    }

    /**
     * The addCodeToLibrary function allows the user to add code to an existing library.
     * The function prompts the user for a library name, and then checks if that file exists.
     * If it does not exist, it prints out an error message and returns from the function.
     * Otherwise, it opens up a FileWriter object in append mode (so that we don't overwrite any existing code)
     * and writes whatever string is entered by the user into this file on its own line.

     *
     * @param scanner Get user input from the console
     *
     * @return Nothing
     *
     */
    private static void addCodeToLibrary(Scanner scanner) {
        System.out.print("Enter the name of the library to add code to: ");
        String libraryName = scanner.nextLine();
        String fileName = libraryName + FILE_EXTENSION;
        File file = new File(ROOT_PATH + fileName);
        if (!file.exists()) {
            System.out.println("Library not found: " + fileName);
            return;
        }
        try (FileWriter writer = new FileWriter(file, true)) {
            System.out.print("Enter the code to add: ");
            String code = scanner.nextLine();
            writer.write(code + "\n");
            System.out.println("Code added to library: " + fileName);
        } catch (IOException e) {
            System.out.println("Failed to add code to library: " + fileName);
            e.printStackTrace();
        }
    }

    public static String getFileContents(String fileName) {
        File file = new File(ROOT_PATH + fileName);
        if (!file.exists()) {
            return "File not found: " + fileName;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to read file: " + fileName;
        }
    }

}
