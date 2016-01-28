package co.watermelonime.Core;

import co.watermelonime.C;

public class OldController implements EngineController {
    @Override
    public void init() {

    }

    @Override
    public void add(char pinyin, char characterLock) {
        if (characterLock == '\0') {
            if (Engine.getLength() == 9) C.commit(Engine.pop());
            Engine.addConsonant(String.valueOf(pinyin));
        } else {
            Engine.addVowel(String.valueOf(pinyin), String.valueOf(characterLock));
            Engine.thread1.execute(Runnables.onAdd);
        }
    }

    @Override
    public void del() {

    }

    @Override
    public void popLeft(CharSequence c) {

    }

    @Override
    public void popRight(CharSequence c) {

    }

    @Override
    public void change(int start, int end, CharSequence c) {

    }

    @Override
    public void clear() {

    }
}
