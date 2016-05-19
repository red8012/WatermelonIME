package co.watermelonime.InputView.Chinese.Sentence;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Font;
import co.watermelonime.Common.Size;
import co.watermelonime.Core.Engine;

public class SentenceView extends ViewGroup {
    public static SentenceButton[] sentenceButtons = new SentenceButton[9];
    public static View[] children;

    public SentenceView() {
        super(C.mainService);
        setBackgroundColor(Colour.SENTENCE);
        for (int i = 0; i < 9; i++)
            sentenceButtons[i] = new SentenceButton(i);
        children = LanguageSelector.keys;
        for (View i : children)
            addView(i);
    }

    public static void setSelected(int index) {
        if (SentenceButton.selectedIndex == index) {
            SentenceButton sb = sentenceButtons[SentenceButton.selectedIndex];
            SentenceButton.selectedIndex = -1;
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
        if (children == sentenceButtons) return;
        children = sentenceButtons;
        removeAllViews();
        for (View i : children)
            addView(i);
        layout(0, 0, 0, 0);
    }

    public void display() {
        if (Engine.isEmpty()) {
            children = LanguageSelector.keys;
            removeAllViews();
            for (int i = 0; i < 9; i++) sentenceButtons[i].setText(null);
            for (View i : children) addView(i);
            return;
        }
        addSentenceButtons();
        StringBuilder sentence = Engine.sentence;
        final int length = sentence.length();

        for (int i = 0; i < length; i++) {
            final String s = sentence.substring(i, i + 1);
            final SentenceButton sb = sentenceButtons[i];
            if (sb.text != null && sb.text.charAt(0) == s.charAt(0)) continue;
            sb.setText(s);
        }
        for (int i = length; i < 9; i++) {
            final SentenceButton sb = sentenceButtons[i];
            if (sb.text == null) continue;
            sb.setText(null);
        }
    }

    public void consonantPreview(String text) {
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
        t = Size.HSentencePaddingTop;

        for (int i = 0; i < end; ++i) {
            View v = getChildAt(i);
            int h = v.getMeasuredHeight();
            v.layout(0, t, Size.WSentenceView, t + h);
            t += h;
        }
    }
}
