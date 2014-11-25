package hunt.snake.com.snaketreasurehunt;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Tom on 11/23/14.
 */

public class Tile implements View.OnTouchListener{

    private Canvas canvas;
    private Paint paint;

    private int tileWidth;
    private int tileHeight;
    private int posX;
    private int posY;
    private boolean isRightBorder;
    private boolean isLeftBorder;
    private boolean isBottomBorder;
    private boolean isTopBorder;

    public Tile(Canvas canvas) {
        this.canvas = canvas;
        tileWidth = Constants.TILE_WIDTH.getValue();
        tileHeight = Constants.TILE_HEIGHT.getValue();

        init();
    }

    public void init() {
        paint = new Paint();
        int strokeColor = Color.parseColor("#4B8A08");
        paint.setColor(strokeColor);
        paint.setStyle(Paint.Style.STROKE);
    }

    public void setPositionOnBoard(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void drawTile() {
        int left = posX * tileWidth;
        int top = posY * tileHeight;
        int width = left + tileWidth;
        int height = top + tileHeight;
        canvas.drawRect(left, top, width, height, paint);

        paint.setColor(Color.WHITE);
        canvas.drawText(posX + ", " + posY, left + 50f, top + 50f, paint);

        drawDetails();

        //System.out.println("Tile " + posX + ", " + posY + ": " + top + ", " + left + ", " + height + ", " + width);
    }

    public void drawDetails() {
        if(isTopBorder | isBottomBorder | isRightBorder | isLeftBorder ) {
            drawBorders();
        }
    }

    public void drawGameElement() {

    }

    public void drawBorders() {
        float startX;
        float startY;
        float stopX;
        float stopY;

        paint.setColor(Color.parseColor("#2E1E10"));
        paint.setStrokeWidth(10);

        if(isTopBorder) {
            startX = posX * tileWidth;
            startY = posY * tileHeight;
            stopX = startX + tileWidth;
            stopY = startY;
            canvas.drawLine(startX, startY, stopX, stopY, paint);
        }

        if(isBottomBorder) {
            startX = posX * tileWidth;
            startY = posY * tileHeight + tileHeight;
            stopX = startX + tileWidth;
            stopY = startY;
            canvas.drawLine(startX, startY, stopX, stopY, paint);
        }

        if(isRightBorder) {
            startX = posX * tileWidth + tileWidth;
            startY = posY * tileHeight;
            stopX = startX;
            stopY = startY + tileHeight;
            canvas.drawLine(startX, startY, stopX, stopY, paint);
        }

        if(isLeftBorder) {
            startX = posX * tileWidth;
            startY = posY * tileHeight;
            stopX = startX;
            stopY = startY + tileHeight;
            canvas.drawLine(startX, startY, stopX, stopY, paint);
        }
    }

    public void setIsTopBorder(boolean isTopBorder) {
        this.isTopBorder = isTopBorder;
    }

    public void setIsBottomBorder(boolean isBottomBorder) {
        this.isBottomBorder = isBottomBorder;
    }

    public void setIsLeftBorder(boolean isLeftBorder) {
        this.isLeftBorder = isLeftBorder;
    }

    public void setIsRightBorder(boolean isRightBorder) {
        this.isRightBorder = isRightBorder;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        System.out.println("Tile: " + posX + ", " + posY);
        return false;
    }
}
