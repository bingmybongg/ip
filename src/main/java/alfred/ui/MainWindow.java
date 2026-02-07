package alfred.ui;

import java.io.IOException;

import alfred.Alfred;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Alfred alfred;

    private Image batmanImage = new Image(this.getClass().getResourceAsStream("/images/batman.png"));
    private Image alfredImage = new Image(this.getClass().getResourceAsStream("/images/alfred.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setAlfred(Alfred alfred) {
        this.alfred = alfred;
        dialogContainer.getChildren().addAll(
                DialogBox.getAlfredDialog(this.alfred.toString(), alfredImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() throws IOException, InterruptedException {
        String input = userInput.getText();
        String response = this.alfred.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, batmanImage),
                DialogBox.getAlfredDialog(response, alfredImage)
        );

        if (input.equals("bye")) {
            userInput.clear();
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(e -> Platform.exit());
            delay.play();

            return;
        }
        userInput.clear();
    }
}

