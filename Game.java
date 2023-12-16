import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Game extends Application {

    private final Canvas canvas = new Canvas(500, 500);
    private final Board board = new Board(10,10,10);
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
        int x = (int) Math.floor(mouseEvent.getX() / 50);
        int y = (int) Math.floor(mouseEvent.getY() / 50);
        Tile tile = board.getTile(x,y);
        tile.reveal();
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
}
