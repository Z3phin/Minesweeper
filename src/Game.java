import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Game extends Application {

    private static final int CANVAS_WIDTH = 400;
    private static final int CANVAS_HEIGHT = 400;
    private static final int INITIAL_BOARD_HEIGHT = 10;
    private static final int INITIAL_BOARD_WIDTH = 10;
    private static final int INITIAL_BOARD_BOMBS = 10;
    private static final Button resetButton = new Button();

    private static int time;
    private final Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    private static final Timeline timer = new Timeline(new KeyFrame(Duration.millis(1000),
            actionEvent -> updateTimer()));

    private static final Label timeLabel = new Label(String.valueOf(time));
    public static Board board =
            new Board(INITIAL_BOARD_HEIGHT, INITIAL_BOARD_WIDTH, INITIAL_BOARD_BOMBS);
    private static boolean gameOver;
    private static boolean gameStart;


    @Override
    public void start(Stage stage) throws Exception {
        BorderPane pane = new BorderPane();
        pane.setCenter(canvas);
        Scene root = new Scene(pane,600,600);
        stage.setScene(root);

        canvas.setOnMouseClicked(this::clickSquare);
        gameOver = false;
        gameStart = true;
        time = 0;
        timer.setCycleCount(Timeline.INDEFINITE);

        HBox top = new HBox();

        top.getChildren().add(timeLabel);
        timeLabel.setAlignment(Pos.TOP_RIGHT);

        resetButton.setOnAction(actionEvent -> reset());
        resetButton.setAlignment(Pos.CENTER);
        Image image = new Image("resources/resetButtonHappy.png", 40,40, false, true);
        resetButton.setGraphic(new ImageView(image));
        top.getChildren().add(resetButton);

        pane.setTop(top);
        render();
        stage.show();
    }

    private void clickSquare(MouseEvent mouseEvent) {
        if (!gameOver) {
            int x = (int) Math.floor(mouseEvent.getX() / 40);
            int y = (int) Math.floor(mouseEvent.getY() / 40);
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (gameStart) {
                    board.populateBombs(x, y);
                    timer.play();
                    gameStart = false;
                }
                board.click(x, y);
            } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                board.rightClick(x, y);
            }
            render();
        }
    }

    private void render() {
        clearCanvas();
        board.render(canvas.getGraphicsContext2D());
    }

    private void clearCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
    }

    public static void setGameOver() {
        gameOver = true;
        board.reveal();
        timer.stop();
        Image image = new Image("resources/resetButtonDead.png", 60,60, false, true);
        resetButton.setGraphic(new ImageView(image));
    }

    public static void setGameWin() {
        gameOver = true;
        board.flagAllBombs();
        timer.stop();
        Image image = new Image("resources/resetButtonWin.png", 60,60, false, true);
        resetButton.setGraphic(new ImageView(image));
    }

    private void reset() {
        gameStart = true;
        gameOver = false;
        timer.stop();
        time = 0;
        timeLabel.setText(String.valueOf(time));
        board = new Board(INITIAL_BOARD_HEIGHT, INITIAL_BOARD_WIDTH, INITIAL_BOARD_BOMBS);
        Image image = new Image("resources/resetButtonHappy.png", 40,40, false, true);
        resetButton.setGraphic(new ImageView(image));

        render();
    }

    private static void updateTimer() {
        time++;
        timeLabel.setText(String.valueOf(time));
    }
}
