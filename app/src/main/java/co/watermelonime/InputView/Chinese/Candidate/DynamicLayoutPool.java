package co.watermelonime.InputView.Chinese.Candidate;

import android.text.DynamicLayout;
import android.text.Layout;
import android.util.Log;

import java.util.ArrayList;

import co.watermelonime.Common.Font;
import co.watermelonime.Common.Size;

public class DynamicLayoutPool {
    public static ArrayList<DynamicLayout>[] pool = new ArrayList[10]; // pool[length]
    public static ArrayList<StringBuilder>[] span = new ArrayList[10]; // span[length]

    public static void init() {
        for (int length = 1; length < 10; length++) {
            int numberOfDefaultLayouts = 10 - length;
            if (length == 1) numberOfDefaultLayouts = 16;
            if (length == 2) numberOfDefaultLayouts = 12;

            pool[length] = new ArrayList<>(numberOfDefaultLayouts);
            span[length] = new ArrayList<>(numberOfDefaultLayouts);

            for (int i = 0; i < numberOfDefaultLayouts; i++) {
                StringBuilder content = new StringBuilder("123456789".substring(0, length));
                DynamicLayout dynamicLayout = Font.candidate.makeDynamic(content,
                        (int) (Size.FCandidate * length));
                span[length].add(content);
                pool[length].add(dynamicLayout);
            }
        }
    }

    public static DynamicLayout get(CharSequence text) {
        DynamicLayout dynamicLayout;
        int length = text.length();
        ArrayList<DynamicLayout> p = pool[length];
        ArrayList<StringBuilder> s = span[length];

        if (p.isEmpty()) {
            StringBuilder content = new StringBuilder(text);
            dynamicLayout = Font.candidate.makeDynamic(content, (int) (Size.FCandidate * length));
            Log.e("dynamic layout pool", "empty: " + length);
        } else {
            int last = p.size() - 1;
            dynamicLayout = p.remove(last);
            StringBuilder content = s.remove(last);
            content.setLength(0);
            content.append(text);
        }
//        System.out.println("get "+ dynamicLayout);
        return dynamicLayout;
    }

    public static void release(Layout dynamicLayout) {
        StringBuilder sb = (StringBuilder) dynamicLayout.getText();
        int length = sb.length();

        if (pool[length].contains(dynamicLayout))
            System.err.println("Warn: double release " + dynamicLayout);

        span[length].add(sb);
        pool[length].add((DynamicLayout) dynamicLayout);
//        System.out.println("release "+ dynamicLayout);
    }
}
