package co.watermelonime.InputView.Chinese.Candidate;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import co.watermelonime.C;
import co.watermelonime.Common.Size;

public class CandidateButton extends View {
    public static final Paint separatorPaint = new Paint();
    public static final int BOTTOM = -1, TOP = 1, DICT = 2;
    static final OnClickCandidate onClickCandidate = new OnClickCandidate();
    static ArrayList<CandidateButton> pool = new ArrayList<>(16);
    public float dx, dy;
    int type = 0;
    int width;
    CharSequence text;
    Layout textLayout;
    boolean needSeparator = true;

    public CandidateButton() {
        super(C.mainService);
        setOnClickListener(onClickCandidate);
    }

    public static void init() {
        CandidateButton.separatorPaint.setColor(0xFF666666);
        CandidateButton.separatorPaint.setStrokeWidth(Size.WSeparator);
        for (int i = 0; i < 16; i++) {
            pool.add(new CandidateButton());
        }
    }

    public static CandidateButton get() {
        CandidateButton d;
        if (pool.isEmpty()) {
            d = new CandidateButton();
            Log.e("pool", "empty");
        } else
            d = pool.remove(pool.size() - 1);
        return d;
    }

    public static int calculateMinWidth(CharSequence s) {
        int len = s.length();
        if (len < 2)
            return (int) (Size.FCandidate * len + Size.u * 3);
        else return (int) (Size.FCandidate * len + Size.u * 2);
    }

    public static int calculateMinWidth() {
        return (int) (Size.FCandidate + Size.u * 3);
    }

    public void release() {
        pool.add(this);
        DynamicLayoutPool.release(textLayout);
        textLayout = null;
    }

    public void setText(CharSequence s, int padding, boolean separator, int type, int paddingTopBottom) {
        text = s;
        width = calculateMinWidth(s) + padding;

        this.type = type;
        needSeparator = separator;
        setMeasuredDimension(width, (int) (Size.HCandidateRow + (paddingTopBottom == 0 ? 0 : Size.u / 2)));
        if (textLayout != null) DynamicLayoutPool.release(textLayout);

        if (s == null) {
            textLayout = null;
        } else {
            textLayout = DynamicLayoutPool.get(s);
            dx = width / 2;
            dy = (Size.HCandidateRow - textLayout.getHeight() * 1.5f) / 2 +
                    (paddingTopBottom == TOP ? Size.u / 2 : 0);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (textLayout == null) return;
        canvas.save();
        canvas.translate(dx, dy);
        textLayout.draw(canvas);
        canvas.restore();
        if (needSeparator) {
//            float center = dy + Size.FCandidate / 2;
            float center = dy + Size.FCandidate / 2 + textLayout.getHeight() * 0.25f;
            canvas.drawLine(width, center - Size.HSeparator / 2,
                    width, center + Size.HSeparator / 2, separatorPaint);
        }
    }
}
