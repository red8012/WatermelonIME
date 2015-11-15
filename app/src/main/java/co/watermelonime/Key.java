package co.watermelonime;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.StaticLayout;
import android.view.View;

public class Key extends View {
    public StaticLayout textLayout;
    public float dx, dy;

    public Key(Context context, StaticLayout textLayout) {
        super(context);
        this.textLayout = textLayout;
        setBackgroundColor(Color.GREEN);
        dx = textLayout.getWidth() / 2;
        dy = 0;
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        setMeasuredDimension(100, 100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        if (textLayout != null) {
//            canvas.save();
            canvas.translate(dx, dy);
            Timer.t(101);
            textLayout.draw(canvas);
            Timer.t(101, "onDraw");
//            canvas.restore();
        }
    }
}
