package co.watermelonime.InputView.Chinese.Candidate;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Size;
import co.watermelonime.Common.Timer;

public class CandidateView extends ViewGroup {
    static boolean isDictionaryMode = false;
    public int height = Size.HCandidateVisible;

    public CandidateView() {
        super(C.mainService);
        setBackgroundColor(Colour.CANDIDATE);
    }

    public static CandidateView getCandidateViewForDict() {
        return C.isLandscape ? C.landscapeCandidateViewRight : C.candidateView;
    }

    public void clearCandidates() {
        int len = getChildCount();
        for (int i = 0; i < len; i++) {
            View child = getChildAt(i);
            Class cls = child.getClass();
            if (cls == DictButton.class)
                ((DictButton) child).release();
            else if (cls == DictTitle.class)
                ((DictTitle) child).release();
            else if (cls == CandidateButton.class)
                ((CandidateButton) child).release();
        }
        removeAllViews();
    }

    public int setCandidate(ArrayList<StringBuilder> list, int start, int end, int type) {
        Timer.t(8);
        if (end - start == 0) return end;
        int totalWidth = 0;

        Logger.d("len: %d   end: %d", list.size(), end);

        int i = start;
        for (; i < end; i++) { // todo: may lost one?
            int w = CandidateButton.calculateMinWidth(list.get(i));
            if (totalWidth + w > Size.WCandidateView) break;
            totalWidth += w;
        }
        int paddingTopBottom = type;
        if (type == CandidateButton.DICT)
            if (start == 0) paddingTopBottom = CandidateButton.TOP;
            else if (end == i) paddingTopBottom = CandidateButton.BOTTOM;
            else paddingTopBottom = 0;

        end = i;

        int padding = (Size.WCandidateView - totalWidth) / (end - start);
        for (int j = start; j < end; j++) {
            CandidateButton c = CandidateButton.get();
            c.setText(list.get(j), padding, j < end - 1, type, paddingTopBottom);
            C.candidateView.addView(c);
        }
        Timer.t(8, "set candidate" + type);
        return end;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isDictionaryMode) height = Size.HCandidateVisible;
        else if (this == C.candidateView && C.isLandscape) {
            final int end = getChildCount();
            int l = 0, t = 0;
            for (int i = 0; i < end; ++i) {
                View v = getChildAt(i);
                int w = v.getMeasuredWidth(), h = v.getMeasuredHeight();

                if (l + w > Size.WKeyboard + Size.u) {
                    l = 0;
                    t += h;
                }
                if (w > Size.WKeyboard) {
                    l = 0;
                    t += h;
                } else {
                    l += w;
                }
            }
            height = t + Size.HCandidateRow;
        }
        setMeasuredDimension(
                C.isLandscape ? Size.WChineseLandscapeLeft : Size.WCandidateView,
                height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i("CandidateView", "onLayout");

        final int end = getChildCount();
        l = 0;
        t = 0;
        int lastH = 0;
        for (int i = 0; i < end; ++i) {
            View v = getChildAt(i);
            int w = v.getMeasuredWidth(), h = v.getMeasuredHeight();

            if (lastH == 0) lastH = h;

            if (v.getClass() == DictTitle.class) {
                if (l > 0) t += lastH;
                l = 0;
            }
            if (l + w > Size.WKeyboard + Size.u) {
                l = 0;
                t += lastH;
            }
            if (w > Size.WKeyboard) {
                v.layout(l, t, l + w, t + h);
//                try {
//                    CharSequence cc = ((CandidateButton) v).text;
//                    if (cc == null) cc = String.valueOf(((CharacterKey) v).character);
//                    Logger.d("new line %s %d %d %d %d %d", cc, lastH, l, t, l + w, t + h);
//                } catch (Exception e) {
//                }
                l = 0;
                t += lastH;
            } else {
                v.layout(l, t, l + w, t + h);
//                try {
//                    CharSequence cc = ((CandidateButton) v).text;
//                    if (cc == null) cc = String.valueOf(((CharacterKey) v).character);
//                    Logger.d("%s %d %d %d %d %d", cc, lastH, l, t, l + w, t + h);
//                } catch (Exception e) {
//                }
                l += w;
            }
            lastH = h;

        }
    }

}
