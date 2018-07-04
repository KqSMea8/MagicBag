import org.apache.commons.codec.digest.DigestUtils

/**
 * @Email: wb-zj268791@alibaba-inc.com .
 * @Author: wb-zj268791
 * @Date: 2018/4/10.
 * @Desc:
 */

import java.security.Key
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESedeKeySpec


private val KEY_ALGORITHM: String by lazy { "DESede" }
private val CIPHER_ALGORITHM: String by lazy { "DESede/ECB/PKCS5Padding" }

/**
 * 生成随机key
 */
fun initKey(): String? {
    val keyGenerator: KeyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM)
    keyGenerator.init(168)
    val secret: SecretKey = keyGenerator.generateKey()
    return Base64.getEncoder().encodeToString(secret.encoded)
}

private fun toKey(key: ByteArray): Key? {
    val dks = DESedeKeySpec(key)
    val secretKeyFactory: SecretKeyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM)
    return secretKeyFactory.generateSecret(dks)
}


/**
 * DES加密
 * @param data 所要加密字符串
 * @param key
 * @return String
 */
fun desEncode(data: String, key: String): String? {
    val cipher = Cipher.getInstance(CIPHER_ALGORITHM)
    cipher.init(Cipher.ENCRYPT_MODE, toKey(Base64.getDecoder().decode(key)))
    return Base64.getEncoder().encodeToString(cipher.doFinal(data.toByteArray()))
}

/**
 * DES 解密
 * @param encodingData 索要解密的字符串
 * @param key
 * @return String
 */
fun desDecode(encodingData: String, key: String): String? {
    val cipher = Cipher.getInstance(CIPHER_ALGORITHM)
    cipher.init(Cipher.DECRYPT_MODE, toKey(Base64.getDecoder().decode(key)))
    return String(cipher.doFinal(Base64.getDecoder().decode(encodingData)))
}

/**
 * MD5摘要生成
 * @param data 索要加密字符串
 * @return String
 */
fun md5hex(data: String): String = DigestUtils.md5Hex(data)

/**
 * Base64 编码
 * @param data 索要编码的字符串
 * @return String
 */
fun base64Encode(data: String): String? = String(Base64.getEncoder().encode(data.toByteArray()))

/**
 * Base64 解码
 * @param data 索要解码的Base64字符串
 * @return String
 */
fun base64Decode(data: String): String? = String(Base64.getDecoder().decode(data.toByteArray()))

fun main(args: Array<String>) {
    var plainText = "HelloWorld 你好世界"
    var key = initKey()
    println("key is $key")
    var encodingData = desEncode(data = plainText,key = key!!)
    println("encoding text is : $encodingData")
    var decodingData = desDecode(encodingData = encodingData!!,key = key)
    print("decoding string is $decodingData")

}