package my.function;

import java.security.InvalidKeyException;
import java.security.KeyFactory;  
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;  
import java.security.Signature;  
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;  
import java.security.interfaces.RSAPublicKey;  
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;  
import java.security.spec.X509EncodedKeySpec;  
  
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;  
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class RSAUtil {
	/**
     * 私钥
     */
    private RSAPrivateKey privateKey;


    /**
     * 公钥
     */
    private RSAPublicKey publicKey;


    /**
     * 获取私钥
     *
     * @return 当前的私钥对象
     */
    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    /**
     * 获取公钥
     *
     * @return 当前的公钥对象
     */
    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * 从字符串中加载私钥
     *
     * @param privateKeyStr
     * @throws Exception
     */
    public void loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = Base64.decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);

        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr
     * @throws Exception
     */
    public void loadPublicKey(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = Base64Util.decode(publicKeyStr);
            //PKCS8EncodedKeySpec keySpec= new PKCS8EncodedKeySpec(buffer);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);

        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * rsa私钥加密
     *
     * @param privateKey
     * @param plainTextData
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(RSAPrivateKey privateKey, byte[] plainTextData) throws Exception {
        if (privateKey == null) {
            throw new Exception("加密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            System.out.println(plainTextData);
            byte[] output = cipher.doFinal(plainTextData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }
    
    public static String encrypt(String privateKey, byte[] plainTextData) throws Exception {
    	 RSAUtil rsaEncrypt = new RSAUtil();

         try {
             rsaEncrypt.loadPrivateKey(privateKey);
             //System.out.println("加载私钥成功");
         } catch (Exception e) {
             System.out.println("加载私钥失败");
             e.printStackTrace();
         }
         
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, rsaEncrypt.getPrivateKey());
            System.out.println(plainTextData);
            byte[] output = cipher.doFinal(plainTextData);
            return Base64.encode(output);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }


    /**
     * rsa公钥解密
     *
     * @param publicKey
     * @param cipherData
     * @return
     * @throws Exception
     */
    public byte[] decrypt(RSAPublicKey publicKey, byte[] cipherData) throws Exception {
        if (publicKey == null) {
            throw new Exception("解密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }


    /**
     * 私钥签名
     *
     * @param plainTextData 要签名的字符串
     * @return
     */
    public static String sign(String plainTextData,String PRIVATE_KEY ) {
        RSAUtil rsaEncrypt = new RSAUtil();

        try {
            rsaEncrypt.loadPrivateKey(PRIVATE_KEY);
            //System.out.println("加载私钥成功");
        } catch (Exception e) {
            System.out.println("加载私钥失败");
            e.printStackTrace();
        }

        try {
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(rsaEncrypt.getPrivateKey());
            signature.update(plainTextData.getBytes());
            byte[] signResult = signature.sign();
            return Base64Util.encode(signResult);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 私钥加密
     *
     * @param data       待加密数据
     * @param privateKey 密钥
     * @return byte[] 加密数据
     */
    public static byte[] encryptByPrivateKey(byte[] data, byte[] privateKey) throws Exception {
        // 得到私钥
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey keyPrivate = kf.generatePrivate(keySpec);
        // 数据加密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keyPrivate);
        return cipher.doFinal(data);
    }
    /**
     * 验证签名
     *
     * @param plainTextData 原值(rsa签名步骤前的值)(此处rsa签名前步骤=客户端传过来的参数->排除sign后排序->utf8编码->MD5加密)
     * @param sign          签名过后的值(rsa签名步骤后的值)(客户端传过来的sign进行utf8解码后的值)
     * @return
     */
    public static boolean verify(String plainTextData, String sign,String PUBLICK_KEY) {
        RSAUtil rsaEncrypt = new RSAUtil();

        try {
            rsaEncrypt.loadPublicKey(PUBLICK_KEY);
            //System.out.println("加载公钥成功");
        } catch (Exception e) {
            System.out.println("加载公钥失败");
            e.printStackTrace();
        }

        try {
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(rsaEncrypt.getPublicKey());
            signature.update(plainTextData.getBytes());

            boolean result = signature.verify(Base64Util.decode(sign));
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args)   {
        String str = new String(Base64Util.encode("MTM1Njg5OTk2MzU=".getBytes()));

        byte[] bys = Base64Util.decode("");
        String str1 = new String(bys);
        System.out.println(str1);
    }

}
