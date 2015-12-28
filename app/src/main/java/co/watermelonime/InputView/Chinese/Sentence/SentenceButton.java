package co.watermelonime.InputView.Chinese.Sentence;

import android.graphics.Canvas;
import android.text.Layout;
import android.util.Log;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Common.Font;
import co.watermelonime.Common.Size;

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
        canvas.save();
        canvas.translate(dx, dy);
        textLayout.draw(canvas);
        canvas.restore();
    }
}
