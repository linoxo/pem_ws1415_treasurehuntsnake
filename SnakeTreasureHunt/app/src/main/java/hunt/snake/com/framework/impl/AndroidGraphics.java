package hunt.snake.com.framework.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;

import hunt.snake.com.framework.Graphics;

public class AndroidGraphics implements Graphics {
    Canvas canvas;
    Paint paint;

    public AndroidGraphics(Bitmap frameBuffer) {
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
    }

    public void clear(int color) {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
                (color & 0xff));
    }

    public void drawLine(int x, int y, int x2, int y2, int strokeWidth, int color) {
        paint.setColor(color);
        float oldStrokeWidth = paint.getStrokeWidth();
        paint.setStrokeWidth(strokeWidth);
        canvas.drawLine(x, y, x2, y2, paint);
        paint.setStrokeWidth(oldStrokeWidth);
    }

    public void drawRect(int x, int y, int width, int height, int color) {
        paint.setColor(color);
        paint.setStyle(Style.FILL);
        canvas.drawRect(x, y, x + width, y + height, paint);
    }

    public void drawRoundRect(RectF rect, float rx, float ry, int color) {
        paint.setColor(color);
        paint.setStyle(Style.FILL);
        canvas.drawRoundRect(rect, rx, ry, paint);
    }

    public void drawPath(Path path, int color) {
        paint.setColor(color);
        paint.setStyle(Style.FILL);
        canvas.drawPath(path, paint);
    }

    public void drawOval(RectF rect, int color) {
        paint.setColor(color);
        canvas.drawOval(rect, paint);
    }

    public void drawHalo(int centerX, int centerY, int radius, int width, int color) {
        paint.setColor(color);
        paint.setStyle(Style.STROKE);
        float oldStrokeWidth = paint.getStrokeWidth();
        paint.setStrokeWidth(width);
        canvas.drawCircle(centerX, centerY, radius, paint);
        paint.setStrokeWidth(oldStrokeWidth);
    }

    public void drawText(String text, int x, int y, int color, int size, Paint.Align align) {
        paint.setColor(color);
        paint.setTextSize(size);
        paint.setTextAlign(align);
        paint.setStyle(Style.FILL);
        canvas.drawText(text, x, y, paint);
    }
}

