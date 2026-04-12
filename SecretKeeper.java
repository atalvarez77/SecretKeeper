import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class SecretKeeper extends Application {

    @Override
    public void start(Stage stage) {
        TabPane tabPane = new TabPane();

        // Two tabs: Message, Files
        tabPane.getTabs().add(createEncryptTab(stage));
        tabPane.getTabs().add(createDecryptTab(stage));

        // Prevent closing tabs
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Scene scene = new Scene(tabPane, 500, 420);
        stage.setTitle("Secret Keeper v2.1 - The Vault");
        stage.setScene(scene);
        stage.show();
    }

    // TAB 1 : Encrypt and Save
    private Tab createEncryptTab(Stage stage) {
        Tab tab = new Tab("🔒 Encrypt");

        TextArea textArea = new TextArea();
        textArea.setPromptText("Type your secret message here...");
        textArea.setWrapText(true);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Master Password");

        Button encryptButton = new Button("Encrypt");
        Button saveButton = new Button("Save to .txt");
        saveButton.setDisable(true);

        Label statusLabel = new Label("Ready to encrypt.");
        statusLabel.setStyle("-fx-test-fill: gray; -fx-font-style: italic;");

        encryptButton.setOnAction(e -> {
            if (textArea.getText().isEmpty() || passwordField.getText().isEmpty()) {
                statusLabel.setText("Error: Please enter a message and password.");
                statusLabel.setStyle("-fx-text-fill: red;");
                return;
            }
            try {
                String encrypted = CryptoUtils.encrypt(textArea.getText(), passwordField.getText());
                textArea.setText(encrypted);
                saveButton.setDisable(false);

                statusLabel.setText("Message encrypted successfully!");
                statusLabel.setStyle("-fx-text-fill: green;");

                passwordField.clear();
            } catch (Exception ex) {
                statusLabel.setText("Encryption Error: " + ex.getMessage());
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        });

        saveButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Secret File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                try {
                    FileManager.saveToFile(file, textArea.getText());
                    statusLabel.setText("Successfully saved to: " + file.getName());
                    statusLabel.setStyle("-fx-text-fill: green;");

                    saveButton.setDisable(true);
                    textArea.clear();
                } catch (Exception ex) {
                    statusLabel.setText("Failed to Save: " + ex.getMessage());
                    statusLabel.setStyle("-fx-text-fill: red;");
                }
            }
        });

        VBox layout = new VBox(10, new Label("Message to hide:"), textArea, passwordField, encryptButton, saveButton, statusLabel);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        tab.setContent(layout);
        return tab;
    }

    // TAB 2 : Load and Decrypt
    private Tab createDecryptTab(Stage stage) {
        Tab tab = new Tab("🔓 Decrypt");

        TextArea textArea = new TextArea();
        textArea.setPromptText("Encrypted text will appear here...");
        textArea.setWrapText(true);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Master Password");

        Button loadButton = new Button("Load .txt File");
        Button decryptButton = new Button("Decrypt");

        Label statusLabel = new Label("Ready to load file.");
        statusLabel.setStyle("-fx-text-fill: gray; -fx-font-style: italic;");

        loadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Secret File");
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                try {
                    String content = FileManager.readFromFile(file);
                    textArea.setText(content);
                    statusLabel.setText("File loaded. Enter password to decrypt");
                    statusLabel.setStyle("-fx-text-fill: #a9821e");
                } catch (Exception ex) {
                    statusLabel.setText("Failed to load: " + ex.getMessage());
                    statusLabel.setStyle("-fx-text-fill: red;");
                }
            }
        });

        decryptButton.setOnAction(e -> {
            if (textArea.getText().isEmpty() || passwordField.getText().isEmpty()) {
                statusLabel.setText("Error: Needs both encrypted text and password to decrypt");
                statusLabel.setStyle("-fx-text-fill: red;");
                return;
            }
            try {
                String decrypted = CryptoUtils.decrypt(textArea.getText(), passwordField.getText());
                textArea.setText(decrypted);

                statusLabel.setText("Decrypted successfully!");
                statusLabel.setStyle("-fx-text-fill: green;");

                passwordField.clear();
            } catch (Exception ex) {
                statusLabel.setText("Decryption Failed! Wrong Password or Corrupted File");
                statusLabel.setStyle("-fx-text-fill: red;");
                passwordField.clear();
            }
        });

        VBox layout = new VBox(10, loadButton, textArea, passwordField, decryptButton, statusLabel);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        tab.setContent(layout);
        return tab;
    }

    public static void main(String[] args) {
        launch(args);
    }
}