package hunt.snake.com.framework;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public interface Graphics {
    public static enum PixmapFormat {
        ARGB8888, ARGB4444, RGB565
    }

    public Pixmap newPixmap(String fileName, PixmapFormat format);

    public void clear(int color);

    public void drawPixel(int x, int y, int color);

    public void drawLine(int x, int y, int x2, int y2, int strokeWidth, int color);

    public void drawRect(int x, int y, int width, int height, int color);

    public void drawRoundRect(RectF rect, float rx, float ry, int color);

    public void drawRoundRectStroke(RectF rect, float rx, float ry, int color);

    public void drawPath(Path path, int color);

    public void drawPath(Path path, int color, Paint.Style style);

    public void drawOval(RectF rect, int color);

    public void drawText(String text, int x, int y, int color, int size, Paint.Align align);

    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
            int srcWidth, int srcHeight);

    public void drawPixmap(Pixmap pixmap, int x, int y);

    public int getWidth();

    public int getHeight();
}
