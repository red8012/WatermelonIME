package co.watermelonime.InputView.Emoji;

import android.util.Log;
import android.view.ViewGroup;

import co.watermelonime.C;
import co.watermelonime.Common.Size;
import co.watermelonime.Common.Timer;
import co.watermelonime.InputView.Emoji.EmojiKey;
import co.watermelonime.InputView.Emoji.Expressions;

/**
 * Created by din on 2016/3/7.
 */
public class EmojiKeyboard extends ViewGroup {

    public static EmojiKey[] currentKeys;
    public static boolean visible = true;

    public EmojiKeyboard() {
        super(C.mainService);
        setMeasuredDimension(Size.WKeyboard, Size.HKeyboard);
        setKeys(Expressions.keys);
    }

    public void setKeys(EmojiKey[] k) {
        //Timer.t(5);
        removeAllViews();
        currentKeys = k;
        for (EmojiKey i : k)
            if (i != null)
                addView(i);
            else Log.e("setKeys", "null");
        layout(0, 0, 0, 0);
        //Timer.t(5, "set current keys + request layout");
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        Log.i("ChineseKeyboard", "onLayout");
        l = 0;
        t = 0;
        r = l + Size.WKey;
        final int end = getChildCount();
        for (int i = 0; i < end; ++i) {
            EmojiKey k = (EmojiKey) getChildAt(i);
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
        animate().translationY(Size.HKeyboard).setDuration(250).setInterpolator(C.decelerate);
    }

    public void show() {
        if (visible) return;
        visible = true;
        animate().translationY(0f).setDuration(250).setInterpolator(C.decelerate);
    }

}
