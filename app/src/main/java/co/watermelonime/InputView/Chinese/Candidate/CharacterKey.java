package co.watermelonime.InputView.Chinese.Candidate;

import co.watermelonime.Common.Font;
import co.watermelonime.Common.Size;

public class CharacterKey extends CandidateButton {
    public static final CharacterKey[][] keys = new CharacterKey[22][];
    public static final char[][] characters = {
            {'吧', '把', '被', '比', '並', '不'},
            {'的', '得', '地', '但', '對', '等'},
            {'個', '各', '給', '跟', '過', '該'},
            {'就', '及', '即', '將', '僅', '皆'},
            {'只', '隻', '之', '至', '這', '著'},
            {'在', '再', '做', '作', '最', '怎'},
            {'拍', '排', '片', '偏', '篇', '頗'},
            {'他', '她', '它', '牠', '祂', '太'},
            {'看', '靠', '開', '可', '顆', 'ㄎ'},
            {'前', '其', '卻', '去', '且', '請'},
            {'常', '超', '除', '出', '差'},
            {'此', '才', '從', '曾'},
            {'買', '賣', '嗎', '嘛', '沒', '每'},
            {'你', '妳', '那', '哪', '呢', '乃'},
            {'和', '會', '或', '還', '很', 'ㄏ'},
            {'先', '想', '像', '向', '型', '些'},
            {'是', '時', '使', '事', '式', '受'},
            {'雖', '隨', '所', '似'},
            {'非', '放', '分', '反', '發', '法'},
            {'啦', '了', '裡', '來', '另', '令'},
            {'以', '已', '有', '又', '由', '要', '我', '為', '而', '與', '用', '也'},
            {'若', '如', '讓', '仍'}
    };
    public static final char[][] keyCodes = {
            {'a', 'a', 'f', 'u', 'p', 'v', },
            {'4', '0', '4', 'i', 'r', 'l', },
            {'c', 'c', 'f', 'j', 'b', 'e', },
            {'t', 'u', 'u', 'o', 'n', 'd', },
            {'u', 'u', 'u', 'u', '0', '1', },
            {'e', 'e', 'b', 'b', 'r', '2', },
            {'e', 'e', 'm', 'm', 'm', 'b', },
            {'a', 'a', 'a', 'a', 'a', 'e', },
            {'i', 'g', 'e', 'c', 'c', 'c', },
            {'m', 'u', 'd', 'w', 'd', 'p', },
            {'k', 'g', 'v', 'v', '+', },
            {'u', 'e', 'p', 'l', },
            {'e', 'e', 'a', 'a', '6', 'f', },
            {'u', 'u', '-', ',', '4', 'e', },
            {'/', 'r', 'b', '5', 'j', 'c', },
            {'m', 'o', 'o', 'o', 'p', 'd', },
            {'u', 'u', 'u', 'u', 'u', 'h', },
            {'r', 'r', 'b', 'u', },
            {'f', 'k', 'j', 'i', 'a', 'a', },
            {'a', '3', 'u', 'e', 'p', 'p', },
            {'u', 'u', 't', 't', 't', 's', 'b', 'r', 'l', 'w', 'p', 'd', },
            {'.', 'v', 'k', 'l', },
    };
    //    final static ArrayList<CharacterKey> pool = new ArrayList<>(10);
    final static OnTouchListener onTouchListener = new OnTouchCharacterKey();
    char vowel, character;

    public CharacterKey(char character, char vowel, int width, int paddingTopBottom) {
        super();
        setOnTouchListener(onTouchListener);
        needSeparator = false;
        this.vowel = vowel;
        this.character = character;
//        text = s;
        this.type = paddingTopBottom;
        setMeasuredDimension(width, (int) (Size.HCandidateRow + (paddingTopBottom == 0 ? 0 : Size.u / 2)));
        textLayout = Font.character.make(String.valueOf(character), width);
        dx = width / 2;
        dy = (Size.HCandidateRow - textLayout.getHeight()) / 2 +
                (paddingTopBottom == TOP ? Size.u / 2 : 0);
    }

    public static void init() {
        for (int i = 0; i < 22; i++) {
            keys[i] = new CharacterKey[characters[i].length];
            char[] chara = characters[i];
            char[] vowels = keyCodes[i];
            int len = chara.length;
            if (len == 0) continue;
            boolean isIUV = len > 6;
            int width = isIUV ? Size.WCandidateView / 6 : Size.WCandidateView / len;
            for (int j = 0; j < len; j++) {
                CharacterKey characterKey = new CharacterKey(
                        chara[j], vowels[j], width,
                        isIUV && j < 6 ? CandidateButton.TOP : CandidateButton.BOTTOM);
                keys[i][j] = characterKey;
            }
        }
    }
}
