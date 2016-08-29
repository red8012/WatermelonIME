package co.watermelonime.InputView.Chinese.Candidate;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import co.watermelonime.C;
import co.watermelonime.Common.Size;

public class PredictionArea extends ViewGroup {

    public static PredictionArea area;

    public PredictionArea() {
        super(C.mainService);
    }

    public static void init() {
        area = new PredictionArea();
        area.setMeasuredDimension(Size.WCandidateView, Size.HCandidateVisible / 2);
    }

    public static void setPrediction(String[] list, int[] startPositions, StringBuilder[] pinyin) {
        area.removeAllViews();
        int totalWidth = 0;
        int count = 0;
        for (int i = 0; i < 3; i++) {
            String s = list[i];
            if (s == null) break;
            if (s.length() == 0) break;
            int w = CandidateButton.calculateMinWidth(s);
            if (totalWidth + w > Size.WCandidateView) break;
            totalWidth += w;
            count++;
        }
        if (count == 0) return;
        int padding = (Size.WCandidateView - totalWidth - 1) / count;
        for (int i = 0; i < count; i++) {
            PredictionKey k = PredictionKey.keys[i];
            k.startPosition = startPositions[i];
            k.pinyin = pinyin[i];
            k.setText(list[i], padding, i < count - 1, CandidateButton.TOP, CandidateButton.TOP);
            area.addView(k);
        }
        area.layout(0, 0, 0, 0);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        setMeasuredDimension(Size.WCandidateView, Size.HCandidateVisible / 2);
//    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i("PredictionArea", "onLayout");
        final int end = getChildCount();
        l = 0;
        t = 0;
        int lastH = 0;
        for (int i = 0; i < end; ++i) {
            View v = getChildAt(i);
            int w = v.getMeasuredWidth(), h = v.getMeasuredHeight();
            try {
                CharSequence cc = ((CandidateButton) v).text;
                if (cc == null) cc = String.valueOf(((CharacterKey) v).character);
//                Logger.d("PA: %d, h: %d, %s", w, h, cc);
            } catch (Exception e) {
            }

            if (l + w > Size.WKeyboard + Size.u) {
                l = 0;
                t += lastH;
            }
            if (w > Size.WKeyboard) {
//                Logger.d("%d %d %d %d", l, t, l + w, t + h);
                v.layout(l, t, l + w, t + h);
                l = 0;
                t += lastH;
            } else {
//                Logger.d("%d %d %d %d", l, t, l + w, t + h);
                v.layout(l, t, l + w, t + h);
                l += w;
            }
            lastH = h;
        }
    }
}
