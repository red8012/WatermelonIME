package co.watermelonime.InputView.Chinese.Sentence;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Layout;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Font;
import co.watermelonime.Common.Size;

public class SentenceButton extends View {
    static final OnTouchListener onTouchListener = new OnTouchSentenceButton();
    static final Paint rectPaint = new Paint();
    public static int selectedIndex = -1; // -1 means nothing selected
    static RectF rect;
    public float dx, dy;
    public int index; // the i-th sentence button
    public StringBuilder text = new StringBuilder("　");
    Layout textLayout, whiteTextLayout, blackTextLayout;

    public SentenceButton(int index) {
        super(C.mainService);
        if (index >= 0) setOnTouchListener(onTouchListener);
        this.index = index;
        setMeasuredDimension(Size.WSentenceButton, Size.HSentenceButton);
        whiteTextLayout = Font.sentence.makeDynamic(text, Size.WSentenceView);
        blackTextLayout = Font.sentenceSelected.makeDynamic(text, Size.WSentenceView);
    }

    public static void init() {
        selectedIndex = -1;
        rectPaint.setColor(Colour.CANDIDATE);
        if (C.isLandscape)
            rect = new RectF(0, Size.u / 2, Size.WSentenceButton, Size.HSentenceButton);
        else rect = new RectF(Size.u / 2, 0, Size.WSentenceView, Size.HSentenceButton);
    }

    public void setText(char s) {
        if (s == '\0') textLayout = null;
        else {
            text.setLength(0);
            text.append(s);
            textLayout = whiteTextLayout;
            dx = Size.WSentenceButton / 2;
            dy = (Size.HSentenceButton - textLayout.getHeight()) / 2;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (textLayout == null) return;
        if (selectedIndex == index) {
            if (C.isLandscape) {
                canvas.drawRoundRect(rect, Size.u, Size.u, rectPaint);
                canvas.drawRect(0, Size.HSentenceButton - Size.u,
                        Size.WSentenceButton, Size.HSentenceButton, rectPaint);
            } else {
                canvas.drawRoundRect(rect, Size.u, Size.u, rectPaint);
                canvas.drawRect(Size.WSentenceView - Size.u, 0,
                        Size.WSentenceView, Size.HSentenceButton, rectPaint);
            }
        }
        canvas.save();
        canvas.translate(dx, dy);
        textLayout.draw(canvas);
        canvas.restore();
    }


}
