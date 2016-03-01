package co.watermelonime.InputView.Chinese.Candidate;

import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Core.Engine;

public class OnClickCandidate implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        String zi = ((CandidateButton) v).text;
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
//        Runnables.displayCandidate.run(); // TODO: 2016/3/1 display candidate
    }
}
