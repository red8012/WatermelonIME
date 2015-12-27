package co.watermelonime.InputView.Chinese.Candidate;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import co.watermelonime.C;
import co.watermelonime.Common.Printer;
import co.watermelonime.Common.Timer;

public class CandidateView extends ViewGroup {
    static ArrayList<CandidateButton> candidateButtonPool = new ArrayList<>(128);

    public CandidateView() {
        super(C.mainService);
        setBackgroundColor(C.COLOR_CANDIDATE);
    }

    static CandidateButton getCandidateButton() {
        if (candidateButtonPool.isEmpty())
            return new CandidateButton();
        int index = candidateButtonPool.size() - 1;
        return candidateButtonPool.remove(index);
    }

    static void releaseCandidateButton(CandidateButton c) {
        candidateButtonPool.add(c);
    }

    public void removeAllCandidates() {
        int end = getChildCount();
        for (int i = 0; i < end; i++) {
            releaseCandidateButton((CandidateButton) getChildAt(i));
        }
        removeAllViews();
    }

    public void setCandidate(ArrayList<String> list, int type) {
        Timer.t(8);
        int totalWidth = 0;
        int end = list.size();
        int i = 0;
        for (; i < end; i++) {
            int w = CandidateButton.calculateMinWidth(list.get(i));
            if (totalWidth + w > C.candidateWidth) break;
            totalWidth += w;
        }
        int padding = (C.candidateWidth - totalWidth - 1) / (i);
        for (String s : list) {
            if (i-- == 0) break;
            CandidateButton c = getCandidateButton();
            c.setText(s, padding, i > 0, type);
            addView(c);
        }
        Timer.t(8, "set left candidate");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("CandidateView", "onMeasure");
        setMeasuredDimension(C.u * 60, C.u * 49);
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
            Printer.p("layout w", w);
            if (l + w > C.keyboardWidth + C.u) {
                l = 0;
                t += v.getMeasuredHeight();
            }
            if (w > C.keyboardWidth) {
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
