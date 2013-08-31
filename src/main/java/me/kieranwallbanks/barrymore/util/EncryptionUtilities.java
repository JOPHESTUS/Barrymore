package me.kieranwallbanks.barrymore.util;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.Sha2Crypt;

/**
 * Class containing encryption based utilities
 */
public class EncryptionUtilities {

    /**
     * Encrypts some bytes using the SHA-512 algorithm
     *
     * @param bytes the bytes to encrypt
     *
     * @return the encrypted string
     */
    public static String sha512(byte[] bytes) {
        return Sha2Crypt.sha512Crypt(bytes);
    }

    /**
     * Encrypts some bytes using the BASE64 algorithm
     *
     * @param bytes the bytes to encrypt
     *
     * @return the encrypted string
     */
    public static String base64Encode(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    /**
     * Decodes a string using the BASE64 encryption
     *
     * @param string the string to decrypt
     *
     * @return the decrypted bytes
     */
    public static byte[] base64Decode(String string) {
        return Base64.decodeBase64(string);
    }

    /**
     * Encrypts a string using a password. This can be decrypted using {@link #decrypt(String, byte[], String)}
     *
     * @param toEncrypt the bytes to encrypt
     * @param salt the salt
     * @param password the password
     *
     * @return the encrypted string
     *
     * @throws GeneralSecurityException if a problem occurred whilst generating the hash
     */
    public static String encrypt(byte[] toEncrypt, byte[] salt, String password) throws GeneralSecurityException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(password.toCharArray()));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(salt, 20));
        return base64Encode(pbeCipher.doFinal(toEncrypt));
    }

    /**
     * Decrypts a string using a password. You can encrypt strings using {@link #encrypt(byte[], byte[], String)}
     *
     * @param toDecrypt the string to decrypt
     * @param salt the salt
     * @param password the password
     *
     * @return the decrypted string
     *
     * @throws GeneralSecurityException if a problem occurred whilst generating the hash
     * @throws IOException if an error occurred while finalising the decryption
     */
    public static String decrypt(String toDecrypt, byte[] salt, String password) throws GeneralSecurityException, IOException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(password.toCharArray()));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(salt, 20));
        return new String(pbeCipher.doFinal(base64Decode(toDecrypt)), "UTF-8");
    }

}
