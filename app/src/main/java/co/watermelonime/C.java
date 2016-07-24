package co.watermelonime;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.view.animation.DecelerateInterpolator;

import java.util.concurrent.ExecutorService;

import co.watermelonime.InputView.Chinese.Candidate.CandidateView;
import co.watermelonime.InputView.Chinese.InputView;
import co.watermelonime.InputView.Chinese.Keyboard.ChineseKeyboard;
import co.watermelonime.InputView.Chinese.Sentence.SentenceView;
import co.watermelonime.InputView.Emoji.EmojiKeyboard;
import co.watermelonime.InputView.English.EnglishKeyboard;
import co.watermelonime.InputView.Number.NumberKeyboard;

public class C {
    public final static String DBFileName = "v7.db";
    public final static int DBVersion = 7;
    public final static TimeInterpolator
            decelerate = new DecelerateInterpolator(2f);
    public static MainService mainService;
    public static Context context;
    public static ExecutorService threadPool;
    public static int inputType, initialCapsMode;
    public static boolean isLandscape, debug = true;
    public static SentenceView sentenceView;
    public static CandidateView candidateView;
    public static ChineseKeyboard chineseKeyboard;
    public static InputView inputView;
    public static EmojiKeyboard emojiKeyboard;
    public static NumberKeyboard numberKeyboard;
    public static EnglishKeyboard englishKeyboard;
    public static StringBuilder commitBuffer = new StringBuilder(9);

    public static void commit(CharSequence text) {
        if (MainService.inputConnection != null)
            try {
                MainService.inputConnection.commitText(text, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void commit() {
        commit(commitBuffer);
    }
}
