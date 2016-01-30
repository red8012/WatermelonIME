package co.watermelonime.Core;

import net.sqlcipher.Cursor;

import java.util.ArrayList;

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

    @Override
    public void getDict(int index) {
        Engine.thread1.submit(() -> {
            String pinyin = Engine.pinyin.substring(index * 2, index * 2 + 2);
            Cursor cursor = Database.query("select orig, zi, priority from dict where pinyin = '" + pinyin + "' order by priority");
            ArrayList<String> list = new ArrayList<>(16);
            while (cursor.moveToNext()) {
                list.add(cursor.getString(0));
                list.add(cursor.getString(1));
            }
            cursor.close();
            C.candidateView.post(()->{
                for (String s : list) {
                    ArrayList<String> charList = new ArrayList<>(32);
                    for (int i = 0; i < s.length(); i++) {
                        charList.add(String.valueOf(s.charAt(i)));
                    }
                    C.candidateView.setCandidate(charList, 0);
                }
            });

        });

    }
}
