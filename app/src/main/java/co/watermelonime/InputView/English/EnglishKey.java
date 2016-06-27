package co.watermelonime.InputView.English;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Font;
import co.watermelonime.Common.Size;

public class EnglishKey extends View {
    static final OnTouchListener ontouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            EnglishKey key = (EnglishKey) v;
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    C.commit(key.text);
                    EnglishKeyboard.committed = true;
                    if (EnglishKeyboard.mode == EnglishKeyboard.UPPER && !EnglishKeyboard.isShiftPressed)
                        C.englishKeyboard.changeMode(EnglishKeyboard.LOWER);
                    key.setBackgroundColor(Colour.CANDIDATE_SELECTED);
                    return true;
//                    break;
                case MotionEvent.ACTION_UP:
                    key.setBackgroundColor(Colour.NORMAL);
                    return true;
            }
            return false;
        }
    };
    public String text;
    public Layout textLayout;
    public Drawable image;
    public float dx, dy;


    public EnglishKey(final String s, int width) {
        super(C.mainService);
        setMeasuredDimension(width, Size.HEnglishKey);
        text = s;
        textLayout = Font.fr.make(s, width);

        dx = textLayout.getWidth() / 2;
        dy = (Size.HEnglishKey - textLayout.getHeight() * 1.5f) / 2;
        setBackgroundColor(Colour.SENTENCE);
    }

    public EnglishKey(final String s) {
        super(C.mainService);
        setMeasuredDimension(Size.WEnglishKey, Size.HEnglishKey);
        text = s;
        textLayout = Font.fr.make(s, Size.WEnglishKey);

        dx = textLayout.getWidth() / 2;
        dy = (Size.HEnglishKey - textLayout.getHeight() * 1.5f) / 2;
        setOnTouchListener(ontouchListener);
        setBackgroundColor(Colour.NORMAL);
    }

    public EnglishKey(int resource, int width) {
        super(C.mainService);
//        image = C.mainService.getResources().getDrawable(resource);
        image = ContextCompat.getDrawable(C.context, resource);
        int sideLength = Size.HEnglishKey * 4 / 5;
        image.setBounds(0, 0, sideLength, sideLength);
        dx = (width - sideLength) / 2;
        dy = (Size.HEnglishKey - sideLength) / 2;
        setBackgroundColor(Colour.FUNCTION);
        setMeasuredDimension(width, Size.HEnglishKey);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (textLayout != null) {
            canvas.save();
            canvas.translate(dx, dy);
            textLayout.draw(canvas);
            canvas.restore();
        }
        if (image != null) {
            canvas.save();
            canvas.translate(dx, dy);
            image.draw(canvas);
            canvas.restore();
        }
    }
}
