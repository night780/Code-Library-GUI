import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


/**
 * Code Snippet Library GUI, a program that allows the user to
 * create, view, and add to code snippet libraries.
 *
 * @author Jacob Jonas
 * @version 3.0
 * @date 4-11-23
 */
public class CodeSnippetLibraryGUI extends JFrame implements ActionListener {
    private static final String ROOT_PATH = "./";
    private static final String FILE_EXTENSION = ".txt";
    private JLabel label;
    private JTextField textField;
    private JTextArea textArea;
    private JButton viewButton, createButton, addButton;



    /**
     * The CodeSnippetLibraryGUI function creates a GUI for the Code Snippet Library program.
     * It contains three buttons: View, Create, and Add. The View button allows the user to view existing libraries;
     * the Create button allows them to create a new library; and the Add button allows them to add code snippets
     * from an existing library into another one. Each of these buttons has an icon associated with it that is displayed
     * on top of it instead of text (this makes for a more visually appealing GUI). When each button is clicked, its corresponding function will be executed.

     *
     *
     * @return Nothing
     *
     */
    public CodeSnippetLibraryGUI() {


        super("Snippet Library");
        setSize(500, 500);
        //set icon
        try {
            setIconImage(ImageIO.read(new File("icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(new Color(135, 145, 145)); // Use a consistent color scheme throughout the GUI.
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(135, 145, 145));
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        //set background color for bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(135, 145, 145));

        ImageIcon logoIcon = new ImageIcon("logo.png"); // Use a title bar image to give the GUI a professional look.
        JLabel logoLabel = new JLabel(logoIcon);
        topPanel.add(logoLabel);

        label = new JLabel("Snippet Library");
        label.setFont(new Font("Arial", Font.BOLD, 18)); // Increase the font size and use a clear and readable font.
        topPanel.add(label);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(135, 145, 145));
        centerPanel.setLayout(new GridLayout(3, 1, 10, 10));

        viewButton = new JButton(new ImageIcon("view.png")); // Use icons or images instead of text for buttons.
        viewButton.setIcon(new ImageIcon(((ImageIcon) viewButton.getIcon()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        viewButton.setToolTipText("View existing libraries");
        viewButton.addActionListener(this);
        viewButton.setBackground(new Color(165, 199, 199, 255));

        centerPanel.add(viewButton);

        createButton = new JButton(new ImageIcon("create.png"));
        createButton.setIcon(new ImageIcon(((ImageIcon) createButton.getIcon()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        createButton.setToolTipText("Create a new library");
        createButton.addActionListener(this);
        createButton.setBackground(new Color(165, 199, 199, 255));
        centerPanel.add(createButton);

        addButton = new JButton(new ImageIcon("add.png"));
        //make the icon smaller
        addButton.setIcon(new ImageIcon(((ImageIcon) addButton.getIcon()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        addButton.setToolTipText("Add code to a library");
        addButton.addActionListener(this);
        addButton.setBackground(new Color(165, 199, 199, 255));

        centerPanel.add(addButton);

        add(centerPanel, BorderLayout.CENTER);


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBackground(new Color(135, 145, 145));
        //add author name
        textArea = new JTextArea("Author: Jacob Jonas\nThis program is released under the MIT License\t\tgithub.com/night780");
        //make the text area bold
        textArea.setFont(new Font("Arial", Font.BOLD, 10));

        textArea.setEditable(false);
        textArea.setBackground(new Color(135, 145, 145));
        scrollPane.setViewportView(textArea);
        add(scrollPane, BorderLayout.SOUTH);

        setVisible(true);
    }




    /**
     * The main function of the program.
     *
     *
     * @param args Pass arguments to the program
     *
     * @return Void
     *
     */
    public static void main(String[] args) {
        new CodeSnippetLibraryGUI();
    }

    /**
     * The actionPerformed function is the function that is called when a button
     * on the GUI is pressed. It determines which button was pressed and calls
     * the appropriate function to handle it.

     *
     * @param e Determine which button was pressed
     *
     * @return Void
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewButton) {
            viewExistingLibraries();
        } else if (e.getSource() == createButton) {
            createNewLibrary();
        } else if (e.getSource() == addButton) {
            addCodeToLibrary(this);
        }
    }


    /**
     * The viewExistingLibraries function allows the user to view the contents of a library.
     * The function first checks if there are any libraries in existence, and if not, displays an error message.
     * If there are libraries in existence, it prompts the user to choose one from a list of existing libraries.
     * Once chosen, it opens up a new window with that library's content displayed inside of it.  It also adds a window listener so that when this new window is closed by clicking on its &quot;X&quot; button or otherwise closing itself out (e.g., via Alt+F4), then the program will prompt you to save changes before
     *
     *
     * @return A string of the file name
     *
     */
    public void viewExistingLibraries() {
        File root = new File(ROOT_PATH);
        File[] files = root.listFiles((dir, name) -> name.endsWith(FILE_EXTENSION));
        if (files == null || files.length == 0) {
            JOptionPane.showMessageDialog(this, "No libraries found.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Existing libraries:\n");
            // prompt user to choose a library to view
            String[] options = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                options[i] = files[i].getName();
            }
            String libraryName = (String) JOptionPane.showInputDialog(this, sb.toString(), "View Library",
                    JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (libraryName != null && !libraryName.isEmpty()) {
                String filePath = ROOT_PATH + libraryName;
                try {
                    JFrame frame = new JFrame("Library Content: " + libraryName);
                    JTextArea textArea = new JTextArea();
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    frame.add(scrollPane);
                    frame.setSize(500, 500);
                    frame.setVisible(true);
                    // load file content into the text area
                    String fileContent = CodeSnippetLibrary.getFileContents(libraryName);
                    if (fileContent != null) {
                        textArea.setText(fileContent);
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to read file: " + libraryName + FILE_EXTENSION);
                    }

                    // add window listener to prompt user to save changes before closing
                    frame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            //if no changes were made, then just close the window
                            if (textArea.getText().equals(fileContent)) {
                                frame.dispose();
                                return;
                            }
                            int choice = JOptionPane.showConfirmDialog(frame, "Save changes?", "Save Changes",
                                    JOptionPane.YES_NO_CANCEL_OPTION);
                            if (choice == JOptionPane.YES_OPTION) {
                                String content = textArea.getText();
                                try (PrintWriter out = new PrintWriter(filePath)) {
                                    out.println(content);
                                    JOptionPane.showMessageDialog(frame, "Changes saved successfully.");
                                } catch (FileNotFoundException ex) {
                                    JOptionPane.showMessageDialog(frame, "Failed to save changes: " + ex.getMessage());
                                }
                            } else if (choice == JOptionPane.NO_OPTION) {
                                // do nothing
                            } else {
                                // user cancelled
                                return;
                            }
                            frame.dispose();
                        }
                    });
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Failed to open file: " + libraryName + FILE_EXTENSION);
                }
            }
        }
    }





    /**
     * The createNewLibrary function creates a new library file.
     * The user is prompted to enter the name of the new library, and if they do not cancel out of this prompt,
     * then a file with that name is created in the root directory. If there already exists a file with that name,
     * then an error message will be displayed to inform them of this fact. Otherwise, it will display a success message

     *
     *
     * @return Nothing
     *
     */
    public void createNewLibrary() {

        String libraryName = JOptionPane.showInputDialog(this, "Enter the name of the new library:");
        if (libraryName != null && !libraryName.isEmpty()) {
            String fileName = libraryName + FILE_EXTENSION;
            File file = new File(ROOT_PATH + fileName);
            try {
                if (file.createNewFile()) {
                    JOptionPane.showMessageDialog(this, "New library created: " + fileName);
                } else {
                    JOptionPane.showMessageDialog(this, "Library already exists: " + fileName);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Failed to create library: " + fileName);
                e.printStackTrace();
            }
        }
    }


    /**
     * The addCodeToLibrary function allows the user to add code to a library.
     * The function first prompts the user for a library name, and then checks if that file exists.
     * If it does not exist, an error message is displayed and the function returns.
     * Otherwise, another prompt asks for code to be added to that library file.

     *
     * @param mainFrame Display the joptionpane dialogs
     *
     * @return Void
     *
     */
    private static void addCodeToLibrary(JFrame mainFrame) {
        File root = new File(ROOT_PATH);
        File[] files = root.listFiles((dir, name) -> name.endsWith(FILE_EXTENSION));
        if (files == null || files.length == 0) {
            JOptionPane.showMessageDialog(mainFrame, "No libraries found.");
            return;
        }

        // prompt user to choose a library to add code to
        String[] options = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            options[i] = files[i].getName().replace(FILE_EXTENSION, "");
        }
        String libraryName = (String) JOptionPane.showInputDialog(mainFrame, "Select a library to add code to:",
                "Add Code to Library", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (libraryName == null || libraryName.isEmpty()) {
            return;
        }
        String fileName = libraryName + FILE_EXTENSION;
        File file = new File(ROOT_PATH + fileName);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(mainFrame, "Library not found: " + fileName, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // prompt user to enter code to add to library
        String code = JOptionPane.showInputDialog(mainFrame, "Enter the code to add:", "Add Code to Library", JOptionPane.PLAIN_MESSAGE);
        if (code == null || code.isEmpty()) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < code.length(); i += 58) {
            int endIndex = Math.min(i + 58, code.length());
            String line = code.substring(i, endIndex) + "\n";
            sb.append(line);
        }
        sb.append("----------------------------------------------------------------\n");
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(sb.toString());
            JOptionPane.showMessageDialog(mainFrame, "Code added to library: " + fileName, "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainFrame, "Failed to add code to library: " + fileName, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }




}
