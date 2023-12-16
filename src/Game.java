import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Game extends Application {

    private final Canvas canvas = new Canvas(400, 400);
    public static final Board board = new Board(10,10,10);
    private static boolean gameOver = false;
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane pane = new BorderPane();
        pane.setCenter(canvas);
        Scene root = new Scene(pane,600,600);
        stage.setScene(root);

        canvas.setOnMouseClicked(this::clickSquare);
        System.out.println(board);
        render();
        stage.show();
    }

    private void clickSquare(MouseEvent mouseEvent) {
        int x = (int) Math.floor(mouseEvent.getX() / 40);
        int y = (int) Math.floor(mouseEvent.getY() / 40);
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            board.click(x, y);
        } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            board.rightClick(x, y);
        }
        render();
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
}
