package co.watermelonime;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import co.watermelonime.Core.Engine;
import co.watermelonime.InputView.WaitingView;

public class DBCopy {

    public static void start() {
        C.threadPool.submit(() -> {
            try {
                final Context c = C.mainService;

                InputStream in = c.getApplicationContext().getAssets().open(C.DBVersion);
                File output = c.getDatabasePath(C.DBVersion);


                output.mkdirs();
                output.delete();
                FileOutputStream out = new FileOutputStream(output);

                byte[] buffer = new byte[256 * 1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                    WaitingView.me.increment(1); // Todo: calculate correct percentage
                }
                in.close();
                out.close();
                MainService.handler.post(() -> {
                    try {
                        Engine.init();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    C.mainService.setInputView(C.chineseInputView);
                });
                System.out.println("Copy finished!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
