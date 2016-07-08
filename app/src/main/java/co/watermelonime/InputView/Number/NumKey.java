package co.watermelonime.InputView.Number;

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

public class NumKey extends View {
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
                    key.setBackgroundColor(key.isDigit ? Colour.CHARACTER : Colour.NORMAL);
                    return true;
            }
            return false;
        }
    };
    public String text;
    public int keyCode;
    public Layout textLayout;
    public Drawable image;
    public float dx, dy;
    boolean isDigit = false;

    public NumKey(final String s, boolean isDigit) {
        super(C.mainService);
        setMeasuredDimension(Size.WKey, Size.HNumKey);
        text = s;
        textLayout = Font.big.make(s);

        dx = textLayout.getWidth() / 2;
        dy = (Size.HNumKey - textLayout.getHeight()) / 2;
        setOnTouchListener(ontouchListener);
        setBackgroundColor(isDigit ? Colour.CHARACTER : Colour.NORMAL);
        this.isDigit = isDigit;
    }

    public NumKey(int resource) {
        super(C.mainService);
//        image = C.mainService.getResources().getDrawable(resource);
        image = ContextCompat.getDrawable(C.context, resource);
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
