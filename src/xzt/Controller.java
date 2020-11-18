package xzt;


import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;


class Controller {
    static final int SIZE = 35;
    static int XMAX = SIZE * 12;
    static int YMAX = SIZE * 24;
    // 设置对应坐标网格
    static int[][] MESH = new int[XMAX / SIZE][YMAX / SIZE];
    private Scene scene;
    private Block current;
    private Block next;
    private Pane pane;
    private Block paint;
    private boolean game = true;
    private int top = 0;
    int score = 0;
    int lines = 0;
    boolean pause = true;

    Controller(Scene scene, Pane pane) {
        this.current = new Block(pane);
        current.setVisual(true);
        this.next = new Block(pane);
        this.pane = pane;
        this.scene = scene;
        paint();
        setPress();
    }

    // 设置监听键盘行为
    private void setPress() {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (game && !pause) {
                    switch (event.getCode()) {
                        case RIGHT:
                            // 判断是否可以右移
                            if (current.canMove("a", 1, 0) && current.canMove("b", 1, 0) &&
                                    current.canMove("c", 1, 0) && current.canMove("d", 1, 0))
                                current.moveRight();
                            break;
                        case LEFT:
                            if (current.canMove("a", -1, 0) && current.canMove("b", -1, 0) &&
                                    current.canMove("c", -1, 0) && current.canMove("d", -1, 0))
                                current.moveLeft();
                            break;
                        case DOWN:
                            down();
                            break;
                        case UP:
                            current.turn();
                            break;
                    }
                }
            }
        });
    }

    // 方块向下移动方法
    void down() {
        if (current.canMove("a", 0, 1) && current.canMove("b", 0, 1) &&
                current.canMove("c", 0, 1) && current.canMove("d", 0, 1)) {
            current.moveDown();
            score++;
        }
        if (shouldChang()) { // 判断形状是否可以继续移动，否则切换到下一个
            setMESH();
            current = next;
            current.setVisual(true);
            exchange(current);
            next = new Block(pane);
            paint.remove();  // 绘制预测的下一个形状
            paint();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    removeRows();
                }
            });
        }
        GameStage.game = isGameOver();
    }

    // 修改监听/控制对象
    private void exchange(Block current) {
        this.current = current;
    }

    // 判断是否切换方块对象
    private boolean shouldChang() {
        return judge(current.a) || judge(current.b) || judge(current.c) || judge(current.d);
    }

    private boolean judge(Rectangle r) {
        return (int) r.getY() / SIZE == 23 || MESH[(int) r.getX() / SIZE][(int) r.getY() / SIZE + 1] == 1;
    }

    // 设置MESH
    private void setMESH() {
        setData(current.a);
        setData(current.b);
        setData(current.c);
        setData(current.d);
    }

    private void setData(Rectangle r) {
        MESH[(int) r.getX() / SIZE][(int) r.getY() / SIZE] = 1;
    }

    private void paint() {
        paint = new Block(pane, next.getName());
    }

    private boolean isGameOver() {
        if (current.onTheTop())
            top++;
        else
            top = 0;
        return !(top >= 2);
    }

    void stop() {
        game = false;
        pause = true;
    }

    void pause() {
        pause = !pause;
    }

    // 消行
    private void removeRows() {
        ArrayList<Node> rects = new ArrayList<>();
        ArrayList<Integer> lineList = new ArrayList<>();
        ArrayList<Integer> lineMoveList = new ArrayList<>();
        ArrayList<Node> newRects = new ArrayList<>();
        int full = 0;
        for (int i = 0; i < MESH[0].length; i++) {
            for (int j = 0; j < MESH.length; j++) {
                if (MESH[j][i] == 1)
                    full++;
            }
            if (full == MESH.length) {
                lineList.add(i);
            }
            full = 0;
        }
        for (int i = 0; i < lineList.size(); i++) {
            lineMoveList.add(lineList.get(lineList.size() - i - 1));
        }

        if (lineList.size() > 0) {
            for (Node node : pane.getChildren()) {
                if (node instanceof Rectangle)
                    rects.add(node);
            }
            score += 50 * lineList.size();
            lines += lineList.size();

            // 先消掉全部行
            while (lineList.size() > 0) {
                for (Node node : rects) {
                    Rectangle rect = (Rectangle) node;
                    if (rect.getY() == lineList.get(0) * SIZE && rect.getX() < XMAX) {
                        MESH[(int) rect.getX() / SIZE][(int) rect.getY() / SIZE] = 0;
                        pane.getChildren().remove(node);
                    }
                }
                lineList.remove(0);
            }
            rects.clear();

            // 得到剩下的rects
            for (Node node : pane.getChildren()) {
                if (node instanceof Rectangle) {
                    newRects.add(node);
                }
            }
            if (lineMoveList.size() > 0) {
                // 下移
                while (lineMoveList.size() > 0) {
                    for (Node node : newRects) {
                        Rectangle rectangle = (Rectangle) node;
                        if (rectangle.getY() < lineMoveList.get(0) * SIZE && rectangle.getX() < XMAX) {
                            rectangle.setY(rectangle.getY() + SIZE * lineMoveList.size());
                        }
                    }
                    lineMoveList.remove(0);
                }
                // 设置MESH
                MESH = new int[XMAX / SIZE][YMAX / SIZE];
                for (Node node : pane.getChildren()) {
                    if (node instanceof Rectangle) {
                        rects.add(node);
                    }
                }
                for (Node node : rects) {
                    Rectangle a = (Rectangle) node;
                    // next 和 current 的 rectangle 是不能设置为1的
                    if (a.getX() < XMAX && a != next.a && a != next.b &&
                            a != next.c && a != next.d && a != current.a && a != current.b &&
                            a != current.c && a != current.d)
                        MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 1;
                }
            }
        }
    }
}
