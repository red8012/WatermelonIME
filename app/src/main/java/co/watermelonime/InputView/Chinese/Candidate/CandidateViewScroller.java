package co.watermelonime.InputView.Chinese.Candidate;

import android.util.Log;
import android.widget.ScrollView;

import co.watermelonime.C;
import co.watermelonime.Common.Size;

public class CandidateViewScroller extends ScrollView {
    public CandidateViewScroller() {
        super(C.mainService);
        addView(C.candidateView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("CandidateView", "onMeasure");
        setMeasuredDimension(Size.WCandidateView, Size.HCandidateView);
    }
}
