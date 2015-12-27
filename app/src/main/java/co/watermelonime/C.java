package co.watermelonime;

import android.animation.TimeInterpolator;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.animation.DecelerateInterpolator;

import java.util.concurrent.ExecutorService;

import co.watermelonime.Common.TextLayoutFactory;
import co.watermelonime.InputView.Chinese.Candidate.CandidateView;
import co.watermelonime.InputView.Chinese.ChineseInputView;
import co.watermelonime.InputView.Chinese.Keyboard.ChineseKeyboard;
import co.watermelonime.InputView.Chinese.Sentence.SentenceView;

public class C {
    public final static int
            COLOR_SENTENCE = Color.rgb(0, 77, 64),
            COLOR_CANDIDATE = Color.rgb(33, 33, 33),
            COLOR_CANDIDATE_SELECTED = Color.rgb(76, 175, 80),
            COLOR_NORMAL = Color.rgb(38, 50, 56),
            COLOR_FUNCTION = Color.rgb(27, 94, 32),
            COLOR_CHARACTER = Color.rgb(13, 71, 161),
            COLOR_DISABLED = Color.BLACK;
    public final static TimeInterpolator
            decelerate = new DecelerateInterpolator(2f);
    public static MainService mainService;
    public static Typeface sourceSans;
    public static ExecutorService threadPool;
    public static int screenWidth, screenHeight, keyboardWidth, u;
    public static int chineseKeyWidth, chineseKeyHeight;
    public static int sentenceFontSize;
    public static int candidateWidth, candidateButtonHeight, candidateFontSize;
    public static boolean isLandscape;
    public static SentenceView sentenceView;
    public static CandidateView candidateView;
    public static ChineseKeyboard chineseKeyboard;
    public static TextLayoutFactory bigFont, frFont, midFont, smallFont,
            bigDisabledFont, midDisabledFont,
            candidateFont, sentenceFont;
    public static ChineseInputView chineseInputView;
}
