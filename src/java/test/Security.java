package test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletContext;
import org.apache.commons.codec.binary.*;

public class Security {

    private static String encryptionKey;
    private static String cipherAlgorithm;
    private static ServletContext servletContext;

    public static String encrypt(String strToEncrypt, String key) {
        String encryptedString = null;
        try {
            if (encryptionKey == null || cipherAlgorithm == null) {
                encryptionKey = servletContext.getInitParameter("ENCRYPTION_KEY");
                cipherAlgorithm = servletContext.getInitParameter("CIPHER_ALGORITHM");
            }

            Cipher cipher = getCipherInstance(cipherAlgorithm);
            final SecretKeySpec secretKey = getSecretKey(encryptionKey);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            encryptedString = Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return encryptedString;
    }

    public static String decrypt(String codeDecrypt, ServletContext servletContext) throws Exception {
        String decryptedString = null;
        try {
            if (encryptionKey == null || cipherAlgorithm == null) {
                encryptionKey = servletContext.getInitParameter("ENCRYPTION_KEY");
                cipherAlgorithm = servletContext.getInitParameter("CIPHER_ALGORITHM");
            }

            Cipher cipher = getCipherInstance(cipherAlgorithm);
            final SecretKeySpec secretKey = getSecretKey(encryptionKey);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            decryptedString = new String(cipher.doFinal(Base64.decodeBase64(codeDecrypt)), StandardCharsets.UTF_8).trim();
        } catch (NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
            System.err.println(e.getMessage());
        }
        return decryptedString;
    }

    private static Cipher getCipherInstance(String cipherAlgorithm) throws Exception {
        return Cipher.getInstance(cipherAlgorithm);
    }
    
    private static SecretKeySpec getSecretKey(String encryptionKey) {
        return new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), "AES");
    }
}
