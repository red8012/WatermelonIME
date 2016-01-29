package co.watermelonime.InputView.Chinese.Sentence;

import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Core.Engine;

public class OnTouchSentenceButton implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                final int index = ((SentenceButton) v).index;
                if (Engine.getLength() <= index) return true;
                SentenceView.setSelected(index);
                if (SentenceButton.selectedIndex < 0) {
                    C.chineseKeyboard.show();
                } else {
                    C.chineseKeyboard.hide();
                }
                break;
        }
        return true;
    }
}
