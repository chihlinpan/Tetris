package xzt;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Block {
    private String name;
    private Color color;

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    private Pane pane;
    private int form;
    private int xMax = Controller.XMAX;
    private int yMax = Controller.YMAX;
    private int size = Controller.SIZE;
    Rectangle a = new Rectangle(size - 1, size - 1);
    Rectangle b = new Rectangle(size - 1, size - 1);
    Rectangle c = new Rectangle(size - 1, size - 1);
    Rectangle d = new Rectangle(size - 1, size - 1);


    Block(Pane pane, String name)
    {
        make(pane, name);
        a.setX(a.getX() + 350);
        b.setX(b.getX() + 350);
        c.setX(c.getX() + 350);
        d.setX(d.getX() + 350);
        a.setY(a.getY() + 400);
        b.setY(b.getY() + 400);
        c.setY(c.getY() + 400);
        d.setY(d.getY() + 400);
        setVisual(true);
    }

    Block(Pane pane) {
        form = 1;
        double rand = Math.random() * 700;
        if (rand < 100) {
            make(pane, "s");
        } else if (rand < 200) {
            make(pane, "z");
        } else if (rand < 300) {
            make(pane, "l");
        } else if (rand < 400) {
            make(pane, "j");
        } else if (rand < 500) {
            make(pane, "t");
        } else if (rand < 600) {
            make(pane, "i");
        } else
            make(pane, "o");
    }

    private void make(Pane pane, String name) {
        this.name = name;
        setColor();
        this.pane = pane;
        initFrom();
    }

    private void setColor() {
        switch (name) {
            case "s":
                color = Color.GHOSTWHITE;
                break;
            case "z":
                color = Color.HOTPINK;
                break;
            case "l":
                color = Color.SPRINGGREEN;
                break;
            case "j":
                color = Color.ORANGERED;
                break;
            case "t":
                color = Color.ALICEBLUE;
                break;
            case "i":
                color = Color.DODGERBLUE;
                break;
            case "o":
                color = Color.ORANGE;
                break;
        }
        a.setFill(color);
        b.setFill(color);
        c.setFill(color);
        d.setFill(color);
    }

    // 初始化图形
    private void initFrom() {
        form = 0;
        switch (name) {
            case "s":
                b.setX(xMax / 2 - size);
                b.setY(0);
                a.setX(xMax / 2);
                a.setY(0);
                c.setX(b.getX());
                c.setY(b.getY() + size);
                d.setY(c.getY());
                d.setX(c.getX() - size);
                break;
            case "z":
                c.setX(xMax / 2 - size);
                c.setY(0);
                d.setX(c.getX() - size);
                d.setY(c.getY());
                b.setY(c.getY() + size);
                b.setX(c.getX());
                a.setX(c.getX() + size);
                a.setY(b.getY());
                break;
            case "l":
                b.setY(size);
                b.setX(xMax / 2 - size);
                a.setY(b.getY());
                a.setX(b.getX() - size);
                c.setY(b.getY());
                c.setX(b.getX() + size);
                d.setY(b.getY() - size);
                d.setX(c.getX());
                break;
            case "j":
                b.setX(xMax / 2 - size);
                b.setY(0);
                a.setY(b.getY());
                a.setX(b.getX() - size);
                c.setY(b.getY());
                c.setX(b.getX() + size);
                d.setX(c.getX());
                d.setY(c.getY() + size);
                break;
            case "t":
                b.setX(xMax / 2 - size);
                b.setY(size);
                a.setY(b.getY());
                a.setX(b.getX() + size);
                c.setX(b.getX() - size);
                c.setY(b.getY());
                d.setX(b.getX());
                d.setY(0);
                break;
            case "i":
                a.setX(xMax / 2 - 2 * size);
                a.setY(0);
                b.setX(xMax / 2 - size);
                b.setY(0);
                c.setX(xMax / 2);
                c.setY(0);
                d.setX(xMax / 2 + size);
                d.setY(0);
                break;
            case "o":
                a.setX(xMax / 2 - size);
                a.setY(0);
                b.setX(a.getX());
                b.setY(a.getY() + size);
                c.setX(b.getX() - size);
                c.setY(b.getY());
                d.setX(c.getX());
                d.setY(a.getY());
                break;

        }
    }

    void setVisual(boolean bool)
    {
        if (bool)
            pane.getChildren().addAll(a, b, c, d);
    }


    void moveRight() {
        a.setX(a.getX() + size);
        b.setX(b.getX() + size);
        c.setX(c.getX() + size);
        d.setX(d.getX() + size);
    }

    void moveLeft() {
        a.setX(a.getX() - size);
        b.setX(b.getX() - size);
        c.setX(c.getX() - size);
        d.setX(d.getX() - size);
    }

    void moveDown() {
        a.setY(a.getY() + size);
        b.setY(b.getY() + size);
        c.setY(c.getY() + size);
        d.setY(d.getY() + size);
    }

    // 判断各个方块是否可以移动的方法
    // x表示向右移动x个单位
    // y表示向下移动y个单位
    boolean canMove(String name, int x, int y) {
        Rectangle rect;
        switch (name) {
            case "a":
                rect = a;
                break;
            case "b":
                rect = b;
                break;
            case "c":
                rect = c;
                break;
            default:
                rect = d;
                break;
        }
        boolean xb = false;
        boolean yb = false;
        // 先判断是否到边界
        if (x >= 0)
            xb = rect.getX() + x * size <= xMax - size;
        if (x < 0)
            xb = rect.getX() + x * size >= 0;
        if (y >= 0)
            yb = rect.getY() + y * size <= yMax - size;
        if (y < 0)
            yb = rect.getY() + y * size >= 0;
        // 最后判断下一步是否有方块，条件需要同时满足才可以
        // 这里只需要处理Y的向下越界判断，因为&&是短路运算，X越界xb已经可以判断了，所以后面不会运行，也就不会报错
        return xb && yb && Controller.MESH[(int)rect.getX() / size + x][(int)rect.getY() / size + y] == 0;

    }

    // 变换图形
    void turn() {
        form = (form + 1) % 4;
        /*
            以下变换都是基于b的，所以翻转时对应的应该是相对与b的位置
         */
        switch (name) {
            case "s":
                if (form == 1 || form == 3) {
                    if (canMove("b", 0, -1) && canMove("b", 1, 0) && canMove("b", 1, 1)) {
                        a.setY(b.getY() - size);
                        a.setX(b.getX());
                        c.setX(b.getX() + size);
                        c.setY(b.getY());
                        d.setX(b.getX() + size);
                        d.setY(b.getY() + size);
                    } else {
                        form = (form - 1) % 4;
                        break;
                    }

                }
                if (form == 0 || form == 2) {
                    if (canMove("b", 1, 0) && canMove("b", 0, 1) && canMove("b", -1, 1)) {
                        a.setY(b.getY());
                        a.setX(b.getX() + size);
                        c.setX(b.getX());
                        c.setY(b.getY() + size);
                        d.setX(b.getX() - size);
                        d.setY(b.getY() + size);
                    } else {
                        form = (form - 1) % 4;
                        break;
                    }
                }
                break;
            case "z":
                if (form == 1 || form == 3) {
                    if (canMove("b", 0, -1) && canMove("b", -1, 0) && canMove("b", -1, 1)) {
                        a.setY(b.getY() - size);
                        a.setX(b.getX());
                        c.setX(b.getX() - size);
                        c.setY(b.getY());
                        d.setX(b.getX() - size);
                        d.setY(b.getY() + size);
                    } else {
                        form = (form - 1) % 4;
                        break;
                    }
                }
                if (form == 0 || form == 2) {
                    if (canMove("b", 1, 0) && canMove("b", 0, -1) && canMove("b", -1, -1)) {
                        a.setX(b.getX() + size);
                        a.setY(b.getY());
                        c.setX(b.getX());
                        c.setY(b.getY() - size);
                        d.setX(b.getX() - size);
                        d.setY(b.getY() - size);
                    } else {
                        form = (form - 1) % 4;
                        break;
                    }
                }
                break;
            case "l":
                if (form == 1) {
                    if (canMove("b", 0, -1) && canMove("b", -1, -2) && canMove("b", -1, 0) && canMove("b", 0, 0)
                            && canMove("b", -1, -1)) {
                        b.setY(b.getY() - size);
                        a.setX(b.getX() - size);
                        a.setY(b.getY() - size);
                        c.setX(b.getX() - size);
                        c.setY(b.getY() + size);
                        d.setX(b.getX());
                        d.setY(b.getY() + size);
                        b.setX(b.getX() - size);
                    } else {
                        form = (form - 1) % 4;
                        break;
                    }
                }
                if (form == 2) {
                    if (canMove("b", 1, 0) && canMove("b", 2, -1) && canMove("b", 0, -1) && canMove("b", 0, 0)
                            && canMove("b", 1, -1)) {
                        b.setX(b.getX() + size);
                        a.setX(b.getX() + size);
                        a.setY(b.getY() - size);
                        c.setX(b.getX() - size);
                        c.setY(b.getY() - size);
                        d.setY(b.getY());
                        d.setX(b.getX() - size);
                        b.setY(b.getY() - size);
                    } else {
                        form = (form - 1) % 4;
                        break;
                    }
                }
                if (form == 3) {
                    if (canMove("b", 0, 1) && canMove("b", 1, 2) && canMove("b", 1, 1) && canMove("b", 0, 0)
                            && canMove("b", 1, 1)) {
                        b.setY(b.getY() + size);
                        a.setX(b.getX() + size);
                        a.setY(b.getY() + size);
                        c.setX(b.getX() + size);
                        c.setY(b.getY() - size);
                        d.setX(b.getX());
                        d.setY(b.getY() - size);
                        b.setX(b.getX() + size);
                    } else {
                        form = (form - 1) % 4;
                        break;
                    }
                }
                if (form == 0) {
                    if (canMove("b", -1, 0) && canMove("b", -2, 1) && canMove("b", 0, 1) && canMove("b", 0, 0)
                            && canMove("b", -1, 1)) {
                        b.setX(b.getX() - size);
                        a.setX(b.getX() - size);
                        a.setY(b.getY() + size);
                        c.setX(b.getX() + size);
                        c.setY(b.getY() + size);
                        d.setY(b.getY());
                        d.setX(b.getX() + size);
                        b.setY(b.getY() + size);
                    } else {
                        form = (form - 1) % 4;
                        break;
                    }
                }
                break;
            case "j":
                if (form == 1) {
                    if (canMove("b", 0, 1) && canMove("b", 1, 0) && canMove("b", 1, 2) && canMove("b", 0, 2)
                            && canMove("b", 1, 1)) {
                        b.setY(b.getY() + size);
                        a.setX(b.getX() + size);
                        a.setY(b.getY() - size);
                        c.setY(b.getY() + size);
                        c.setX(b.getX() + size);
                        d.setY(b.getY() + size);
                        d.setX(b.getX());
                        b.setX(b.getX() + size);
                    } else {
                        form = (form - 1) % 4;
                        break;
                    }
                }
                if (form == 2) {
                    if (canMove("b", -1, 0) && canMove("b", 0, 1) && canMove("b", -2, 1) && canMove("b", -2, 0)
                            && canMove("b", -1, 1)) {
                        b.setX(b.getX() - size);
                        a.setX(b.getX() + size);
                        a.setY(b.getY() + size);
                        c.setX(b.getX() - size);
                        c.setY(b.getY() + size);
                        d.setX(b.getX() - size);
                        d.setY(b.getY());
                        b.setY(b.getY() + size);
                    } else {
                        form = (form - 1) % 4;
                        break;
                    }
                }
                if (form == 3) {
                    if (canMove("b", 0, -1) && canMove("b", -1, 0) && canMove("b", -1, -2) && canMove("b", 0, -2)
                            && canMove("b", -1, -1)) {
                        b.setY(b.getY() - size);
                        a.setX(b.getX() - size);
                        a.setY(b.getY() + size);
                        c.setX(b.getX() - size);
                        c.setY(b.getY() - size);
                        d.setX(b.getX());
                        d.setY(b.getY() - size);
                        b.setX(b.getX() - size);
                    } else {
                        form = (form - 1) % 4;
                        break;
                    }
                }
                if (form == 0) {
                    if (canMove("b", 1, 0) && canMove("b", 0, -1) && canMove("b", 2, -1) && canMove("b", 2, 0)
                            && canMove("b", 1, -1)) {
                        b.setX(b.getX() + size);
                        a.setX(b.getX() - size);
                        a.setY(b.getY() - size);
                        c.setX(b.getX() + size);
                        c.setY(b.getY() - size);
                        d.setX(b.getX() + size);
                        d.setY(b.getY());
                        b.setY(b.getY() - size);
                    } else {
                        form = (form - 1) % 4;
                        break;
                    }
                }
                break;
            case "t":
                if (form == 1) {
                    if (canMove("b", 0, 1) && canMove("b", 0, -1) && canMove("b", 1, 0)) {
                        a.setX(b.getX());
                        a.setY(b.getY() + size);
                        c.setY(b.getY() - size);
                        c.setX(b.getX());
                        d.setX(b.getX() + size);
                        d.setY(b.getY());
                    } else {
                        form = (form - 1) % 4;
                        break;
                    }
                }
                if (form == 2) {
                    if (canMove("b", -1, 0) && canMove("b", 1, 0) && canMove("b", 0, 1)) {
                        a.setX(b.getX() - size);
                        a.setY(b.getY());
                        c.setY(b.getY());
                        c.setX(b.getX() + size);
                        d.setX(b.getX());
                        d.setY(b.getY() + size);
                    } else {
                        form = (form - 1) % 4;
                        break;
                    }
                }
                if (form == 3) {
                    if (canMove("b", 0, -1) && canMove("b", 0, 1) && canMove("b", -1, 0)) {
                        a.setX(b.getX());
                        a.setY(b.getY() - size);
                        c.setY(b.getY() + size);
                        c.setX(b.getX());
                        d.setX(b.getX() - size);
                        d.setY(b.getY());
                    } else {
                        form = (form - 1) % 4;
                        break;
                    }
                }
                if (form == 0) {
                    if (canMove("b", 1, 0) && canMove("b", -1, 0) && canMove("b", 0, -1)) {
                        a.setX(b.getX() + size);
                        a.setY(b.getY());
                        c.setY(b.getY());
                        c.setX(b.getX() - size);
                        d.setX(b.getX());
                        d.setY(b.getY() - size);
                    } else {
                        form = (form - 1) % 4;
                        break;
                    }
                }
                break;
            // 这里以c作为相对位置
            case "i":
                if (form == 1 || form == 3) {
                    if (canMove("c", 0, -2) && canMove("c", 0, -1) && canMove("c", 0, 1)) {
                        a.setX(c.getX());
                        a.setY(c.getY() - size * 2);
                        b.setY(c.getY() - size);
                        b.setX(c.getX());
                        d.setX(c.getX());
                        d.setY(c.getY() + size);
                    } else {
                        form = (form - 1) % 4;
                        break;
                    }
                }
                if (form == 0 || form == 2) {
                    if (canMove("c", 2, 0) && canMove("c", 1, 0) && canMove("c", -1, 0)) {
                        a.setX(c.getX() + size * 2);
                        a.setY(c.getY());
                        b.setY(c.getY());
                        b.setX(c.getX() + size);
                        d.setX(c.getX() - size);
                        d.setY(c.getY());
                    } else {
                        form = (form - 1) % 4;
                        break;
                    }
                }
                break;
            case "o":
                break;
        }
    }

    void remove()
    {
        pane.getChildren().removeAll(a,b,c,d);
    }

    boolean onTheTop()
    {
        return a.getY() <= 0 || b.getY() <= 0 || c.getY() <= 0 || d.getY() <= 0;
    }

}
