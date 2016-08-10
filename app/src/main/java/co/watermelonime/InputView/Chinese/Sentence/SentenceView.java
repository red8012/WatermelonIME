package co.watermelonime.InputView.Chinese.Sentence;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
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
        if (!C.isLandscape)
            children = LanguageSelector.keys;
        else children = sentenceButtons;
        for (View i : children)
            addView(i);
    }

    public static void setSelected(int index) {
        if (SentenceButton.selectedIndex == index) {
            SentenceButton sb = sentenceButtons[SentenceButton.selectedIndex];
            SentenceButton.selectedIndex = -1;
            sb.textLayout = sb.whiteTextLayout;
            sb.invalidate();
            return;
        } else if (SentenceButton.selectedIndex >= 0) {
            SentenceButton sb = sentenceButtons[SentenceButton.selectedIndex];
            sb.textLayout = sb.whiteTextLayout;
            sb.invalidate();
        }
        SentenceButton.selectedIndex = index;
        SentenceButton sb = sentenceButtons[index];
        sb.textLayout = sb.blackTextLayout;
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
            if (!C.isLandscape)
                children = LanguageSelector.keys;
            removeAllViews();
            for (int i = 0; i < 9; i++)
                sentenceButtons[i].setText('\0');
            for (View i : children) addView(i);
            return;
        }
        addSentenceButtons();
        StringBuilder sentence = Engine.sentence;
        final int length = sentence.length();

        for (int i = 0; i < length; i++) {
//            final String s = sentence.substring(i, i + 1);
            final SentenceButton sb = sentenceButtons[i];
            if (sb.text != null && sb.text.charAt(0) == sentence.charAt(i)) continue;
            sb.setText(sentence.charAt(i));
        }
        for (int i = length; i < 9; i++) {
            final SentenceButton sb = sentenceButtons[i];
            if (sb.textLayout == null) continue;
            sb.setText('\0');
        }
    }

    public void consonantPreview(String text) {
        addSentenceButtons();
        final int length = Engine.getLength();
        sentenceButtons[length].setText(text.charAt(0));
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

        t = 0;
        l = 0;
        if (C.isLandscape) l += Size.HSentencePaddingTop;
        else t += Size.HSentencePaddingTop;

        for (int i = 0; i < end; ++i) {
            View v = getChildAt(i);
            if (!C.isLandscape) {
                int h = v.getMeasuredHeight();
                v.layout(0, t, Size.WSentenceView, t + h);
                t += h;
            } else {
                int w = v.getMeasuredWidth();
                v.layout(l, 0, l + w, Size.HKey);
                l += w;
            }
        }
    }
}
