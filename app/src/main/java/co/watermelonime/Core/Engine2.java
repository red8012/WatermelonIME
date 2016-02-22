package co.watermelonime.Core;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;

import co.watermelonime.C;

public class Engine2 {
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
	final static StringBuilder
			sql = new StringBuilder(2048),
			pinyin = new StringBuilder(32),
			ziLock = new StringBuilder(32),   // after dict select or direct zi pinyin
			ziOrig = new StringBuilder(32),   // before dict select
			sentence = new StringBuilder(32);
	final static String[][][] queryResult = new String[10][][]; // [length][start at][i]
	final static ArrayList<String> candidateLeft = new ArrayList<>(8);
	final static ArrayList<String> candidateRight = new ArrayList<>(8);
	final static String[] qs = {
			"select * from (",
			"select group_concat(c) from (select c from s",
			" where p in (",
			") and c glob '",
			"' order by o) union all select group_concat(c) from (select c from s", //	"') union all ",
			"' order by o))"
	};
	static SQLiteDatabase dictionary;
	static Cursor cursor = null;
	static int separatorAnswer[];

	public static void init() throws Exception {
		// open DB
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
        SQLiteDatabase.loadLibs(C.mainService);
		dictionary = SQLiteDatabase.openDatabase(
				C.mainService.getDatabasePath("v2.db").getAbsolutePath(),
				"",
				null,
                SQLiteDatabase.OPEN_READONLY|SQLiteDatabase.NO_LOCALIZED_COLLATORS
		);
		dictionary.setMaxSqlCacheSize(SQLiteDatabase.MAX_SQL_CACHE_SIZE);
        dictionary.execSQL("PRAGMA synchronous = OFF;");
		dictionary.execSQL("PRAGMA temp_store = MEMORY;");
        dictionary.execSQL("PRAGMA mmap_size=16435456;");
		Cursor c = dictionary.rawQuery("PRAGMA journal_mode = OFF;", null);
		c.moveToNext();
		c.close();
		c = dictionary.rawQuery("PRAGMA locking_mode = EXCLUSIVE;", null);
		c.moveToNext();
		c.close();

		for (int i = 1; i <= 9; i++)
			queryResult[i] = new String[9 - i + 1][];
		// warm up engine
		final String[]
				p = {"GuLbFaTiKhLnLrWbLu", "E1SuCaH8Kj"},
				z = {"你?他????所?", "的是嗎了很"};
		for (int j = 0; j < 1; j++) {
			for (int i = 0; i < 2; i++) {
				Engine2.pinyin.append(p[j].substring(i + i, i + i + 2));
				Engine2.ziLock.append(z[j].substring(i, i + 1));
				Engine2.queryDB();
				Engine2.readQueryResult();
				Engine2.makeSeparator();
				Engine2.makeSentence();
			}
			Engine2.clear();
		}
	}

	public static void clear() {
		pinyin.setLength(0);
		ziLock.setLength(0);
		ziOrig.setLength(0);
		sentence.setLength(0);
		for (int i = 1; i <= 9; i++)
			for (int j = 0; j < 9 - i + 1; j++) {
				queryResult[i][j] = null;
			}
		candidateLeft.clear();
		candidateRight.clear();
	}

	public static int getLength() {
        return pinyin.length() / 2;
	}

	public static void add(final char keyCode) {
		pinyin.append(keyCode);
	}

	public static void add(final char keyCode, final char characterLock) {
		pinyin.append(keyCode);
		ziLock.append(characterLock);
		ziOrig.append(characterLock);
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
	 *
	 */
	public static void queryDB() {
		sql.setLength(0);
		final int length = getLength(), pinyinLength = length + length;

		sql.append(qs[0]);
		sql.append(qs[1]);
		sql.append('1');
		sql.append(qs[2]);
		Transform2.expandQuery2(pinyin.substring(pinyinLength - 2, pinyinLength));
		sql.append(qs[3]);
		sql.append(ziLock, length - 1, length);

		for (int i = length - 2; i >= 0; i--) {
			sql.append(qs[4]);
			sql.append(length - i);
			sql.append(qs[2]);
			Transform2.expandQuery2(pinyin.substring(i + i, pinyinLength));
			sql.append(qs[3]);
			sql.append(ziLock, i, length);
		}
		sql.append(qs[5]);

//		System.out.println(sql);
		cursor = dictionary.rawQuery(sql.toString(), null);
	}

	public static void readQueryResult() {
		int i = 1, end = getLength();
		while (cursor.moveToNext()) {
			String result = cursor.getString(1);
//			System.out.println(result);
			queryResult[i][end - i] = result == null ? null : result.split(",");
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
			sentence.append(queryResult[i][counter][0]);
			counter += i;
		}
	}

	public static void makeCandidateLeft(final int length) {
		candidateLeft.clear();
		for (int i = length - 1; i > 0; i--)
			if (queryResult[i][0] != null)
                Collections.addAll(candidateLeft, queryResult[i][0]);
	}

	public static void makeCandidateRight(final int length) {
		candidateRight.clear();
		for (int i = length; i > 0; i--)
			if (queryResult[i][length - i] != null)
                Collections.addAll(candidateRight, queryResult[i][length - i]);
	}

	public static String pop(final int popLength) throws Exception {
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
				queryResult[i][j] = queryResult[i][j + popLength];
				queryResult[i][j + popLength] = null;
			}
		for (int i = 1; i <= originalLength; i++)
			for (int j = Math.max(newLength - i + 1, 0); j <= originalLength - i; j++) {
				queryResult[i][j] = null;
			}

		final int len = getLength();
		makeCandidateLeft(len);
		makeCandidateRight(len);
		makeSeparator();
		makeSentence();

		return result;
	}

	public static String pop() throws Exception {
		return pop(separatorAnswer[0]);
	}
}