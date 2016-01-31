package co.watermelonime.Core;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import co.watermelonime.C;

/**
 * Created by ray on 2016/1/9.
 */
public class Database {
//    final static String[] tableNames = {"m.concerto", "m.solo", "m.duet", "m.trio", "m.quartet"};
    final static String[] tableNames = {"concerto", "solo", "duet", "trio", "quartet"};
    final static String s1 = "select length(zi), zi, priority from ",
            s2 = " where ",
            s3 = " and zi glob '",
            s4 = "' union all \n",
            s5 = "' order by length(zi) desc, priority desc;";
    public static SQLiteDatabase dictionary;

    public static void connect() {
        SQLiteDatabase.loadLibs(C.mainService);
        String key = "Dinis Cottage";
        dictionary = SQLiteDatabase.openDatabase(
                C.mainService.getDatabasePath("encrypted.db3").getAbsolutePath(),
                key, null, SQLiteDatabase.OPEN_READWRITE
        );
        dictionary.setMaxSqlCacheSize(SQLiteDatabase.MAX_SQL_CACHE_SIZE);
        dictionary.execSQL("PRAGMA synchronous = OFF;");
        dictionary.execSQL("PRAGMA temp_store = MEMORY;");
        Cursor c = dictionary.rawQuery("PRAGMA journal_mode = OFF;", null);
        c.moveToNext();
        c.close();
        c = dictionary.rawQuery("PRAGMA locking_mode = EXCLUSIVE;", null);
        c.moveToNext();
        c.close();

//        learn = SQLiteDatabase.openDatabase(
//                C.mainService.getDatabasePath("learning.db3").getAbsolutePath(),
//                key, null, 0
//        );
//        learn.setMaxSqlCacheSize(SQLiteDatabase.MAX_SQL_CACHE_SIZE);
        Engine.thread1.execute(Runnables.setThreadPriority);
        Engine.thread2.execute(Runnables.setThreadPriority);
    }

    public static boolean isConnected() {
//        if (dictionary == null || learn == null) return false;
//        if (dictionary.isOpen() && learn.isOpen()) return true;
        if (dictionary == null) return false;
        return dictionary.isOpen();
    }

//    public static Cursor queryLearn(String statement, String... selection) {
//        return learn.rawQuery(statement, selection);
//    }

    public static Cursor query(final String statement) {
//		Cursor explain = dictionary.rawQuery("explain query plan " + statement, null);
//		while (explain.moveToNext()) {
//			for (int i = 0; i < explain.getColumnCount(); i++)
//				System.err.print(explain.getString(i) + "|");
//			System.err.println();
//		}
//		explain.close();
        return dictionary.rawQuery(statement, null);
    }

    public static Cursor query(final String pinyin, final String ziLock, final boolean limit) {
        String limiter = "";
        int length = ziLock.length();
        if (limit)
            if (length > 2) limiter = "limit 3";
            else if (length == 2) limiter = "limit 7";
            else limiter = "limit 14";
        String table = length > 4 ? tableNames[0] : tableNames[length];
        Cursor c = query(String.format(
                "select zi, priority from %s where %s and zi glob '%s' order by priority desc %s",
                table, Transform.makeGlob(Transform.replaceVowel(pinyin)), ziLock, limiter));
        return c;
    }

    public static Cursor query(String pinyin, String ziLock) {
        final int length = Engine.getLength(), pinyinLength = pinyin.length();
        if (C.debug && pinyinLength % 2 != 0) Engine.print("Bad query1");
        final StringBuilder builder = new StringBuilder(2048);

        String table = length > 4 ? tableNames[0] : tableNames[length];
        builder.append(s1).append(table).append(s2)
                .append(Transform.makeGlob(Transform.replaceVowel(pinyin.toString())))
                .append(s3).append(ziLock);

        for (int i = 1; i < length; i++) {
            final int l = length - i;
            table = l > 4 ? tableNames[0] : tableNames[l];
            builder.append(s4).append(s1).append(table).append(s2)
                    .append(Transform.makeGlob(Transform.replaceVowel(
                            pinyin.substring(i + i, pinyinLength))))
                    .append(s3).append(ziLock.substring(i, length));
        }
        builder.append(s5);
        final String queryString = builder.toString();

//		System.out.println(queryString);
        return query(queryString);
    }
}
