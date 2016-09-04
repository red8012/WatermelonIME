package co.watermelonime.InputView.Chinese.Sentence;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Size;

public class LandscapeLanguageSelectorBar extends ViewGroup {
    public static boolean visible = true;
    static View[] children;

    public LandscapeLanguageSelectorBar() {
        super(C.mainService);
        visible = true;
        children = LanguageSelector.keys;
        for (View i : children) addView(i);
        setBackgroundColor(Colour.SENTENCE);
        setMeasuredDimension(300, 300);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("LLSB", "onMeasure");
        setMeasuredDimension(Size.WSentenceView, Size.HSentenceView);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        l = 0;
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View v = getChildAt(i);
            int w = v.getMeasuredWidth();
            v.layout(l, 0, l + w, Size.HKey);
            l += w;
        }
    }

    public void hide() {
        if (!visible) return;
        visible = false;
        animate().translationY(Size.HKey).setDuration(250).setInterpolator(C.decelerate);
    }

    public void show() {
        if (visible) return;
        visible = true;
        animate().translationY(0f).setDuration(250).setInterpolator(C.decelerate);
    }
}
