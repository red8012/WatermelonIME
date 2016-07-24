package co.watermelonime.InputView.English;

import android.view.MotionEvent;
import android.view.View;

import java.util.concurrent.Future;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Size;

public class OnTouchEnglishKey implements View.OnTouchListener {
    Future<Integer> future;
    int lastX, lastY;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        EnglishKey key = (EnglishKey) v;
        final int x = (int) event.getRawX(), y = (int) event.getRawY();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                key.setBackgroundColor(Colour.CANDIDATE_SELECTED);
                if (key.umlaut != null && event.getPointerCount() == 1)
                    future = C.threadPool.submit(() -> {
                        try {
                            Thread.sleep(400);
                            C.englishKeyboard.post(() -> {
                                key.displayUmlaut = true;
                                try {
                                    C.englishKeyboard.addView(key.umlaut);
                                } catch (Exception e) {
                                    C.englishKeyboard.removeView(key.umlaut);
                                    C.englishKeyboard.addView(key.umlaut);
                                }
                                C.englishKeyboard.layout(0, 0, 0, 0);
                                key.umlaut.reset();
                                lastX = x;
                                lastY = y;
                            });
                        } catch (Exception e) {
//                            e.printStackTrace();
                        }
                        return 0;
                    });
                return true;

            case MotionEvent.ACTION_MOVE:
                if (key.umlaut == null) return false;

                if (x > lastX + Size.WEnglishKey) {
                    if (key.umlaut.move(1, 0))
                        lastX = x;
                } else if (x < lastX - Size.WEnglishKey) {
                    if (key.umlaut.move(-1, 0))
                        lastX = x;
                }
                if (y > lastY + Size.HEnglishKey) {
                    if (key.umlaut.move(0, 1))
                        lastY = y;
                } else if (y < lastY - Size.HEnglishKey) {
                    if (key.umlaut.move(0, -1))
                        lastY = y;
                }
                return true;

            case MotionEvent.ACTION_UP:
                if (future != null) {
                    future.cancel(true);
                    future = null;
                }

                if (key.displayUmlaut) {
                    key.displayUmlaut = false;
                    CandidateBar.commit(key.umlaut.currentText);
                    try {
                        C.englishKeyboard.removeView(key.umlaut);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    C.englishKeyboard.invalidate();
                } else
                    CandidateBar.commit(key.text);
                EnglishKeyboard.committed = true;

                if (EnglishKeyboard.mode == EnglishKeyboard.UPPER &&
                        !EnglishKeyboard.isShiftPressed)
                    C.englishKeyboard.changeMode(EnglishKeyboard.LOWER);

                if (EnglishKeyboard.mode == EnglishKeyboard.PUNCTUATION &&
                        !EnglishKeyboard.isPunctuationPressed)
                    C.englishKeyboard.changeMode(EnglishKeyboard.modeBeforePunctuation);
                key.setBackgroundColor(Colour.NORMAL);
                return true;
        }
        return false;
    }


}
