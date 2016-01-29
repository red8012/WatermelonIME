package co.watermelonime.Core;

import net.sqlcipher.Cursor;

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
            Engine.thread1.submit(() -> {
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
                C.candidateView.post(Runnables.displayCandidate);
                Runnables.makeSeparator();
                Runnables.makeSentence();
                C.sentenceView.post(Runnables.displaySentence);
            });
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
