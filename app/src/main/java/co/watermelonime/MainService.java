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
import co.watermelonime.InputView.Chinese.Keyboard.Consonants;

public class MainService extends InputMethodService {
    long initializationTimer;

    public MainService() {
        initializationTimer = System.nanoTime();
        C.mainService = this;
        Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_DISPLAY);
        C.threadPool = Executors.newFixedThreadPool(4);
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
        C.sourceSans = Typeface.createFromAsset(getAssets(), "normal.otf");
//        System.gc();

//        Timer.t(0);
////        for (int i = 0; i < 10; i++) {
//        Button b = new Button(this);
//        b.setBackgroundColor(Color.RED);
//        b.setText("龜");
//        b.setTypeface(C.sourceSans);
//        b.setOnClickListener(v -> System.out.println("gut"));
////        }
//        Timer.t(0, "init button");


//        Timer.t(1);
//        TextLayoutFactory textLayoutFactory =
//                new TextLayoutFactory(100f, C.sourceSans, Color.WHITE, C.u*10);
//        Timer.t(1, "init TextLayoutFactory");
//        Timer.t(2);
//        StaticLayout layout = textLayoutFactory.make("龜");
//        Timer.t(2, "make staticLayout");
//
//        Timer.t(3);
//        Key key = new Key(layout);
//
//        Timer.t(3, "init Key");


        Timer.t(1);
        Consonants.buildKeys();
        Timer.t(1, "Build keys");
        Timer.t(2);
        C.chineseKeyboard = new ChineseKeyboard();
        for (int i = 0; i < 24; i++)
            ChineseKeyboard.keys[i] = Consonants.keys.get(i);
        Timer.t(2, "Build keyboard");
        System.out.println("Service startup takes " +
                String.valueOf((System.nanoTime() - initializationTimer) / 1e6) + " ms");
        return C.chineseKeyboard;
    }
}
