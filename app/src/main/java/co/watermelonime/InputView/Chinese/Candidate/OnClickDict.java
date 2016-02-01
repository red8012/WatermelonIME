package co.watermelonime.InputView.Chinese.Candidate;

import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Core.Engine;
import co.watermelonime.Core.Runnables;
import co.watermelonime.InputView.Chinese.Sentence.SentenceButton;
import co.watermelonime.InputView.Chinese.Sentence.SentenceView;

public class OnClickDict implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        DictButton dictButton = (DictButton) v;
        String text = dictButton.text;
        int index = SentenceButton.selectedIndex;
        Engine.setZiLock(index, text);
        C.sentenceView.display();
//        C.candidateView.closeDictionary();
        DictController.closeDict();
        SentenceView.setSelected(index);
        C.chineseKeyboard.show();

        DictController.clearCandidate();
        Runnables.displayCandidate.run();


    }
}
