package co.watermelonime.InputView.English;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Size;

public class SpaceBar extends View {
    static final Paint rectPaint = new Paint();
    static final OnTouchListener ontouchListener = (v, event) -> {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                rectPaint.setColor(Colour.textKeyboard);
                C.mainService.sendDownUpKeyEvents(KeyEvent.KEYCODE_SPACE);
                CandidateBar.reset();
//                CandidateBar.setApplicable(true);
                v.invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                rectPaint.setColor(Colour.SPACE);
                v.invalidate();
                return true;
        }
        return false;
    };
    static RectF rect;


    public SpaceBar() {
        super(C.mainService);
        setMeasuredDimension(Size.WSpace, Size.HEnglishKey);
        setOnTouchListener(ontouchListener);
        setBackgroundColor(Colour.NORMAL);
        if (rect == null) {
            rectPaint.setColor(Colour.SPACE);
            rect = new RectF(Size.u * 2, Size.u * 2, Size.WSpace - Size.u * 2, Size.HEnglishKey - Size.u * 2);
            setMeasuredDimension(Size.WSpace, Size.HEnglishKey);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(rect, Size.u, Size.u, rectPaint);
    }
}
