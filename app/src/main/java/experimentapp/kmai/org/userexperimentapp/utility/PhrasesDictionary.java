package experimentapp.kmai.org.userexperimentapp.utility;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by saila on 3/17/18.
 */

public class PhrasesDictionary {
    List<String> lines;
    static HashMap<Integer, String> map;
    static int phraseCount;

    public PhrasesDictionary(Reader reader) throws IOException{
        BufferedReader in = new BufferedReader(reader);
        String line;
        int count = 0;
        lines = new ArrayList<String>();
        map = new HashMap<Integer, String>();
        while((line = in.readLine()) != null) {
            String word = line.trim();
            lines.add(line);
            map.put(count++,line);
        }
        phraseCount = count;
    }

    public static String getPhrase(int count){
        return map.get(count);
    }

    public static int getphraseCount(){
        return phraseCount;
    }

}
