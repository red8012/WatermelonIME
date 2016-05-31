package co.watermelonime.Core;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

import co.watermelonime.C;
import co.watermelonime.Common.Timer;

public class Engine {
    public static final int separator[][][] = {
            {{1}},
            {{2}, {1, 1}},
            {{3}, {2, 1}, {1, 2}, {1, 1, 1}},
            {{4}, {2, 2}, {3, 1}, {1, 3}, {1, 2, 1}, {2, 1, 1}, {1, 1, 2}, {1, 1, 1, 1}},
            {{5}, {3, 2}, {2, 3}, {4, 1}, {1, 4}, {2, 2, 1}, {2, 1, 2}, {1, 3, 1}, {1, 2, 2}, {3, 1, 1}, {1, 1, 3}, {1, 2, 1, 1}, {1, 1, 2, 1}, {2, 1, 1, 1}, {1, 1, 1, 2}, {1, 1, 1, 1, 1}},
            {{6}, {4, 2}, {3, 3}, {2, 4}, {5, 1}, {1, 5}, {2, 2, 2}, {3, 2, 1}, {3, 1, 2}, {2, 3, 1}, {2, 1, 3}, {1, 4, 1}, {1, 3, 2}, {1, 2, 3}, {4, 1, 1}, {1, 1, 4}, {2, 1, 2, 1}, {1, 2, 2, 1}, {1, 2, 1, 2}, {2, 2, 1, 1}, {2, 1, 1, 2}, {1, 3, 1, 1}, {1, 1, 3, 1}, {1, 1, 2, 2}, {3, 1, 1, 1}, {1, 1, 1, 3}, {1, 1, 2, 1, 1}, {1, 2, 1, 1, 1}, {1, 1, 1, 2, 1}, {2, 1, 1, 1, 1}, {1, 1, 1, 1, 2}, {1, 1, 1, 1, 1, 1}},
            {{7}, {5, 2}, {4, 3}, {3, 4}, {2, 5}, {6, 1}, {1, 6}, {3, 2, 2}, {2, 3, 2}, {2, 2, 3}, {4, 2, 1}, {4, 1, 2}, {3, 3, 1}, {3, 1, 3}, {2, 4, 1}, {2, 1, 4}, {1, 5, 1}, {1, 4, 2}, {1, 3, 3}, {1, 2, 4}, {5, 1, 1}, {1, 1, 5}, {3, 1, 2, 1}, {2, 2, 2, 1}, {2, 2, 1, 2}, {2, 1, 3, 1}, {2, 1, 2, 2}, {1, 3, 2, 1}, {1, 3, 1, 2}, {1, 2, 3, 1}, {1, 2, 2, 2}, {1, 2, 1, 3}, {3, 2, 1, 1}, {3, 1, 1, 2}, {2, 3, 1, 1}, {2, 1, 1, 3}, {1, 4, 1, 1}, {1, 1, 4, 1}, {1, 1, 3, 2}, {1, 1, 2, 3}, {4, 1, 1, 1}, {1, 1, 1, 4}, {1, 2, 1, 2, 1}, {2, 1, 2, 1, 1}, {2, 1, 1, 2, 1}, {1, 2, 2, 1, 1}, {1, 2, 1, 1, 2}, {1, 1, 3, 1, 1}, {1, 1, 2, 2, 1}, {1, 1, 2, 1, 2}, {2, 2, 1, 1, 1}, {2, 1, 1, 1, 2}, {1, 3, 1, 1, 1}, {1, 1, 1, 3, 1}, {1, 1, 1, 2, 2}, {3, 1, 1, 1, 1}, {1, 1, 1, 1, 3}, {1, 1, 2, 1, 1, 1}, {1, 1, 1, 2, 1, 1}, {1, 2, 1, 1, 1, 1}, {1, 1, 1, 1, 2, 1}, {2, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 2}, {1, 1, 1, 1, 1, 1, 1}},
            {{8}, {6, 2}, {5, 3}, {4, 4}, {3, 5}, {2, 6}, {7, 1}, {1, 7}, {4, 2, 2}, {3, 3, 2}, {3, 2, 3}, {2, 4, 2}, {2, 3, 3}, {2, 2, 4}, {5, 2, 1}, {5, 1, 2}, {4, 3, 1}, {4, 1, 3}, {3, 4, 1}, {3, 1, 4}, {2, 5, 1}, {2, 1, 5}, {1, 6, 1}, {1, 5, 2}, {1, 4, 3}, {1, 3, 4}, {1, 2, 5}, {6, 1, 1}, {1, 1, 6}, {2, 2, 2, 2}, {4, 1, 2, 1}, {3, 2, 2, 1}, {3, 2, 1, 2}, {3, 1, 3, 1}, {3, 1, 2, 2}, {2, 3, 2, 1}, {2, 3, 1, 2}, {2, 2, 3, 1}, {2, 2, 1, 3}, {2, 1, 4, 1}, {2, 1, 3, 2}, {2, 1, 2, 3}, {1, 4, 2, 1}, {1, 4, 1, 2}, {1, 3, 3, 1}, {1, 3, 2, 2}, {1, 3, 1, 3}, {1, 2, 4, 1}, {1, 2, 3, 2}, {1, 2, 2, 3}, {1, 2, 1, 4}, {4, 2, 1, 1}, {4, 1, 1, 2}, {3, 3, 1, 1}, {3, 1, 1, 3}, {2, 4, 1, 1}, {2, 1, 1, 4}, {1, 5, 1, 1}, {1, 1, 5, 1}, {1, 1, 4, 2}, {1, 1, 3, 3}, {1, 1, 2, 4}, {5, 1, 1, 1}, {1, 1, 1, 5}, {2, 2, 1, 2, 1}, {2, 1, 2, 2, 1}, {2, 1, 2, 1, 2}, {1, 3, 1, 2, 1}, {1, 2, 2, 2, 1}, {1, 2, 2, 1, 2}, {1, 2, 1, 3, 1}, {1, 2, 1, 2, 2}, {3, 1, 2, 1, 1}, {3, 1, 1, 2, 1}, {2, 2, 2, 1, 1}, {2, 2, 1, 1, 2}, {2, 1, 3, 1, 1}, {2, 1, 1, 3, 1}, {2, 1, 1, 2, 2}, {1, 3, 2, 1, 1}, {1, 3, 1, 1, 2}, {1, 2, 3, 1, 1}, {1, 2, 1, 1, 3}, {1, 1, 4, 1, 1}, {1, 1, 3, 2, 1}, {1, 1, 3, 1, 2}, {1, 1, 2, 3, 1}, {1, 1, 2, 2, 2}, {1, 1, 2, 1, 3}, {3, 2, 1, 1, 1}, {3, 1, 1, 1, 2}, {2, 3, 1, 1, 1}, {2, 1, 1, 1, 3}, {1, 4, 1, 1, 1}, {1, 1, 1, 4, 1}, {1, 1, 1, 3, 2}, {1, 1, 1, 2, 3}, {4, 1, 1, 1, 1}, {1, 1, 1, 1, 4}, {2, 1, 1, 2, 1, 1}, {1, 2, 1, 2, 1, 1}, {1, 2, 1, 1, 2, 1}, {1, 1, 2, 2, 1, 1}, {1, 1, 2, 1, 2, 1}, {1, 1, 2, 1, 1, 2}, {2, 1, 2, 1, 1, 1}, {2, 1, 1, 1, 2, 1}, {1, 2, 2, 1, 1, 1}, {1, 2, 1, 1, 1, 2}, {1, 1, 3, 1, 1, 1}, {1, 1, 1, 3, 1, 1}, {1, 1, 1, 2, 2, 1}, {1, 1, 1, 2, 1, 2}, {2, 2, 1, 1, 1, 1}, {2, 1, 1, 1, 1, 2}, {1, 3, 1, 1, 1, 1}, {1, 1, 1, 1, 3, 1}, {1, 1, 1, 1, 2, 2}, {3, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 3}, {1, 1, 1, 2, 1, 1, 1}, {1, 1, 2, 1, 1, 1, 1}, {1, 1, 1, 1, 2, 1, 1}, {1, 2, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 2, 1}, {2, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 2}, {1, 1, 1, 1, 1, 1, 1, 1}},
            {{9}, {7, 2}, {6, 3}, {5, 4}, {4, 5}, {3, 6}, {2, 7}, {8, 1}, {1, 8}, {5, 2, 2}, {4, 3, 2}, {4, 2, 3}, {3, 4, 2}, {3, 3, 3}, {3, 2, 4}, {2, 5, 2}, {2, 4, 3}, {2, 3, 4}, {2, 2, 5}, {6, 2, 1}, {6, 1, 2}, {5, 3, 1}, {5, 1, 3}, {4, 4, 1}, {4, 1, 4}, {3, 5, 1}, {3, 1, 5}, {2, 6, 1}, {2, 1, 6}, {1, 7, 1}, {1, 6, 2}, {1, 5, 3}, {1, 4, 4}, {1, 3, 5}, {1, 2, 6}, {7, 1, 1}, {1, 1, 7}, {3, 2, 2, 2}, {2, 3, 2, 2}, {2, 2, 3, 2}, {2, 2, 2, 3}, {5, 1, 2, 1}, {4, 2, 2, 1}, {4, 2, 1, 2}, {4, 1, 3, 1}, {4, 1, 2, 2}, {3, 3, 2, 1}, {3, 3, 1, 2}, {3, 2, 3, 1}, {3, 2, 1, 3}, {3, 1, 4, 1}, {3, 1, 3, 2}, {3, 1, 2, 3}, {2, 4, 2, 1}, {2, 4, 1, 2}, {2, 3, 3, 1}, {2, 3, 1, 3}, {2, 2, 4, 1}, {2, 2, 1, 4}, {2, 1, 5, 1}, {2, 1, 4, 2}, {2, 1, 3, 3}, {2, 1, 2, 4}, {1, 5, 2, 1}, {1, 5, 1, 2}, {1, 4, 3, 1}, {1, 4, 2, 2}, {1, 4, 1, 3}, {1, 3, 4, 1}, {1, 3, 3, 2}, {1, 3, 2, 3}, {1, 3, 1, 4}, {1, 2, 5, 1}, {1, 2, 4, 2}, {1, 2, 3, 3}, {1, 2, 2, 4}, {1, 2, 1, 5}, {5, 2, 1, 1}, {5, 1, 1, 2}, {4, 3, 1, 1}, {4, 1, 1, 3}, {3, 4, 1, 1}, {3, 1, 1, 4}, {2, 5, 1, 1}, {2, 1, 1, 5}, {1, 6, 1, 1}, {1, 1, 6, 1}, {1, 1, 5, 2}, {1, 1, 4, 3}, {1, 1, 3, 4}, {1, 1, 2, 5}, {6, 1, 1, 1}, {1, 1, 1, 6}, {3, 2, 1, 2, 1}, {3, 1, 2, 2, 1}, {3, 1, 2, 1, 2}, {2, 3, 1, 2, 1}, {2, 2, 2, 2, 1}, {2, 2, 2, 1, 2}, {2, 2, 1, 3, 1}, {2, 2, 1, 2, 2}, {2, 1, 3, 2, 1}, {2, 1, 3, 1, 2}, {2, 1, 2, 3, 1}, {2, 1, 2, 2, 2}, {2, 1, 2, 1, 3}, {1, 4, 1, 2, 1}, {1, 3, 2, 2, 1}, {1, 3, 2, 1, 2}, {1, 3, 1, 3, 1}, {1, 3, 1, 2, 2}, {1, 2, 3, 2, 1}, {1, 2, 3, 1, 2}, {1, 2, 2, 3, 1}, {1, 2, 2, 2, 2}, {1, 2, 2, 1, 3}, {1, 2, 1, 4, 1}, {1, 2, 1, 3, 2}, {1, 2, 1, 2, 3}, {4, 1, 2, 1, 1}, {4, 1, 1, 2, 1}, {3, 2, 2, 1, 1}, {3, 2, 1, 1, 2}, {3, 1, 3, 1, 1}, {3, 1, 1, 3, 1}, {3, 1, 1, 2, 2}, {2, 3, 2, 1, 1}, {2, 3, 1, 1, 2}, {2, 2, 3, 1, 1}, {2, 2, 1, 1, 3}, {2, 1, 4, 1, 1}, {2, 1, 1, 4, 1}, {2, 1, 1, 3, 2}, {2, 1, 1, 2, 3}, {1, 4, 2, 1, 1}, {1, 4, 1, 1, 2}, {1, 3, 3, 1, 1}, {1, 3, 1, 1, 3}, {1, 2, 4, 1, 1}, {1, 2, 1, 1, 4}, {1, 1, 5, 1, 1}, {1, 1, 4, 2, 1}, {1, 1, 4, 1, 2}, {1, 1, 3, 3, 1}, {1, 1, 3, 2, 2}, {1, 1, 3, 1, 3}, {1, 1, 2, 4, 1}, {1, 1, 2, 3, 2}, {1, 1, 2, 2, 3}, {1, 1, 2, 1, 4}, {4, 2, 1, 1, 1}, {4, 1, 1, 1, 2}, {3, 3, 1, 1, 1}, {3, 1, 1, 1, 3}, {2, 4, 1, 1, 1}, {2, 1, 1, 1, 4}, {1, 5, 1, 1, 1}, {1, 1, 1, 5, 1}, {1, 1, 1, 4, 2}, {1, 1, 1, 3, 3}, {1, 1, 1, 2, 4}, {5, 1, 1, 1, 1}, {1, 1, 1, 1, 5}, {2, 1, 2, 1, 2, 1}, {1, 2, 2, 1, 2, 1}, {1, 2, 1, 2, 2, 1}, {1, 2, 1, 2, 1, 2}, {3, 1, 1, 2, 1, 1}, {2, 2, 1, 2, 1, 1}, {2, 2, 1, 1, 2, 1}, {2, 1, 2, 2, 1, 1}, {2, 1, 2, 1, 1, 2}, {2, 1, 1, 3, 1, 1}, {2, 1, 1, 2, 2, 1}, {2, 1, 1, 2, 1, 2}, {1, 3, 1, 2, 1, 1}, {1, 3, 1, 1, 2, 1}, {1, 2, 2, 2, 1, 1}, {1, 2, 2, 1, 1, 2}, {1, 2, 1, 3, 1, 1}, {1, 2, 1, 1, 3, 1}, {1, 2, 1, 1, 2, 2}, {1, 1, 3, 2, 1, 1}, {1, 1, 3, 1, 2, 1}, {1, 1, 3, 1, 1, 2}, {1, 1, 2, 3, 1, 1}, {1, 1, 2, 2, 2, 1}, {1, 1, 2, 2, 1, 2}, {1, 1, 2, 1, 3, 1}, {1, 1, 2, 1, 2, 2}, {1, 1, 2, 1, 1, 3}, {3, 1, 2, 1, 1, 1}, {3, 1, 1, 1, 2, 1}, {2, 2, 2, 1, 1, 1}, {2, 2, 1, 1, 1, 2}, {2, 1, 3, 1, 1, 1}, {2, 1, 1, 1, 3, 1}, {2, 1, 1, 1, 2, 2}, {1, 3, 2, 1, 1, 1}, {1, 3, 1, 1, 1, 2}, {1, 2, 3, 1, 1, 1}, {1, 2, 1, 1, 1, 3}, {1, 1, 4, 1, 1, 1}, {1, 1, 1, 4, 1, 1}, {1, 1, 1, 3, 2, 1}, {1, 1, 1, 3, 1, 2}, {1, 1, 1, 2, 3, 1}, {1, 1, 1, 2, 2, 2}, {1, 1, 1, 2, 1, 3}, {3, 2, 1, 1, 1, 1}, {3, 1, 1, 1, 1, 2}, {2, 3, 1, 1, 1, 1}, {2, 1, 1, 1, 1, 3}, {1, 4, 1, 1, 1, 1}, {1, 1, 1, 1, 4, 1}, {1, 1, 1, 1, 3, 2}, {1, 1, 1, 1, 2, 3}, {4, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 4}, {1, 2, 1, 1, 2, 1, 1}, {1, 1, 2, 1, 2, 1, 1}, {1, 1, 2, 1, 1, 2, 1}, {2, 1, 1, 2, 1, 1, 1}, {2, 1, 1, 1, 2, 1, 1}, {1, 2, 1, 2, 1, 1, 1}, {1, 2, 1, 1, 1, 2, 1}, {1, 1, 2, 2, 1, 1, 1}, {1, 1, 2, 1, 1, 1, 2}, {1, 1, 1, 3, 1, 1, 1}, {1, 1, 1, 2, 2, 1, 1}, {1, 1, 1, 2, 1, 2, 1}, {1, 1, 1, 2, 1, 1, 2}, {2, 1, 2, 1, 1, 1, 1}, {2, 1, 1, 1, 1, 2, 1}, {1, 2, 2, 1, 1, 1, 1}, {1, 2, 1, 1, 1, 1, 2}, {1, 1, 3, 1, 1, 1, 1}, {1, 1, 1, 1, 3, 1, 1}, {1, 1, 1, 1, 2, 2, 1}, {1, 1, 1, 1, 2, 1, 2}, {2, 2, 1, 1, 1, 1, 1}, {2, 1, 1, 1, 1, 1, 2}, {1, 3, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 3, 1}, {1, 1, 1, 1, 1, 2, 2}, {3, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 3}, {1, 1, 1, 2, 1, 1, 1, 1}, {1, 1, 1, 1, 2, 1, 1, 1}, {1, 1, 2, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 2, 1, 1}, {1, 2, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 2, 1}, {2, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 2}, {1, 1, 1, 1, 1, 1, 1, 1, 1}}
    };
    final static public StringBuilder
            sql = new StringBuilder(2048),
            pinyin = new StringBuilder(32),
            ziLock = new StringBuilder(16),   // after dict select or direct zi pinyin
            ziOrig = new StringBuilder(16),   // before dict select
            sentence = new StringBuilder(16);
    public final static ArrayList<String> dictResult = new ArrayList<>(16);
    final static ArrayList<StringBuilder>[][] queryResult = new ArrayList[10][]; // [length][start at][i]
    final static ArrayList<StringBuilder> candidateLeft = new ArrayList<>(8);
    final static ArrayList<StringBuilder> candidateRight = new ArrayList<>(16);
    final static String[] qs = {
            "select * from(",
            "select group_concat(c)from(select c from s",
            " where p in(",
            "and c glob'",
            "order by o)union all select group_concat(c)from(select c from s",
            "order by o))",
            "select group_concat(c)from(select distinct c from s"
    };
    final static String[] pq = {
            "select c from(",
            "select c,o from s",
            " where p glob'",
            "'and c glob'",
            "'union all ",
            "')order by o limit 3"
    };
    final static ArrayList<StringBuilder> predictionResults = new ArrayList<>(3);
    final static String[] arg = new String[1];
    static SQLiteDatabase db;
    static Cursor cursor = null;
    static int separatorAnswer[];
    static StringBuilder pSQL = new StringBuilder(2048);

