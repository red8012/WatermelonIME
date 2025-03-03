package co.watermelonime.InputView.Chinese.Keyboard;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Common.Size;

public class ChineseKey extends View {
    public static final int CONSONANT = -1, CHARACTER = -2, PUNCTUATION = -3;
    public int action = -1; // positive vowel keyboard num or the three types above
    public char pinyin, character;
    public Layout mainText, subText;
    public Drawable image;
    public float dx, dy, dxSub, dySub;

    public ChineseKey(final Layout mainText, final Layout subText, int backgroundColor) {
        super(C.mainService);
        this.mainText = mainText;
        this.subText = subText;

        dx = mainText.getWidth() / 2;
        float delta = 0;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP && mainText.getText().length() > 1)
            delta = Size.FMid / 2.5f;
        dy = (Size.HKey - mainText.getHeight() - delta) / 2;
        if (subText != null) {
            dy -= subText.getHeight() / 2;
            dxSub = subText.getWidth() / 2;
            dySub = (Size.HKey - subText.getHeight() * 1.2f);
        }
        setBackgroundColor(backgroundColor);
        setMeasuredDimension(Size.WKey, Size.HKey);
    }

    public ChineseKey(int resource, int backgroundColor) {
        super(C.mainService);
        image = ContextCompat.getDrawable(C.context, resource);
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
