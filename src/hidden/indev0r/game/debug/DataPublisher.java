package hidden.indev0r.game.debug;

import hidden.indev0r.game.reference.References;
import hidden.indev0r.game.util.CipherEngine;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by MrDeathJockey on 14/12/10.
 *
 * A neat little class that will encrypt an entire data_raw folder and place them into data folder, encrypted
 * automatically. Invoked at startup before data loading, useful huh!
 */
public class DataPublisher {

    private static final Path NPC_DATA = Paths.get("data_raw").resolve("npc");
    private static final Path MON_DATA = Paths.get("data_raw").resolve("mon");
    private static final Path MON_SPAWN_DATA = Paths.get("data_raw").resolve("mon_spawn");
    private static final Path SCRIPT_DATA = Paths.get("data_raw").resolve("scripts");

    /*
        BE EXTRA CAREFUL ABOUT SPECIFYING PATHS, OR ELSE WE WILL LOSE FOLDERS!!
     */
    public static void publishContents() throws Exception {
        publish(NPC_DATA, References.NPC_PATH);
        publish(MON_DATA, References.MONSTER_PATH);
        publish(MON_SPAWN_DATA, References.MONSTER_SPAWN_PATH);
        publish(SCRIPT_DATA, References.SCRIPT_PATH);
    }

    public static void publish(Path origin, Path target) throws Exception {
        Util.removeDirectory(target, true);
        Util.copyDirectory(origin, target);

        encryptFolderContent(target, ".xml", References.CIPHER_KEY_2);
    }

    private static void encryptFolderContent(Path folder, String extension, String cipherKey) throws Exception {
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folder);
        for(Path path : directoryStream) {
            if(Files.isDirectory(path))
                encryptFolderContent(path, extension, cipherKey);

            else if(path.getFileName().toString().endsWith(extension)) {
                //Encrypt data
                BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"));
                StringBuffer data = new StringBuffer();
                String line;
                while((line = reader.readLine()) != null) {
                    data.append(line);
                }
                reader.close();

                Path destinationFile = path.getParent().resolve(path.getFileName().toString().replace(extension, ".dat"));

                Cipher c = CipherEngine.getCipher(Cipher.ENCRYPT_MODE, cipherKey);
                DataOutputStream output = new DataOutputStream(new CipherOutputStream(Files.newOutputStream(destinationFile), c));
                byte[] bytes = data.toString().getBytes(Charset.forName("UTF-8"));
                output.writeInt(bytes.length);
                output.write(bytes);
                output.flush();
                output.close();

                Files.delete(path);
            }
        }
    }

}
