package co.watermelonime.InputView.Chinese.Sentence;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import co.watermelonime.C;
import co.watermelonime.Common.Size;

public class SentenceView extends ViewGroup {

    public static int width, height;
    public SentenceButton[] sentenceButton = new SentenceButton[9];

    public SentenceView() {
        super(C.mainService);
        setBackgroundColor(C.COLOR_SENTENCE);
        String s = "床前明月光疑是地上霜";
        for (int i = 0; i < 9; i++) {
            SentenceButton sb = new SentenceButton();
            sb.setText(s.substring(i, i + 1));
            addView(sb);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("SentenceView", "onMeasure");
        setMeasuredDimension(Size.WSentenceView, Size.HSentenceView);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i("SentenceView", "onLayout");
        final int end = getChildCount();
        t = Size.HSentencePaddingTop;

        int h = Size.HSentenceButton;
        for (int i = 0; i < end; ++i) {
            View v = getChildAt(i);
            v.layout(0, t, Size.WSentenceView, t + h);
            t += h;
        }
    }
}
