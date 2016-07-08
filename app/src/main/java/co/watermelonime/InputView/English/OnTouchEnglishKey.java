package co.watermelonime.InputView.English;

import android.view.MotionEvent;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.util.concurrent.Future;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;

public class OnTouchEnglishKey implements View.OnTouchListener {
    Future<Integer> future;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        EnglishKey key = (EnglishKey) v;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                C.commit(key.text);
                EnglishKeyboard.committed = true;
                key.setBackgroundColor(Colour.CANDIDATE_SELECTED);

                future = C.threadPool.submit(() -> {
                    try {
                        Thread.sleep(400);
                        C.englishKeyboard.post(() -> {
                            key.displayUmlaut = true;
                            C.englishKeyboard.addView(key.umlaut);
                            C.englishKeyboard.layout(0, 0, 0, 0);
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Logger.d("show umlaut");
                    return 0;
                });

                return true;
            case MotionEvent.ACTION_UP:
                if (EnglishKeyboard.mode == EnglishKeyboard.UPPER &&
                        !EnglishKeyboard.isShiftPressed)
                    C.englishKeyboard.changeMode(EnglishKeyboard.LOWER);

                if (future != null) {
                    if (future.isDone()) {
                        Logger.d("dismiss umlaut");
                        key.displayUmlaut = false;
                        C.englishKeyboard.removeView(key.umlaut);
                        C.englishKeyboard.invalidate();
                    }
                    future.cancel(true);
                    future = null;
                }

                if (EnglishKeyboard.mode == EnglishKeyboard.PUNCTUATION &&
                        !EnglishKeyboard.isPunctuationPressed)
                    C.englishKeyboard.changeMode(EnglishKeyboard.modeBeforePunctuation);
                key.setBackgroundColor(Colour.NORMAL);
                return true;
        }
        return false;
    }
}
