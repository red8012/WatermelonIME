package co.watermelonime.InputView;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ScrollView;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Size;
import co.watermelonime.InputView.Chinese.Sentence.LanguageSelector;

public class InputView extends ViewGroup {
    public static ScrollView scrollView;
//    public static View vx;

    public InputView() {
        super(C.mainService);
        scrollView = new ScrollView(C.mainService);
        scrollView.addView(C.candidateView);
        addChildren(LanguageSelector.CHINESE);
        setBackgroundColor(Colour.CANDIDATE);
    }

    public void addChildren(int inputLanguage) {
        removeAllViews();
        if (inputLanguage != LanguageSelector.ENGLISH)
            addView(C.sentenceView);
        switch (inputLanguage) {
            case LanguageSelector.CHINESE:
                addView(InputView.scrollView);
                addView(C.chineseKeyboard);
                if (C.isLandscape) addView(C.landscapeLanguageSelectorBar);
                break;
            case LanguageSelector.ENGLISH:
                addView(C.englishKeyboard); // todo: possibly null
                break;
            case LanguageSelector.NUMBER:
                addView(C.numberKeyboard);
                break;
            case LanguageSelector.EMOJI:
                addView(C.emojiKeyboard);
                break;
        }
        invalidate();
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
//        if (!changed) return;
        Log.i("ChineseInputView", "onLayout");
        l = 0;
        t = 0;
        if (!C.isLandscape) {
            if (LanguageSelector.inputLanguage != LanguageSelector.ENGLISH) {
                C.sentenceView.layout(l, t, l + Size.WSentenceView, t + Size.HSentenceView);
                l += Size.WSentenceView;
            }
            switch (LanguageSelector.inputLanguage) {
                case LanguageSelector.CHINESE:
                    scrollView.layout(l, t, l + Size.WCandidateView, t + Size.HCandidateView);
                    C.chineseKeyboard.layout(l, t + Size.HCandidateVisible, l + Size.WKeyboard, t + Size.HInputView);
                    break;
                case LanguageSelector.NUMBER:
                    C.numberKeyboard.layout(l, t, l + Size.WCandidateView, t + Size.HCandidateView);
                    break;
                case LanguageSelector.ENGLISH:
                    C.englishKeyboard.layout(l, t, Size.WInputView, Size.HInputView);
                    break;
                case LanguageSelector.EMOJI:
                    C.emojiKeyboard.layout(l, t, l + Size.WCandidateView, t + Size.HCandidateView);
                    break;
            }
        } else {
            switch (LanguageSelector.inputLanguage) {
                case LanguageSelector.CHINESE:
//                    vx.layout(0,0,1000,1000);
                    C.sentenceView.layout(l, t, l + Size.WChineseLandscapeLeft, t + Size.HKey);
                    t += Size.HKey;
                    scrollView.layout(l, t, l + Size.WChineseLandscapeLeft, t + Size.HCandidateView);
                    t += Size.HKey * 2;
//                    Logger.d("%d %d %d %d", l, t, l + Size.WChineseLandscapeLeft, t + Size.HKey);
                    C.landscapeLanguageSelectorBar.layout(l, t, l + Size.WChineseLandscapeLeft, t + Size.HKey);

                    l += Size.WChineseLandscapeLeft;
                    t = 0;
                    C.chineseKeyboard.layout(l, t, l + Size.WKeyboard, t + Size.HInputView);
                    break;
                case LanguageSelector.ENGLISH:
                    C.englishKeyboard.layout(l, t, Size.WInputView, Size.HInputView);
                    break;
            }
        }
    }
}
