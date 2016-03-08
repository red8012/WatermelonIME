package co.watermelonime.InputView.Emoji;

import android.view.ViewGroup;
import android.widget.ScrollView;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Size;
import co.watermelonime.InputView.Chinese.Sentence.LanguageSelector;

public class Emoji extends ViewGroup {
    public static ScrollView scrollView;

    public Emoji() {
        super(C.mainService);
        setBackgroundColor(Colour.DISABLED);

//        LanguageSelector.setInputLanguage(LanguageSelector.EMOJI);
        addView(C.sentenceView);
//        addView(scrollView);
//        addView(C.emojiKeyboard);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Log.i("ChineseInputView", "onMeasure");
        scrollView.measure(MeasureSpec.makeMeasureSpec(Size.WCandidateView, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(Size.HCandidateView, MeasureSpec.EXACTLY));
        setMeasuredDimension(Size.WInputView, Size.HInputView);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        l = 0;
        t = 0;
        C.sentenceView.layout(l, t, l + Size.WSentenceView, t + Size.HSentenceView);
        l += Size.WSentenceView;
        switch (LanguageSelector.inputLanguage) {
            case LanguageSelector.CHINESE:
                scrollView.layout(l, t, l + Size.WCandidateView, t + Size.HCandidateView);
                C.emojiKeyboard.layout(l, t + Size.HCandidateVisible, l + Size.WKeyboard, t + Size.HInputView);
                break;
            case LanguageSelector.EMOJI:
                C.emoji.layout(l, t, l + Size.WCandidateView, t + Size.HCandidateView);
                break;
        }
    }
}
