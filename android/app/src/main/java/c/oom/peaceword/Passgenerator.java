package c.oom.peaceword;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class Passgenerator {

    private static String m_salt = null;
    private static int m_minlength = 16;
    private static boolean m_needupercase = false;
    private static boolean m_needspecialchar = false;

    public static void SetSalt(String salt) {
        Passgenerator.m_salt = salt;
    }

    public static int GetMinLength() {
        return Passgenerator.m_minlength;
    }

    public static boolean GetNeedUperCase() {
        return Passgenerator.m_needupercase;
    }

    public static boolean GetNeedSpecialChar() {
        return Passgenerator.m_needspecialchar;
    }

    public static String GetSalt() {
        if (Passgenerator.m_salt == null)
            Passgenerator.GenSalt();
        return Passgenerator.m_salt;
    }

    public static void SetConfig(int minlength, boolean needupercase, boolean needspecialchar) {
        Passgenerator.m_minlength = minlength;
        Passgenerator.m_needupercase = needupercase;
        Passgenerator.m_needspecialchar = needspecialchar;
    }


    public static void GenSalt() {
        Passgenerator.m_salt = sha256(UUID.randomUUID().toString());
    }

    public static String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String Generate(String Input) {
        if (Passgenerator.m_salt == null) {
            Passgenerator.GenSalt();
        }
        String password = sha256(Passgenerator.m_salt + Input);

        //Gestion longueur infini
        while (password.length() <= Passgenerator.m_minlength) {
            password += sha256(password);
        }
        password = password.substring(0, Passgenerator.m_minlength);

        //Uppercase (forced length -2 index)
        if (Passgenerator.m_needupercase) {
            Pattern p = Pattern.compile("[a-z]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(password);
            if (m.find()) {
                int index = m.start();
                password = password.substring(0, index) + ("" + password.charAt(index)).toUpperCase() + password.substring(index + 1);
            } else {
                int up = (Input.length() + Passgenerator.m_minlength) % 26;
                String upchar = String.valueOf(up);
                char backup = password.charAt(password.length() - 1);
                password = password.substring(0, password.length() - 2);
                password += upchar + backup;
            }
        }

        //Special char  (forced length -1 index)
        if (Passgenerator.m_needspecialchar) {
            char[] spechar = {'!', '#', '$', '%', '&', '(', ')', '*', '+', ',', '-', '.', '/', ';', ':', '<', '>', '=', '?', '@', '[', ']', '\\', '/', '^', '_', '{', '}', '~'};
            int sel = (Input.length() + Passgenerator.m_minlength) % spechar.length;
            char selc = spechar[sel];
            password = password.substring(0, password.length() - 1);
            password += selc;
        }

        return password;
    }
}
