package me.techsavvy;

// org.json library
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

// standard java library
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class JsonTextConverter extends JFrame implements ActionListener {

    // GUI components
    private JTextArea jsonInputArea; // json area
    private JTextArea outputArea; // plaintext area
    private JButton convertButton; // convert button
    private JMenuBar menuBar; // menu bar
    private JMenu fileMenu, helpMenu;
    private JMenuItem importJsonItem, exportPlainTextItem, exitItem, aboutItem; // menu options

    // IO components
    private BufferedReader fileReader; // file reader
    private FileWriter fileWriter; // file writer
    private String output = ""; // parsed json
    private JFileChooser fileChooser;
    private String file = ""; // json file

    public JsonTextConverter() {

        menuBar = new JMenuBar();

        importJsonItem = new JMenuItem("Import");
        importJsonItem.addActionListener(this);

        exportPlainTextItem = new JMenuItem("Export");
        exportPlainTextItem.addActionListener(this);

        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(this);


        aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(this);

        // basic JFrame configs
        setTitle("JsonText Converter");
        setSize(800, 630);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);

        fileMenu = new JMenu("File");
        helpMenu = new JMenu("Help");
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        fileMenu.add(importJsonItem);
        fileMenu.add(exportPlainTextItem);
        fileMenu.add(exitItem);

        helpMenu.add(aboutItem);

        setJMenuBar(menuBar);

        jsonInputArea = new JTextArea(15, 40);
        outputArea = new JTextArea(15, 40);
        outputArea.setEditable(false);

        convertButton = new JButton("Convert");

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("JSON Input"));
        inputPanel.add(new JScrollPane(jsonInputArea), BorderLayout.CENTER);

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("Preview Output"));
        outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        add(inputPanel, BorderLayout.NORTH);
        add(convertButton, BorderLayout.CENTER);
        add(outputPanel, BorderLayout.SOUTH);

        convertButton.addActionListener(e -> convertJsonToPlaintext());
    }

    // convert json to plaintext
    private void convertJsonToPlaintext() {

        String jsonText = jsonInputArea.getText().trim(); // get and clean up json string
        try {
            Object json = jsonText.startsWith("[") ? new JSONArray(jsonText) : new JSONObject(jsonText);
            StringBuilder output = new StringBuilder();
            parseJson(json, output, 0);
            outputArea.setText(output.toString());
            this.output = output.toString();
        }
        catch (JSONException ex) {
            outputArea.setText("Invalid JSON: " + ex.getMessage());
        }
    }

    // I have no idea how is this thing work (DO NOT TOUCH IF YOU HAVE NO IDEA WHAT ARE YOU DOING)
    private void parseJson(Object obj, StringBuilder sb, int indent) {
        String indentStr = "  ".repeat(indent);
        if (obj instanceof JSONObject jsonObject) {
            for (String key : jsonObject.keySet()) {
                Object value = jsonObject.get(key);
                sb.append(indentStr).append(key).append(": ");
                if (value instanceof JSONObject || value instanceof JSONArray) {
                    sb.append("\n");
                    parseJson(value, sb, indent + 1);
                } else {
                    sb.append(value.toString()).append("\n");
                }
            }
        } else if (obj instanceof JSONArray array) {
            for (int i = 0; i < array.length(); i++) {
                sb.append(indentStr).append("- ");
                Object value = array.get(i);
                if (value instanceof JSONObject || value instanceof JSONArray) {
                    sb.append("\n");
                    parseJson(value, sb, indent + 1);
                } else {
                    sb.append(value.toString()).append("\n");
                }
            }
        }
    }

    // main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JsonTextConverter converter = new JsonTextConverter();
            converter.setVisible(true);
        });
    }

    // perform JMenuItem stuff
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == importJsonItem){
            if (!jsonInputArea.getText().isEmpty())
                JOptionPane.showMessageDialog(null, "Current JSON string will be overwrite!", "Overwrite Warning", JOptionPane.WARNING_MESSAGE);

            fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION){
                importFile(fileChooser.getSelectedFile().getAbsolutePath());
            }
        }

        if (e.getSource() == exportPlainTextItem){
            if (output.isEmpty())
                JOptionPane.showMessageDialog(null, "Can not export if output string is empty", "IOException", JOptionPane.ERROR_MESSAGE);
            else {
                fileChooser = new JFileChooser();
                int response = fileChooser.showSaveDialog(null);

                if (response == JFileChooser.APPROVE_OPTION){
                    exportFile(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        }

        if (e.getSource() == exitItem){
            System.exit(0);
        }

        if (e.getSource() == aboutItem) {
            JOptionPane.showMessageDialog(this, "Json to Text Converter v1.0\nBy TechSavvy21", "About", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    // open the file
    private void importFile(String path){
        try {
            fileReader = new BufferedReader(new FileReader(path));

            String line, file = "";
            while ((line = fileReader.readLine()) != null){
                file = file.concat(line + '\n');
            }
            jsonInputArea.setText(file);
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "IOException", JOptionPane.ERROR_MESSAGE);
        }
    }

    // save the file
    private void exportFile(String path){
        try {
            fileWriter = new FileWriter(path);
            fileWriter.write(output);

            fileWriter.close();
            JOptionPane.showMessageDialog(null, "Successfully exported the file", "", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "IOException", JOptionPane.ERROR_MESSAGE);
        }

    }
}
