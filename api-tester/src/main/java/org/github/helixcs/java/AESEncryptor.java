package org.github.helixcs.java;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.github.helixcs.kotlin.DigestUtilKt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * AES属于块加密,对越长的字符串进行加密，代价越大，
 * 所以通常对明文进行分段，然后对每段明文进行加密，
 * 最后再拼成一个字符串。块加密的一个要面临的问题就是如何填满最后一块？
 * 所以这就是PADDING的作用，使用各种方式填满最后一块字符串，所以对于解密端，
 * 也需要用同样的PADDING来找到最后一块中的真实数据的长度。
 */
public class AESEncryptor {
    public static final String AES_IV = "tuofenguaccatsdk";
    public static final String AES_KEY_ALGORITHM = "AES";
    public final static String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final int KEYSIZE = 256;

    public static  Map<String, String> keygen(Map<String, String> initVars) {
        try {
            String seed;
            if (initVars != null && initVars.containsKey("seed")) {
                seed = initVars.get("seed");
            } else {
                seed = String.valueOf(System.currentTimeMillis());
            }

            Map<String, String> map = new HashMap<String, String>();
            KeyGenerator keygen = KeyGenerator.getInstance(AES_KEY_ALGORITHM);
            SecureRandom random = new SecureRandom(seed.getBytes());
            keygen.init(KEYSIZE, random);
            Key key = keygen.generateKey();
            String keyStr = Base64.encodeBase64String(key.getEncoded());
            map.put("key", keyStr);
            return map;
        } catch (Exception e) {
            return null;
        }
    }

    private static IvParameterSpec getIV(String iv) {
        iv = StringUtils.isNotBlank(iv) ? iv : AES_IV;
        byte[] ivBytes = iv.getBytes();
        int len = ivBytes.length;

        //16bit,不足16位,填充
        byte[] bytes = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i < len && i < 8; ++i) {
            bytes[i] = ivBytes[i];
            bytes[16 - i - 1] = ivBytes[len - i - 1];
        }
        IvParameterSpec ivSpec = new IvParameterSpec(bytes);
        return ivSpec;
    }

    public static String encode(String plainText, String key, String iv) {
        if (StringUtils.isBlank(plainText) || StringUtils.isBlank(key)) {
            return plainText;
        }
        // iv 向量也就是salt
        //IV的作用
        //
        //IV称为初始向量，不同的IV加密后的字符串是不同的，加密和解密需要相同的IV，既然IV看起来和key一样，却还要多一个IV的目的，对于每个块来说，key是不变的，但是只有第一个块的IV是用户提供的，其他块IV都是自动生成。
        //IV的长度为16字节。超过或者不足，可能实现的库都会进行补齐或截断。但是由于块的长度是16字节，所以一般可以认为需要的IV是16字节。
        iv = StringUtils.isBlank(iv) ? AES_IV : iv;
        try {
            IvParameterSpec ivSpec = getIV(iv);
            byte[] keyBytes = Base64.decodeBase64(key);
            byte[] plainTextBytes = plainText.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, AES_KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);
            byte[] cipherBytes = cipher.doFinal(plainTextBytes);
            Base64 base64 = new Base64(true);
            return base64.encodeAsString(cipherBytes);
        } catch (Exception e) {
            return plainText;
        }
    }

    public static String encode(String plainText, String key) {
        return encode(plainText, key, null);
    }

    public static String decode(String cipherText, String key) {
        return decode(cipherText, key, null);
    }

    public static String decode(String cipherText, String key, String iv) {
        if (StringUtils.isBlank(cipherText) || StringUtils.isBlank(key)) {
            return cipherText;
        }
        iv = StringUtils.isBlank(iv) ? AES_IV : iv;
        try {
            IvParameterSpec ivSpec = getIV(iv);
            byte[] keyBytes = Base64.decodeBase64(key);
            byte[] cipherBytes = Base64.decodeBase64(cipherText);
            SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, AES_KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);
            byte[] plainBytes = cipher.doFinal(cipherBytes);
            return new String(plainBytes, "utf-8");
        } catch (Exception e) {
            return cipherText;
        }
    }

    public static void main(String[] args) {
        String plainText = "HelloWorld你好";
        String key = Objects.requireNonNull(DigestUtilKt.initKey()).substring(0,22);
        String encodeString = encode(plainText, key);
        System.out.println(key);
        System.out.println(encodeString);
        System.out.println(decode(encodeString, key));
    }
}
