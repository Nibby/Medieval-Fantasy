package hidden.indev0r.game.util;

import hidden.indev0r.game.entity.Actor;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrDeathJockey on 14/12/18.
 */
public class Util  {

    public static Map<Actor.Stat, Integer> cloneStatMap(Map<Actor.Stat, Integer> sample) {
        Map<Actor.Stat, Integer> result = new HashMap<>();
        for(Actor.Stat key : sample.keySet()) {
            result.put(key, sample.get(key));
        }
        return result;
    }

    public static Object deepCopyInstance(Object instance) throws Exception {
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        ObjectOutputStream output = new ObjectOutputStream(byteOutput);
        output.writeObject(instance);
        output.flush();
        output.close();

        ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(byteOutput.toByteArray()));
        Object result = input.readObject();
        return result;
    }

}
