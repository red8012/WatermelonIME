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
//            else if (cls == CharacterKey.class)
//                ((CharacterKey) child).release();
            else if (cls == CandidateButton.class)
              ((CandidateButton) child).release();
        }
        C.candidateView.removeAllViews();
    }

    public static void setCandidate(ArrayList<String> list, int type) { //Todo: can be static
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
            CandidateButton c = CandidateButton.get();
            c.setText(s, padding, i > 0, type);
            C.candidateView.addView(c);
        }
//        final int length = list.size();
//        final int padding = CandidateButton.calculatePadding(list);
//        for (int i = 0; i < length; i++) {
//            CandidateButton c = CandidateButton.get();
//            c.setText(list.get(i), padding, i < length - 1, type);
//            addView(c);
//        }
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
