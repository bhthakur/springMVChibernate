package com.journaldev.spring.helper;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.RandomStringUtils;

public class StringUtils {

	private String generateRandomNumber(final char firstCharacter, final char secondCharacter) {
		final StringBuffer trn = new StringBuffer();
		trn.append(firstCharacter);
		trn.append(RandomStringUtils.random(4, false, true));
		trn.append(secondCharacter);
		trn.append(RandomStringUtils.random(4, false, true));
		return trn.toString();
	}

	private static SecureRandom sr = new SecureRandom();

	public static String encryptDes(String value) {

		try {
			DESKeySpec dks = new DESKeySpec(getKey());
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			SecretKey sk = skf.generateSecret(dks);

			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, sk, sr);

			byte[] data = value.getBytes();
			byte[] rawKeyData = cipher.doFinal(data);
			String encodeBase64URLSafeString = Base64.encodeBase64URLSafeString(rawKeyData);
			return encodeBase64URLSafeString;

		} catch (Exception e) {
			return null;
		}

	}

	public static String decryptDes(String encryptString) {

		try {
			byte[] decodeBytes	= Base64.decodeBase64(encryptString);
			DESKeySpec dks = new DESKeySpec(getKey());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey sk = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, sk, sr);
			// byte encryptedData[] = rawKeyData;
			// byte encryptedData[] = encryptString.getBytes();
			byte decryptedData[] = cipher.doFinal(decodeBytes);

			String decryptedString = new String(decryptedData);

			return decryptedString;
		} catch (Exception e) {
			return null;
		}
	}

	private static byte[] getKey() {
		// TODO Auto-generated method stub
		return "Rishi".getBytes();
	}

}
