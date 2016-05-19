package co.watermelonime.Core;

import java.util.ArrayList;
import java.util.Iterator;

import co.watermelonime.C;
import co.watermelonime.InputView.Chinese.Candidate.CandidateButton;
import co.watermelonime.InputView.Chinese.Candidate.CandidateView;
import co.watermelonime.InputView.Chinese.Candidate.NavigationKey;

public class Controller {
    private static ArrayList<String> arrayList = new ArrayList<>(8);

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
            Engine.readQueryResult();
            try {
                Engine.makeSeparator();
            } catch (Exception e) {
                e.printStackTrace();
                Engine.clear();
            }
            Engine.makeSentence();
            Engine.makeCandidateLeft();
            Engine.makeCandidateRight();

            C.sentenceView.display();
            displayCandidates();
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

        Iterator<String> it1, it2;

        if (Engine.getLength() == 1) { // candidate should fill both when only character typed
            it1 = Engine.candidateRight.iterator();
            it2 = it1;
        } else {
            it1 = Engine.candidateLeft.iterator();
            it2 = Engine.candidateRight.iterator();
        }

        arrayList.clear();
        for (int i = 0; i < 8; i++)
            if (it1.hasNext()) arrayList.add(it1.next());
            else break;
        CandidateView.setCandidate(arrayList, CandidateButton.TOP);

        arrayList.clear();
        for (int i = 0; i < 8; i++)
            if (it2.hasNext()) arrayList.add(it2.next());
            else break;
        CandidateView.setCandidate(arrayList, CandidateButton.BOTTOM);
    }
}
