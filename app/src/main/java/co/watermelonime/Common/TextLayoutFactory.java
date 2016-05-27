package co.watermelonime.Common;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.DynamicLayout;
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

    public Layout make(CharSequence text) {
        return new StaticLayout(text, textPaint, width, Layout.Alignment.ALIGN_NORMAL,
                1.0f, 0.0f, false);
    }

    public Layout make(CharSequence text, int width) {
        return new StaticLayout(text, textPaint, width, Layout.Alignment.ALIGN_NORMAL,
                1.0f, 0.0f, false);
    }

    public DynamicLayout makeDynamic(CharSequence text, int width) {
        return new DynamicLayout(text, textPaint, width, Layout.Alignment.ALIGN_NORMAL,
                1.0f, 0.0f, false);
    }
}
