package co.watermelonime;

import android.graphics.Color;
import android.graphics.Typeface;
import android.inputmethodservice.InputMethodService;
import android.os.Process;
import android.text.StaticLayout;
import android.view.View;
import android.widget.Button;

public class MainService extends InputMethodService {
    long initializationTimer;

    public MainService() {
        initializationTimer = System.nanoTime();
        C.mainService = this;
        Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_DISPLAY);
    }

    @Override
    public void onInitializeInterface() {

    }

    @Override
    public View onCreateInputView() {
        C.sourceSans = Typeface.createFromAsset(getAssets(), "normal.otf");
        System.gc();

        Timer.t(0);
//        for (int i = 0; i < 10; i++) {
            Button b = new Button(this);
            b.setBackgroundColor(Color.RED);
            b.setText("龜");
            b.setTypeface(C.sourceSans);
            b.setOnClickListener(v -> System.out.println("gut"));
//        }
        Timer.t(0, "init button");

        System.gc();
        String[] ss = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};

        Timer.t(1);
        TextLayoutFactory textLayoutFactory =
                new TextLayoutFactory(100f, C.sourceSans, Color.RED, 100);
        Timer.t(1, "init TextLayoutFactory");
        Timer.t(2);
        StaticLayout layout = null;
        for (int i = 0; i < 10; i++)
            layout = textLayoutFactory.make(ss[i]);
        Timer.t(2, "make staticLayout");

        Timer.t(3);
        Key key = new Key(this, layout);

        Timer.t(3, "init Key");


        System.out.println("Service startup takes " +
                String.valueOf((System.nanoTime() - initializationTimer) / 1e6) + " ms");
        return key;
    }
}
