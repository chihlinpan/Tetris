package xzt;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Timer;
import java.util.TimerTask;

public class GameStage extends Application {
    private int xMax = Controller.XMAX;
    private int yMax = Controller.YMAX;
    static boolean game;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        game = true;

        Pane pane = new Pane();
        // 300 为计分区域的大小
        Scene gameScene = new Scene(pane, xMax + 300, yMax);
        stage.setResizable(true);
        // 分隔线，分隔出计分区域
        Line line = new Line(xMax, 0, xMax, yMax);
        line.setStroke(Color.ANTIQUEWHITE);
        // 显示分数
        Text scoreText = new Text("Score: ");
        scoreText.setStyle("-fx-font: 25 arial;");
        scoreText.setY(100);
        scoreText.setX(xMax + 10);
        scoreText.setFill(Color.RED);
        // 显示消去行数
        Text level = new Text("Lines: ");
        level.setStyle("-fx-font: 25 arial;");
        level.setY(200);
        level.setX(xMax + 10);
        level.setFill(Color.GREEN);
        // 显示下一个方块
        Text next = new Text("NEXT: ");
        next.setStyle("-fx-font: 30 arial;");
        next.setY(300);
        next.setX(xMax + 10);
        next.setFill(Color.YELLOW);
        pane.getChildren().addAll(line, scoreText, level, next);

        // 设置按钮
        Button start = new Button("Pause\\Start");
        start.setPrefSize(150,50);
        start.setLayoutX(xMax + 10);
        start.setLayoutY(yMax - 100);
        Button quit = new Button("Quit");
        quit.setPrefSize(100,50);
        quit.setLayoutX(xMax + 180);
        quit.setLayoutY(yMax - 100);
        pane.getChildren().addAll(start,quit);


        // 新建一个controller

        Controller controller = new Controller(gameScene, pane);
        stage.setScene(gameScene);
        stage.setTitle("俄罗斯方块");
        BackgroundFill bkg = new BackgroundFill(Paint.valueOf("#73C6B6"), new CornerRadii(0), new Insets(0));
        Background bg = new Background(bkg);
        pane.setBackground(bg);
        stage.show();

        // 设置按钮监听事件
        quit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.exit(0);
            }
        });

        start.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                controller.pause();
            }
        });

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (game && !controller.pause) {

                            controller.down();
                            scoreText.setText("Score: " + controller.score);
                            level.setText("Lines: " + controller.lines);
                        } else if (!game){
                            controller.stop();
                            // 游戲結束
                            Text over = new Text("GAME OVER");
                            over.setFill(Color.RED);
                            over.setStyle("-fx-font: 100 arial;");
                            over.setY(yMax / 2);
                            over.setX(50);
                            pane.getChildren().add(over);
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 400);


        // 设置窗口关闭即退出
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
    }


}
