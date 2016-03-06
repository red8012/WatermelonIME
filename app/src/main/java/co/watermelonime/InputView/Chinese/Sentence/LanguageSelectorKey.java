package co.watermelonime.InputView.Chinese.Sentence;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Font;
import co.watermelonime.Common.Size;

public class LanguageSelectorKey extends View {
    static Paint rectPaint;
    public final int language;
    static final OnTouchListener onTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getActionMasked() != MotionEvent.ACTION_DOWN) return false;
            LanguageSelectorKey me = (LanguageSelectorKey) v;
            LanguageSelector.setInputLanguage(me.language);
//            LanguageSelector.inputLanguage = me.language;
//            C.sentenceView.invalidate();
            return true;
        }
    };
    public float dx, dy;
    Layout textLayout;

    public LanguageSelectorKey(final int language, final String text) {
        super(C.mainService);
        setOnTouchListener(onTouchListener);
        this.language = language;
        textLayout = Font.sentence.make(text, Size.WSentenceView);
        dx = Size.WSentenceView / 2;
        dy = (Size.HLanguageSelectorKey - textLayout.getHeight()) / 2;
        setMeasuredDimension(Size.WSentenceView, Size.HLanguageSelectorKey);
        if (rectPaint == null) {
            rectPaint = new Paint();
            rectPaint.setColor(Colour.CANDIDATE);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Log.i("LanguageSelectorKey", "onMeasure");
        setMeasuredDimension(Size.WSentenceView, Size.HLanguageSelectorKey);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (textLayout == null) return;
        if (LanguageSelector.inputLanguage == language) {
            canvas.drawRect(0, 0, Size.u / 2, Size.HLanguageSelectorKey, rectPaint);
        }
        canvas.save();
        canvas.translate(dx, dy);
        textLayout.draw(canvas);
        canvas.restore();
    }
}
