package gluka.morsecodeggc;

import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;

public class MorseCode {

    static HashMap<Character, String> map;
    private static int DI_LENGTH = 50;             // msecs
    private static int INTER_SYMB_SPACE = 50;      //
    private static int INTER_WORD_SPACE = 200;      //
    private static int INTER_CHAR_SPACE = 100;      //

    static {
        map = new HashMap<>();
        map.put('a', ".-");     map.put('b', "-...");       map.put('c', "-.-.");
        map.put('d', "-..");    map.put('e', ".");          map.put('f', "..-.");
        map.put('g', "--.");    map.put('h', "....");       map.put('i', "..");
        map.put('j', ".---");   map.put('k', "-.-");        map.put('l', ".-..");
        map.put('m', "--");     map.put('n', "-.");         map.put('o', "---");
        map.put('p', ".--.");   map.put('q', "--.-");       map.put('r', ".-.");
        map.put('s', "...");    map.put('t', "-");          map.put('u', "..-");
        map.put('v', "...-");   map.put('w', ".--");        map.put('x', "-..-");
        map.put('y', "-.--");   map.put('z', "--..");

        map.put('0', "-----");  map.put('1', ".----");      map.put('2', "..---");
        map.put('3', "...--");  map.put('4', "....-");      map.put('5', ".....");
        map.put('6', "-....");  map.put('7', "--...");      map.put('8', "---..");
        map.put('9', "----.");

        map.put('.', ".-.-.-"); map.put(',', "--..--");     map.put(':', "---...");
        map.put('?', "..--.."); map.put('\'', ".----.");    map.put('-', "-....-");
        map.put('/', "-..-.");  map.put('(', "-.--.-");     map.put(')', "-.--.-");
        map.put('"', ".-..-."); map.put('@', ".--.-.");     map.put('=', "-...-");
        map.put(' ', " ");

    }

    public static String getDitDah(char _c) {

        return map.get(Character.toLowerCase(_c));
    }
    public void getDitDahLoop(char _c) {
        //ditdah is the value of the map
        String ditdah = map.get(Character.toLowerCase(_c));
        Log.d("print length", "getDitDahLoop: " + ditdah.length());
        for(int i = 0; i<ditdah.length();i++){
            Log.d("print loop", "getDitDahLoop: " + ditdah.charAt(i));
            Log.d("printed", "getDitDahLoop: ");

        }
        //return "";
    }

    public static LinkedList<Signal> genOnOffSchedule(String _s, int _defer) {
        LinkedList<Signal> result = new LinkedList<>();
        long accumulated = _defer;

        for (int i = 0; i < _s.length(); i++) {

            String didah = getDitDah(_s.charAt(i));
            if (didah == null) continue;

            for (int j = 0; j < didah.length(); j++) {
                if (didah.charAt(j) == '.') {
                    result.add(new Signal(i, Signal.ON, accumulated, DI_LENGTH));
                    accumulated += DI_LENGTH;
                    result.add(new Signal(i, Signal.OFF, accumulated, INTER_SYMB_SPACE));
                    accumulated += INTER_SYMB_SPACE;
                } else if (didah.charAt(j) == '-') {
                    result.add(new Signal(i, Signal.ON, accumulated, DI_LENGTH * 3));
                    accumulated += (DI_LENGTH * 3);
                    result.add(new Signal(i, Signal.OFF, accumulated, INTER_SYMB_SPACE));
                    accumulated += INTER_SYMB_SPACE;
                } else if (didah.charAt(j) == ' ') {
                    result.add(new Signal(i, Signal.OFF, INTER_WORD_SPACE, INTER_WORD_SPACE));
                }
            }

            accumulated += INTER_CHAR_SPACE;
        }
        return result;
    }

    // useful if we elect to change individual characters within an EditText view
    public static Spanned annotateMessage(int _position, String _message) {
        String before = _message.substring(0, _position);
        String preHtml = "<b><u><font color=\"red\">";
        char subj = _message.charAt(_position);
        String postHtml = "</font></u></b>";
        String after = _message.substring(_position + 1);
        String result = before + preHtml + subj + postHtml + after;
        return Html.fromHtml(result);
    }
}
