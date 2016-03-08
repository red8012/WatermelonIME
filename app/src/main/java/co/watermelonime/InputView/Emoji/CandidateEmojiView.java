package co.watermelonime.InputView.Emoji;

import android.view.View;
import android.view.ViewGroup;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Size;
import co.watermelonime.InputView.Chinese.Candidate.DictTitle;

/**
 * Created by din on 2016/3/8.
 */
public class CandidateEmojiView extends ViewGroup{
    public CandidateEmojiView() {
        super(C.mainService);
        setBackgroundColor(Colour.CANDIDATE);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        final int end = getChildCount();    //know the number of views
        l = t = 0;

        for (int i = 0; i < end; ++i) {
            View v = getChildAt(i);
            int w = v.getMeasuredWidth(), h = v.getMeasuredHeight();

            if (v.getClass() == DictTitle.class) {
                if (l > 0) t += h;
                l = 0;
            }
            if (l + w > Size.WKeyboard + Size.u) {
                l = 0;
                t += v.getMeasuredHeight();
            }
            if (w > Size.WKeyboard) {
                v.layout(l, t, l + w, t + h);
                l = 0;
                t += h;
            } else {
                v.layout(l, t, l + w, t + h);
                l += w;
            }
        }
    }
}
