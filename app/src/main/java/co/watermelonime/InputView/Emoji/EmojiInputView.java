package co.watermelonime.InputView.Emoji;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ScrollView;

import co.watermelonime.C;
import co.watermelonime.Common.Size;
import co.watermelonime.InputView.Chinese.Candidate.DictButton;
import co.watermelonime.InputView.Chinese.Candidate.DictTitle;
import co.watermelonime.InputView.Chinese.Sentence.LanguageSelector;

/**
 * Created by din on 2016/3/7.
 */
public class EmojiInputView extends ViewGroup {


    //    public static int mode = LanguageSelector.CHINESE;
    public static ScrollView scrollView;

    public EmojiInputView() {
        super(C.mainService);

        scrollView = new ScrollView(C.mainService);
        scrollView.addView(C.candidateView);
    //    LanguageSelector.setInputLanguage(LanguageSelector.EMOJI);
        addView(C.sentenceView);
        addView(scrollView);
        addView(C.emojiKeyboard);
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
        Log.i("EmojiInputView", "onLayout");
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
