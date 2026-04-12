import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

public class CryptoUtils {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    // Hashes User's password using SHA-256 -> Secure 256-bit key
    private static byte[] generateKey(String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(password.getBytes("UTF-8"));
    }

    // Encrypts plain text using AES and hashed password
    public static String encrypt(String plainText, String password) {
        try {
            // Generate key from password
            byte[] key = generateKey(password);
            SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);

            // Initialize cipher
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Perform Encryption
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));

            // Convert scrambled bytes into Base64 string
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            return "Encryption Error: " + e.getMessage();
        }
    }

    // Decrypt Message
    public static String decrypt(String encryptedBase64, String password) throws Exception {
        byte[] key = generateKey(password);
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        // Reverse Encryption process
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedBase64);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, "UTF-8");
    }
}