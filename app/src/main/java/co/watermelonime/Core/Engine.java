package co.watermelonime.Core;

import android.util.Pair;

import net.sqlcipher.Cursor;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import co.watermelonime.C;

/**
 * Created by ray on 2016/1/9.
 * This class is ported from the old repo and should be rewrote for better performance.
 * BENCHMARK REQUIRED!!!!!!!!!!!!!
 */

public class Engine {
    public static final ExecutorService thread1 = Executors.newFixedThreadPool(1),
            thread2 = Executors.newFixedThreadPool(1);
    public static LinkedList<String> candidateLeft, candidateRight;
    static LinkedList<String>[][] queryResultL = initQueryResult(); // [length][start at][i]  // learning
    static LinkedList<String>[][] queryResultD = initQueryResult(); // [length][start at][i]  // dictionary
    static StringBuffer pinyin = new StringBuffer(32),
            ziLock = new StringBuffer(32),   // after dict select or direct zi pinyin
            ziOrig = new StringBuffer(32),   // before dict select
            sentence = new StringBuffer(32);
    static int separatorAnswer[];
    static Cursor[] dictCursors = new Cursor[9]; // [start at]

    public static LinkedList<String>[][] initQueryResult() {
        LinkedList<String>[][] x = new LinkedList[9 + 1][];
        for (int i = 1; i <= 9; i++) {
            x[i] = new LinkedList[9 - i + 1];
            for (int j = 0; j < 9 - i + 1; j++)
                x[i][j] = new LinkedList<>();
        }
        return x;
    }

    public static void clearQueryResult() {
        for (int i = 1; i <= 9; i++)
            for (int j = 0; j < 9 - i + 1; j++) {
                queryResultD[i][j].clear();
                queryResultL[i][j].clear();
            }
    }

    public static void clear() {
        if (C.debug) System.out.println("Engine clear");
        pinyin = new StringBuffer(32);
        ziLock = new StringBuffer(32);
        ziOrig = new StringBuffer(32);
        sentence = new StringBuffer(32);
        if (candidateLeft != null) candidateLeft.clear();
        if (candidateRight != null) candidateRight.clear();
        Engine.clearQueryResult();
        System.gc();
    }

    public static boolean isEmpty() {
        if (pinyin.length() == 0) {
            if (C.debug) {
                if (ziLock.length() != 0 || ziOrig.length() != 0)
                    print("Error: not consistent!");
            }
            return true;
        }
        return false;
    }

    public static int getLength() {
        int length = pinyin.length() / 2;
        if (C.debug)
            if (ziLock.length() != length || ziOrig.length() != length)
                print("Error: not consistent!");
        return length;
    }

    public static String getSentence() {
        return sentence.toString();
    }

    public static StringBuffer getSentenceBuffer() {
        return sentence;
    }

    public static void print(String info, String... args) {
        System.out.println(String.format(info, args));
        System.out.println(String.format("pinyin = %s ziLock = %s ziOrig = %s", pinyin, ziLock, ziOrig));
    }

    /**
     * @param keyCode
     * @return true if successful
     */
    public static boolean addConsonant(final String keyCode) {
        if (pinyin.length() % 2 != 0) {
            if (C.debug)
                System.err.println("Error: On add consonant " + keyCode + " , pinyin not even!");
            return false;
        } else pinyin.append(keyCode);
        if (C.debug)
            print("addConsonant(%s)", keyCode);
        return true;
    }

    /**
     * @param keyCode
     * @param ziLock
     * @return true if successful
     */
    public static boolean addVowel(final String keyCode, final String ziLock) {
        if (pinyin.length() % 2 != 1) {
            if (C.debug)
                System.err.println("Error: On add vowel " + keyCode + " , pinyin not odd!");
            return false;
        } else {
            pinyin.append(keyCode);
            Engine.ziLock.append(ziLock);
            Engine.ziOrig.append(ziLock);
        }
        if (C.debug)
            print("addVowel(%s, %s)", keyCode, ziLock);
        return true;
    }

    public static void delConsonant() {
        int length = pinyin.length();
        if (length % 2 != 1) {
            if (C.debug) System.err.println("Error: On del consonant, pinyin not odd!");
        } else {
            pinyin.delete(length - 1, length);
        }
        if (C.debug)
            print("delConsonant()");
    }

    public static void delCharacter() {
        int length = pinyin.length();
        if (length == 0 || length == 2) clear();
        else if (length % 2 != 0) {
            if (C.debug) System.err.println("Error: On del character, pinyin not even!");
        } else {
            pinyin.delete(length - 2, length);
            int end = ziLock.length();
            ziLock.delete(end - 1, end);
            ziOrig.delete(end - 1, end);
            for (int start = 0; start < end; start++) {
                queryResultD[end - start][start].clear();
                queryResultL[end - start][start].clear();
            }
        }
        if (C.debug)
            print("delCharacter()");
    }

    public static void makeCandidateLeft(final int length) {
        candidateLeft = new LinkedList<>();
        String last = ""; // prevent repeated result
        for (int i = length - 1; i > 0; i--)
            if (queryResultD[i][0] != null)
                for (final String s : queryResultD[i][0])
                    if (!s.equals(last)) {
                        candidateLeft.addLast(s);
                        last = s;
                    }
    }

    public static void makeCandidateRight(final int length) {
        candidateRight = new LinkedList<String>();
        String last = ""; // prevent repeated result
        for (int i = length; i > 0; i--)
            if (queryResultD[i][length - i] != null)
                for (final String s : queryResultD[i][length - i])
                    if (!s.equals(last)) {
                        candidateRight.addLast(s);
                        last = s;
                    }
    }

    public static String pop(int popLength) {
        int originalLength = getLength();
        Pair<String, String> result = new Pair<>(
                sentence.substring(0, popLength), pinyin.substring(0, popLength * 2));
        sentence.delete(0, popLength);
        ziLock.delete(0, popLength);
        ziOrig.delete(0, popLength);
        pinyin.delete(0, popLength * 2);

        int newLength = originalLength - popLength;
        for (int i = 1; i <= newLength; i++)
            for (int j = 0; j <= newLength - i; j++) {
                queryResultD[i][j] = queryResultD[i][j + popLength];
                queryResultD[i][j + popLength] = new LinkedList<>();
                queryResultL[i][j] = queryResultL[i][j + popLength];
                queryResultL[i][j + popLength] = new LinkedList<>();
            }
        for (int i = 1; i <= originalLength; i++)
            for (int j = Math.max(newLength - i + 1, 0); j <= originalLength - i; j++) {
                queryResultD[i][j].clear();
                queryResultL[i][j].clear();
            }

        if (isEmpty()) clear();
        else {
            popLength = getLength();
            makeCandidateLeft(popLength);
            makeCandidateRight(popLength);
            Runnables.makeSeparator();
            Runnables.makeSentence();
        }
        return result.first;
    }

    public static String pop() {
        return pop(separatorAnswer[0]);
    }
}

