package co.watermelonime.InputView.Chinese.Sentence;

import co.watermelonime.C;
import co.watermelonime.InputView.Chinese.ChineseInputView;

public class FunctionKeys {
    public static SentenceButton[] keys = new SentenceButton[1];
    public static SentenceButton emoji;

    public static void init() {
        emoji = new SentenceButton();
        emoji.setText("\uD83D\uDE19");
        emoji.setOnClickListener((v) -> {
            if (ChineseInputView.mode==ChineseInputView.CHINESE)
                C.chineseInputView.changeInputMode(ChineseInputView.EMOJI);

            else if (ChineseInputView.mode==ChineseInputView.EMOJI)
                C.chineseInputView.changeInputMode(ChineseInputView.CHINESE);
        });
        keys[0] = emoji;
    }
}
