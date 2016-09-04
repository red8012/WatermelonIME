package co.watermelonime.InputView.Chinese.Keyboard;

import android.util.Log;
import android.view.ViewGroup;

import co.watermelonime.C;
import co.watermelonime.Common.Size;
import co.watermelonime.Common.Timer;

public class ChineseKeyboard extends ViewGroup {
    public static ChineseKey[] currentKeys;
    public static boolean visible = true;

    public ChineseKeyboard() {
        super(C.mainService);
        setMeasuredDimension(Size.WKeyboard, Size.HKeyboard);
        setKeys(Consonants.keys);
    }

    public void setKeys(ChineseKey[] k) {
        Timer.t(5);
        if (C.candidateView != null)
            C.candidateView.clearCandidates();
        removeAllViews();
        currentKeys = k;
        for (ChineseKey i : k)
            if (i != null)
                addView(i);
            else Log.e("setKeys", "null");
        layout(0, 0, 0, 0);
        Timer.t(5, "set current keys + request layout");
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i("ChineseKeyboard", "onLayout");
        l = 0;
        t = 0;
        r = l + Size.WKey;
        final int end = getChildCount();
        for (int i = 0; i < end; ++i) {
            ChineseKey k = (ChineseKey) getChildAt(i);
            if (k != null)
                k.layout(r - Size.WKey, t, r, t + Size.HKey);

            r += Size.WKey;
            if (r > Size.WKeyboard) {
                r = Size.WKey;
                t += Size.HKey;
            }
        }
    }

    public void hide() {
        if (!visible) return;
        visible = false;
        if (C.isLandscape) C.landscapeLanguageSelectorBar.hide();
        animate().translationY(Size.HKeyboard).setDuration(250).setInterpolator(C.decelerate);
    }

    public void show() {
        if (visible) return;
        visible = true;
        if (C.isLandscape) C.landscapeLanguageSelectorBar.show();
        animate().translationY(0f).setDuration(250).setInterpolator(C.decelerate);
    }
}
