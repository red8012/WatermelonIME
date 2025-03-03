package co.watermelonime;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.inputmethodservice.InputMethodService;
import android.os.Handler;
import android.os.Process;
import android.support.v7.widget.TintContextWrapper;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import com.orhanobut.logger.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import co.watermelonime.Common.Font;
import co.watermelonime.Common.Size;
import co.watermelonime.Common.Timer;
import co.watermelonime.Core.BufferedSplitter;
import co.watermelonime.Core.Controller;
import co.watermelonime.Core.Engine;
import co.watermelonime.InputView.Chinese.Candidate.CandidateButton;
import co.watermelonime.InputView.Chinese.Candidate.CandidateView;
import co.watermelonime.InputView.Chinese.Candidate.CharacterKey;
import co.watermelonime.InputView.Chinese.Candidate.DictButton;
import co.watermelonime.InputView.Chinese.Candidate.DictTitle;
import co.watermelonime.InputView.Chinese.Candidate.DynamicLayoutPool;
import co.watermelonime.InputView.Chinese.Candidate.NavigationKey;
import co.watermelonime.InputView.Chinese.Candidate.PredictionArea;
import co.watermelonime.InputView.Chinese.Candidate.PredictionKey;
import co.watermelonime.InputView.Chinese.Keyboard.ChineseKeyboard;
import co.watermelonime.InputView.Chinese.Keyboard.Consonants;
import co.watermelonime.InputView.Chinese.Keyboard.Vowels;
import co.watermelonime.InputView.Chinese.Sentence.LandscapeLanguageSelectorBar;
import co.watermelonime.InputView.Chinese.Sentence.LanguageSelector;
import co.watermelonime.InputView.Chinese.Sentence.SentenceView;
import co.watermelonime.InputView.Emoji.EmojiKeyboard;
import co.watermelonime.InputView.English.CandidateBar;
import co.watermelonime.InputView.English.EnglishKeyboard;
import co.watermelonime.InputView.InputView;
import co.watermelonime.InputView.Number.NumberKeyboard;
import co.watermelonime.InputView.WaitingView;


public class MainService extends InputMethodService {
    public static final Handler handler = new Handler();
    public static InputConnection inputConnection;
    public static InputMethodManager inputMethodManager;
    static long initializationTimer;
    static View v;

