package co.watermelonime;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.inputmethodservice.InputMethodService;
import android.os.Process;
import android.view.View;
import android.view.WindowManager;

import java.util.concurrent.Executors;

import co.watermelonime.InputView.Chinese.Keyboard.ChineseKeyboard;
import co.watermelonime.InputView.Chinese.Keyboard.Common;
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

        System.out.println("Service startup takes " +
                String.valueOf((System.nanoTime() - initializationTimer) / 1e6) + " ms");
        return C.chineseKeyboard;
    }
}
