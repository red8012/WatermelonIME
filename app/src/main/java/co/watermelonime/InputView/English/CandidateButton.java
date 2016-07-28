package co.watermelonime.InputView.English;

import android.graphics.Canvas;
import android.text.Layout;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Common.Font;
import co.watermelonime.Common.Size;
import co.watermelonime.Common.TextLayoutFactory;

public class CandidateButton extends View {
    public static int[] width = {0, 0, 0};
    static CandidateButton[] pool = new CandidateButton[3];
    static OnTouchListener onTouchListener = new OnTouchEnglishCandidate();
    public float dx, dy;
    public Layout layout;
    boolean needSeparator = true;

    public CandidateButton() {
        super(C.mainService);
    }

    public static void init() {
        for (int i = 0; i < 3; i++) {
            CandidateButton button = new CandidateButton();
            button.setOnTouchListener(onTouchListener);
            pool[i] = button;

        }
    }

    public static float measureText(String text) {
        return Font.englishCandidate.textPaint.measureText(text, 0, text.length());
    }

    public void setText(String text, int w, int padding) {
        setText(text, w, padding, Font.englishCandidate);
    }

    public void setText(String text, int w, int padding, TextLayoutFactory style) {
        layout = style.make(text, w);
        int width = w + padding;
        int height = layout.getHeight();
        dx = width / 2;
        dy = height / 3;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (layout == null) return;
        canvas.save();
        canvas.translate(dx, dy);
        layout.draw(canvas);
        canvas.restore();
        if (needSeparator) {
            float center = Size.HEnglishKey / 2;
            canvas.drawLine(getMeasuredWidth(), center - Size.HSeparator / 2,
                    getMeasuredWidth(), center + Size.HSeparator / 2,
                    co.watermelonime.InputView.Chinese.Candidate.CandidateButton.separatorPaint);
        }
    }
}
