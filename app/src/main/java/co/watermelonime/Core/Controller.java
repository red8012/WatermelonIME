package co.watermelonime.Core;

import co.watermelonime.C;
import co.watermelonime.Common.Timer;
import co.watermelonime.InputView.Chinese.Candidate.CandidateButton;
import co.watermelonime.InputView.Chinese.Candidate.NavigationKey;
import co.watermelonime.InputView.Chinese.Candidate.PredictionArea;
import co.watermelonime.InputView.Chinese.Keyboard.ChineseKeyboard;
import co.watermelonime.InputView.Chinese.Keyboard.Consonants;
import co.watermelonime.InputView.Chinese.Keyboard.OnTouchConsonant;
import co.watermelonime.InputView.Chinese.Keyboard.Vowels;

public class Controller {
    public static Runnable predict = () -> {
        try {
            Engine.queryPrediction();
            C.candidateView.post(() -> {
                PredictionArea.setPrediction(
                        Engine.predictionResults,
                        Engine.predictionStartPosition,
                        Engine.predictionPinyin
                );
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

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
            return;
        }
        try {
            Engine.add(pinyin, characterLock);
            Engine.queryDB();

            Timer.t(111);
            Engine.readQueryResult();
            Timer.t(111, "read");
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
        C.candidateView.clearCandidates();
        if (Engine.isEmpty()) {
            NavigationKey.display();
            return;
        }

        if (ChineseKeyboard.currentKeys != Consonants.keys) {
            for (int id = 0; id < Vowels.keyArray.length; id++)
                if (ChineseKeyboard.currentKeys == Vowels.keyArray[id]) {
                    OnTouchConsonant.fillInPredictionAndCharacterKeys(id);
                    return;
                }
            return;
        }

        if (Engine.getLength() == 1) { // candidate should fill both when only one character typed
            int leftLength = 8, rightLength = 8;
            int totalLength = Engine.candidateRight.size();
            if (totalLength < 16) {
                rightLength = totalLength / 2;
                leftLength = totalLength - rightLength;
            }
            C.candidateView.setCandidate(Engine.candidateRight, 0, leftLength, CandidateButton.TOP);
            C.candidateView.setCandidate(Engine.candidateRight,
                    leftLength, leftLength + rightLength, CandidateButton.BOTTOM);
            return;
        }

        C.candidateView.setCandidate(Engine.candidateLeft,
                0, Engine.candidateLeft.size(), CandidateButton.TOP);
        C.candidateView.setCandidate(Engine.candidateRight,
                0, Engine.candidateRight.size(), CandidateButton.BOTTOM);
    }

    /**
     *
     */
    public static void commitAll() {
        Timer.t(419);
        if (Engine.isEmpty()) {
            Engine.clear();
            C.sentenceView.display();
            Controller.displayCandidates();
            return;
        }
        C.commit(Engine.sentence);
//        Logger.d("Sentence: %s, ZiLock: %s, ZiOrig: %s", Engine.sentence, Engine.ziLock, Engine.ziOrig);

        boolean existedAsAWhole = Learner.learnWord(Engine.sentence.toString(), Engine.pinyin);

        if (!existedAsAWhole) {
            int start, end = 0;
            for (int i : Engine.separatorAnswer) { // learn each word
                end += i;
                start = end - i;
                if (i == 1) continue;
                boolean dirty = false;
                for (int j = start; j < end; j++)
                    if (Engine.ziLock.charAt(j) != Engine.ziOrig.charAt(j)) {
                        dirty = true;
                        break;
                    }
                if (dirty) continue; // new word, do not know word boundary, skip update

                Learner.learnWordAsync(Engine.sentence.substring(start, end),
                        Engine.pinyin.substring(start * 2, end * 2));
            }
        }

        if (Learner.wordBuffer.length() != 0) {
            Learner.wordBuffer.append(Engine.sentence);
            Learner.pinyinBuffer.append(Engine.pinyin);
            Learner.learnFromBuffer();
        }
        Engine.clear();
        C.sentenceView.display();
        Controller.displayCandidates();
        Timer.t(419, "commit all");
    }
}
