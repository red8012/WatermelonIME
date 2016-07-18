package co.watermelonime.InputView.English;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Size;

public class Listeners {
    public final static View.OnTouchListener enter = (v, event) -> {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                C.mainService.sendDownUpKeyEvents(KeyEvent.KEYCODE_ENTER);
                CandidateBar.learn();
                v.setBackgroundColor(Colour.CANDIDATE_SELECTED);
                return true;
            case MotionEvent.ACTION_UP:
                v.setBackgroundColor(Colour.FUNCTION);
        }
        return false;
    };

    public final static View.OnTouchListener backspace = new View.OnTouchListener() {
        int lastX;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final int x = (int) event.getRawX();
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    lastX = x;
                    C.mainService.sendDownUpKeyEvents(KeyEvent.KEYCODE_DEL);
                    CandidateBar.delete();
                    v.setBackgroundColor(Colour.CANDIDATE_SELECTED);
                    return true;
                case MotionEvent.ACTION_UP:
                    v.setBackgroundColor(Colour.FUNCTION);
                    return true;
                case MotionEvent.ACTION_MOVE:
                    if (x < lastX - Size.HCandidateRow || x > lastX + Size.HCandidateRow) {
                        lastX = x;
                        C.mainService.sendDownUpKeyEvents(KeyEvent.KEYCODE_DEL);
                        CandidateBar.delete();
                        return true;
                    }
            }
            return false;
        }
    };
}
