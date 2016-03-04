package co.watermelonime.InputView.Chinese.Sentence;

import co.watermelonime.C;
import co.watermelonime.InputView.Chinese.ChineseInputView;

public class FunctionKeys {
    public static SentenceButton[] keys = new SentenceButton[1];
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

//        benchmark = new SentenceButton(-2);
//        benchmark.setText("B");
//        keys[1] = benchmark;
//        benchmark.setOnClickListener((v) -> {
//            C.threadPool.submit(() -> {
//                try {
//                    Benchmark2.run();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//        });
    }
}
