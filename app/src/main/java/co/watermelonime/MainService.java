package co.watermelonime;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.inputmethodservice.InputMethodService;
import android.os.Handler;
import android.os.Process;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import co.watermelonime.Common.Font;
import co.watermelonime.Common.Size;
import co.watermelonime.Common.Timer;
import co.watermelonime.Core.DB;
import co.watermelonime.InputView.Chinese.Candidate.CandidateView;
import co.watermelonime.InputView.Chinese.ChineseInputView;
import co.watermelonime.InputView.Chinese.Common;
import co.watermelonime.InputView.Chinese.Keyboard.ChineseKeyboard;
import co.watermelonime.InputView.Chinese.Keyboard.Consonants;
import co.watermelonime.InputView.Chinese.Keyboard.Vowels;
import co.watermelonime.InputView.Chinese.Sentence.SentenceView;
import co.watermelonime.InputView.WaitingView;

//import net.sqlcipher.*;

public class MainService extends InputMethodService {
    public static final Handler handler = new Handler();
    static long initializationTimer;
    static InputConnection inputConnection;

    public MainService() {
        initializationTimer = System.nanoTime();
        C.mainService = this;
        Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_DISPLAY);
        C.threadPool = Executors.newFixedThreadPool(2);
    }

    public static View getStartupView() {
        try {
            // check DB availability
            DB.init();
//            Cursor c = DB.dictionary.rawQuery(
//                    "select zi, priority from solo where pinyin = 'Aa' order by priority desc",
//                    null);
//            c.moveToNext();
//            c.getString(0);
        } catch (Exception e) {
            C.waitingView = new WaitingView();
            DBCopy.start();
            return C.waitingView;
        }
        return C.chineseInputView;
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

        if (C.sans == null) {
            Timer.t(0);
            C.sans = Typeface.createFromAsset(getAssets(), "normal.otf");
            Timer.t(0, "Load fonts");
        }
        Font.init();

        Timer.t(1);
        Common.initialize();
        Consonants.buildKeys();
        Timer.t(1, "Build consonants");

//        Timer.t(3);
        Vowels.buildKeysAsync();
//        Timer.t(3, "Build vowels");

//        Timer.t(2);
        C.chineseKeyboard = new ChineseKeyboard();
//        Timer.t(2, "Build keyboard");

        Timer.t(4);
        C.candidateView = new CandidateView();
//        ArrayList<String> list = new ArrayList<>();
//        list.add("中文輸入法測");
//        list.add("文");
//        list.add("輸入法");
//        list.add("試");
//        list.add("中文輸入法測試");
//        list.add("輸入法試");
//        list.add("輸法試");
//        list.add("試");
//        list.add("1234");
//        list.add("abcd");
//        ArrayList<String> list2 = new ArrayList<>();
//        list2.add("中");
//        list2.add("文");
//        list2.add("輸");
//        list2.add("入");
//        list2.add("法");
//        list2.add("試");
//        list2.add("中");
//        list2.add("文");
//        list2.add("輸");
//        list2.add("入");
//        C.candidateView.setCandidate(list, CandidateButton.TOP);
//        C.candidateView.setCandidate(list2, CandidateButton.BOTTOM);
        Timer.t(4, "Build CandidateView");

        Timer.t(325);
        C.sentenceView = new SentenceView();
        Timer.t(325, "Build SentenceView");


        Timer.t(5);
        C.chineseInputView = new ChineseInputView();
        Timer.t(5, "Build ChineseInputView");

        System.out.println("Service startup takes " +
                String.valueOf((System.nanoTime() - initializationTimer) / 1e6) + " ms");

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
