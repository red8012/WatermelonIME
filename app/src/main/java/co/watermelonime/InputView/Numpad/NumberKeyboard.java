package co.watermelonime.InputView.Numpad;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Size;
import co.watermelonime.R;

public class NumberKeyboard extends ViewGroup {
    public static final String number[] = {
            "±", ":", " ", " ", "小", "大",
            "$", "¥", "€", "<", ">", "#",
            "7", "8", "9", "(", ")", "%",
            "4", "5", "6", "+", "×", "÷",
            "1", "2", "3", "-", "*", "/",
            "0", ".", ",", "="};
    static NumKey[] keys = new NumKey[36];

    public NumberKeyboard() {
        super(C.mainService);
        setBackgroundColor(Colour.NORMAL);
        for (int i = 0; i < number.length; i++) {
            keys[i] = new NumKey(number[i]);
            addView(keys[i]);
        }

        NumKey enter = new NumKey(R.drawable.enter);
        enter.setBackgroundColor(Colour.FUNCTION);
        enter.setOnTouchListener(((v, event) -> {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    C.mainService.sendDownUpKeyEvents(KeyEvent.KEYCODE_ENTER);
                    v.setBackgroundColor(Colour.CANDIDATE_SELECTED);
                    return true;
                case MotionEvent.ACTION_UP:
                    v.setBackgroundColor(Colour.FUNCTION);
            }
            return false;
        }));
        addView(enter);

        NumKey backspace = new NumKey(R.drawable.backspace);
        backspace.setBackgroundColor(Colour.FUNCTION);
        backspace.setOnTouchListener(new OnTouchListener() {
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
        });
        addView(backspace);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        l = 0;
        t = 0;
        r = l + Size.WKey;
        final int end = getChildCount();
        for (int i = 0; i < end; ++i) {
            NumKey k = (NumKey) getChildAt(i);
            if (k != null)
                k.layout(r - Size.WKey, t, r, t + Size.HNumKey);

            r += Size.WKey;
            if (r > Size.WKeyboard) {
                r = Size.WKey;
                t += Size.HNumKey;
            }
        }
    }
}
