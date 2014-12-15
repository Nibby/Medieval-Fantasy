package hidden.indev0r.game.entity.npc.script;

import hidden.indev0r.game.reference.References;
import hidden.indev0r.game.util.CipherEngine;
import hidden.indev0r.game.util.XMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrDeathJockey on 14/12/15.
 */
public class ScriptDatabase {

    private static final Map<String, Script> scriptMap = new HashMap<>();

    public void loadScripts() throws Exception {
        Path scriptPath = References.SCRIPT_PATH;
        DirectoryStream<Path> scriptList = Files.newDirectoryStream(scriptPath);
        for(Path scriptFile : scriptList) {
            if(Files.isDirectory(scriptFile)) continue;
            if(!scriptFile.toString().endsWith(".dat")) continue;

            Cipher c = CipherEngine.getCipher(Cipher.DECRYPT_MODE, References.CIPHER_KEY_2);
            DataInputStream input = new DataInputStream(new CipherInputStream(Files.newInputStream(scriptFile), c));
            byte[] data = new byte[input.readInt()];
            input.readFully(data);
            input.close();

            String stringData = new String(data, Charset.forName("UTF-8"));
            XMLParser parser = new XMLParser(stringData);
            Document doc = parser.getDocument();
            Element root = (Element) doc.getElementsByTagName("script").item(0);

            Script script = ScriptParser.parse(root);
            if(script != null) {
                Path relative = References.SCRIPT_PATH.relativize(scriptFile);
                String key = "";
                for(int i = 0; i < relative.getNameCount() - 1; i++) {
                    key += relative.getName(i) + ".";
                }
                key += relative.getName(relative.getNameCount() - 1).toString().replace(".dat", "");

                scriptMap.put(key, script);
            } else {
                JOptionPane.showMessageDialog(null, "Cannot create script from '"+ scriptFile +"'!",
                        "Internal Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static final ScriptDatabase database = new ScriptDatabase();

    private ScriptDatabase() {}

    public static ScriptDatabase getDatabase() { return database; }

    public static Script get(String scriptID) {
        return scriptMap.get(scriptID);
    }
}
