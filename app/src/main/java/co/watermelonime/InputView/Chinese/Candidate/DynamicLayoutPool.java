package co.watermelonime.InputView.Chinese.Candidate;

import android.text.DynamicLayout;
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
            Log.e("dynamic layout pool", "empty");
        } else {
            dynamicLayout = p.remove(p.size() - 1);
            StringBuilder content = s.remove(s.size() - 1);
            content.setLength(0);
            content.append(text);
        }
        return dynamicLayout;
    }

    public static void release(DynamicLayout dynamicLayout) {
        StringBuilder sb = (StringBuilder) dynamicLayout.getText();
        int length = sb.length();
        span[length].add(sb);
        pool[length].add(dynamicLayout);
    }
}