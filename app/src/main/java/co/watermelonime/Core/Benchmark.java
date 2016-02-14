package co.watermelonime.Core;

import android.os.Process;

import net.sqlcipher.Cursor;

import co.watermelonime.C;

public class Benchmark {
    static String[] input = {
//            "UeIcIcRpMpQiJeCfIrWcIgQpSlKbEcOrSlQuQp QcCvDlFeHkNrSuLuIc KrWcEcMdGlQvLuQc TjLr LvLpQuSuAvUb AuLsQuSuVpMm AjLpCfLtOpNwViMaJcLqKbEpEcFa NrLnLrMdMdEcCpHpLlMaTvHcNuOrOsSjSiIgQpEc IvMuLmMtSc IeAvMnGmQgHiEgDlFeHkUeGfEcWuCpOnScLm AgIaNuKgLtDvAvHuQu AvFeNnMnEcMtFpOrLuLmCbLdKa LuMuKgNuOnLoSlEcNmDiFmLeHt RlIpAuIbDfScLrMu TiLl QpTjWrMuMmTvWuSuLvGmNmEcCuFmQuQp EkGmFvTiAfFrOrEcScQk HtOaHcLuAjScJi ApIb GfHuMpLnVkQcLuOdLnTjHbHfEcOnO。"
//              "UeIcIcRpMpQiJeCfIrWcIgQpSlKbEcOrSlQuQp。QcCvDlFeHkNrSuLuIc。KrWcEcMdGlQvLuQc。。TjLr。LvLpQuSuAvUb。AuLsQuSuVpMm。。AjLpCfLtOpNwViMaJcLqKbEpEcFa。NrLnLrMdMdEcCpHpLlMaTvHcNuOrOsSjSiIgQpEc。IvMuLmMtSc。。IeAvMnGmQgHiEgDlFeHkUeGfEcWuCpOnScLm。AgIaNuKgLtDvAvHuQu。AvFeNnMnEcMtFpOrLuLmCbLdKa。LuMuKgNuOnLoSlEcNmDiFmLeHt。RlIpAuIbDfScLrMu。TiLl。QpTjWrMuMmTvWuSuLvGmNmEcCuFmQuQp。EkGmFvTiAfFrOrEcScQk。HtOaHcLuAjScJi。ApIb。。GfHuMpLnVkQcLuOdLnTjHbHfEcOnOu。"
            "UeIcIcRpMpQiJeCfIrWcIgQpSlKbE1OrSlQuQp。QcCvDlFeHkNrSuLuIc。KrWcE1MdGlQvLuQc。。TjLr。LvLpQuSuAvUb。AuLsQuSuVpMm。。AjLpCfLtOpNwViMaJcLqKbEpE1Fa。NrLnLrMdMdE1CpHpLlMaTvH8NuOrOsSjSiIgQpE1。IvMuLmMtSc。。IeAvMnGmQgHiEgDlFeHkUeGfE1WuCpOnScLm。AgIaNuKgLtDvAvHuQu。AvFeNnMnE1MtFpOrLuLmCbLdKa。LuMuKgNuOnLoSlE1NmDiFmLeHt。RlIpAuIbDfScLrMu。TiLl。QpTjWrMuMmTvWuSuLvGmNmE1CuFmQuQp。EkGmFvTiAfFrOrE1ScQk。HtOaH8LuAjScJi。ApIb。。GfHuMpLnVkQ4LuOdLnTjHbHfE1OnOu。"
    };
    static String[] chara = {
//            "??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????"
            "在各個???開???????的??之?。???太?卻是?個。??的?????。。?為。?用之事不做。?要之事從?。。???有????????的他。卻?為??的??而??了其??????的。?????。。????????太?在?的?????。??其??????。不太??的????????。以及?????的?????。???過????。?而。???即??????前的??之?。????被??的??。??了????。??。。?????著?些????的??"
    };
    static String[] groundTruth = {
            "在各個憧憬展開玫瑰色高中生活的學生之中。折木奉太郎卻是一個。灰色的節能主義者。。認為。無用之事不做。必要之事從簡。。本應沒有興趣參加課外活動的他。卻因為姐姐的命令而加入了其學校神山高中的。古籍研究社。。該部今年招攬到奉太郎在內的四名新社員。包括其好友福部里志。不太親近的舊同學伊原摩耶花。以及好奇心旺盛的千反田愛瑠。成功避過廢社危機。然而。眾人隨即捲入四十五年前的謎團之中。當年突然被退學的社長。留下了一本社刊。冰菓。。內裡竟隱藏著一些引人落淚的訊息"
    };
    static StringBuffer output = new StringBuffer(65536);

    public static void run() {
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);
        Engine.clear();
        output.setLength(0);
        C.debug = false;
        long t = System.nanoTime();
        int len = input[0].length();
        String pinyin = input[0],
                characterLock = chara[0];
//        for (int i = 0; i < len; i++) {
        iter(pinyin, characterLock);
//        }
        System.out.println("Total time = " + (System.nanoTime() - t) / 1000000);
        C.debug = true;
        System.out.println(groundTruth[0]);
//        System.out.println(output);
//        int correct = 0, all = 0;
//        for (int i = 0; i < groundTruth[0].length(); i++) {
//            char tt = output.charAt(i);
//            char gt = groundTruth[0].charAt(i);
//            if (tt == '。') continue;
//            if (tt == gt) correct++;
//            all++;
//        }
//        System.out.println("Correct Ratio = " + correct + " / " + all + " = " + (float) correct / all);
    }

    public static void iter(final String pinyin, final String characterLock) {
        int len = pinyin.length();
        int characterCounter = 0;
        for (int i = 0; i < len; i++) {
            char p = pinyin.charAt(i);
//            long start = System.nanoTime();
            if (p == '。') {
                characterCounter++;
                String answer = Engine.getSentence();
                C.commit(answer);
//                output.append(answer);
                C.commit("。");
                Engine.clear();
            } else if (Character.isUpperCase(p)) {
                add(p, '\0');
            } else if (Character.isLowerCase(p) || Character.isDigit(p)) {
                add(p, characterLock.charAt(characterCounter++));
            } else System.out.println("Error");
//            long end = System.nanoTime();
//            end = (end - start) / 1000000;
        }

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
