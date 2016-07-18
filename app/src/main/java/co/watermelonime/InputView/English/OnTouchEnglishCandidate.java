package co.watermelonime.InputView.English;

import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Core.EnglishPredictor;

public class OnTouchEnglishCandidate implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getActionMasked() != MotionEvent.ACTION_DOWN) return true;
        CandidateButton button = ((CandidateButton) v);
        int start = EnglishPredictor.completionBuffer.length();
        CharSequence text = button.layout.getText();
        C.commit(text.subSequence(start, text.length()));
        C.commit(" ");
        CandidateBar.reset();
        return true;
    }
}
