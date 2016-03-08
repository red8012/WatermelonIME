package co.watermelonime.InputView.Numpad;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Font;
import co.watermelonime.Common.Size;

public class NumKey extends View {
    static final OnTouchListener shiftListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            NumKey key = (NumKey) v;
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    C.numberKeyboard.removeAllViews();
                    for (NumKey i : NumberKeyboard.chineseNumberKeys)
                        C.numberKeyboard.addView(i);
                    return true;
                case MotionEvent.ACTION_UP:
//                    key.setBackgroundColor(Colour.FUNCTION);
                    return true;
            }
            return false;
        }
    };
    public String text;
    static final OnTouchListener ontouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            NumKey key = (NumKey) v;
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    C.commit(key.text);
                    key.setBackgroundColor(Colour.CANDIDATE_SELECTED);
                    return true;
                case MotionEvent.ACTION_UP:
                    if (Character.isDigit(key.text.charAt(0)))
                        key.setBackgroundColor(Colour.CHARACTER);
                    else if (Character.isSpaceChar(key.text.charAt(0)))
                        key.setBackgroundColor(Colour.FUNCTION);
                    else
                        key.setBackgroundColor(Colour.NORMAL);
                    return true;
            }
            return false;
        }
    };
    public int keyCode;
    static final OnTouchListener functionKeyListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            NumKey key = (NumKey) v;
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    C.mainService.sendDownUpKeyEvents(key.keyCode);
                    key.setBackgroundColor(Colour.CANDIDATE_SELECTED);
                    return true;
                case MotionEvent.ACTION_UP:
                    key.setBackgroundColor(Colour.FUNCTION);
                    return true;
            }
            return false;
        }
    };
    public Layout textLayout;
    public Drawable image;
    public float dx, dy;

    public NumKey(final String s) {
        super(C.mainService);
        setMeasuredDimension(Size.WKey, Size.HNumKey);
        text = s;
        textLayout = Font.big.make(s);

        dx = textLayout.getWidth() / 2;
        dy = (Size.HNumKey - textLayout.getHeight()) / 2;
        setOnTouchListener(ontouchListener);
        if (Character.isDigit(s.charAt(0)))
            setBackgroundColor(Colour.CHARACTER);

    }

    public NumKey(int resource) {
        super(C.mainService);
        image = C.mainService.getResources().getDrawable(resource);
        image.setBounds(0, 0, Size.keyIcon, Size.keyIcon);
        dx = Size.u * 3 / 2;
        dy = Size.u;
        setBackgroundColor(Colour.FUNCTION);
        setMeasuredDimension(Size.WKey, Size.HKey);
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
