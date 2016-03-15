package co.watermelonime.InputView.Emoji;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Font;
import co.watermelonime.Common.Size;

/**
 * Created by din on 2016/3/7.
 */
public class EmojiKey extends View {

    public char character;
    public Drawable image;
    public float dx, dy;
    public String text;
    public Layout textLayout;

    static final OnTouchListener ontouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            EmojiKey key = (EmojiKey) v;
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    C.commit(key.text);
                    key.setBackgroundColor(Colour.CANDIDATE_SELECTED);
                    return true;
                case MotionEvent.ACTION_UP:
                    return true;
            }
            return false;
        }
    };

    public EmojiKey(final String s) {
        super(C.mainService);
        setMeasuredDimension(Size.WKey, Size.HEmojiKey);
        text = s;
        textLayout = Font.fr.make(s);

        dx = textLayout.getWidth() / 2;
        dy = (Size.HEmojiKey - textLayout.getHeight()) / 2;
        setOnTouchListener(ontouchListener);
        setBackgroundColor(Colour.NORMAL);
    }

    public EmojiKey(int resource) {
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
