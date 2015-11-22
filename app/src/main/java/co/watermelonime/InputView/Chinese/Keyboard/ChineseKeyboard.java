package co.watermelonime.InputView.Chinese.Keyboard;

import android.util.Log;
import android.view.ViewGroup;

import co.watermelonime.C;
import co.watermelonime.ChineseKey;
import co.watermelonime.Timer;

public class ChineseKeyboard extends ViewGroup {

    public ChineseKeyboard() {
        super(C.mainService);
        setBackgroundColor(C.COLOR_DISABLED);
        setCurrentKeys(Consonants.keys);
    }

    public void setCurrentKeys(ChineseKey[] k) {
        Timer.t(5);
        removeAllViews();
        for (ChineseKey i : k)
            if (i != null)
                addView(i);
            else Log.e("setCurrentKeys", "null");
        requestLayout();
        Timer.t(5, "set current keys + request layout");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(C.u * 60, C.u * 36);
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.w("ChineseKeyboard", "onLayout");
        r = l + C.chineseKeyWidth;
        final int end = getChildCount();
        for (int i = 0; i < end; ++i) {
            ChineseKey k = (ChineseKey) getChildAt(i);
            if (k != null)
                k.layout(r - C.chineseKeyWidth, t, r, t + C.chineseKeyWidth);

            r += C.chineseKeyWidth;
            if (r > C.keyboardWidth) {
                r = C.chineseKeyWidth;
                t += C.chineseKeyHeight;
            }
        }
    }
}
