package co.watermelonime.InputView.Chinese.Sentence;

import co.watermelonime.C;
import co.watermelonime.Common.Timer;
import co.watermelonime.Core.Benchmark2;
import co.watermelonime.Core.Database;
import co.watermelonime.Core.Engine;
import co.watermelonime.InputView.Chinese.ChineseInputView;

public class FunctionKeys {
    public static SentenceButton[] keys = new SentenceButton[2];
    public static SentenceButton emoji, testMemory, benchmark;

    public static void init() {
        emoji = new SentenceButton(-2);
        emoji.setText("\uD83D\uDE19");
        keys[0] = emoji;
        emoji.setOnClickListener((v) -> {
            if (ChineseInputView.mode == ChineseInputView.CHINESE)
                C.chineseInputView.changeInputMode(ChineseInputView.EMOJI);

            else if (ChineseInputView.mode == ChineseInputView.EMOJI)
                C.chineseInputView.changeInputMode(ChineseInputView.CHINESE);
        });

        benchmark = new SentenceButton(-2);
        benchmark.setText("B");
        keys[1] = benchmark;
        benchmark.setOnClickListener((v) -> {
            C.threadPool.submit(() -> {
                try {
                    Benchmark2.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });


        // Test in memory database
        testMemory = new SentenceButton(-2);
        testMemory.setText("M");
//        keys[1] = testMemory;
        testMemory.setOnClickListener((v) -> {
            Engine.thread2.submit(() -> {
                Timer.t(957);
                System.out.println("start copy DB");

                Timer.t(11);
                Database.dictionary.rawQuery("select * from solo where pinyin glob 'Aa'", null).moveToNext();
                Timer.t(11, "aa");

                Timer.t(11);
                Database.dictionary.rawQuery("select * from solo where pinyin glob 'Bb'", null).moveToNext();
                Timer.t(11, "bb");

                Timer.t(11);
                Database.dictionary.rawQuery("select * from solo where pinyin glob 'Cc'", null).moveToNext();
                Timer.t(11, "cc");

                Timer.t(11);
                Database.dictionary.rawQuery("select * from solo where pinyin glob 'Ee'", null).moveToNext();
                Timer.t(11, "ee");

                Timer.t(11);
                Database.dictionary.rawQuery("select * from duet where pinyin glob 'AaAa'", null).moveToNext();
                Timer.t(11, "aaaa");

                Timer.t(11);
                Database.dictionary.rawQuery("select * from duet where pinyin glob 'BaBa'", null).moveToNext();
                Timer.t(11, "baba");

                Timer.t(11);
                Database.dictionary.rawQuery("select * from quartet where pinyin glob 'LuLvSuRv' and zi glob '??是?'", null).moveToNext();
                Timer.t(11, "LuLvSuRv");

                Timer.t(11);
                Database.dictionary.rawQuery("select * from quartet where pinyin glob 'KmSlHfEp' and zi glob '??是?'", null).moveToNext();
                Timer.t(11, "KmSlHfEp");

                Database.dictionary.execSQL(
                        "ATTACH DATABASE ':memory:' AS m KEY '';");
                Database.dictionary.rawExecSQL(
                        "SELECT sqlcipher_export('m');");
                Timer.t(957, "copy DB");

                try {
                    Thread.sleep(1000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Timer.t(11);
                Database.dictionary.rawQuery("select * from m.solo where pinyin glob 'Aa'", null).moveToNext();
                Timer.t(11, "m.aa");

                Timer.t(11);
                Database.dictionary.rawQuery("select * from m.solo where pinyin glob 'Bb'", null).moveToNext();
                Timer.t(11, "m.bb");

                Timer.t(11);
                Database.dictionary.rawQuery("select * from m.solo where pinyin glob 'Cc'", null).moveToNext();
                Timer.t(11, "m.cc");

                Timer.t(11);
                Database.dictionary.rawQuery("select * from m.solo where pinyin glob 'Ee'", null).moveToNext();
                Timer.t(11, "m.ee");

                Timer.t(11);
                Database.dictionary.rawQuery("select * from m.duet where pinyin glob 'AaAa'", null).moveToNext();
                Timer.t(11, "m.aaaa");

                Timer.t(11);
                Database.dictionary.rawQuery("select * from m.duet where pinyin glob 'BaBa'", null).moveToNext();
                Timer.t(11, "baba");

                Timer.t(11);
                Database.dictionary.rawQuery("select * from m.quartet where pinyin glob 'LuLvSuRv' and zi glob '??是?'", null).moveToNext();
                Timer.t(11, "m.LuLvSuRv");

                Timer.t(11);
                Database.dictionary.rawQuery("select * from m.quartet where pinyin glob 'KmSlHfEp' and zi glob '??是?'", null).moveToNext();
                Timer.t(11, "KmSlHfEp");
            });
        });
    }
}
