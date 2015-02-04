package hunt.snake.com.snaketreasurehunt;

import android.graphics.Color;

import hunt.snake.com.framework.Graphics;

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
        this(0, 0);
    }

    public Tile(int posX, int posY) {
        tileWidth = Constants.TILE_WIDTH.getValue();
        tileHeight = Constants.TILE_HEIGHT.getValue();
        hasGameElement = false;
        setPositionOnBoard(posX, posY);
    }

    public void setPositionOnBoard(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void drawTile(Graphics g, int deltaX, int deltaY) {
        int left = (posX + deltaX) * tileWidth;
        int top = (posY + deltaY) * tileHeight;

        int color;
        if(isOnBorder()) {
            // tile is on border --> some color as the obstacles
            color = Color.parseColor("#472400");
            //color = posX % 2 == posY % 2 ? Color.parseColor("#61210B") : Color.parseColor("#8A2908");
        } else {
            // tile is inside the field --> chess board pattern
            color = posX % 2 == posY % 2 ? Color.parseColor("#007900") : Color.parseColor("#008600");
        }

        g.drawRect(left, top, tileWidth, tileHeight, color);

        drawDetails(g);
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
        int strokeWidth = 25;

        if(isTopBorder) {
            startX = posX * tileWidth;
            startY = posY * tileHeight;
            stopX = startX + tileWidth;
            stopY = startY;
            g.drawLine(startX, startY, stopX, stopY, strokeWidth, color);
        }

        if(isBottomBorder) {
            startX = posX * tileWidth;
            startY = posY * tileHeight + tileHeight - strokeWidth;
            stopX = startX + tileWidth;
            stopY = startY;
            g.drawLine(startX, startY, stopX, stopY, strokeWidth, color);
        }

        if(isRightBorder) {
            startX = posX * tileWidth + tileWidth - strokeWidth;
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

    public boolean hasGameElement() {
        return hasGameElement;
    }

    public void setHasGameElement(boolean hasGameElement) { this.hasGameElement = hasGameElement; }

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

    public boolean isOnBorder() {
        return isBottomBorder || isLeftBorder || isRightBorder || isTopBorder;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
