package hunt.snake.com.framework;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public interface Graphics {

    public void clear(int color);

    public void drawLine(int x, int y, int x2, int y2, int strokeWidth, int color);

    public void drawRect(int x, int y, int width, int height, int color);

    public void drawRoundRect(RectF rect, float rx, float ry, int color);

    public void drawPath(Path path, int color);

    public void drawOval(RectF rect, int color);

    public void drawHalo(int centerX, int centerY, int radius, int width, int color);

    public void drawText(String text, int x, int y, int color, int size, Paint.Align align);

}
