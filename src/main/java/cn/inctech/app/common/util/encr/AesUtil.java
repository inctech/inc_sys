package cn.inctech.app.common.util.encr;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component()
public class AesUtil {

	public String encr(String content) {
		return Base64.getEncoder().encodeToString(encrypt(content));
	}
	
	public String unencr(String content) {
		byte[] b_enc_ii = Base64.getDecoder().decode(content);
		return new String(decrypt(b_enc_ii));
	}

	Key getKey() {
		try {
			KeyGenerator keyGenerator;// 生成key
			keyGenerator = KeyGenerator.getInstance("AES");// 构造密钥生成器，指定为AES算法,不区分大小写
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(AES_PASS.getBytes());
			keyGenerator.init(128, secureRandom);
			SecretKey secretKey = keyGenerator.generateKey();// 产生原始对称密钥
			byte[] keyBytes = secretKey.getEncoded();// 获得原始对称密钥的字节数组
			Key key = new SecretKeySpec(keyBytes, "AES");// key转换,根据字节数组生成AES密钥
			return key;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public byte[] encrypt(String context) {
		return encrypt(context,getKey());
	}

	public byte[] encrypt(String context, Key key) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cipher.doFinal(context.getBytes());// 将加密并编码后的内容解码成字节数组
			log.debug("[aes encr base64] : " + Base64.getEncoder().encodeToString(result));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public byte[] decrypt(byte[] result) {
		return decrypt(result,getKey());
	}

	public byte[] decrypt(byte[] result, Key key) {
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
			result = cipher.doFinal(result);
 		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}
	
	static final String AES_PASS="Inc@2018";
	
	public static void main(String[] args) {
		AesUtil util=new AesUtil();
		String AccessKeyId="LTAIBy05lplmc1wj";
		String AccessKeySecret="DsraNFQM1J1QMIwgeFlBlI06FNqbzB";
		System.out.println("AccessKeyId:"+util.encr(AccessKeyId));
		System.out.println("AccessKeySecret:"+util.encr(AccessKeySecret));
	}

}
