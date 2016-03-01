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
    static ArrayList<CandidateButton> candidateButtonPool = new ArrayList<>(128);
    static boolean isDictionaryMode = false;

    public CandidateView() {
        super(C.mainService);
        setBackgroundColor(Colour.CANDIDATE);
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

    public void clearCandidates() {
        int end = getChildCount();
        for (int i = 0; i < end; i++) {
            releaseCandidateButton((CandidateButton) getChildAt(i));
        }
        removeAllViews();
    }

    public void setCandidate(ArrayList<String> list, int type) { //Todo: can be static
        Timer.t(8);
        int totalWidth = 0;
        int end = list.size();
        if (end == 0) return; // ? why this happens?
        int i = 0;
        for (; i < end; i++) {
            int w = CandidateButton.calculateMinWidth(list.get(i));
            if (totalWidth + w > Size.WCandidateView) break;
            totalWidth += w;
        }
        int padding = (Size.WCandidateView - totalWidth - 1) / (i);
        for (String s : list) {
            if (i-- == 0) break;
            CandidateButton c = getCandidateButton();
            c.setText(s, padding, i > 0, type);
            addView(c);
        }
//        layout(0, 0, Size.WCandidateView, Size.HCandidateView);
        onLayout(true, 0, 0, Size.WCandidateView, Size.HCandidateView);
        Timer.t(8, "set left candidate");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Log.i("CandidateView", "onMeasure");
        if (!isDictionaryMode)
            height = Size.HCandidateVisible;
        setMeasuredDimension(Size.WCandidateView, height);
//        else
//            setMeasuredDimension(Size.WCandidateView, Size.HCandidateView);
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
