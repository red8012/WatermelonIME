package co.watermelonime.InputView.Chinese;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ScrollView;

import co.watermelonime.C;
import co.watermelonime.Common.Size;
import co.watermelonime.InputView.Chinese.Candidate.DictButton;
import co.watermelonime.InputView.Chinese.Candidate.DictTitle;
import co.watermelonime.InputView.Chinese.Sentence.LanguageSelector;

public class ChineseInputView extends ViewGroup {


    //    public static int mode = LanguageSelector.CHINESE;
    public static ScrollView scrollView;

    public ChineseInputView() {
        super(C.mainService);

        scrollView = new ScrollView(C.mainService);
        scrollView.addView(C.candidateView);
//        LanguageSelector.setInputLanguage(LanguageSelector.CHINESE);
        addView(C.sentenceView);
        addView(scrollView);
        addView(C.chineseKeyboard);
    }

    public static void initializeHiddenPartsAsync() {
        C.threadPool.submit(() -> {
                    DictTitle.init();
                    DictButton.init();
                }
        );
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
    }
}
