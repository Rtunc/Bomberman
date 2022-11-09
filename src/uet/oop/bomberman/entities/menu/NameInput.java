package uet.oop.bomberman.entities.menu;

import com.sun.javafx.scene.control.skin.resources.ControlResources;
import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Highscore;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Optional;

public class NameInput extends SceneManager {

    public static NameInput NAMEINPUTSCENE = new NameInput();

    private Group textGroup;
    private Text textScore = new Text();
    private Text textName = new Text();
    private String name = "Bomber";

    public NameInput() {
        Text textPause = new Text();
        textPause.setText("CONGRATS");
        textPause.setFont(Font.font("Courier New", FontWeight.BOLD, 24 * Sprite.SCALED));
        textPause.setFill(Color.WHITE);
        textPause.setX(Sprite.SCALED_SIZE * 6 - textPause.getLayoutBounds().getWidth() / 2);
        textPause.setY(50 * Sprite.SCALED);

        textScore.setText("SCORE: " + BombermanGame.bomberman.getPoint());
        textScore.setFont(Font.font("Courier New", FontWeight.NORMAL, 18 * Sprite.SCALED));
        textScore.setFill(Color.WHITE);
        textScore.setX(Sprite.SCALED_SIZE * 6 - textScore.getLayoutBounds().getWidth() / 2);
        textScore.setY(90 * Sprite.SCALED);

        Text textResume = new Text("NEXT");
        textResume.setFont(Font.font("Courier New", FontWeight.BOLD, 24 * Sprite.SCALED));
        textResume.setFill(Color.WHITE);
        textResume.setX(Sprite.SCALED_SIZE * 6 - textResume.getLayoutBounds().getWidth() / 2);
        textResume.setY(300 * Sprite.SCALED);
        textResume.setOnMouseEntered(e -> {
            textResume.setFill(Color.SKYBLUE);
        });
        textResume.setOnMouseExited(e -> {
            textResume.setFill(Color.WHITE);
        });
        textResume.setOnMouseClicked(e -> {
            BombermanGame.bomberman.setName(name);
            Highscore.getInstance().addScore(name, BombermanGame.bomberman.getPoint());
            if (BombermanGame.gameOver) {
                GameOverScene.GAMEOVERSCENE.update();
                BombermanGame.switchState(SceneState.GAMEOVER);
            } else {
                Credit.CREDITSCENE.update();
                BombermanGame.switchState(SceneState.CREDIT);
            }
        });

        Text button = new Text("SET NAME");
        button.setFont(Font.font("Courier New", FontWeight.BOLD, 18 * Sprite.SCALED));
        button.setFill(Color.WHITE);
        button.setX(Sprite.SCALED_SIZE * 6 - button.getLayoutBounds().getWidth() / 2);
        button.setY(200 * Sprite.SCALED);
        button.setOnMouseEntered(e -> {
            button.setFill(Color.SKYBLUE);
        });
        button.setOnMouseExited(e -> {
            button.setFill(Color.WHITE);
        });

        textName.setText("NAME: " + name);
        textName.setFont(Font.font("Courier New", FontWeight.NORMAL, 18 * Sprite.SCALED));
        textName.setFill(Color.WHITE);
        textName.setX(Sprite.SCALED_SIZE * 6 - textName.getLayoutBounds().getWidth() / 2);
        textName.setY(150 * Sprite.SCALED);

        button.setOnMouseClicked(e -> {

            // Create the new dialog
            TextAreaInputDialog dialog = new TextAreaInputDialog();
            dialog.setHeaderText(null);
            dialog.setGraphic(null);

            // Show the dialog and capture the result.
            Optional<String> result = dialog.showAndWait();

            // If the "Okay" button was clicked, the result will contain our String in the get() method
            result.ifPresent(s -> name = s);

            update();
        });

        textGroup = new Group(textScore, textResume, textPause, button, textName);
        super.scene = new Scene(textGroup, Color.BLACK);
    }

    public void update() {
        textScore.setText("SCORE: " + BombermanGame.bomberman.getPoint());
        textName.setText("NAME: " + name);
        textScore.setX(Sprite.SCALED_SIZE * 6 - textScore.getLayoutBounds().getWidth() / 2);
        textName.setX(Sprite.SCALED_SIZE * 6 - textScore.getLayoutBounds().getWidth() / 2);
        super.scene.setRoot(new Group());
        super.scene = new Scene(textGroup, Color.BLACK);
    }

    public NameInput addHandler() {
        return this;
    }

    public class TextAreaInputDialog extends Dialog<String> {

        /**************************************************************************
         *
         * Fields
         *
         **************************************************************************/

        private final GridPane grid;
        private final TextArea textArea;
        private final String defaultValue;

        /**************************************************************************
         *
         * Constructors
         *
         **************************************************************************/

        /**
         * Creates a new TextInputDialog without a default value entered into the
         * dialog {@link TextField}.
         */
        public TextAreaInputDialog() {
            this("");
        }

        /**
         * Creates a new TextInputDialog with the default value entered into the
         * dialog {@link TextField}.
         */
        public TextAreaInputDialog(@NamedArg("defaultValue") String defaultValue) {
            final DialogPane dialogPane = getDialogPane();

            // -- textarea
            this.textArea = new TextArea(defaultValue);
            this.textArea.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(textArea, Priority.ALWAYS);
            GridPane.setFillWidth(textArea, true);

            this.defaultValue = defaultValue;

            this.grid = new GridPane();
            this.grid.setHgap(10);
            this.grid.setMaxWidth(Double.MAX_VALUE);
            this.grid.setAlignment(Pos.CENTER_LEFT);

            dialogPane.contentTextProperty().addListener(o -> updateGrid());

            setTitle(ControlResources.getString("Dialog.confirm.title"));
            dialogPane.setHeaderText(ControlResources.getString("Dialog.confirm.header"));
            dialogPane.getStyleClass().add("text-input-dialog");
            dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            updateGrid();

            setResultConverter((dialogButton) -> {
                ButtonBar.ButtonData data = dialogButton == null ? null : dialogButton.getButtonData();
                return data == ButtonBar.ButtonData.OK_DONE ? textArea.getText() : null;
            });
        }

        /**************************************************************************
         *
         * Public API
         *
         **************************************************************************/

        /**
         * Returns the {@link TextField} used within this dialog.
         */
        public final TextArea getEditor() {
            return textArea;
        }

        /**
         * Returns the default value that was specified in the constructor.
         */
        public final String getDefaultValue() {
            return defaultValue;
        }

        /**************************************************************************
         *
         * Private Implementation
         *
         **************************************************************************/

        private void updateGrid() {
            grid.getChildren().clear();

            grid.add(textArea, 1, 0);
            getDialogPane().setContent(grid);

            Platform.runLater(() -> textArea.requestFocus());
        }
    }
}
