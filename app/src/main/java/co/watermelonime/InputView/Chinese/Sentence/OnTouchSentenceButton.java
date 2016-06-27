package co.watermelonime.InputView.Chinese.Sentence;

import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Core.Engine;
import co.watermelonime.InputView.Chinese.Candidate.CandidateView;
import co.watermelonime.InputView.Chinese.Candidate.DictController;

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
                    DictController.closeDict();
                } else {
                    C.chineseKeyboard.hide();
                    CandidateView.clearCandidates();
                    Engine.queryDict(index);
//                    DictController.showDictionary();
                    DictController.fillInWordsInDict(index);
                    DictController.openDict();
                }
                break;
        }
        return true;
    }
}
