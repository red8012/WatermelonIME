package co.watermelonime.Core;

import android.view.MotionEvent;
import android.view.View;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;

public class NavigationKeyListener implements View.OnTouchListener {
    public static final ExecutorService thread = Executors.newFixedThreadPool(1);
    static Future future;
    Runnable command;
    final Runnable job = () -> {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            return;
        }
        command.run();
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }
            command.run();
        }
    };
    boolean needRepeat = true;

    public NavigationKeyListener(final int keyCode) {
        command = () -> C.mainService.sendDownUpKeyEvents(keyCode);
    }

    public NavigationKeyListener(final String commitText) {
        command = () -> C.commit(commitText);
    }

    public NavigationKeyListener(final Runnable runnable) {
        command = runnable;
        needRepeat = false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                v.setBackgroundColor(Colour.CANDIDATE_SELECTED);
                try {
                    if (future != null && !future.isDone() && !future.isCancelled())
                        future.cancel(true);
                    future = null;
                    thread.submit(command);
                    if (needRepeat)
                        future = thread.submit(job);
                } finally {
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                v.setBackgroundColor(Colour.CANDIDATE);
                if (needRepeat && future != null && !future.isDone() && !future.isCancelled())
                    future.cancel(true);
        }
        return true;
    }
}
