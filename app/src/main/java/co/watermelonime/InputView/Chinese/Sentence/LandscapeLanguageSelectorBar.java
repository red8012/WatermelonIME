package co.watermelonime.InputView.Chinese.Sentence;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Size;

public class LandscapeLanguageSelectorBar extends ViewGroup {
    static View[] children;
    public LandscapeLanguageSelectorBar() {
        super(C.mainService);
        children = LanguageSelector.keys;
        for (View i : children) addView(i);
        setBackgroundColor(Colour.SENTENCE);
        setMeasuredDimension(300,300);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("LLSB", "onMeasure");
        setMeasuredDimension(Size.WSentenceView, Size.HSentenceView);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int end = getChildCount();
        l = 0;
        for (int i = getChildCount()-1; i >= 0; i--) {
            View v = getChildAt(i);
            int w = v.getMeasuredWidth();
            v.layout(l, 0, l + w, Size.HKey);
            l += w;
        }
    }
}
