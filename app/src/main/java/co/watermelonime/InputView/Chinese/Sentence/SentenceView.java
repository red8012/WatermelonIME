package co.watermelonime.InputView.Chinese.Sentence;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Size;
import co.watermelonime.Core.Engine;

public class SentenceView extends ViewGroup {

    //    public static int width, height;
    public SentenceButton[] sentenceButtons = new SentenceButton[9];

    public SentenceView() {
        super(C.mainService);
        setBackgroundColor(Colour.SENTENCE);
//        String s = "床前明月光疑是地上霜";
        for (int i = 0; i < 9; i++) {
            SentenceButton sb = new SentenceButton();
            sentenceButtons[i] = sb;
//            sb.setText(s.substring(i, i + 1));
            addView(sb);
        }
    }

    public void display() {
        StringBuffer sentence = Engine.getSentenceBuffer();
        final int length = sentence.length();
        for (int i = 0; i < length; i++) sentenceButtons[i].setText(sentence.substring(i, i + 1));
        for (int i = length; i < 9; i++) sentenceButtons[i].setText(null);
        invalidate();
    }

    public void consonantPreview(String text) {
        final int length = Engine.getLength();
        sentenceButtons[length].setText(text);
        sentenceButtons[length].invalidate();
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
