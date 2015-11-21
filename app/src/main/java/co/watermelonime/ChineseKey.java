package co.watermelonime;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.StaticLayout;
import android.view.View;

public class ChineseKey extends View {

    public StaticLayout mainText, subText;
    public Drawable image;
    public float dx, dy, dxSub, dySub;
    RectF rect;
    Paint paint;

    public ChineseKey(StaticLayout mainText, StaticLayout subText, int backgroundColor) {
        super(C.mainService);
        this.mainText = mainText;
        this.subText = subText;

        dx = mainText.getWidth() / 2;
        dy = (C.u * 9 - mainText.getHeight()) / 2;
        if (subText != null) {
            dy -= subText.getHeight() / 2;
            dxSub = subText.getWidth() / 2;
            dySub = (C.u * 8 - subText.getHeight());
        }

        if (backgroundColor == C.COLOR_NORMAL)
            setBackgroundColor(backgroundColor);
        else {
            paint = new Paint();
            paint.setColor(backgroundColor);
            rect = new RectF(0f, 0f, C.chineseKeyWidth, C.chineseKeyHeight);
        }
    }

    public ChineseKey(int resource, int backgroundColor) {
        super(C.mainService);
        image = C.mainService.getResources().getDrawable(resource);
        image.setBounds(0, 0, C.u * 13 / 2, C.u * 13 / 2);
        dx = C.u * 3 / 2;
        dy = C.u;
        if (backgroundColor == C.COLOR_NORMAL)
            setBackgroundColor(backgroundColor);
        else {
            paint = new Paint();
            paint.setColor(backgroundColor);
            rect = new RectF(0f, 0f, C.chineseKeyWidth, C.chineseKeyHeight);
        }
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        setMeasuredDimension(C.chineseKeyWidth, C.chineseKeyHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
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
