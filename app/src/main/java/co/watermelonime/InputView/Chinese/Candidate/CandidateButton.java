package co.watermelonime.InputView.Chinese.Candidate;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.view.View;

import co.watermelonime.C;

public class CandidateButton extends View {
    public static final Paint separatorPaint = new Paint();
    public static final int BOTTOM = -1, TOP = 1;
    public float dx, dy;
    int paddingTopBottom = 0;
    int length, width;
    String text;
    Layout textLayout;
    boolean needSeparator = true;
    OnTouchListener onTouchListener = new OnTouchCandidate();

    public CandidateButton() {
        super(C.mainService);
        setOnTouchListener(onTouchListener);
    }

    public static int calculateMinWidth(String s) {
        int len = s.length();
        if (len < 2)
            return C.candidateFontSize * len + C.u * 4;
        else return C.candidateFontSize * len + C.u * 2;
    }

    public void setText(String s, int padding, boolean separator, int paddingTopBottom) {
        text = s;
        width = calculateMinWidth(s) + padding;
        this.paddingTopBottom = paddingTopBottom;
        needSeparator = separator;
        setMeasuredDimension(width, C.candidateButtonHeight + (paddingTopBottom == 0 ? 0 : C.u / 2));
        if (s == null) textLayout = null;
        else {
            textLayout = C.candidateFont.make(text, width);
            dx = width / 2;
            dy = (C.candidateButtonHeight - textLayout.getHeight()) / 2 +
                    (paddingTopBottom == TOP ? C.u / 2 : 0);
        }
    }

    public void setText(String s, int padding, boolean separator) {
        setText(s, padding, separator, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (textLayout == null) return;
        canvas.save();
        canvas.translate(dx, dy);
        textLayout.draw(canvas);
        canvas.restore();
        if (needSeparator)
            canvas.drawLine(width, dy, width, dy + C.u * 7 / 2, separatorPaint);
    }
}
