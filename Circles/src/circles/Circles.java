import java.util.stream.Stream;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Circles extends Application {

    public static final int ROWS = 4;
    public static final int COLS = 5;
    public static final int CELL_SIZE = 100;
    public static final int RADIUS = 25;

    @Override
    public void start(Stage primaryStage) {
        root = new VBox();
        canvas = new Pane();
        starter = new Button("Circles");

        configure();
        addButtonHandler();

        root.getChildren().addAll(canvas, starter);

        primaryStage.setTitle("Java 8 Lab Exercise");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void configure() {
        root.setAlignment(Pos.CENTER);
        canvas.setPrefSize(COLS*CELL_SIZE, ROWS*CELL_SIZE);
    }

    private void addButtonHandler() {
        starter.setOnAction(e -> {
            circles();
        });
    }

    private void circles() {
        canvas.getChildren().clear();
        Stream<Stream<Circle>> circles = Stream.generate(() -> this.makeRow()).limit(ROWS);
        row = 0;
        circles.forEach(r -> {
            addRowToCanvas(r);
        });
    }

    private Stream <Circle> makeRow() {
        return Stream.generate(() -> new Circle(RADIUS)).limit(COLS);
    }

    private void addRowToCanvas(Stream <Circle> s) {
        column = 0;
        s.forEach(c -> {
            addToCanvas(c);
        });
        row++;
    }

    private void addToCanvas(Circle c) {
        c.setFill((Paint) new Color(Math.random(), Math.random(), Math.random(), 1));
        double toX = column * CELL_SIZE + (CELL_SIZE/2);
        double toY = row * CELL_SIZE + (CELL_SIZE/2);
        double fromX = (COLS*CELL_SIZE) - (CELL_SIZE/2);
        double fromY = (ROWS*CELL_SIZE) - (CELL_SIZE/2);
        c.setCenterX(fromX);
        c.setCenterY(fromY);
        canvas.getChildren().add((Circle)c);
        column++;
        TranslateTransition translate = new TranslateTransition(Duration.millis((double)500));
        translate.setNode((Node)c);
        translate.setByX(toX - fromX);
        translate.setByY(toY - fromY);
        translate.play();
        ScaleTransition scale = new ScaleTransition(Duration.millis((double)(500 * Math.random() + 500)));
        scale.setNode((Node)c);
        scale.setByX(2);
        scale.setByY(2);
        scale.setCycleCount(-1);
        scale.setAutoReverse(true);
        scale.play();
    }

    private VBox root;
    private Pane canvas;
    private Button starter;
    private int row;
    private int column;

    public static void main(String... args) {
        launch(args);
    }
}