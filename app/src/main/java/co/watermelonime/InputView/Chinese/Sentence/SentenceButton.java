package co.watermelonime.InputView.Chinese.Sentence;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Layout;
import android.util.Log;
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
    public String text;
    Layout textLayout, originalTextLayout;

    public SentenceButton(int index) {
        super(C.mainService);
        if (index >= 0)
            setOnTouchListener(onTouchListener);
        this.index = index;
        if (rect == null) {
            rectPaint.setColor(Colour.CANDIDATE);
            rect = new RectF(Size.u / 2, 0, Size.WSentenceView, Size.HSentenceButton);
            setMeasuredDimension(Size.WSentenceView, Size.HSentenceButton);
        }
    }

    public void setText(final String s) {
        text = s;
        if (s == null) textLayout = null;
        else {
            textLayout = Font.sentence.make(text, Size.WSentenceView);
            dx = Size.WSentenceView / 2;
            dy = (Size.HSentenceButton - textLayout.getHeight()) / 2;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("SentenceButton", "onMeasure");
        setMeasuredDimension(Size.WSentenceView, Size.HSentenceButton);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (textLayout == null) return;
        if (selectedIndex == index) {
            canvas.drawRoundRect(rect, Size.u, Size.u, rectPaint);
            canvas.drawRect(Size.WSentenceView - Size.u, 0,
                    Size.WSentenceView, Size.HSentenceButton, rectPaint);
        }
        canvas.save();
        canvas.translate(dx, dy);
        textLayout.draw(canvas);
        canvas.restore();
    }


}
