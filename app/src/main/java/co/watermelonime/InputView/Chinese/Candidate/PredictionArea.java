package co.watermelonime.InputView.Chinese.Candidate;

import android.util.Log;

import co.watermelonime.Common.Size;

public class PredictionArea extends CandidateView {

    public static PredictionArea area = new PredictionArea();

    public PredictionArea() {
        setMeasuredDimension(Size.WCandidateView, Size.HCandidateVisible / 2);
    }

    public static void setPrediction(String[] list, int[] startPositions, StringBuilder[] pinyin) {
        area.removeAllViews();
        int totalWidth = 0;
        int count = 0;
        for (int i = 0; i < 3; i++) {
//            Layout layout = PredictionKey.keys[i].textLayout;
//            if (layout != null) DynamicLayoutPool.release(layout);
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
//            System.out.println("setPrediction: "+ list[i]);
            area.addView(k);
        }
        area.layout(0, 0, 0, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(Size.WCandidateView, Size.HCandidateVisible / 2);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.i("PredictionArea", "onLayout");
    }
}
