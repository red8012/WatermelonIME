package co.watermelonime.InputView.Chinese;

import android.util.Log;
import android.view.ViewGroup;

import co.watermelonime.C;

public class ChineseInputView extends ViewGroup {

    public ChineseInputView() {
        super(C.mainService);
        setLayerType(LAYER_TYPE_HARDWARE, null);
        addView(C.candidateView);
        addView(C.chineseKeyboard);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("ChineseInputView", "onMeasure");
        setMeasuredDimension(C.u * 60, C.u * 49);
//        C.candidateView.measure(0, 0);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i("ChineseInputView", "onLayout");
        l = 0;
        t = 0;
        C.candidateView.layout(l, t, l + C.screenWidth, t + C.u * 49);
        C.chineseKeyboard.layout(l, t + C.u * 13, l + C.screenWidth, t + C.u * 49);
    }
}
