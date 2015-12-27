package co.watermelonime.InputView.Chinese.Keyboard;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.View;

import co.watermelonime.C;

public class ChineseKey extends View {
    public int consonantId = 0;
    public Layout mainText, subText;
    public Drawable image;
    public float dx, dy, dxSub, dySub;
    RectF rect;
    Paint paint;

    public ChineseKey(final Layout mainText, final Layout subText, int backgroundColor) {
        super(C.mainService);
        this.mainText = mainText;
        this.subText = subText;
//        setLayerType(LAYER_TYPE_HARDWARE, null);

        dx = mainText.getWidth() / 2;
        dy = (C.u * 9 - mainText.getHeight()) / 2;
        if (subText != null) {
            dy -= subText.getHeight() / 2;
            dxSub = subText.getWidth() / 2;
            dySub = (C.u * 8 - subText.getHeight());
        }
        setBackgroundColor(backgroundColor);
    }

    public ChineseKey(int resource, int backgroundColor) {
        super(C.mainService);
        image = C.mainService.getResources().getDrawable(resource);
        image.setBounds(0, 0, C.u * 13 / 2, C.u * 13 / 2);
        dx = C.u * 3 / 2;
        dy = C.u;
        setBackgroundColor(backgroundColor);
    }

//    @Override
//    protected void onMeasure(int widthSpec, int heightSpec) {
//        Log.i("ChineseKey", "onMeasure");
//        setMeasuredDimension(C.chineseKeyWidth, C.chineseKeyHeight);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
//        Log.i("ChineseKey", "onDraw");
        if (paint != null) {
            canvas.drawRoundRect(rect, C.u, C.u, paint);
        }
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
