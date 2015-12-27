package co.watermelonime.InputView.Chinese;

import android.util.Log;
import android.view.ViewGroup;

import co.watermelonime.C;
import co.watermelonime.Common.Size;

public class ChineseInputView extends ViewGroup {

    public ChineseInputView() {
        super(C.mainService);
//        setLayerType(LAYER_TYPE_HARDWARE, null);
        addView(C.sentenceView);
        addView(C.candidateView);
        addView(C.chineseKeyboard);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Log.i("ChineseInputView", "onMeasure");
        setMeasuredDimension(Size.WInputView, Size.HInputView);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!changed) return;
        Log.i("ChineseInputView", "onLayout");
        l = 0;
        t = 0;
        C.sentenceView.layout(l, t, l + Size.WSentenceView, t + Size.HSentenceView);
        l += Size.WSentenceView;
        C.candidateView.layout(l, t, l + Size.WCandidateView, t + Size.HCandidateView);
        C.chineseKeyboard.layout(l, t + Size.HCandidateVisible, l + Size.WKeyboard, t + Size.HInputView);
    }
}
