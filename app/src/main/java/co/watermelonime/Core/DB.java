package co.watermelonime.Core;

import net.sqlcipher.database.SQLiteDatabase;

import co.watermelonime.C;

public class DB {
    final static String[] tableNames = {"concerto", "solo", "duet", "trio", "quartet"};
    public static SQLiteDatabase dictionary, learn;

    public static void init() {
        SQLiteDatabase.loadLibs(C.mainService);

        dictionary = SQLiteDatabase.openDatabase(
                C.mainService.getDatabasePath("encrypted.db3").getAbsolutePath(),
                "Dinis Cottage".toCharArray(),
                null,
                SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.NO_LOCALIZED_COLLATORS
//                SQLiteDatabase.OPEN_READONLY|SQLiteDatabase.NO_LOCALIZED_COLLATORS
        );

//        should remove
//        Database.connect();
        Database.dictionary = dictionary;


//        dictionary.setMaxSqlCacheSize(SQLiteDatabase.MAX_SQL_CACHE_SIZE);
//        dictionary.execSQL("PRAGMA synchronous = OFF;");
//        dictionary.execSQL("PRAGMA temp_store = MEMORY;");
//        Cursor c = dictionary.rawQuery("PRAGMA journal_mode = OFF;", null);
//        c.moveToNext();
//        c.close();
//        c = dictionary.rawQuery("PRAGMA locking_mode = EXCLUSIVE;", null);
//        c.moveToNext();
//        c.close();
    }


}
