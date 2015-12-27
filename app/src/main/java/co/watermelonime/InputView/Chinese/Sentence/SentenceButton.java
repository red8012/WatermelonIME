package co.watermelonime.InputView.Chinese.Sentence;

import android.graphics.Canvas;
import android.text.Layout;
import android.util.Log;
import android.view.View;

import co.watermelonime.C;

public class SentenceButton  extends View {
    public float dx, dy;
    String text;
    Layout textLayout;
    OnTouchListener onTouchListener;

    public SentenceButton() {
        super(C.mainService);
    }

    public void setText(String s) {
        text = s;
//        setMeasuredDimension(C.sentenceView.width, C.u * 5);
        if (s == null) textLayout = null;
        else {
            textLayout = C.sentenceFont.make(text, SentenceView.width);
            dx = SentenceView.width / 2;
            dy = (C.u * 5 - textLayout.getHeight()) / 2;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("SentenceButton", "onMeasure");
        setMeasuredDimension(SentenceView.width, C.u * 5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (textLayout == null) return;
        canvas.save();
        canvas.translate(dx, dy);
        textLayout.draw(canvas);
        canvas.restore();
    }
}
