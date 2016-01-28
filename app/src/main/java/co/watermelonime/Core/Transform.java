package co.watermelonime.Core;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by ray on 2016/1/9.
 * This class is ported from the old repo and should be rewritten for better performance.
 * BENCHMARK REQUIRED!!!!!!!!!!!!!
 */
public class Transform {
    public static String replaceVowel(final String pinyin) {
        return pinyin
                .replaceAll("1", "[cu]")
                .replaceAll("2", "[cf]")
                .replaceAll("3", "[uc]")
                .replaceAll("4", "[cbgv]")
                .replaceAll("5", "[jc]")
                .replaceAll("6", "[icb]")
                .replaceAll("7", "[em]")
                .replaceAll("8", "[cs]");
    }

    public static String makeGlob(String pinyin) {
        LinkedList<String> input = new LinkedList<String>();
        input.addFirst(pinyin);
        StringBuilder builder = new StringBuilder(256).append("(pinyin glob '");
        Iterator<String> iterator = expand(input).iterator();
        while (iterator.hasNext()) {
            builder.append(iterator.next());
            if (iterator.hasNext()) builder.append("' or pinyin glob '");
        }
        return builder.append("')").toString();
    }

    public static LinkedList<String> expand(LinkedList<String> input) {
        LinkedList<String> result = new LinkedList<String>();
        for (String i : input) {
            if (i.contains("[")) {
                final int start = i.indexOf('['), end = i.indexOf(']');
                String head = i.substring(0, start), tail = i.substring(end + 1),
                        body = i.substring(start + 1, end);
                LinkedList<String> next = new LinkedList<String>();
                char[] cc = body.toCharArray();
                for (char c : cc)
                    next.addLast(head+c+tail);
                result.addAll(expand(next));
            } else result.addLast(i);
        }
        return result;
    }
}
