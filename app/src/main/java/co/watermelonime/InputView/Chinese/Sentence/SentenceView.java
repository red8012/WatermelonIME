package co.watermelonime.InputView.Chinese.Sentence;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Font;
import co.watermelonime.Common.Printer;
import co.watermelonime.Common.Size;
import co.watermelonime.Core.Engine;

public class SentenceView extends ViewGroup {
    public static SentenceButton[] sentenceButtons = new SentenceButton[9];
    public SentenceButton[] children;

    public SentenceView() {
        super(C.mainService);
        setBackgroundColor(Colour.SENTENCE);
        for (int i = 0; i < 9; i++) {
            SentenceButton sb = new SentenceButton(i);
            sentenceButtons[i] = sb;
        }
        children = FunctionKeys.keys;
        for (SentenceButton i : children)
            addView(i);
    }

    public static void setSelected(int index) {
        if (SentenceButton.selectedIndex == index) {
            SentenceButton sb = sentenceButtons[SentenceButton.selectedIndex];
            SentenceButton.selectedIndex = -1;
//            sb.textLayout = sb.originalTextLayout;
            sb.textLayout = Font.sentence.make(sb.text);
            sb.invalidate();
            return;
        } else if (SentenceButton.selectedIndex >= 0) {
            SentenceButton sb = sentenceButtons[SentenceButton.selectedIndex];
            if (sb.originalTextLayout != null) sb.textLayout = sb.originalTextLayout;
            sb.invalidate();
        }
        SentenceButton.selectedIndex = index;
        SentenceButton sb = sentenceButtons[index];
        sb.originalTextLayout = sb.textLayout;
        sb.textLayout = Font.sentenceSelected.make(sb.text);
        sb.invalidate();
    }

    public void addSentenceButtons() {
        if (children != sentenceButtons) {
            children = sentenceButtons;
            removeAllViews();
            for (SentenceButton i : children)
                addView(i);
            onLayout(true, 0, 0, 0, 0);
        }
    }

    public void display() {
        if (Engine.isEmpty()) {
            children = FunctionKeys.keys;
            for (int i = 0; i < 9; i++) sentenceButtons[i].setText(null);
            removeAllViews();
            for (SentenceButton i : children)
                addView(i);
        } else {
            addSentenceButtons();
            StringBuffer sentence = Engine.getSentenceBuffer();
            final int length = sentence.length();
            for (int i = 0; i < length; i++) {
                final String s = sentence.substring(i, i + 1);
                final SentenceButton sb = sentenceButtons[i];
                if (sb.text.equals(s)) continue;
                sb.setText(s);
                sb.invalidate();
            }
            for (int i = length; i < 9; i++) {
                final SentenceButton sb = sentenceButtons[i];
                if (sb.text == null) continue;
                sb.setText(null);
                sb.invalidate();
            }
        }
    }

    public void consonantPreview(String text) {
        System.out.println("displayTexts consonant preview");
        addSentenceButtons();
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
        Printer.p("child count", end);
        t = Size.HSentencePaddingTop;

        int h = Size.HSentenceButton;
        for (int i = 0; i < end; ++i) {
            View v = getChildAt(i);
            v.layout(0, t, Size.WSentenceView, t + h);
            t += h;
        }
    }
}
