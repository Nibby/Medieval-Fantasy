package hidden.indev0r.game.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Created by MrDeathJockey on 14/12/3.
 * Ported from TinyTiles~
 */
public class CipherEngine {

	public static Cipher getCipher(int encryptMode, String key) throws Exception {
		SecretKey desKey = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(key.getBytes()));
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(encryptMode, desKey);
		return cipher;
	}

}
