package co.watermelonime;

import android.graphics.Canvas;
import android.graphics.Color;
import android.text.StaticLayout;
import android.view.View;

public class Key extends View {
    public StaticLayout textLayout;
    public float dx, dy;

    public Key(StaticLayout textLayout) {
        super(C.mainService);
        this.textLayout = textLayout;
        setBackgroundColor(Color.rgb(67, 84, 90));
        dx = textLayout.getWidth() / 2;
        dy = (C.u * 9 - textLayout.getHeight())/2;
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        setMeasuredDimension(C.u * 10, C.u * 9);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (textLayout != null) {
            canvas.save();
            canvas.translate(dx, dy);
            textLayout.draw(canvas);
            canvas.restore();
        }
    }
}
