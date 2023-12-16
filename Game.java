import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
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

        System.out.println(board);
        board.render(canvas.getGraphicsContext2D());
        stage.show();
    }
}
