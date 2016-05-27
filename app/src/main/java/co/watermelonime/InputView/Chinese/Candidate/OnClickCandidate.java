package co.watermelonime.InputView.Chinese.Candidate;

import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Core.Controller;
import co.watermelonime.Core.Engine;
import co.watermelonime.InputView.Chinese.Keyboard.ChineseKeyboard;
import co.watermelonime.InputView.Chinese.Keyboard.Consonants;

public class OnClickCandidate implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        if (ChineseKeyboard.currentKeys != Consonants.keys) return; // TODO: 2016/3/4 can remove if hint is enabled
        CharSequence zi = ((CandidateButton) v).text;
        if (((CandidateButton) v).type == CandidateButton.BOTTOM) {
            String sentence = Engine.getSentence();
            sentence = sentence.substring(0, sentence.length() - zi.length()) + zi;
            C.commit(sentence);
            Engine.clear();
        } else {
            try {
                Engine.pop(zi.length());
            } catch (Exception e) {
                e.printStackTrace();
                Engine.clear();  // TODO: 2016/3/1 should clear everything
            }
            C.commit(zi);

        }
        C.sentenceView.display();
        Controller.displayCandidates();
    }
}
