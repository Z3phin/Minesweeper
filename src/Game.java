import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Game extends Application {

    private static final int CANVAS_WIDTH = 400;
    private static final int CANVAS_HEIGHT = 400;
    private static final int INITIAL_BOARD_HEIGHT = 10;
    private static final int INITIAL_BOARD_WIDTH = 10;
    private static final int INITIAL_BOARD_BOMBS = 10;
    private final Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    public static final Board board =
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
    }

    public static void setGameWin() {
        gameOver = true;
        board.flagAllBombs();
    }
}
