package co.watermelonime.Core;

import java.util.ArrayList;

import co.watermelonime.C;
import co.watermelonime.Common.Timer;
import co.watermelonime.InputView.Chinese.Candidate.CandidateButton;
import co.watermelonime.InputView.Chinese.Candidate.CandidateView;
import co.watermelonime.InputView.Chinese.Candidate.NavigationKey;

public class Controller {
    private static ArrayList<String> arrayList = new ArrayList<>(16);

    public static void init() {

    }

    /**
     * Add pinyin to the engine and trigger actions
     *
     * @param pinyin        Raw pinyin before transformation
     * @param characterLock Character on the blue key, should be a single character
     *                      if pinyin is a consonant, this should be null
     */
    public static void add(final char pinyin, final char characterLock) {
//        System.out.println("controller add: " + pinyin + " | " + characterLock);
        if (characterLock == '\0') {
            if (Engine.getLength() == 9) {
                C.commit(Engine.pop());
                C.sentenceView.display();
            }
            Engine.add(pinyin);
        } else {
            Engine.add(pinyin, characterLock);
            Engine.queryDB();

            Timer.t(111);
            Engine.readQueryResult();
            Timer.t(111, "read");
            try {
                Engine.makeSeparator();
            } catch (Exception e) {
                e.printStackTrace();
                Engine.clear();
                C.sentenceView.display();
                displayCandidates();
                return;
            }

            Engine.makeSentence();
            Engine.makeCandidateLeft();
            Engine.makeCandidateRight();

            C.sentenceView.display();
            Timer.t(444);
            displayCandidates();
            Timer.t(444, "candidate display");
        }
    }

    /**
     * Delete a single pinyin (can be either a consonant or a vowel depending on whether current pinyin length is even or odd)
     */
    static void del() {

    }

    /**
     * Called when left candidate or enter is clicked
     *
     * @param c the characters to be changed and popped,
     *          if null, the left most phrase is popped
     */
    static void popLeft(final CharSequence c) {

    }

    /**
     * @param c the characters to be changed,
     *          if null, the current sentence is popped
     */
    static void popRight(final CharSequence c) {

    }

    /**
     * Change the characters in sentence[start:end].
     *
     * @param start
     * @param end
     * @param c     modified characters
     */
    static void change(final int start, final int end, final CharSequence c) {

    }

    /**
     * Clear everything in the engine
     */
    static void clear() {

    }


    static void getDict(final int index) {

    }

    public static void displayCandidates() {
        CandidateView.clearCandidates();
        if (Engine.isEmpty()) {
            NavigationKey.display();
            return;
        }

        if (Engine.getLength() == 1) { // candidate should fill both when only character typed
            int leftLength = 8, rightLength = 8;
            int totalLength = Engine.candidateRight.size();
            if (totalLength < 16) {
                rightLength = totalLength / 2;
                leftLength = totalLength - rightLength;
            }
            CandidateView.setCandidate(Engine.candidateRight, 0, leftLength, CandidateButton.TOP);
            CandidateView.setCandidate(Engine.candidateRight,
                    leftLength, leftLength + rightLength, CandidateButton.BOTTOM);
            return;
        }

        CandidateView.setCandidate(Engine.candidateLeft,
                0, Engine.candidateLeft.size(), CandidateButton.TOP);
        CandidateView.setCandidate(Engine.candidateRight,
                0, Engine.candidateRight.size(), CandidateButton.BOTTOM);
    }
}
