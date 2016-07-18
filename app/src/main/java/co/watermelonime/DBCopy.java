package co.watermelonime;

import android.app.AlertDialog;
import android.content.Context;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import co.watermelonime.Core.ContactsLearner;
import co.watermelonime.Core.Engine;
import co.watermelonime.InputView.WaitingView;

public class DBCopy {
    static boolean
            shouldCopyContacts = false,
            inputViewShown = false;

    static void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(C.mainService);
        builder
                .setTitle("西瓜輸入法")
                .setMessage("從聯絡人清單匯入姓名可以讓您在輸入人名時更加準確，請問要現在進行匯入嗎？")
                .setPositiveButton("是", (dialog, which) -> {
                    ContactsLearner.start();
//                    if (inputViewShown) ContactsLearner.start();
//                    else shouldCopyContacts = true;
                })
                .setNegativeButton("否", (dialog, which) -> {
                    System.out.println("negative");
                })
                .setOnCancelListener(dialog1 -> System.out.println("cancel"));
        AlertDialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        dialog.show();
        System.out.println("isShowing" + dialog.isShowing());
    }

    public static void start() {
        WaitingView.me = new WaitingView();
        C.mainService.setInputView(WaitingView.me);
        MainService.handler.post(() ->showAlert());

        C.threadPool.submit(() -> {
            try {
                final Context c = C.mainService;
                Thread.sleep(1000);
                InputStream in = c.getApplicationContext().getAssets().open(C.DBFileName);
                File output = c.getDatabasePath("newDB.db");

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

                try {
                    Engine.close();
                } catch (Exception e) {
                }

                WaitingView.me.increment(1);
                // Rename
                File to = c.getDatabasePath(C.DBFileName);
                to.mkdirs();
                to.delete();
                output.renameTo(to);
                WaitingView.me.increment(10);

                Logger.d("DB upgrade finished");

                MainService.handler.post(() -> {
                    try {
                        Engine.init();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    inputViewShown = true;
//                    if (shouldCopyContacts)
//                        ContactsLearner.start();
                    C.mainService.setInputView(C.inputView);
//                    showAlert();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}