package co.watermelonime;

import android.animation.TimeInterpolator;
import android.graphics.Typeface;
import android.view.animation.DecelerateInterpolator;

import java.util.concurrent.ExecutorService;

import co.watermelonime.InputView.Chinese.Candidate.CandidateView;
import co.watermelonime.InputView.Chinese.ChineseInputView;
import co.watermelonime.InputView.Chinese.Keyboard.ChineseKeyboard;
import co.watermelonime.InputView.Chinese.Sentence.SentenceView;
import co.watermelonime.InputView.Emoji.Emoji;
import co.watermelonime.InputView.WaitingView;

public class C {
    public final static TimeInterpolator
            decelerate = new DecelerateInterpolator(2f);
    public static MainService mainService;
    public static ExecutorService threadPool;
    public static Typeface sans;

    public static WaitingView waitingView;
    public static boolean shouldDisplayInputView = false;

    public static boolean isLandscape, debug = true;
    public static SentenceView sentenceView;
    public static CandidateView candidateView;
    public static ChineseKeyboard chineseKeyboard;
    public static ChineseInputView chineseInputView;
    public static Emoji emoji;

    public static void commit(String text) {
        if (MainService.inputConnection != null)
            try {
                MainService.inputConnection.commitText(text, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
