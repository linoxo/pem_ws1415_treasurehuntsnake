package hunt.snake.com.snaketreasurehunt;

import android.graphics.Path;
import android.graphics.Point;

import java.util.ArrayList;

import hunt.snake.com.framework.Graphics;

public class ComplexGameElement extends GameElement{

    private ArrayList<Point> points;
    private Path path;

    public ComplexGameElement()  {

    }

    @Override
    public void update() {
        path = new Path();
        int factorX = getTile().getPosX() * Constants.TILE_WIDTH.getValue();
        int factorY = getTile().getPosY() * Constants.TILE_HEIGHT.getValue();

        int x;
        int y;
        for(int i = 0; i < points.size(); i++) {
            x = factorX + points.get(i).x;
            y = factorY + points.get(i).y;

            if(i == 0) path.moveTo(x, y);
            else path.lineTo(x, y);
        }
        path.close();
    }

    @Override
    public void drawGameElement(Graphics g) {
        drawGameElement(g, 0, 0);
    }

    @Override
    public void drawGameElement(Graphics g, int deltaX, int deltaY) {
        int color = getColor();
        Path drawPath = new Path();
        drawPath.addPath(path, deltaX * Constants.TILE_WIDTH.getValue(), deltaY * Constants.TILE_HEIGHT.getValue());
        g.drawPath(drawPath, color);
    }

    public void addPoint(Point point) {
        if(points == null)
            points = new ArrayList<Point>();

        points.add(point);
        if(getTile() != null)
            update();
    }

    public void addPoint(int x, int y) {

        addPoint(new Point(x, y));
    }

    @Override
    public void setPosition(Position position) {}
}
