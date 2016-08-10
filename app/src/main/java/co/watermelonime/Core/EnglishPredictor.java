package co.watermelonime.Core;

import com.orhanobut.logger.Logger;

import net.sqlcipher.database.SQLiteStatement;

import co.watermelonime.InputView.English.CandidateBar;
import co.watermelonime.InputView.English.EnglishKeyboard;

public class EnglishPredictor {
    public static StringBuilder
            completionBuffer = new StringBuilder(32),
            resultBuffer = new StringBuilder(64);
    static String[] predictions, empty = {},
            defaultPredictions = {"the", "I", "a"},
            defaultPredictionsUpper = {"The", "I", "A"},
            defaultPredictionsCaps = {"THE", "I", "A"};
    static SQLiteStatement query, queryNext, update, insert;

    public static void init() {
        if (query != null) return;
        query = Engine.db.compileStatement(
                "select group_concat(w)from(select w from eng where w glob ? order by o limit 3)"
        );
        queryNext = Engine.db.compileStatement(
                "select group_concat(b)from(select b from eng where w=? order by o limit 3)"
        );
        update = Engine.db.compileStatement(
                "update eng set o=(select o from eng where w='=')-1 where w in('=',?)"
        );
        insert = Engine.db.compileStatement(
                "insert into eng values(?,(select o from eng where w='='),NULL)"
        );
    }

    public static void setDefaultPredictions() {
        switch (EnglishKeyboard.mode) {
            case EnglishKeyboard.LOWER:
                predictions = defaultPredictions;
                break;
            case EnglishKeyboard.UPPER:
                predictions = defaultPredictionsUpper;
                break;
            case EnglishKeyboard.CAPSLOCK:
                predictions = defaultPredictionsCaps;
                break;
            default:
                predictions = null;
        }
    }

    public static String[] getPredictions() {
        String s = completionBuffer.toString();
        if (s.length() == 0) {
            setDefaultPredictions();
            return predictions;
        }
        query.bindString(1, s.toLowerCase() + "*");

        String results = query.simpleQueryForString();
        if (results == null) {
            predictions = null;
            return predictions;
        }
        predictions = results.split(",");
        for (int i = 0; i < predictions.length; i++) {
            predictions[i] = toProperCase(predictions[i]);
        }
        Logger.d("%s", results);
        return predictions;
    }

    public static String[] getNextPredictions(String s) {
        if (s.length() == 0) {
            setDefaultPredictions();
            return predictions;
        }
        queryNext.bindString(1, s.toLowerCase());
        String results = queryNext.simpleQueryForString();
        if (results == null) {
            setDefaultPredictions();
        } else
            predictions = results.split(",");
        for (int i = 0; i < predictions.length; i++) {
            predictions[i] = toProperCase(predictions[i]);
        }
        Logger.d("Next %s", results);
        return predictions;
    }

    public static String toProperCase(String s) {
        resultBuffer.setLength(0);
        resultBuffer.append(s);
        for (int i = 0; i < completionBuffer.length(); i++) {
            char c = completionBuffer.charAt(i);
            if (Character.isUpperCase(c))
                resultBuffer.setCharAt(i, c);
        }
        if (EnglishKeyboard.mode == EnglishKeyboard.CAPSLOCK)
            for (int i = completionBuffer.length(); i < resultBuffer.length(); i++) {
                char c = resultBuffer.charAt(i);
                resultBuffer.setCharAt(i, Character.toUpperCase(c));
            }
        return resultBuffer.toString();
    }

    public static void learn(String s) {
        Logger.d("learn %s", s);
        s = s.toLowerCase();
        update.bindString(1, s);
        int updatedCount = update.executeUpdateDelete();
        if (updatedCount <= 1) {
            insert.bindString(1, s);
            insert.execute();
            Logger.d("insert %s", s);
        }
        CandidateBar.reset();
    }
}
