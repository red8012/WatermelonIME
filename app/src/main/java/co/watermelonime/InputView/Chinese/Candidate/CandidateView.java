package co.watermelonime.InputView.Chinese.Candidate;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Size;
import co.watermelonime.Common.Timer;

public class CandidateView extends ViewGroup {
    public static int height = Size.HCandidateVisible;
    static boolean isDictionaryMode = false;

    public CandidateView() {
        super(C.mainService);
        setBackgroundColor(Colour.CANDIDATE);
    }

    public static void clearCandidates() {
        int len = C.candidateView.getChildCount();
        for (int i = 0; i < len; i++) {
            View child = C.candidateView.getChildAt(i);
            Class cls = child.getClass();
            if (cls == DictButton.class)
                ((DictButton) child).release();
            else if (cls == DictTitle.class)
                ((DictTitle) child).release();
            else if (cls == CandidateButton.class)
                ((CandidateButton) child).release();
        }
        C.candidateView.removeAllViews();
    }

    public static void setCandidate(ArrayList<StringBuilder> list, int start, int end, int type) {
        Timer.t(8);
        if (end - start == 0) return;
        int totalWidth = 0;

        int i = start;
        for (; i < end; i++) {
            int w = CandidateButton.calculateMinWidth(list.get(i));
            if (totalWidth + w > Size.WCandidateView) break;
            totalWidth += w;
        }
        end = i;

        int padding = (Size.WCandidateView - totalWidth - 1) / (end - start);

        for (int j = start; j < end; j++) {
            CandidateButton c = CandidateButton.get();
            c.setText(list.get(j), padding, j < end - 1, type);
            C.candidateView.addView(c);
        }
        Timer.t(8, "set candidate" + type);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isDictionaryMode)
            height = Size.HCandidateVisible;
        setMeasuredDimension(Size.WCandidateView, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i("CandidateView", "onLayout");

        final int end = getChildCount();
        l = 0;
        t = 0;

        for (int i = 0; i < end; ++i) {
            View v = getChildAt(i);
            int w = v.getMeasuredWidth(), h = v.getMeasuredHeight();

            if (v.getClass() == DictTitle.class) {
                if (l > 0) t += h;
                l = 0;
            }
            if (l + w > Size.WKeyboard + Size.u) {
                l = 0;
                t += v.getMeasuredHeight();
            }
            if (w > Size.WKeyboard) {
                v.layout(l, t, l + w, t + h);
                l = 0;
                t += h;
            } else {
                v.layout(l, t, l + w, t + h);
                l += w;
            }
        }
    }

}
