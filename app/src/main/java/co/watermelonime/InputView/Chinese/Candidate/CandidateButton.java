package co.watermelonime.InputView.Chinese.Candidate;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.DynamicLayout;
import android.text.Layout;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import co.watermelonime.C;
import co.watermelonime.Common.Size;

public class CandidateButton extends View {
    public static final Paint separatorPaint = new Paint();
    public static final int BOTTOM = -1, TOP = 1;
    static final OnClickCandidate onClickCandidate = new OnClickCandidate();
    static ArrayList<CandidateButton> pool = new ArrayList<>(16);
    public float dx, dy;
    int type = 0;
    int width;
//    String text;
    CharSequence text;
    Layout textLayout;
    boolean needSeparator = true;

    public CandidateButton() {
        super(C.mainService);
        setOnClickListener(onClickCandidate);
    }

    public static void init() {
        for (int i = 0; i < 16; i++) {
            pool.add(new CandidateButton());
        }
    }

    public static CandidateButton get() {
        CandidateButton d;
        if (pool.isEmpty()) {
            d = new CandidateButton();
            Log.e("pool", "empty");
        }
        else
            d = pool.remove(pool.size() - 1);
        return d;
    }

    public static int calculateMinWidth(String s) {
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
        DynamicLayoutPool.release((DynamicLayout) textLayout);
    }

    public void setText(String s, int padding, boolean separator, int paddingTopBottom) {
        text = s;
        width = calculateMinWidth(s) + padding;
        this.type = paddingTopBottom;
        needSeparator = separator;
        setMeasuredDimension(width, (int) (Size.HCandidateRow + (paddingTopBottom == 0 ? 0 : Size.u / 2)));
        if (s == null) textLayout = null;
        else {
//            textLayout = Font.candidate.make(text, width);
            textLayout = DynamicLayoutPool.get(s);
            dx = width / 2;
            dy = (Size.HCandidateRow - textLayout.getHeight()) / 2 +
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
            float center = dy + Size.FCandidate / 2;
            canvas.drawLine(width, center - Size.HSeparator / 2,
                    width, center + Size.HSeparator / 2, separatorPaint);
        }
    }
}
