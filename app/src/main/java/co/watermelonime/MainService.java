package co.watermelonime;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.inputmethodservice.InputMethodService;
import android.os.Handler;
import android.os.Process;
import android.support.v7.widget.TintContextWrapper;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import co.watermelonime.Common.Font;
import co.watermelonime.Common.Size;
import co.watermelonime.Common.Timer;
import co.watermelonime.Core.Engine;
import co.watermelonime.InputView.Chinese.Candidate.CandidateView;
import co.watermelonime.InputView.Chinese.Candidate.CharacterKey;
import co.watermelonime.InputView.Chinese.Candidate.NavigationKey;
import co.watermelonime.InputView.Chinese.Candidate.PredictionKey;
import co.watermelonime.InputView.Chinese.ChineseInputView;
import co.watermelonime.InputView.Chinese.Common;
import co.watermelonime.InputView.Chinese.Keyboard.ChineseKeyboard;
import co.watermelonime.InputView.Chinese.Keyboard.Consonants;
import co.watermelonime.InputView.Chinese.Keyboard.Vowels;
import co.watermelonime.InputView.Chinese.Sentence.LanguageSelector;
import co.watermelonime.InputView.Chinese.Sentence.SentenceView;
import co.watermelonime.InputView.Emoji.EmojiKeyboard;
import co.watermelonime.InputView.English.EnglishKeyboard;
import co.watermelonime.InputView.Number.NumberKeyboard;
import co.watermelonime.InputView.WaitingView;


public class MainService extends InputMethodService {
    public static final Handler handler = new Handler();
    public static InputConnection inputConnection;
    public static InputMethodManager inputMethodManager;
    static long initializationTimer;

    public MainService() {
        initializationTimer = System.nanoTime();
        C.mainService = this;
        Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_DISPLAY);
        C.threadPool = Executors.newFixedThreadPool(2);
    }

    public static View getStartupView() {
        try {
            Engine.init();
        } catch (Exception e) {
            e.printStackTrace();
            WaitingView.me = new WaitingView();
            DBCopy.start();
            return WaitingView.me;
        }
        return null; // not sure if this can prevent crash when idle for a long time...
//        return C.chineseInputView;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public boolean onEvaluateFullscreenMode() {
        Point size = new Point();
        ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
        Size.calculate(size);
        return false;
    }

    @Override
    public void onInitializeInterface() {

    }

    @Override
    public View onCreateInputView() {
        Future<View> inputViewFuture = C.threadPool.submit(() -> getStartupView());

        if (Font.sans == null) {
            Timer.t(0);
            Font.sans = Typeface.createFromAsset(getAssets(), "normal.otf");
            Timer.t(0, "Load fonts");
        }
        Font.init();

        Timer.t(1);
        C.context = TintContextWrapper.wrap(C.mainService);
        Common.initialize();
        Consonants.buildKeys();
        Timer.t(1, "Build consonants");

//        Timer.t(3);
        Vowels.buildKeysAsync();
        ChineseInputView.initializeHiddenPartsAsync();
//        Timer.t(3, "Build vowels");

//        Timer.t(2);
        C.chineseKeyboard = new ChineseKeyboard();
//        Timer.t(2, "Build keyboard");

        Timer.t(4);
        C.candidateView = new CandidateView();
        NavigationKey.init();
        Timer.t(4, "Build CandidateView");

        Timer.t(325);
        LanguageSelector.init();
        CharacterKey.init();
        PredictionKey.init();

        C.sentenceView = new SentenceView();
        C.numberKeyboard = new NumberKeyboard();
        C.englishKeyboard = new EnglishKeyboard();
        C.emojiKeyboard = new EmojiKeyboard();
        Timer.t(325, "Build SentenceView");

        Timer.t(5);
        C.chineseInputView = new ChineseInputView();
        Timer.t(5, "Build ChineseInputView");

        View v = null;
        try {
            v = inputViewFuture.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Service startup (includes getting future) takes " +
                String.valueOf((System.nanoTime() - initializationTimer) / 1e6) + " ms");

        if (v == null) return C.chineseInputView;
        else return v;
    }

    @Override
    public void onStartInput(EditorInfo editorInfo, boolean restarting) {
        inputConnection = getCurrentInputConnection();
    }

}