    public MainService() {
        C.mainService = this;
        Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_DISPLAY);
        C.threadPool = Executors.newFixedThreadPool(3);
        BufferedSplitter.init();
        Logger.init("L@");
    }

    public static View getStartupView() {
        try {
            Engine.init();
        } catch (Exception e) {
//            e.printStackTrace();
            Engine.close();
            DBCopy.start();
            return WaitingView.me;
        }
        return null; // not sure if this can prevent crash when idle for a long time...
    }

    @Override
    public void onCreate() {
        super.onCreate();
        inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public boolean onEvaluateFullscreenMode() {

        return false;
    }

    @Override
    public void onInitializeInterface() {
        System.out.println("onInitializeInterface");
        initializationTimer = System.nanoTime();

        Point size = new Point();
        ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
        Size.calculate(size);

        // async task 1
        Future<View> inputViewFuture = C.threadPool.submit(() -> getStartupView());

        if (Font.sans == null) {
            Timer.t(0);
            Font.sans = Typeface.createFromAsset(getAssets(), "normal.otf");
            Font.notoEmoji = Typeface.createFromAsset(getAssets(), "NotoColorEmoji.ttf");
            Font.latin = Typeface.createFromAsset(getAssets(), "NotoSans-Regular.ttf");
            Timer.t(0, "Load fonts");
        }
        Font.init();

        Timer.t(1);
        C.context = TintContextWrapper.wrap(C.mainService);
        Consonants.buildKeys();
        Timer.t(1, "Build consonants");

        // async task 2
        Future f2 = C.threadPool.submit(() -> {
            Timer.t(22);
            Process.setThreadPriority(Process.THREAD_PRIORITY_DISPLAY);
            C.numberKeyboard = new NumberKeyboard();
            C.englishKeyboard = new EnglishKeyboard();
            C.emojiKeyboard = new EmojiKeyboard();
            C.inputView.addView(C.numberKeyboard);
            C.inputView.addView(C.englishKeyboard);
            C.inputView.addView(C.emojiKeyboard);
            Timer.t(22, "f2");
            return null;
        });

        // async task 3
        Vowels.buildKeysAsync();

        // async task 4
        C.threadPool.submit(() -> {
            Timer.t(24);
            Process.setThreadPriority(Process.THREAD_PRIORITY_DISPLAY);
            CharacterKey.init();
            PredictionKey.init();
            DynamicLayoutPool.init();
            CandidateButton.init();
            DictTitle.init();
            DictButton.init();
            Timer.t(24, "f4");
            return null;
        });


        Timer.t(3);
        C.chineseKeyboard = new ChineseKeyboard();
        Timer.t(3, "Build keyboard");

        Timer.t(4);
        PredictionArea.init();
        C.candidateView = new CandidateView();
        if (C.isLandscape) C.landscapeCandidateViewRight = new CandidateView();
        NavigationKey.init();
        Timer.t(4, "Build CandidateView");

        Timer.t(325);
        LanguageSelector.init();
        C.sentenceView = new SentenceView();
        if (C.isLandscape)
            C.landscapeLanguageSelectorBar = new LandscapeLanguageSelectorBar();
        Timer.t(325, "Build SentenceView");

        Timer.t(5);
        C.inputView = new InputView();
        Timer.t(5, "Build ChineseInputView");

        v = null;
        try {
            v = inputViewFuture.get();
            f2.get();
            Controller.displayCandidates();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Service startup (includes getting future) takes " +
                String.valueOf((System.nanoTime() - initializationTimer) / 1e6) + " ms");
    }

    @Override
    public View onCreateInputView() {
        System.out.println("onCreateInputView");
        if (C.isLandscape)
            if (LanguageSelector.inputLanguage != LanguageSelector.CHINESE &&
                    LanguageSelector.inputLanguage != LanguageSelector.ENGLISH){
                LanguageSelector.setInputLanguage(LanguageSelector.ENGLISH);
            }
        if (v == null) return C.inputView;
        else return v;
    }

    @Override
    public void onStartInput(EditorInfo editorInfo, boolean restarting) {
        System.out.println("onStartInput");
        inputConnection = getCurrentInputConnection();
        C.inputType = editorInfo.inputType;
        C.initialCapsMode = editorInfo.initialCapsMode;

        switch (C.inputType & InputType.TYPE_MASK_CLASS) {
            case InputType.TYPE_CLASS_DATETIME:
            case InputType.TYPE_CLASS_NUMBER:
            case InputType.TYPE_CLASS_PHONE:
                LanguageSelector.setInputLanguage(LanguageSelector.NUMBER, false);
                break;
            case InputType.TYPE_CLASS_TEXT:
                switch (C.inputType & InputType.TYPE_MASK_VARIATION) {
                    case InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS:
                    case InputType.TYPE_TEXT_VARIATION_URI:
                    case InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS:
                    case InputType.TYPE_TEXT_VARIATION_PASSWORD:
                    case InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD:
                        Logger.d("Switch to english: %d", C.inputType & InputType.TYPE_MASK_VARIATION);
                        LanguageSelector.setInputLanguage(LanguageSelector.ENGLISH, false);
                        C.englishKeyboard.changeMode(EnglishKeyboard.LOWER);
                        break;
                    default:
                        LanguageSelector.setInputLanguage(LanguageSelector.lastInputLanguage);
                }
                if (LanguageSelector.inputLanguage == LanguageSelector.ENGLISH && C.initialCapsMode > 0)
                    C.englishKeyboard.changeMode(EnglishKeyboard.UPPER);
        }
    }

    @Override
    public void onViewClicked(boolean focusChanged) {
        CandidateBar.reset();
    }

    @Override
    public void onUpdateCursorAnchorInfo(CursorAnchorInfo cursorAnchorInfo) {
        CandidateBar.reset();
    }

    @Override
    public void onBindInput() {
        CandidateBar.reset();
    }
}
