package hunt.snake.com.snaketreasurehunt;

import android.graphics.Color;
import android.graphics.Paint;

import hunt.snake.com.framework.Graphics;

/**
 * Created by Tom on 11/23/14.
 */

public class Tile {

    private int tileWidth;
    private int tileHeight;
    private int posX;
    private int posY;
    private boolean isRightBorder;
    private boolean isLeftBorder;
    private boolean isBottomBorder;
    private boolean isTopBorder;
    private boolean hasGameElement;
    private GameElementType gameElementType;
    private GameElement gameElement;


    public Tile() {
        tileWidth = Constants.TILE_WIDTH.getValue();
        tileHeight = Constants.TILE_HEIGHT.getValue();
        hasGameElement = false;
    }

    public void setPositionOnBoard(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void drawTile(Graphics g) {
        int left = posX * tileWidth;
        int top = posY * tileHeight;
        int width = left + tileWidth;
        int height = top + tileHeight;

        g.drawRect(left, top, width, height, Color.parseColor("#4B8A08"));

        g.drawText(posX + ", " + posY, left + 50, top + 50, Color.WHITE, Constants.TEXT_SIZE_XS.getValue(), Paint.Align.CENTER);

        drawDetails(g);

        //System.out.println("Tile " + posX + ", " + posY + ": " + top + ", " + left + ", " + height + ", " + width);
    }

    public void drawDetails(Graphics g) {
        if(isTopBorder | isBottomBorder | isRightBorder | isLeftBorder ) {
            drawBorders(g);
        }
    }

    public void drawBorders(Graphics g) {
        int startX;
        int startY;
        int stopX;
        int stopY;

        int color = Color.parseColor("#2E1E10");
        int strokeWidth = 10;

        if(isTopBorder) {
            startX = posX * tileWidth;
            startY = posY * tileHeight;
            stopX = startX + tileWidth;
            stopY = startY;
            g.drawLine(startX, startY, stopX, stopY, strokeWidth, color);
        }

        if(isBottomBorder) {
            startX = posX * tileWidth;
            startY = posY * tileHeight + tileHeight;
            stopX = startX + tileWidth;
            stopY = startY;
            g.drawLine(startX, startY, stopX, stopY, strokeWidth, color);
        }

        if(isRightBorder) {
            startX = posX * tileWidth + tileWidth;
            startY = posY * tileHeight;
            stopX = startX;
            stopY = startY + tileHeight;
            g.drawLine(startX, startY, stopX, stopY, strokeWidth, color);
        }

        if(isLeftBorder) {
            startX = posX * tileWidth;
            startY = posY * tileHeight;
            stopX = startX;
            stopY = startY + tileHeight;
            g.drawLine(startX, startY, stopX, stopY, strokeWidth, color);
        }
    }

    public void setGameElement(GameElement gameElement) {
        this.gameElement = gameElement;
        setGameElementType(gameElement.getType());
        hasGameElement = true;
    }

    public void removeGameElement() {
        gameElement = null;
        setGameElementType(null);
        hasGameElement = false;
    }

    public boolean hasGameElement() {
        return hasGameElement;
    }

    public GameElement getGameElement() { return gameElement; }

    public GameElementType getGameElementType() {
        return gameElementType;
    }

    public void setGameElementType(GameElementType gameElementType) { this.gameElementType = gameElementType; }

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
}
