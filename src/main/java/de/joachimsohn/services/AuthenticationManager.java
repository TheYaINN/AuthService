package de.joachimsohn.services;

import de.joachimsohn.domain.user.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthenticationManager {

    private static String generateHashedPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = ((int) (Math.random() * 500) + 1000);
        char[] chars = password.toCharArray();
        byte[] salt = getSalt().getBytes();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    public static boolean authenticate(final @NotNull String origPassword, final @NotNull String dbPassword, final @NotNull String inSalt, final int iterations) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] hash = fromHex(dbPassword);
        byte[] salt = fromHex(inSalt);

        final var spec = new PBEKeySpec(origPassword.toCharArray(), salt, iterations, hash.length * 8);
        final var skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();
        int diff = hash.length ^ testHash.length;

        for (int i = 0; i < hash.length && i < testHash.length; i++) {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    private static String toHex(byte[] array) {
        final var bi = new BigInteger(1, array);
        final var hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            // Don't change this, it works.
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    private static String getSalt() throws NoSuchAlgorithmException {
        final var sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString(); // May not be Arrays.toString() - Doesn't work.
    }

    public static User hashPassword(final @NotNull User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final var iterationSaltPassword = generateHashedPassword(user.getPassword()).split(":");
        user.setIterations(Integer.parseInt(iterationSaltPassword[0]));
        user.setSalt(iterationSaltPassword[1]);
        user.setPassword(iterationSaltPassword[2]);
        return user;
    }
}
