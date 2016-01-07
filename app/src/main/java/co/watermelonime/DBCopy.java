package co.watermelonime;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import co.watermelonime.Core.DB;

public class DBCopy {

    public static void start() {
        C.threadPool.submit(() -> {
            try {
                final Context c = C.mainService;
                InputStream in = c.getApplicationContext().getAssets().open("encrypted.db3");
                File output = c.getDatabasePath("encrypted.db3");
                output.mkdirs();
                output.delete();
                FileOutputStream out = new FileOutputStream(output);

                byte[] buffer = new byte[128 * 1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                    C.waitingView.increment(1);
                }
                in.close();
                out.close();
                C.shouldDisplayInputView = true;
                MainService.handler.post(() -> {
                    DB.init();
                    C.mainService.setInputView(C.chineseInputView);
                });
                System.out.println("Copy finished!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
