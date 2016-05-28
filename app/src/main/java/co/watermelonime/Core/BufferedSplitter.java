package co.watermelonime.Core;

import android.util.Log;

import java.util.ArrayList;

public class BufferedSplitter {
    static ArrayList<StringBuilder>[] pool = new ArrayList[10];
    static ArrayList<ArrayList<StringBuilder>>[] listPool = new ArrayList[10];

    public static void init() {
        for (int length = 1; length < 10; length++) {
            int numberOfObjects = 200 / length / length;
            if (length == 2) numberOfObjects += 10;
            pool[length] = new ArrayList<>(numberOfObjects);
            for (int i = 0; i < numberOfObjects; i++)
                pool[length].add(new StringBuilder(length));
            listPool[length] = new ArrayList<>(10 - length);
            for (int i = 0; i < 10 - length; i++) {
                listPool[length].add(new ArrayList<>(24 / length));
            }
        }
    }

    public static StringBuilder get(int length) {
        ArrayList<StringBuilder> list = pool[length];
        if (list.isEmpty()) {
            Log.e("StringBuilder pool", "is empty" + length);
            return new StringBuilder(length);
        }
        StringBuilder sb = list.remove(list.size() - 1);
        return sb;
    }

    public static void release(StringBuilder stringBuilder) {
        int length = stringBuilder.length();
        stringBuilder.setLength(0);
        if (length > 9) return;
        pool[length].add(stringBuilder);
    }

    public static ArrayList<StringBuilder> getArrayList(int length) {
        ArrayList<ArrayList<StringBuilder>> list = listPool[length];
        if (list.isEmpty()) {
            Log.e("ArrayList pool", "is empty" + length);
            return new ArrayList<>(24 / length);
        }
        return list.remove(list.size() - 1);
    }

    public static void releaseArrayList(ArrayList<StringBuilder> arrayList) {
        if (arrayList == null) return;
        int length = 1;
        try {
            length = arrayList.get(0).length();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Log.e("ArrayList pool", "release" + length);
//        for (StringBuilder s : arrayList)
//            release(s);
        for (int i = arrayList.size() - 1; i >= 0; i--)
            release(arrayList.get(i));
        arrayList.clear();
        listPool[length].add(arrayList);
    }

    public static ArrayList<StringBuilder> split(String s) {
        int start = 0;
        int end = s.indexOf(',');
        ArrayList<StringBuilder> list = getArrayList(end < 0 ? s.length() : end);
        while (end > 0) {
            StringBuilder sb = get(end - start);
            sb.append(s, start, end);
            list.add(sb);
            start = end + 1;
            end = s.indexOf(',', start);
        }
        end = s.length();
        StringBuilder sb = get(end - start);
        sb.append(s, start, end);
        list.add(sb);
        return list;
    }
}
