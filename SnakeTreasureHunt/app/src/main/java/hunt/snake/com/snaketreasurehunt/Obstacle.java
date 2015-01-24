package hunt.snake.com.snaketreasurehunt;

import java.util.ArrayList;
import java.util.Random;

public class Obstacle extends BigGameElement {

    private GameElement gameElement;
    private int size;
    private Tile startTile;
    private Tile[][] tiles;
    private GameElementType type;

    public Obstacle(Tile startTile, Tile[][] tiles, GameElementType type, int size) {
        super();
        this.startTile = startTile;
        setTile(startTile);
        this.tiles = tiles;
        this.type = type;
        setType(type);
        this.size = size;

        init();
    }

    private void init() {
        gameElement = type.getGameElement();
        gameElement.setTile(startTile);
        gameElement.setPosition(Position.NONE);
        setInitialGameElement(gameElement);

        randomizeObstacle();
    }

    public void placeOnRandomAdjacentFreeTile(Tile tile, int size) {
        if(size > 0) {
            Tile newTile = getRandomAdjacentTile(tile);

            if(newTile == null) {
                placeOnRandomAdjacentFreeTile(null, 0);
                return;
            }

            GameElement obstacle = type.getGameElement();

            obstacle.setTile(newTile);
            obstacle.setPosition(Position.NONE);

            addAdditionalGameElement(obstacle);

            placeOnRandomAdjacentFreeTile(newTile, size - 1);
        }
    }

    public void randomizeObstacle() {
        placeOnRandomAdjacentFreeTile(startTile, size);
    }

    private Tile getRandomAdjacentTile(Tile tile) {
        ArrayList<Tile> freeTiles = new ArrayList<Tile>();
        int posX = tile.getPosX();
        int posY = tile.getPosY();

        int left = - 1;
        int right = 1;
        int top = -1;
        int bottom = 1;

        if(posX == 0) left = 0;
        if(posX == tiles.length - 1) right = 0;
        if(posY == 0) top = 0;
        if(posY == tiles[posX].length - 1) bottom = 0;

        for(int x = left; x <= right; x++) {
            for(int y = top; y <= bottom; y++) {
                if(!tiles[posX + x][posY + y].hasGameElement())
                    freeTiles.add(tiles[posX + x][posY + y]);
            }
        }

        if(freeTiles.isEmpty())
            return null;

        return freeTiles.get(new Random().nextInt(freeTiles.size()));
    }

    public boolean isTileOccupiedByObstacle(int tileXPos, int tileYPos) {
        boolean result = false;
        if(getGameElement().getTile().getPosX() == tileXPos && getGameElement().getTile().getPosY() == tileYPos) {
            result = true;
        } else {
            for (GameElement g : getGameElements()) {
                if (g.getTile().getPosX() == tileXPos && g.getTile().getPosY() == tileYPos) {
                    result = true;
                }
            }
        }
        return result;
    }
}
