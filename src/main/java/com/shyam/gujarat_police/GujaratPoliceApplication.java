package com.shyam.gujarat_police;

import com.shyam.gujarat_police.util.DateUtil;
import com.shyam.gujarat_police.util.RegExUtil;
import de.taimos.totp.TOTP;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Date;

@SpringBootApplication
public class GujaratPoliceApplication {
	public static void main(String[] args) {
		SpringApplication.run(GujaratPoliceApplication.class, args);

		String secretKey = "RU66PLHAB6ZTWXTGXFEWSEFOQBSUN632";
//		String lastCode = null;
//		while (true) {
//			String code = getTOTPCode(secretKey);
//			if (!code.equals(lastCode)) {
//				System.out.println(code + " " + DateUtil.dateToString(new Date(), "dd-MM-yyyy HH:mm:ss"));
//			}
//			lastCode = code;
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {};
//		}

	}
	public static String generateSecretKey() {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[20];
		random.nextBytes(bytes);
		Base32 base32 = new Base32();
		return base32.encodeToString(bytes);
	}

	public static String getTOTPCode(String secretKey) {
		Base32 base32 = new Base32();
		byte[] bytes = base32.decode(secretKey);
		String hexKey = Hex.encodeHexString(bytes);
		return TOTP.getOTP(hexKey);
	}

	public static String getGoogleAuthenticatorBarCode(String secretKey, String account, String issuer) {
		try {
			return "otpauth://totp/"
					+ URLEncoder.encode(issuer + ":" + account, "UTF-8").replace("+", "%20")
					+ "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20")
					+ "&issuer=" + URLEncoder.encode(issuer, "UTF-8").replace("+", "%20");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}
}
