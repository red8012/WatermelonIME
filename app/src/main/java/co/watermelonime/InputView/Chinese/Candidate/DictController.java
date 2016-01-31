package co.watermelonime.InputView.Chinese.Candidate;

import java.util.ArrayList;

import co.watermelonime.C;

public class DictController {
    public static void showDictionary(ArrayList<String> phrase, ArrayList<ArrayList<String>> character) {
        for (ArrayList<String> line : character) {
            for (String s : line)
                System.out.print(s);
            System.out.println();
            C.candidateView.addView(DictTitle.get(line.get(0)));
            int len = line.size();
            if (len > 8) len = 8;
            for (int i = 1; i < len; i++) {
                C.candidateView.addView(DictButton.get(line.get(i)));
            }
        }
    }
}
