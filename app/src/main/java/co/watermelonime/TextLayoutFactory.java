package co.watermelonime;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

public class TextLayoutFactory {
    final int width;
    public TextPaint textPaint;

    public TextLayoutFactory(float size, Typeface typeface, int color, int width) {
        this.width = width;
        textPaint = new TextPaint();
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(size);
        textPaint.setTypeface(typeface);
        textPaint.setColor(color);
        textPaint.setAntiAlias(true);
    }

    public StaticLayout make(String text) {
        return new StaticLayout(text, textPaint, width, Layout.Alignment.ALIGN_NORMAL,
                1.0f, 0.0f, false);
    }
}
