package co.watermelonime.InputView.Chinese.Candidate;

import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;

public class OnTouchCandidate implements View.OnTouchListener {
    static boolean hidden = false;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                v.setBackgroundColor(Colour.CANDIDATE_SELECTED);
                if (hidden) C.chineseKeyboard.show();
                else
                    C.chineseKeyboard.hide();
                hidden = !hidden;
                break;
            case MotionEvent.ACTION_UP:
                v.setBackgroundColor(Colour.CANDIDATE);
        }
        return true;
    }
}
