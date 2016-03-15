package co.watermelonime.InputView.Emoji;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Size;
import co.watermelonime.InputView.Chinese.Sentence.LanguageSelector;

public class Emoji extends ViewGroup {
    public static ScrollView scrollView;
    public static EmojiKeyboard emojiKeyboard;              //let emoji keyboard can be scrolled
//    public static CandidateEmojiView candidateEmojiView;
    public Emoji() {
        super(C.mainService);
        setBackgroundColor(Colour.CHARACTER);

//        scrollView = new ScrollView(C.mainService);
//        scrollView.addView(emojiKeyboard);

//        Expressions.buildKeys();
        for (View i : Expressions.keys)
            addView(i);

//        addView(C.sentenceView);
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
        //C.sentenceView.layout(l, t, l + Size.WSentenceView, t + Size.HSentenceView);
        for(int i=0; i<8; i++)
            Expressions.keys[i].layout(l, t, l + Size.WSentenceView, t + Size.HSentenceView);
    }
}
