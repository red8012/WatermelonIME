package co.watermelonime.Core;

import android.os.Process;

import com.orhanobut.logger.Logger;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteStatement;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Learner {
    final public static StringBuilder
            pinyinBuffer = new StringBuilder(18),
            wordBuffer = new StringBuilder(18),
            transformBuffer = new StringBuilder(100);
    final static SQLiteStatement[]
            queryCurrentOrder = new SQLiteStatement[10],
            queryMinOrder = new SQLiteStatement[10],
            update = new SQLiteStatement[10],
            updatePlusTwo = new SQLiteStatement[10],
            insert = new SQLiteStatement[10];
    final static long NOT_EXISTED = Long.MAX_VALUE;
    static ExecutorService thread = Executors.newSingleThreadExecutor();
    static SQLiteDatabase db;

    public static void init() {
        thread.submit(() -> Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND));
        db = Engine.db;
        StringBuilder sb = new StringBuilder(100);
        for (int i = 1; i < 10; i++) {
            sb.setLength(0);
            sb.append("select o from s");
            sb.append(i);
            sb.append(" where p glob ? and c=? order by o limit 1");
            queryCurrentOrder[i] = db.compileStatement(sb.toString());

            sb.setLength(0);
            sb.append("select o from s");
            sb.append(i);
            sb.append(" where p glob ? order by o limit 1");
            queryMinOrder[i] = db.compileStatement(sb.toString());

            sb.setLength(0);
            sb.append("update s");
            sb.append(i);
            sb.append(" set o=? where p glob ? and c=?");
            update[i] = db.compileStatement(sb.toString());

            sb.setLength(0);
            sb.append("update s");
            sb.append(i);
            sb.append(" set o=o+2 where p=? and o>999");
            updatePlusTwo[i] = db.compileStatement(sb.toString());

            sb.setLength(0);
            sb.append("insert into s");
            sb.append(i);
            sb.append(" values(?,?,?)");
            insert[i] = db.compileStatement(sb.toString());
        }
    }

    public static void clear() {
        wordBuffer.setLength(0);
        pinyinBuffer.setLength(0);
        transformBuffer.setLength(0);
    }

    public static void learn(CharSequence word, int start, int end) {

    }

    public static void learnFromBuffer() {
        if (wordBuffer.length() == 0) return;
        Logger.d("learn from buffer: " + wordBuffer);
        learnWord(wordBuffer.toString(), pinyinBuffer);
        clear();
    }

    /**
     * @param word
     * @param pinyin
     * @return true if the word is already in database
     */
    public static boolean learnWord(String word, CharSequence pinyin) {
        String pinyinString = Transform.toGlob(pinyin, 0);
        System.out.println("learnWord: " + word + pinyinString);
        if (word.length() > 9) {
            System.out.println("word over-length");
            return false;
        }
        int len = word.length();
        db.beginTransaction();
        // check if word & pinyin exist
        SQLiteStatement sql = queryCurrentOrder[len];
        sql.bindString(1, pinyinString);
        sql.bindString(2, word);

        long currentOrder = NOT_EXISTED;
        try {
            currentOrder = sql.simpleQueryForLong();
        } catch (Exception e) {
        }
        System.out.println("current order: " + currentOrder);

        if (currentOrder == NOT_EXISTED) {
            System.out.println("newOrder: " + 1001);
            sql = insert[len];
            if (Transform.needTransform(pinyin)) {
                transformBuffer.setLength(0);
                Transform.expandQuery(pinyin, 0, transformBuffer);
                String[] pinyins = transformBuffer.toString().replaceAll("'", "").split(",");
                System.err.print("transformed Pinyins: ");
                for (String i : pinyins) System.out.print(i + "|");
                System.err.println();

                sql.bindString(2, word);
                sql.bindLong(3, 1001);
                for (String i : pinyins) {
                    makeRoomForNewWord(i, len);
                    sql.bindString(1, i);
                    long modifiedRowCount = sql.executeInsert();
                    System.out.println("modifiedRowCount: " + modifiedRowCount);
                }
            } else {
                makeRoomForNewWord(pinyinString, len);
                sql.bindString(1, pinyinString); // should expand pinyin
                sql.bindString(2, word);
                sql.bindLong(3, 1001);
                long modifiedRowCount = sql.executeInsert();
                System.out.println("modifiedRowCount: " + modifiedRowCount);
            }

        } else { // if exist, update order
            sql = queryMinOrder[len];
            sql.bindString(1, pinyinString);
            long minOrder = sql.simpleQueryForLong();
            System.out.println("min order: " + minOrder);
            // don't do this, there can be different word with same priority and same prefix

            long newOrder = -2 + currentOrder % 2;
            if (currentOrder > 1000) newOrder = minOrder / 2 * 2 - 2 + currentOrder % 2;
            else if (currentOrder < 0) newOrder = minOrder / 2 * 2 - 2 + currentOrder % 2;

            System.out.println("newOrder: " + newOrder);
            sql = update[len];
            sql.bindLong(1, newOrder);
            sql.bindString(2, pinyinString);
            sql.bindString(3, word);
            int modifiedRowCount = sql.executeUpdateDelete();
            System.out.println("modifiedRowCount: " + modifiedRowCount);
        }
        db.endTransaction();
        return currentOrder != NOT_EXISTED;
    }

    public static int makeRoomForNewWord(String pinyin, int len) {
        SQLiteStatement sql = updatePlusTwo[len];
        sql.bindString(1, pinyin);
        return sql.executeUpdateDelete();
    }
}
