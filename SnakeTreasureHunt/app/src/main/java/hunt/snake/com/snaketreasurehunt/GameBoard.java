package hunt.snake.com.snaketreasurehunt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Tom on 11/23/14.
 */

public class GameBoard extends View{

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;

    private boolean isInitialized;

    private int screenHeight;
    private int screenWidth;

    private Tile[][] tiles;

    public GameBoard(Context context) {
        super(context);

        setScreenSize(context);

        setFocusableInTouchMode(true);

        paint = new Paint();
        paint.setColor(Color.WHITE);
        isInitialized = false;
    }

    public void setScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;
    }

    public void init() {
        bitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

        int backgroundColor = Color.parseColor("#527A00");
        canvas.drawColor(backgroundColor);

        isInitialized = true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if(!isInitialized)
            init();

        canvas.drawBitmap(bitmap, 0, 0, paint);
        createTiles();
        createGameElements();
    }

    public void createGameElements() {
        //createGameElement(GameElementType.COIN, tiles[3][1], GameElement.Orientation.NONE);
        //createGameElement(GameElementType.SNAKE_HEAD, tiles[2][4], GameElement.Orientation.EAST);
        //createGameElement(GameElementType.SNAKE_BODY_HORIZONTAL, tiles[2][4], GameElement.Orientation.EAST);
        // createGameElement(GameElementType.SNAKE_BODY_HORIZONTAL, tiles[4][4]);

        Snake snake = new Snake(canvas, tiles[3][4], tiles, 4,GameElement.Position.EAST);
        createGameElement(GameElementType.SNAKE_BODY_HORIZONTAL, tiles[0][4], GameElement.Position.WEST);
        createGameElement(GameElementType.SNAKE_BODY_VERTICAL, tiles[0][4], GameElement.Position.NORTH);
        createGameElement(GameElementType.SNAKE_BODY_HORIZONTAL, tiles[0][3], GameElement.Position.WEST);
        createGameElement(GameElementType.SNAKE_BODY_VERTICAL, tiles[0][3], GameElement.Position.SOUTH);
        createGameElement(GameElementType.SNAKE_BODY_HORIZONTAL, tiles[1][3], GameElement.Position.NONE);

        createGameElement(GameElementType.COIN, tiles[3][1]);
    }

    public void createGameElement(GameElementType type, Tile tile, GameElement.Position orientation) {
        GameElement element = type.getGameElement();
        element.setCanvas(canvas);
        element.setTile(tile);
        element.setType(type);
        element.drawGameElement();
    }

    public void createGameElement(GameElementType type, Tile tile) {
        GameElement element = type.getGameElement();
        element.setCanvas(canvas);
        element.setTile(tile);
        element.setType(type);
        element.drawGameElement();
    }

    public void createTiles() {
        tiles = new Tile[8][10];

        Tile tile;

        int cols = tiles[0].length;
        int rows = tiles.length;

        System.out.println(cols);
        System.out.println(rows);

        for(int x = 0; x < rows; x++) {
            for(int y = 0; y < cols; y++) {
                tile = new Tile(canvas);
                tile.setPositionOnBoard(x, y);

                if(x == 0)
                    tile.setIsLeftBorder(true);

                if(y == 0)
                    tile.setIsTopBorder(true);

                if(x == rows - 1)
                    tile.setIsRightBorder(true);

                if(y == cols - 1)
                    tile.setIsBottomBorder(true);

                tile.drawTile();

                tiles[x][y] = tile;
            }
        }

    }
}
