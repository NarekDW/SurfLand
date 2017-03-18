package encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 09.03.2017 by K.N.K
 */

public interface StringEncryptUtil {

    String ALGORITHM = "MD5";  // TODO: 09.03.2017 Посмотреть как работает алгоритм MD5
//    MessageDigest ENCRYPTOR = ExceptionalFunction.getOrThrowUnchecked(MessageDigest::getInstance, ALGORITHM);

    static String encrypt(String s) {
        MessageDigest ENCRYPTOR = null;
        try {

            // TODO: 09.03.2017 Чтобы избавиться от try - catch, используй hegel!!! 
            ENCRYPTOR = MessageDigest.getInstance(ALGORITHM);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        ENCRYPTOR.reset();

        byte[] bs = ENCRYPTOR.digest(s.getBytes());

        StringBuilder stringBuilder = new StringBuilder();

        //hex encode the digest
        for (byte b : bs) {
            String hexVal = Integer.toHexString(0xFF & b);
            if (hexVal.length() == 1)
                stringBuilder.append("0");
            stringBuilder.append(hexVal);
        }

        return stringBuilder.toString();
    }
}