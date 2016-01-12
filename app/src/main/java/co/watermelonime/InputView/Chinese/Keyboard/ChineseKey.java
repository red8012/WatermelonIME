package co.watermelonime.InputView.Chinese.Keyboard;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Common.Size;

public class ChineseKey extends View {
    public int id = -1;
    public Layout mainText, subText;
    public Drawable image;
    public float dx, dy, dxSub, dySub;
//    public String keyCode;

    public ChineseKey(final Layout mainText, final Layout subText, int backgroundColor) {
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

    public ChineseKey(int resource, int backgroundColor) {
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
