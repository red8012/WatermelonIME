package co.watermelonime.InputView.Chinese.Candidate;

import android.graphics.Color;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import co.watermelonime.C;
import co.watermelonime.Common.Size;

public class CandidateViewScroller extends ScrollView {
    public CandidateViewScroller() {
        super(C.mainService);
        setBackgroundColor(Color.RED);
        setMeasuredDimension(Size.WCandidateView, Size.HCandidateView);

//        Button b = new Button(C.mainService);
//        b.setBackgroundColor(Color.GREEN);
//        b.setText("WTFFFFFFFFFFFFFFFFFFFFFFFFFF");

        LinearLayout b = new LinearLayout(C.mainService);
        b.setBackgroundColor(Color.BLUE);
        b.setMinimumHeight(1000);
        b.setMinimumWidth(100);

        setFillViewport(true);

        removeAllViews();
        addView(b, 100, 1000);
        System.out.println("Child count " + getChildCount());
        System.out.println("w " + getWidth());
        System.out.println("h " + getHeight());
//        addView(C.candidateView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("CandidateViewScroller", "onMeasure");
        setMeasuredDimension(Size.WCandidateView, Size.HCandidateView);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//        Printer.p("ltrb", l, t, r, b);
//        View v = getChildAt(0);
//        v.layout(l, t, r, b);
    }
}
