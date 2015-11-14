package co.watermelonime;

import android.graphics.Color;
import android.inputmethodservice.InputMethodService;
import android.os.Process;
import android.view.View;
import android.widget.Button;

public class MainService extends InputMethodService {
    public MainService() {
        C.mainService = this;
        Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_DISPLAY);
        System.out.println("hi");
    }
    @Override
    public void onInitializeInterface() {

    }
    @Override
    public View onCreateInputView() {
        Button b = new Button(this);
        b.setBackgroundColor(Color.RED);
        b.setOnClickListener(v -> System.out.println("gut"));
        return b;
    }
}
