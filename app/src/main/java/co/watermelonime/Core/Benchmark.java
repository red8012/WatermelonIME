package co.watermelonime.Core;

import net.sqlcipher.Cursor;

import co.watermelonime.C;

public class Benchmark {
    static String[] input = {
            "UeIcIcRpMpQiJeCfIrWcIgQpSlKbEcOrSlQuQp QcCvDlFeHkNrSuLuIc KrWcEcMdGlQvLuQc TjLr LvLpQuSuAvUb AuLsQuSuVpMm AjLpCfLtOpNwViMaJcLqKbEpEcFa NrLnLrMdMdEcCpHpLlMaTvHcNuOrOsSjSiIgQpEc IvMuLmMtSc IeAvMnGmQgHiEgDlFeHkUeGfEcWuCpOnScLm AgIaNuKgLtDvAvHuQu AvFeNnMnEcMtFpOrLuLmCbLdKa LuMuKgNuOnLoSlEcNmDiFmLeHt RlIpAuIbDfScLrMu TiLl QpTjWrMuMmTvWuSuLvGmNmEcCuFmQuQp EkGmFvTiAfFrOrEcScQk HtOaHcLuAjScJi ApIb GfHuMpLnVkQcLuOdLnTjHbHfEcOnO"
    };
    static StringBuffer output = new StringBuffer(65536);

    public static void run() {
        output.setLength(0);
        long t = System.nanoTime();
        int len = input[0].length();
        String pinyin = input[0];
        for (int i = 0; i < len; i++) {
            iter(pinyin.charAt(i));
        }
        System.out.println("Total time = "+(System.nanoTime() - t)/1000000);
        System.out.println(output);
    }

    public static void iter(final char c) {
        long start = System.nanoTime();
        if (c == ' ') {
            C.commit(Engine.getSentence());
            C.commit("\n");
            Engine.clear();
        } else if (Character.isUpperCase(c)) {
            add(c, '\0');
        } else if (Character.isLowerCase(c)) {
            add(c, '?');
        } else System.out.println("Error");
        long end = System.nanoTime();
        end = (end - start) / 1000000;
        if (c != ' ' && Character.isLowerCase(c))
            output.append(c).append(",").append(end).append("@");
    }

    public static void add(char pinyin, char characterLock) {
        if (characterLock == '\0') {
            if (Engine.getLength() == 9) C.commit(Engine.pop());
            Engine.addConsonant(String.valueOf(pinyin));
        } else {
                Engine.addVowel(String.valueOf(pinyin), String.valueOf(characterLock));

                final int length = Engine.getLength();
                final Cursor c = Database.query(Engine.pinyin.toString(), Engine.ziLock.toString());
                while (c.moveToNext()) {
                    final int l = c.getInt(0), start = length - l;
                    Engine.queryResultD[l][start].addLast(c.getString(1));
                }
                c.close();

                Engine.makeCandidateLeft(length);
                Engine.makeCandidateRight(length);
                Runnables.makeSeparator();
                Runnables.makeSentence();
        }
    }
}
