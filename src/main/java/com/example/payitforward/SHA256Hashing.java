//package com.example.payitforward;
//
//import javax.crypto.SecretKeyFactory;
//import javax.crypto.spec.PBEKeySpec;
//import javax.xml.bind.DatatypeConverter;
//import java.nio.charset.StandardCharsets;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.security.spec.InvalidKeySpecException;
//import java.util.HashMap;
//import java.util.Map;
//
//public class SHA256Hashing {
//
//    public static String HashWithJavaMessageDigest(final String originalString) throws NoSuchAlgorithmException {
//        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
//        final byte[] encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
//        return bytesToHex(encodedhash);
//
//    }
//
//    private static String bytesToHex(byte[] hash) {
//        StringBuffer hexString = new StringBuffer();
//        for (int i = 0; i < hash.length; i++) {
//            String hex = Integer.toHexString(0xff & hash[i]);
//            if (hex.length() == 1)
//                hexString.append('0');
//            hexString.append(hex);
//        }
//        return hexString.toString();
//    }
//
//_____________________________________
//
//    Map<String, String> DB = new HashMap<String, String>();
//    public static final String SALT = "my-salt-text";
//
//    public static void main(String args[]) {
//        SHA256Hashing demo = new SHA256Hashing();
//        demo.signup("john", "dummy123");
//
//        // login should succeed.
//        if (demo.login("john", "dummy123"))
//            System.out.println("User login successful.");
//
//        // login should fail because of wrong password.
//        if (demo.login("john", "blahblah"))
//            System.out.println("User login successful.");
//        else
//            System.out.println("User login failed.");
//    }
//
//    public void signup(String username, String password) {
//        String saltedPassword = SALT + password;
//        String hashedPassword = generateHash(saltedPassword);
//        DB.put(username, hashedPassword);
//    }
//
//    public Boolean login(String username, String password) {
//        Boolean isAuthenticated = false;
//
//        // remember to use the same SALT value use used while storing password
//        // for the first time.
//        String saltedPassword = SALT + password;
//        String hashedPassword = generateHash(saltedPassword);
//
//        String storedPasswordHash = DB.get(username);
//        if(hashedPassword.equals(storedPasswordHash)){
//            isAuthenticated = true;
//        }else{
//            isAuthenticated = false;
//        }
//        return isAuthenticated;
//    }
//
//    public static String generateHash(String input) {
//        StringBuilder hash = new StringBuilder();
//
//        try {
//            MessageDigest sha = MessageDigest.getInstance("SHA-1");
//            byte[] hashedBytes = sha.digest(input.getBytes());
//            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
//                    'a', 'b', 'c', 'd', 'e', 'f' };
//            for (int idx = 0; idx < hashedBytes.length; ++idx) {
//                byte b = hashedBytes[idx];
//                hash.append(digits[(b & 0xf0) >> 4]);
//                hash.append(digits[b & 0x0f]);
//            }
//        } catch (NoSuchAlgorithmException e) {
//            // handle error here.
//        }
//
//        return hash.toString();
//    }
//
//    ____________________________________
//
//    public class PasswordStorage
//    {
//
//        @SuppressWarnings("serial")
//        static public class InvalidHashException extends Exception {
//            public InvalidHashException(String message) {
//                super(message);
//            }
//            public InvalidHashException(String message, Throwable source) {
//                super(message, source);
//            }
//        }
//
//        @SuppressWarnings("serial")
//        static public class CannotPerformOperationException extends Exception {
//            public CannotPerformOperationException(String message) {
//                super(message);
//            }
//            public CannotPerformOperationException(String message, Throwable source) {
//                super(message, source);
//            }
//        }
//
//        public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
//
//        // These constants may be changed without breaking existing hashes.
//        public static final int SALT_BYTE_SIZE = 24;
//        public static final int HASH_BYTE_SIZE = 18;
//        public static final int PBKDF2_ITERATIONS = 64000;
//
//        // These constants define the encoding and may not be changed.
//        public static final int HASH_SECTIONS = 5;
//        public static final int HASH_ALGORITHM_INDEX = 0;
//        public static final int ITERATION_INDEX = 1;
//        public static final int HASH_SIZE_INDEX = 2;
//        public static final int SALT_INDEX = 3;
//        public static final int PBKDF2_INDEX = 4;
//
//        public static String createHash(String password)
//                throws CannotPerformOperationException
//        {
//            return createHash(password.toCharArray());
//        }
//
//        public static String createHash(char[] password)
//                throws CannotPerformOperationException
//        {
//            // Generate a random salt
//            SecureRandom random = new SecureRandom();
//            byte[] salt = new byte[SALT_BYTE_SIZE];
//            random.nextBytes(salt);
//
//            // Hash the password
//            byte[] hash = pbkdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
//            int hashSize = hash.length;
//
//            // format: algorithm:iterations:hashSize:salt:hash
//            String parts = "sha1:" +
//                    PBKDF2_ITERATIONS +
//                    ":" + hashSize +
//                    ":" +
//                    toBase64(salt) +
//                    ":" +
//                    toBase64(hash);
//            return parts;
//        }
//
//        public static boolean verifyPassword(String password, String correctHash)
//                throws CannotPerformOperationException, InvalidHashException
//        {
//            return verifyPassword(password.toCharArray(), correctHash);
//        }
//
//        public static boolean verifyPassword(char[] password, String correctHash)
//                throws CannotPerformOperationException, InvalidHashException
//        {
//            // Decode the hash into its parameters
//            String[] params = correctHash.split(":");
//            if (params.length != HASH_SECTIONS) {
//                throw new InvalidHashException(
//                        "Fields are missing from the password hash."
//                );
//            }
//
//            // Currently, Java only supports SHA1.
//            if (!params[HASH_ALGORITHM_INDEX].equals("sha1")) {
//                throw new CannotPerformOperationException(
//                        "Unsupported hash type."
//                );
//            }
//
//            int iterations = 0;
//            try {
//                iterations = Integer.parseInt(params[ITERATION_INDEX]);
//            } catch (NumberFormatException ex) {
//                throw new InvalidHashException(
//                        "Could not parse the iteration count as an integer.",
//                        ex
//                );
//            }
//
//            if (iterations < 1) {
//                throw new InvalidHashException(
//                        "Invalid number of iterations. Must be >= 1."
//                );
//            }
//
//
//            byte[] salt = null;
//            try {
//                salt = fromBase64(params[SALT_INDEX]);
//            } catch (IllegalArgumentException ex) {
//                throw new InvalidHashException(
//                        "Base64 decoding of salt failed.",
//                        ex
//                );
//            }
//
//            byte[] hash = null;
//            try {
//                hash = fromBase64(params[PBKDF2_INDEX]);
//            } catch (IllegalArgumentException ex) {
//                throw new InvalidHashException(
//                        "Base64 decoding of pbkdf2 output failed.",
//                        ex
//                );
//            }
//
//
//            int storedHashSize = 0;
//            try {
//                storedHashSize = Integer.parseInt(params[HASH_SIZE_INDEX]);
//            } catch (NumberFormatException ex) {
//                throw new InvalidHashException(
//                        "Could not parse the hash size as an integer.",
//                        ex
//                );
//            }
//
//            if (storedHashSize != hash.length) {
//                throw new InvalidHashException(
//                        "Hash length doesn't match stored hash length."
//                );
//            }
//
//            // Compute the hash of the provided password, using the same salt,
//            // iteration count, and hash length
//            byte[] testHash = pbkdf2(password, salt, iterations, hash.length);
//            // Compare the hashes in constant time. The password is correct if
//            // both hashes match.
//            return slowEquals(hash, testHash);
//        }
//
//        private static boolean slowEquals(byte[] a, byte[] b)
//        {
//            int diff = a.length ^ b.length;
//            for(int i = 0; i < a.length && i < b.length; i++)
//                diff |= a[i] ^ b[i];
//            return diff == 0;
//        }
//
//        private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
//                throws CannotPerformOperationException
//        {
//            try {
//                PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
//                SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
//                return skf.generateSecret(spec).getEncoded();
//            } catch (NoSuchAlgorithmException ex) {
//                throw new CannotPerformOperationException(
//                        "Hash algorithm not supported.",
//                        ex
//                );
//            } catch (InvalidKeySpecException ex) {
//                throw new CannotPerformOperationException(
//                        "Invalid key spec.",
//                        ex
//                );
//            }
//        }
//
//        private static byte[] fromBase64(String hex)
//                throws IllegalArgumentException
//        {
//            return DatatypeConverter.parseBase64Binary(hex);
//        }
//
//        private static String toBase64(byte[] array)
//        {
//            return DatatypeConverter.printBase64Binary(array);
//        }
//
//}
