package co.watermelonime.Common;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;

public class Listeners {
    public final static View.OnTouchListener enter = (v, event) -> {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                C.mainService.sendDownUpKeyEvents(KeyEvent.KEYCODE_ENTER);
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
                    v.setBackgroundColor(Colour.CANDIDATE_SELECTED);
                    return true;
                case MotionEvent.ACTION_UP:
                    v.setBackgroundColor(Colour.FUNCTION);
                    return true;
                case MotionEvent.ACTION_MOVE:
                    if (x < lastX - Size.HCandidateRow || x > lastX + Size.HCandidateRow) {
                        lastX = x;
                        C.mainService.sendDownUpKeyEvents(KeyEvent.KEYCODE_DEL);
                        return true;
                    }
            }
            return false;
        }
    };
}
