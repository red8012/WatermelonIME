package co.watermelonime.Core;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import co.watermelonime.C;

public class DB {
    final static String[] tableNames = {"concerto", "solo", "duet", "trio", "quartet"};
    public static SQLiteDatabase dictionary, learn;

    public static void init() {
        SQLiteDatabase.loadLibs(C.mainService);

//        dictionary = SQLiteDatabase.openDatabase(
//                C.mainService.getDatabasePath("encrypted.db3").getAbsolutePath(),
//                "Dinis Cottage".toCharArray(),
//                null,
//                SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.NO_LOCALIZED_COLLATORS
////                SQLiteDatabase.OPEN_READONLY|SQLiteDatabase.NO_LOCALIZED_COLLATORS
//        );

        dictionary = SQLiteDatabase.openDatabase(
                C.mainService.getDatabasePath("db.db3").getAbsolutePath(),
                "",
                null,
                SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.NO_LOCALIZED_COLLATORS
//                SQLiteDatabase.OPEN_READONLY|SQLiteDatabase.NO_LOCALIZED_COLLATORS
        );


        Database.dictionary = dictionary;


        dictionary.setMaxSqlCacheSize(SQLiteDatabase.MAX_SQL_CACHE_SIZE);
//        dictionary.execSQL("PRAGMA synchronous = OFF;");
        dictionary.execSQL("PRAGMA temp_store = MEMORY;");
//        dictionary.execSQL("PRAGMA mmap_size=68435456;");
        Cursor c = dictionary.rawQuery("PRAGMA journal_mode = OFF;", null);
        c.moveToNext();
        c.close();

//        c = dictionary.rawQuery("PRAGMA mmap_size=64000000;", null);
//        c.moveToNext();
//        c.close();
//
        c = dictionary.rawQuery("PRAGMA automatic_index;", null);
        c.moveToNext();
        System.out.println("automatic_index = " + c.getInt(0));
        c.close();

        c = dictionary.rawQuery("PRAGMA locking_mode = EXCLUSIVE;", null);
        c.moveToNext();
        c.close();

        final String s1 = "select length(zi), zi, priority from ",
                s2 = " where ",
                s3 = " and zi glob '",
                s4 = "' union all \n",
                s5 = "' order by length(zi) desc, priority desc;";

        c = Database.query(s1 + "solo" + s2 + "pinyin glob 'Qp' and zi glob '?' order by length(zi) desc, priority desc;");
        System.out.println(c.moveToNext());
        System.out.println(c.getString(1));
        c.close();
        c = Database.query(s1 + "solo" + s2 + "pinyin glob 'E[cu]' and zi glob '的' order by length(zi) desc, priority desc;");

        c.moveToNext();
        c.getString(1);
        c.close();
        System.out.println("warmed up");
    }


}
