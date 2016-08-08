package co.watermelonime.InputView.Chinese.Sentence;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Font;
import co.watermelonime.Common.Size;

public class LanguageSelectorKey extends View {
    static final OnTouchListener onTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getActionMasked() != MotionEvent.ACTION_DOWN) return false;
            LanguageSelectorKey me = (LanguageSelectorKey) v;
            LanguageSelector.setInputLanguage(me.language);
            return true;
        }
    };
    static Paint rectPaint;
    public final int language;
    public float dx, dy;
    int height;
    Layout textLayout;
    Drawable image;

    public LanguageSelectorKey(final int language, final String text) {
        super(C.mainService);
        setOnTouchListener(onTouchListener);
        this.language = language;
        setSize();
        textLayout = Font.sentence.make(text, Size.WSentenceView);
        dx = C.isLandscape ? Size.WKey / 2 : Size.WSentenceView / 2;
        dy = (Size.HKey - textLayout.getHeight()) / 2;
    }

    public LanguageSelectorKey(final int language, int resource) {
        super(C.mainService);
        setOnTouchListener(onTouchListener);
        this.language = language;
        setSize();

        image = ContextCompat.getDrawable(C.context, resource);
        int size = Size.HKey * 3 / 5;
        dx = C.isLandscape ? (Size.WKey - size) / 2 : (Size.WSentenceView * 1 / 5 + Size.u / 5);
        dy = (height - size) / 2;
        if (C.isLandscape && language != LanguageSelector.EMOJI)
            dy += Size.u / 2;
        image.setBounds(0, 0, size, size);

        if (rectPaint == null) {
            rectPaint = new Paint();
            rectPaint.setColor(Colour.CANDIDATE);
        }
    }

    void setSize() {
        if (!C.isLandscape) {
            if (language == LanguageSelector.GLOBE || language == LanguageSelector.SETTINGS)
                height = Size.HCandidateRow;
            else height = Size.HKey;
            setMeasuredDimension(Size.WSentenceView, height);
        } else {
            height = Size.HKey;
            setMeasuredDimension(Size.WKey, height);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Log.i("LanguageSelectorKey", "onMeasure");
        setSize();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (image != null) {
            canvas.save();
            canvas.translate(dx, dy);
            image.draw(canvas);
            canvas.restore();
        }
        if (LanguageSelector.inputLanguage == language) {
            if (!C.isLandscape)
                canvas.drawRect(0, 0, Size.u / 2, Size.HKey, rectPaint);
            else
                canvas.drawRect(0, Size.HKey - Size.u / 2, Size.WKey, Size.HKey, rectPaint);
        }
        if (textLayout != null) {
            canvas.save();
            canvas.translate(dx, dy);
            textLayout.draw(canvas);
            canvas.restore();
        }

    }
}
