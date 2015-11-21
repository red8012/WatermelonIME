package co.watermelonime.InputView.Chinese.Keyboard;

import android.graphics.Color;
import android.util.Log;
import android.view.ViewGroup;

import co.watermelonime.C;
import co.watermelonime.ChineseKey;

public class ChineseKeyboard extends ViewGroup {
    public static ChineseKey[] keys = new ChineseKey[24];

    public ChineseKeyboard() {
        super(C.mainService);
        setBackgroundColor(Color.BLACK);
        for (ChineseKey k : Consonants.keys)
            if (k != null)
                addView(k);
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
        if (!changed) return;
        Log.w("ChineseKeyboard", "onLayout");
        r = l + C.chineseKeyWidth;
        for (ChineseKey k : keys) {
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
