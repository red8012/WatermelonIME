package co.watermelonime.Core;

import android.database.Cursor;
import android.provider.ContactsContract;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteStatement;

import java.util.ArrayList;
import java.util.HashSet;

import co.watermelonime.C;

public class ContactsLearner {
    final static ArrayList<String> pinyins = new ArrayList<>(8);
    static final String[][] data = new String[9][]; // stores possible pinyin codes at each position
    static final int[] lengths = new int[9]; // stores number of possible pinyin expansion at that position
    static final int[] indices = new int[9]; // stores current index that represents the combination
    static int end;
    static SQLiteDatabase db;
    static SQLiteStatement sqlZi2Pin;

    public static void start() {
        db = Engine.db;
        sqlZi2Pin = db.compileStatement("select p from c2p where c=?");
        try {
            db.beginTransaction();
            for (String word : getContacts()) {
                if (word.length() == 3) {
                    insertWord(word.substring(1));
                }
                insertWord(word);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public static void insertWord(String word) {
        try {
//            System.out.print("\n" + word + " | ");
            getPinyin(word);
            if (pinyins == null) return;

            for (String pinyin : pinyins) {
                SQLiteStatement sql = Learner.queryCurrentOrder[word.length()];
                sql.bindString(1, pinyin);
                sql.bindString(2, word);
                long order = Learner.NOT_EXISTED;
                try {
                    order = sql.simpleQueryForLong();
                } catch (Exception e) {
                }
                if (order != Learner.NOT_EXISTED) continue;

                sql = Learner.queryMinOrder[word.length()];
                sql.bindString(1, pinyin);
                order = sql.simpleQueryForLong();
                if (order < 0 || order % 2 != 0) order = 2;
                else order += 2;

                sql = Learner.insert[word.length()];
                sql.bindString(1, pinyin);
                sql.bindString(2, word);
                sql.bindLong(3, order);
                sql.executeInsert();
//                System.out.print(pinyin + ",");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static HashSet<String> getContacts() {
        Cursor contacts;
        HashSet<String> result = new HashSet<>(1024);
        contacts = C.mainService.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        contacts.moveToFirst();
        while (contacts.moveToNext())
            result.add(contacts.getString(
                    contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            ).trim());
        contacts.close();
        return result;
    }

    static ArrayList<String> getPinyin(String word) {
        pinyins.clear();
        int len = word.length();
        if (len > 9) return null;
        end = len - 1;
        for (int i = 0; i < len; i++) {
            String[] pinyin = zi2pin(word.substring(i, i + 1));
            if (pinyin == null) return null;
            data[i] = pinyin;
            lengths[i] = pinyin.length;
            indices[i] = 0;
        }
        indices[end] = -1; // move pointer before head
        while (getNextCartesianProduct()) {
            StringBuilder sb = new StringBuilder(18);
            for (int i = 0; i <= end; i++)
                sb.append(data[i][indices[i]]);
            pinyins.add(sb.toString());
        }
        return pinyins;
    }

    static String[] zi2pin(String c) {
        sqlZi2Pin.bindString(1, c);
        String result;
        try {
            result = sqlZi2Pin.simpleQueryForString();
            return result.split(",");
        } catch (Exception e) {
            return null;
        }
    }

    static boolean getNextCartesianProduct() {
        indices[end]++;
        for (int i = end; i > 0; i--) {
            if (indices[i] >= lengths[i]) {
                indices[i] = 0;
                indices[i - 1]++;
            }
        }
        return indices[0] < lengths[0];
    }
}
