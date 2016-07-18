package co.watermelonime.Core;

import com.orhanobut.logger.Logger;

import net.sqlcipher.database.SQLiteStatement;

public class EnglishPredictor {
    public static StringBuilder completionBuffer = new StringBuilder(32);
    static String[] predictions, defaultPredictions = {"the", "I", "a"};
    static SQLiteStatement query;

    public static void init() {
//        for (int i = 0; i < 3; i++) {
//            predictions[i] = new StringBuilder(16);
//        }
        query = Engine.db.compileStatement(
                "select group_concat(w)from(select w from eng where w glob ? order by o limit 3)");
    }

    public static String[] getPredictions() {
//        for (int i = 0; i < 3; i++) {
//            predictions[i].setLength(0);
//        }
//        predictions[0].append(completionBuffer);
        if (completionBuffer.length() == 0) {
            predictions = defaultPredictions;
            return predictions;
        }
        query.bindString(1, completionBuffer + "*");
        String results = query.simpleQueryForString();
        predictions = results.split(",");
        Logger.d("%s", results);
        return predictions;
    }
}
