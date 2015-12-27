package co.watermelonime.InputView.Chinese.Candidate;

import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;

public class OnTouchCandidate implements View.OnTouchListener {
    static boolean hidden = false;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                v.setBackgroundColor(C.COLOR_CANDIDATE_SELECTED);
                if (hidden) C.chineseKeyboard.show();
                else
                    C.chineseKeyboard.hide();
                hidden = !hidden;
                break;
            case MotionEvent.ACTION_UP:
                v.setBackgroundColor(C.COLOR_CANDIDATE);
        }
        return true;
    }
}