    public static void init() throws Exception {
        // open DB
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
        for (int i = 0; i < 3; i++)
            predictionResults.add(new StringBuilder(9));

        if (db != null && db.isOpen()) return;
        SQLiteDatabase.loadLibs(C.mainService);
        db = SQLiteDatabase.openDatabase(
                C.mainService.getDatabasePath("magician.db").getAbsolutePath(),
                "",
                null,
                SQLiteDatabase.OPEN_READONLY | SQLiteDatabase.NO_LOCALIZED_COLLATORS
        );
        db.setMaxSqlCacheSize(SQLiteDatabase.MAX_SQL_CACHE_SIZE);
        db.execSQL("PRAGMA synchronous = OFF;");
        db.execSQL("PRAGMA temp_store = MEMORY;");
        Cursor c = db.rawQuery("PRAGMA mmap_size=16777216;", null);
        c.moveToNext();
        c.close();
        c = db.rawQuery("PRAGMA journal_mode = OFF;", null);
        c.moveToNext();
        c.close();
        c = db.rawQuery("PRAGMA locking_mode = EXCLUSIVE;", null);
        c.moveToNext();
        c.close();

        Transform.init();
        for (int i = 1; i <= 9; i++)
            queryResult[i] = new ArrayList[9 - i + 1];
        // warm up engine
        final String[]
                p = {"GuLbFaTiKhLnLrWbLu", "E4SuCaH3KjAaMt", "GuLbFaTiKhLnLrWbLu"},
                z = {"你我他????所以", "的是嗎了很吧就", "?????????"};
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < z[j].length(); i++) {
                Engine.pinyin.append(p[j].substring(i + i, i + i + 2));
                Engine.ziLock.append(z[j].substring(i, i + 1));
                Engine.queryDB();
                Engine.readQueryResult();
                Engine.makeSeparator();
                Engine.makeSentence();
            }
            Engine.clear();
        }
    }

    public static void clear() {
        pinyin.setLength(0);
        ziLock.setLength(0);
        ziOrig.setLength(0);
        sentence.setLength(0);
        for (int i = 1; i <= 9; i++)
            for (int j = 0; j < 9 - i + 1; j++) {
                BufferedSplitter.releaseArrayList(queryResult[i][j]);
                queryResult[i][j] = null;
            }
        candidateLeft.clear();
        candidateRight.clear();
    }

    public static boolean isEmpty() {
        return getLength() == 0;
    }

    public static int getLength() {
        return pinyin.length() / 2;
    }

    public static String getSentence() {
        if (sentence.length() == 0) return "";
        return sentence.toString();
    }

    public static void add(final char keyCode) {
        pinyin.append(keyCode);
//        System.out.println("Engine add" + pinyin);
    }

    public static void add(final char keyCode, final char characterLock) {
        pinyin.append(keyCode);
        ziLock.append(characterLock);
        ziOrig.append(characterLock);
//        System.out.println("Engine add" + pinyin);
    }

    public static void setZiLock(final int index, final char zi) {
        ziLock.setCharAt(index, zi);
        sentence.setCharAt(index, zi);
    }

    /**
     * STEP 1: select database
     * <p>
     * select * from (
     * select group_concat(c) from (
     * select c from s1
     * where p in ('AaAa')
     * and c glob '??'
     * )
     * union all
     * select group_concat(c) from (
     * select c from s1
     * where p in ('Aa')
     * and c glob '?'
     * )
     * )
     * <p>
     * select * from(select group_concat(c)from(select distinct c from s1 where p in('Qp')and c glob'?'order by o))
     * select * from(select group_concat(c)from(select distinct c from s1 where p in('Ln')and c glob'?'order by o)union all select group_concat(c)from(select c from s2 where p in('QpLn')and c glob'??'order by o))
     */
    public static void queryDB() {
        sql.setLength(0);
        final int length = getLength(), pinyinLength = length + length;

        sql.append(qs[0]); // select * from(
        sql.append(qs[6]); // select group_concat(c)from(select distinct c from s
        sql.append('1');
        sql.append(qs[2]); //  where p in(
        Transform.expandQuery(pinyin, pinyinLength - 2);
        sql.append(')');
        if (needGlobbing(length - 1, length)) {
            sql.append(qs[3]); // and c glob'
            sql.append(ziLock, length - 1, length);
            sql.append('\'');
        }

        for (int i = length - 2; i >= 0; i--) {
            sql.append(qs[4]); // order by o)union all select group_concat(c)from(select c from s
            sql.append(length - i);
            sql.append(qs[2]); //  where p in(
            Transform.expandQuery(pinyin, i + i);
            sql.append(')');
            if (needGlobbing(i, length)) {
                sql.append(qs[3]); // and c glob'
                sql.append(ziLock, i, length);
                sql.append('\'');
            }
        }
        sql.append(qs[5]);
//        System.out.println(sql);
//        System.out.println(pinyin);
//        System.out.println(ziLock);
        cursor = db.rawQuery(sql.toString(), null);
    }

    static boolean needGlobbing(int start, int end) {
        for (int i = start; i < end; i++)
            if (ziLock.charAt(i) != '?') return true;
        return false;
    }

    public static void queryDict(final int index) {
        arg[0] = pinyin.substring(index * 2, index * 2 + 2);
        cursor = db.rawQuery("select z, c from d where p=? order by o", arg);
        dictResult.clear();
        while (cursor.moveToNext()) {
            dictResult.add(cursor.getString(0));
            dictResult.add(cursor.getString(1));
        }
        cursor.close();
    }

    public static void readQueryResult() {
        int i = 1, end = getLength();
        while (cursor.moveToNext()) {
            String result = cursor.getString(0); // 0 based
//            System.out.println("STRING: " + result);

            ArrayList<StringBuilder> list = queryResult[i][end - i];
            BufferedSplitter.releaseArrayList(list);
            queryResult[i][end - i] = result == null ? null : BufferedSplitter.split(result);

//            if (result != null) {
//                for (CharSequence s : queryResult[i][end - i])
//                    System.out.print(s + "/");
//                System.out.println("#");
//            }

            i++;
        }
        cursor.close();
    }

    public static void makeSeparator() throws Exception {
        boolean found;
        int counter;
        final int length = getLength();
        try {
            for (int[] a : separator[length - 1]) {
                found = true;
                counter = 0;
                for (int b : a) {
                    if (queryResult[b][counter] == null) {
                        found = false;
                        break;
                    }
                    counter += b;
                }
                if (found) {
                    separatorAnswer = a;
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new Exception("No matched separator");
    }

    public static void makeSentence() {
        int counter = 0;
        sentence.setLength(0);
        for (int i : separatorAnswer) {
//            sentence.append(queryResult[i][counter][0]);
            sentence.append(queryResult[i][counter].get(0));
            counter += i;
        }
        // force apply character lock
        for (int i = 0; i < getLength(); i++) {
            char c = ziLock.charAt(i);
            if (c != '?')
                sentence.setCharAt(i, c);
        }
    }

    public static void makeCandidateLeft() {
        candidateLeft.clear();
        for (int i = getLength() - 1; i > 0; i--)
            if (queryResult[i][0] != null) {
                ArrayList<StringBuilder> list = queryResult[i][0];
                int end = list.size();
                for (int j = 0; j < end; j++)
                    candidateLeft.add(list.get(j));
            }
    }

    public static void makeCandidateRight() {
        int length = getLength();
        candidateRight.clear();
        for (int i = getLength(); i > 0; i--)
            if (queryResult[i][length - i] != null) {
                ArrayList<StringBuilder> list = queryResult[i][length - i];
                int end = list.size();
                for (int j = 0; j < end; j++)
                    candidateRight.add(list.get(j));
            }
    }

    public static String pop(final int popLength) {
        int originalLength = getLength();
        if (popLength >= originalLength) {
            String result = sentence.toString();
            clear();
            return result;
        }
        String result = sentence.substring(0, popLength);
        sentence.delete(0, popLength);
        ziLock.delete(0, popLength);
        ziOrig.delete(0, popLength);
        pinyin.delete(0, popLength * 2);

        int newLength = originalLength - popLength;
        for (int i = 1; i <= newLength; i++)
            for (int j = 0; j <= newLength - i; j++) {
                BufferedSplitter.releaseArrayList(queryResult[i][j]);
                queryResult[i][j] = queryResult[i][j + popLength];
                queryResult[i][j + popLength] = null;
            }
        for (int i = 1; i <= originalLength; i++)
            for (int j = Math.max(newLength - i + 1, 0); j <= originalLength - i; j++) {
                BufferedSplitter.releaseArrayList(queryResult[i][j]);
                queryResult[i][j] = null;
            }

        makeCandidateLeft();
        makeCandidateRight();
        try {
            makeSeparator();
        } catch (Exception e) {
            e.printStackTrace();
            Engine.clear();
            C.sentenceView.display();
            Controller.displayCandidates();
            return result;
        }
        makeSentence();

        return result;
    }

    public static String pop() {
        return pop(separatorAnswer[0]);
    }

    public static void delCharacter() { // TODO: 2016/3/1 check! moved from old Engine
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
                BufferedSplitter.releaseArrayList(queryResult[end - start][start]);
                queryResult[end - start][start] = null;
            }
        }
    }

    public static void delConsonant() {
        int length = pinyin.length();
        if (length % 2 != 1) {
            if (C.debug) System.err.println("Error: On del consonant, pinyin not odd!");
        } else {
            pinyin.delete(length - 1, length);
        }
    }

    public static void queryPrediction() {
        if (pinyin.length() < 3) return;
        Timer.t(847);
        int resultCount = 0;
        pSQL.setLength(0);
        for (int i = 0; i < 3; i++)
            predictionResults.get(i).setLength(0);

        int len = pinyin.length();
        for (int start = 0; start < len; start += 2) {
            pSQL.setLength(0);
            for (int end = len + 1; end <= 18; end += 2) {
                if (end == len + 1) pSQL.append(pq[0]);
                else pSQL.append(pq[4]); // ' union all
                pSQL.append(pq[1]); // select c, o from s
                pSQL.append((end - start) / 2);
                pSQL.append(pq[2]); //  where p glob '
                pSQL.append(pinyin, start, len);
                for (int i = len; i < end; i++)
                    pSQL.append('?');
                pSQL.append(pq[3]); // ' and c glob '
                pSQL.append(ziOrig, start / 2, len / 2);
                for (int i = len; i < end; i += 2)
                    pSQL.append('?');
            }
            pSQL.append(pq[5]);

//            System.out.println(pSQL);
            Cursor pCursor = db.rawQuery(pSQL.toString(), null);
            boolean shouldBreak = false;
            while (pCursor.moveToNext()) {
                predictionResults.get(resultCount++).append(pCursor.getString(0));
                if (resultCount >= 3) {
                    shouldBreak = true;
                    break;
                }
            }
            if (shouldBreak) break;
        }
        Timer.t(847, "Query prediction");

        for (int i = 0; i < 3; i++)
            System.out.print(" " + predictionResults.get(i));
        System.out.println();
    }
}

