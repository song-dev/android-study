package com.song.androidstudy.crypto;

import android.util.Pair;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/**
 * RSA加密实现，java只支持PKCS8Padding 的公钥私钥对
 */
public class RSAUtils {

    /**
     * 加密解密模式
     */
    private static final String RSA_ALGORITHM = "RSA/ECB/PKCS1Padding";
    /**
     * 签名模式
     */
    private static final String RSA_SIGN_ALGORITHM = "SHA256withRSA";
    /**
     * 指定加密算法为RSA
     */
    private static final String ALGORITHM = "RSA";
    /**
     * 密钥长度，用来初始化
     */
    private static final int KEYSIZE = 1024;
    /**
     * PKCS1Padding加密快最大长度
     */
    public static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * 1024位key解密块最大长度
     */
    public static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 生成RSA密钥对
     *
     * @return
     */
    public static Pair<String, String> getRSAPair() {
        try {

            /** RSA算法要求有一个可信任的随机数源 */
            SecureRandom secureRandom = new SecureRandom();
            /** 为RSA算法创建一个KeyPairGenerator对象 */
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            /** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
            keyPairGenerator.initialize(KEYSIZE, secureRandom);
            /** 生成密匙对 */
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            /** 得到公钥 */
            Key publicKey = keyPair.getPublic();
            /** 得到私钥 */
            Key privateKey = keyPair.getPrivate();
            byte[] publicKeyBytes = publicKey.getEncoded();
            byte[] privateKeyBytes = privateKey.getEncoded();

            String publicKeyBase64 = Base64.encode(publicKeyBytes);
            String privateKeyBase64 = Base64.encode(privateKeyBytes);
            return new Pair<>(publicPem(publicKeyBase64), privateKeyBase64);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取公钥对象,公钥只支持 X509EncodedKeySpec
     *
     * @param publicKeyBase64
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public static PublicKey getPublicKey(String publicKeyBase64) throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        X509EncodedKeySpec publicpkcs8KeySpec = new X509EncodedKeySpec(Base64.decode(publicKeyBase64));
        return keyFactory.generatePublic(publicpkcs8KeySpec);
    }

    /**
     * 获取私钥对象,私钥只支持 PKCS8EncodedKeySpec
     *
     * @param privateKeyBase64
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKey(String privateKeyBase64) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PKCS8EncodedKeySpec privatekcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyBase64));
        return keyFactory.generatePrivate(privatekcs8KeySpec);
    }

    /**
     * 将公钥转换成pkcs8编码的pem格式
     *
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String publicPem(String publicKey) {
        try {
            byte[] pubBytes = Base64.decode(publicKey);
            return pkcs1ToPem(pubBytes, true);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将私钥转换成pkcs1编码的pem格式
     *
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String privatePem(String privateKey) throws Exception {
        try {
            byte[] privBytes = Base64.decode(privateKey);
            PrivateKeyInfo pkInfo = PrivateKeyInfo.getInstance(privBytes);
            ASN1Encodable encodeAble = pkInfo.parsePrivateKey();
            // 由pkcs8转换成pkcs1编码
            ASN1Primitive primitive = encodeAble.toASN1Primitive();
            byte[] privateKeyPKCS1 = primitive.getEncoded();
            return pkcs1ToPem(privateKeyPKCS1, false);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * pkcs1格式转换pem格式
     *
     * @param pcks1KeyBytes
     * @param isPublic
     * @return
     * @throws Exception
     */
    public static String pkcs1ToPem(byte[] pcks1KeyBytes, boolean isPublic) throws Exception {
        String type;
        if (isPublic) {
            type = "PUBLIC KEY";
        } else {
            type = "RSA PRIVATE KEY";
        }
        PemObject pemObject = new PemObject(type, pcks1KeyBytes);
        StringWriter stringWriter = new StringWriter();
        PemWriter pemWriter = new PemWriter(stringWriter);
        pemWriter.writeObject(pemObject);
        pemWriter.close();
        return stringWriter.toString();
    }

    /**
     * pem转换pkcs1格式
     *
     * @param base64Key
     * @return
     * @throws Exception
     */
    public static String pemToPkcs1(String base64Key) throws Exception {
        PemReader r = new PemReader(new InputStreamReader(new ByteArrayInputStream(base64Key.getBytes("UTF-8"))));
        PemObject pemObject = r.readPemObject();
        byte[] encodedKey = pemObject.getContent();
        return Base64.encode(encodedKey);
    }

    /**
     * 使用公钥加密（非分段加密）
     *
     * @param content         待加密内容
     * @param publicKeyBase64 公钥 base64 编码
     * @return 经过 base64 编码后的字符串
     */
    public String encipher(String content, String publicKeyBase64) {
        return encipher(content, publicKeyBase64, -1);
    }

    /**
     * 使用公钥加密（分段加密）
     *
     * @param content         待加密内容
     * @param publicKeyBase64 公钥 base64 编码
     * @param segmentSize     分段大小,一般小于 keySize/8（段小于等于0时，将不使用分段加密），pkcs1Padding为117
     * @return 经过 base64 编码后的字符串
     */
    public static String encipher(String content, String publicKeyBase64, int segmentSize) {
        try {
            PublicKey publicKey = getPublicKey(publicKeyBase64);
            return encipher(content, publicKey, segmentSize);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用公钥加密（分段加密）
     *
     * @param content         待加密内容
     * @param publicKeyBase64 公钥 base64 编码
     * @param segmentSize     分段大小,一般小于 keySize/8（段小于等于0时，将不使用分段加密），pkcs1Padding为117
     * @return 经过 base64 编码后的字符串
     */
    public static String encipher(byte[] content, String publicKeyBase64, int segmentSize) {
        try {
            PublicKey publicKey = getPublicKey(publicKeyBase64);
            return encipher(content, publicKey, segmentSize);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 分段加密
     *
     * @param cipherText  密文
     * @param key         加密秘钥
     * @param segmentSize 分段大小，<=0 不分段
     * @return
     */
    public static String encipher(String cipherText, java.security.Key key, int segmentSize) {
        try {
            // 用公钥加密
            byte[] srcBytes = cipherText.getBytes();
            // Cipher负责完成加密或解密工作，基于RSA
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            // 根据公钥，对Cipher对象进行初始化
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] resultBytes = null;

            if (segmentSize > 0) {
                //分段加密
                resultBytes = cipherDoFinal(cipher, srcBytes, segmentSize);
            } else {
                resultBytes = cipher.doFinal(srcBytes);
            }
            return Base64.encode(resultBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 分段加密
     *
     * @param cipherText  密文
     * @param key         加密秘钥
     * @param segmentSize 分段大小，<=0 不分段
     * @return
     */
    public static String encipher(byte[] cipherText, java.security.Key key, int segmentSize) {
        try {
            // 用公钥加密
            byte[] srcBytes = cipherText;

            // Cipher负责完成加密或解密工作，基于RSA
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            // 根据公钥，对Cipher对象进行初始化
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] resultBytes = null;

            if (segmentSize > 0) {
                resultBytes = cipherDoFinal(cipher, srcBytes, segmentSize);
            } else {
                resultBytes = cipher.doFinal(srcBytes);
            }
            String base64Str = Base64.encode(resultBytes);
            return base64Str;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用私钥解密（非分段解密）
     *
     * @param contentBase64    待加密内容,base64 编码
     * @param privateKeyBase64 私钥 base64 编码
     * @return
     */
    public static String decipher(String contentBase64, String privateKeyBase64) {
        return decipher(contentBase64, privateKeyBase64, -1);
    }

    /**
     * 使用私钥解密（分段解密）
     *
     * @param contentBase64    待加密内容,base64 编码
     * @param privateKeyBase64 私钥 base64 编码
     * @param segmentSize      分段大小,一般小于 keySize/8（段小于等于0时，将不使用分段加密），pkcs1Padding为117
     * @return
     */
    public static String decipher(String contentBase64, String privateKeyBase64, int segmentSize) {
        try {
            PrivateKey privateKey = getPrivateKey(privateKeyBase64);
            return decipher(contentBase64, privateKey, segmentSize);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 分段解密
     *
     * @param contentBase64 密文
     * @param key           解密秘钥
     * @param segmentSize   分段大小,一般小于 keySize/8（段小于等于0时，将不使用分段加密），pkcs1Padding为117
     * @return
     */
    public static String decipher(String contentBase64, java.security.Key key, int segmentSize) {
        try {
            // 用私钥解密
            byte[] srcBytes = Base64.decode(contentBase64);
            // Cipher负责完成加密或解密工作，基于RSA
            Cipher deCipher = Cipher.getInstance(RSA_ALGORITHM);
            // 根据公钥，对Cipher对象进行初始化
            deCipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decBytes = null;
            if (segmentSize > 0) {
                decBytes = cipherDoFinal(deCipher, srcBytes, segmentSize);
            } else {
                decBytes = deCipher.doFinal(srcBytes);
            }
            return new String(decBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 分段大小,每次加密的字节数，不能超过密钥的长度值减去11(NOPadding限制为秘钥长度), 否则需要手动切割填充
     *
     * @param cipher
     * @param srcBytes
     * @param segmentSize
     * @return
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    private static byte[] cipherDoFinal(Cipher cipher, byte[] srcBytes, int segmentSize)
            throws IllegalBlockSizeException, BadPaddingException, IOException {
        if (segmentSize <= 0) {
            throw new RuntimeException("分段大小必须大于0");
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int inputLen = srcBytes.length;
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > segmentSize) {
                cache = cipher.doFinal(srcBytes, offSet, segmentSize);
            } else {
                cache = cipher.doFinal(srcBytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * segmentSize;
        }
        byte[] data = out.toByteArray();
        out.close();
        return data;
    }

    /**
     * 私钥签名，签名生成字符串相同, 没有字节大小限制
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) {
        try {
            Signature signature = Signature.getInstance(RSA_SIGN_ALGORITHM);
            signature.initSign(getPrivateKey(privateKey));
            signature.update(data);
            return Base64.encode(signature.sign());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 公钥验签
     *
     * @param data
     * @param sign
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static boolean verify(String data, String sign, String publicKey) {
        try {
            Signature signature = Signature.getInstance(RSA_SIGN_ALGORITHM);
            signature.initVerify(getPublicKey(publicKey));
            signature.update(data.getBytes());
            return signature.verify(Base64.decode(sign));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
