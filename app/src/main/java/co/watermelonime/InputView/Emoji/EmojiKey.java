package co.watermelonime.InputView.Emoji;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Common.Size;

/**
 * Created by din on 2016/3/7.
 */
public class EmojiKey extends View {

    public static final int CONSONANT = -1, CHARACTER = -2, PUNCTUATION = -3;
    public int action = -1; // positive vowel keyboard num or the three types above
    public char pinyin, character;
    public Layout mainText, subText;
    public Drawable image;
    public float dx, dy, dxSub, dySub;

    public EmojiKey(final Layout mainText, final Layout subText, int backgroundColor) {
        super(C.mainService);
        this.mainText = mainText;
        this.subText = subText;
//        this.keyCode = keyCode;

        dx = mainText.getWidth() / 2;
        dy = (Size.u * 9 - mainText.getHeight()) / 2;
        if (subText != null) {
            dy -= subText.getHeight() / 2;
            dxSub = subText.getWidth() / 2;
            dySub = (Size.u * 8 - subText.getHeight());
        }
        setBackgroundColor(backgroundColor);
        setMeasuredDimension(Size.WKey, Size.HKey);
    }

    public EmojiKey(int resource, int backgroundColor) {
        super(C.mainService);
        image = C.mainService.getResources().getDrawable(resource);
        image.setBounds(0, 0, Size.keyIcon, Size.keyIcon);
        dx = Size.u * 3 / 2;
        dy = Size.u;
        setBackgroundColor(backgroundColor);
        setMeasuredDimension(Size.WKey, Size.HKey);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (mainText != null) {
            canvas.save();
            canvas.translate(dx, dy);
            mainText.draw(canvas);
            canvas.restore();
        }
        if (subText != null) {
            canvas.save();
            canvas.translate(dxSub, dySub);
            subText.draw(canvas);
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
