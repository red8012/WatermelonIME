package co.watermelonime.InputView.English;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Font;
import co.watermelonime.Common.Size;

public class EnglishKey extends View {
    public String text;
    static final OnTouchListener ontouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            EnglishKey key = (EnglishKey) v;
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    C.commit(key.text);
                    key.setBackgroundColor(Colour.CANDIDATE_SELECTED);
                    return true;
                case MotionEvent.ACTION_UP:
                    key.setBackgroundColor(Colour.NORMAL);
                    return true;
            }
            return false;
        }
    };
    public Layout textLayout;
    public Drawable image;
    public float dx, dy;


    public EnglishKey(final String s) {
        super(C.mainService);
        setMeasuredDimension(Size.WEnglishKey, Size.HEnglishKey);
        text = s;
        textLayout = Font.fr.make(s, Size.WEnglishKey);

        dx = textLayout.getWidth() / 2;
        dy = (Size.HEnglishKey - textLayout.getHeight()) / 2;
        setOnTouchListener(ontouchListener);
        setBackgroundColor(Colour.NORMAL);
    }

    public EnglishKey(int resource, int width) {
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
