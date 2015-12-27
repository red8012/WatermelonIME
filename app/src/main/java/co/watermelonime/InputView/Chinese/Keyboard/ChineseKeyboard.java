package co.watermelonime.InputView.Chinese.Keyboard;

import android.util.Log;
import android.view.ViewGroup;

import co.watermelonime.C;
import co.watermelonime.Common.Timer;

public class ChineseKeyboard extends ViewGroup {
    static int width, height;

    public ChineseKeyboard() {
        super(C.mainService);
        width = C.u * 60;
        height = C.u * 36;
        setMeasuredDimension(width, height);
        setCurrentKeys(Consonants.keys);
    }

    public void setCurrentKeys(ChineseKey[] k) {
        Timer.t(5);
        Log.i("ChineseKeyboad", "set current keys");
        removeAllViews();
        for (ChineseKey i : k)
            if (i != null)
                addView(i);
            else Log.e("setCurrentKeys", "null");
//        requestLayout(); // Useless???
//        invalidate(); // Useless
//        Why do I need this???
        onLayout(true, 0, 0, 0, 0);
        Timer.t(5, "set current keys + request layout");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width, height);
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
        r = l + C.chineseKeyWidth;
        final int end = getChildCount();
        for (int i = 0; i < end; ++i) {
            ChineseKey k = (ChineseKey) getChildAt(i);
            if (k != null)
                k.layout(r - C.chineseKeyWidth, t, r, t + C.chineseKeyHeight);

            r += C.chineseKeyWidth;
            if (r > C.keyboardWidth) {
                r = C.chineseKeyWidth;
                t += C.chineseKeyHeight;
            }
        }
    }

    public void hide() {
//        setLayerType(LAYER_TYPE_HARDWARE, null);
        animate().translationY(height).setDuration(300).setInterpolator(C.decelerate);
//                .setListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                setLayerType(LAYER_TYPE_NONE, null);
//            }
//        });
    }

    public void show() {
//        setLayerType(LAYER_TYPE_HARDWARE, null);
        animate().translationY(0f).setDuration(300).setInterpolator(C.decelerate);
//                .setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        setLayerType(LAYER_TYPE_NONE, null);
//                    }
//                });
    }
}
