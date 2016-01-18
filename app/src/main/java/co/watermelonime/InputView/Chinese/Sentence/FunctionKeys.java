package co.watermelonime.InputView.Chinese.Sentence;

public class FunctionKeys {
    public static SentenceButton[] keys = new SentenceButton[1];
    public static SentenceButton emoji;

    public static void init() {
        emoji = new SentenceButton();
        emoji.setText("\uD83D\uDE19");
        emoji.setOnClickListener((v) -> {
//            System.out.println("X clicked");
        });
        keys[0] = emoji;
    }
}
