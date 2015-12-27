package co.watermelonime;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.inputmethodservice.InputMethodService;
import android.os.Process;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.concurrent.Executors;

import co.watermelonime.Common.Timer;
import co.watermelonime.InputView.Chinese.Candidate.CandidateButton;
import co.watermelonime.InputView.Chinese.Candidate.CandidateView;
import co.watermelonime.InputView.Chinese.ChineseInputView;
import co.watermelonime.InputView.Chinese.Common;
import co.watermelonime.InputView.Chinese.Keyboard.ChineseKeyboard;
import co.watermelonime.InputView.Chinese.Keyboard.Consonants;
import co.watermelonime.InputView.Chinese.Keyboard.Vowels;

public class MainService extends InputMethodService {
    long initializationTimer;

    public MainService() {
        initializationTimer = System.nanoTime();
        C.mainService = this;
        Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_DISPLAY);
        C.threadPool = Executors.newFixedThreadPool(2);
    }

    public boolean onEvaluateFullscreenMode() {
        Point size = new Point();
        ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
        C.screenWidth = size.x;
        C.screenHeight = size.y;
        C.isLandscape = size.x > size.y;
        C.keyboardWidth = C.isLandscape ? (C.screenWidth / 2) : C.screenWidth;
        C.candidateWidth = C.keyboardWidth;
        C.u = C.keyboardWidth / 60;
        return false;
    }

    @Override
    public void onInitializeInterface() {

    }

    @Override
    public View onCreateInputView() {
        if (C.sourceSans == null) {
            Timer.t(0);
            C.sourceSans = Typeface.createFromAsset(getAssets(), "normal.otf");
            Timer.t(0, "Load fonts");
        }

        Timer.t(1);
        Common.initialize();
        Consonants.buildKeys();
        Timer.t(1, "Build consonants");

        Timer.t(3);
        Vowels.buildKeysAsync();
        Timer.t(3, "Build vowels");

        Timer.t(2);
        C.chineseKeyboard = new ChineseKeyboard();
        Timer.t(2, "Build keyboard");

        Timer.t(4);
        C.candidateView = new CandidateView();
        ArrayList<String> list = new ArrayList<>();
        list.add("中文輸入法測");
        list.add("文");
        list.add("輸入法");
        list.add("試");
        list.add("中文輸入法測試");
        list.add("輸入法試");
        list.add("輸法試");
        list.add("試");
        list.add("1234");
        list.add("abcd");
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("中");
        list2.add("文");
        list2.add("輸");
        list2.add("入");
        list2.add("法");
        list2.add("試");
        list2.add("中");
        list2.add("文");
        list2.add("輸");
        list2.add("入");
        C.candidateView.setCandidate(list, CandidateButton.TOP);
        C.candidateView.setCandidate(list2, CandidateButton.BOTTOM);
        Timer.t(4, "Build CandidateView");

        Timer.t(5);
        C.chineseInputView = new ChineseInputView();
        Timer.t(5, "Build ChineseInputView");


//        C.chineseKeyboard.setCurrentKeys(Consonants.keys);
//        C.chineseInputView.postDelayed(() -> {
//            C.chineseKeyboard.setCurrentKeys(Consonants.keys);
//        }, 500);

        System.out.println("Service startup takes " +
                String.valueOf((System.nanoTime() - initializationTimer) / 1e6) + " ms");
//        return C.chineseKeyboard;
        return C.chineseInputView;
    }
}
