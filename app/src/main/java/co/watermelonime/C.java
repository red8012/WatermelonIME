package co.watermelonime;

import android.animation.TimeInterpolator;
import android.graphics.Typeface;
import android.view.animation.DecelerateInterpolator;

import java.util.concurrent.ExecutorService;

import co.watermelonime.InputView.Chinese.Candidate.CandidateView;
import co.watermelonime.InputView.Chinese.ChineseInputView;
import co.watermelonime.InputView.Chinese.Keyboard.ChineseKeyboard;
import co.watermelonime.InputView.Chinese.Sentence.SentenceView;

public class C {
    public final static TimeInterpolator
            decelerate = new DecelerateInterpolator(2f);
    public static MainService mainService;
    public static ExecutorService threadPool;
    public static Typeface sans;

    public static boolean isLandscape;
    public static SentenceView sentenceView;
    public static CandidateView candidateView;
    public static ChineseKeyboard chineseKeyboard;
    public static ChineseInputView chineseInputView;
}
