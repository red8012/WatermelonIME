package co.watermelonime.InputView.Chinese.Candidate;

import android.view.View;

import java.util.ArrayList;

import co.watermelonime.C;
import co.watermelonime.Common.Size;
import co.watermelonime.Core.Runnables;
import co.watermelonime.InputView.Chinese.ChineseInputView;

public class DictController {
    public static ArrayList<ArrayList<String>> currentDict;

    public static void showDictionary(ArrayList<String> phrase, ArrayList<ArrayList<String>> character) {
        currentDict = character;
        for (int i = 0; i < character.size(); i++) {
            ArrayList<String> line = character.get(i);
            C.candidateView.addView(DictTitle.get(line.get(0), i));

            int len = line.size();
            if (len > 8) len = 8;
            for (int j = 1; j < len; j++) {
                String text = line.get(j);
                DictButton d = DictButton.get();
                int padding = Size.WDictButton - CandidateButton.calculateMinWidth(text);
                d.setText(text, padding, j != len - 1, 0);
                C.candidateView.addView(d);
            }
        }
        CandidateView.height = character.size() * Size.HCandidateRow;
        if (CandidateView.height < Size.HCandidateView)
            CandidateView.height = Size.HCandidateView;
    }

    public static void showLayer2Dictionary(int index) {
        ArrayList<String> characters = currentDict.get(index);
        int len = characters.size();
        int counter = 0;
        for (int i = 1; i < len; i++) {
            String text = characters.get(i);
            if (text.contains("@")) continue;
            DictButton d = DictButton.get();
            int padding = Size.W2ndLayerDictButton - CandidateButton.calculateMinWidth(text);
            counter++;
            d.setText(text, padding, counter % 8 != 0, 0);
            C.candidateView.addView(d);
        }
        CandidateView.height = (counter / 8 + 1) * Size.HCandidateRow;
        if (CandidateView.height < Size.HCandidateView)
            CandidateView.height = Size.HCandidateView;
    }

    public static void clearCandidate() {
        int len = C.candidateView.getChildCount();
        for (int i = 0; i < len; i++) {
            View child = C.candidateView.getChildAt(i);
            if (child.getClass() == DictButton.class)
                ((DictButton) child).release();
            else if (child.getClass() == DictTitle.class)
                ((DictTitle) child).release();
            else CandidateView.releaseCandidateButton((CandidateButton) child);
        }
        C.candidateView.removeAllViews();
    }

    public static void openDict() {
        CandidateView.isDictionaryMode = true;
        C.chineseInputView.invalidate();
    }

    public static void closeDict() {
        CandidateView.isDictionaryMode = false;
        clearCandidate();
        Runnables.displayCandidate.run();
        ChineseInputView.scrollView.removeAllViews(); // why do I have to do this?
        ChineseInputView.scrollView.addView(C.candidateView);
        C.chineseInputView.invalidate();
    }
}
