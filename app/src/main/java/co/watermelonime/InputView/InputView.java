package co.watermelonime.InputView;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ScrollView;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Size;
import co.watermelonime.InputView.Chinese.Sentence.LanguageSelector;

public class InputView extends ViewGroup {
    public static ScrollView scrollView, scrollViewRightForLandscape;
//    public static View vx;

    public InputView() {
        super(C.mainService);
        scrollView = new ScrollView(C.mainService);
        scrollView.addView(C.candidateView);
        if (C.isLandscape) {
            scrollViewRightForLandscape = new ScrollView(C.mainService);
            scrollViewRightForLandscape.addView(C.landscapeCandidateViewRight);
        }
        addChildren(LanguageSelector.CHINESE);
        setBackgroundColor(Colour.CANDIDATE);
    }

    public void addChildren(int inputLanguage) {
        removeAllViews();
        if (inputLanguage != LanguageSelector.ENGLISH && !C.isLandscape)
            addView(C.sentenceView);
        switch (inputLanguage) {
            case LanguageSelector.CHINESE:
                addView(InputView.scrollView);
                if (C.isLandscape) {
                    addView(C.sentenceView);
                    addView(scrollViewRightForLandscape);
                    addView(C.landscapeLanguageSelectorBar);
                }
                addView(C.chineseKeyboard);
                break;
            case LanguageSelector.ENGLISH:
                addView(C.englishKeyboard); // todo: possibly null
                break;
            case LanguageSelector.NUMBER:
                addView(C.numberKeyboard);
                if (C.isLandscape) addView(C.landscapeLanguageSelectorBar);
                break;
            case LanguageSelector.EMOJI:
                addView(C.emojiKeyboard);
                if (C.isLandscape) addView(C.landscapeLanguageSelectorBar);
                break;
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Log.i("ChineseInputView", "onMeasure");
        int height = C.isLandscape ? (Size.HInputView - Size.HSentenceView) : Size.HCandidateView;
        scrollView.measure(MeasureSpec.makeMeasureSpec(Size.WCandidateView, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));

        if (C.isLandscape)
            scrollViewRightForLandscape.measure(MeasureSpec.makeMeasureSpec(Size.WCandidateView, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(Size.HCandidateView, MeasureSpec.EXACTLY));
        setMeasuredDimension(Size.WInputView, Size.HInputView);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
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
                    C.sentenceView.layout(l, t, l + Size.WChineseLandscapeLeft, t + Size.HKey);
                    t += Size.HKey;
                    scrollView.layout(l, t, l + Size.WChineseLandscapeLeft, t + Size.HInputView - Size.HSentenceView);
                    t += Size.HKey * 2;
                    C.landscapeLanguageSelectorBar.layout(l, t, l + Size.WChineseLandscapeLeft, t + Size.HKey);
                    l += Size.WChineseLandscapeLeft;
                    t = 0;
                    scrollViewRightForLandscape.layout(l, 0, l + Size.WKeyboard, t + Size.HInputView);
                    C.chineseKeyboard.layout(l, t, l + Size.WKeyboard, t + Size.HInputView);
                    break;
                case LanguageSelector.ENGLISH:
                    C.englishKeyboard.layout(l, t, Size.WInputView, Size.HInputView);
                    break;
                case LanguageSelector.NUMBER:
                    C.numberKeyboard.layout(l, t, l + Size.WInputView, t + Size.HInputView);
                    break;
                case LanguageSelector.EMOJI:
                    C.emojiKeyboard.layout(l, t, l + Size.WInputView, t + Size.HInputView);
                    break;
            }
        }
    }
}
