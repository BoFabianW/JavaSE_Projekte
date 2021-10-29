package sample;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PwmCrypto {

    /**
     * Salt dient zur Herstellung eines großen Satzes von Schlüsseln
     */
    static String secretKey = " Sicherheitsschlüssel ";

    /**
     * Kodieren eines Passwortes mit einem Gipher-Objekt mit AES und 256 Bit l&auml;nge
     *
     * @param strPWCodieren = Der zu codierende Wert (PW)
     * @param saltListName  = Zusatz zum Hashwert (ListName)
     * @return = String/null
     */
    public static String codieren(String strPWCodieren, String saltListName) {
        try {

            byte[] ivps = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(ivps);

            // Art der Verschlüsselung.
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), saltListName.getBytes(), 65536, 256);
            SecretKey temp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(temp.getEncoded(), "AES");


            // Erzeugung des gewünschten Verschlüsselungsalogrithmus AES durch die getInstance()-Methode
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            /*
             * Cipher - Kern des Java Cryptografic Extension (JCE) Klasse zur Ver- und Entschl&uumlsselung
             * cetInstance - mit Transformationsform mit "Auff&uumlllschema"
             * (Advanced Encryption Standard mit 256Bit /
             * CBC - Cipher Block Chaining - Betriebsart Klartextblock wird vor dem Verschlüsseln mit dem im verherigen
             * Schritt erzeugten Geheimtexxtblock per XOR (Exkl. Oder) verknüpft.
             *
             * PKCS5Padding - Public-Key Cryptografy Standards (PKCS) Passwortbasierte Kryptografiespezifikation
             * asymmetrische Verschlüsselungen - #5 - Verschlüsselung wird aus dem Passwort abgeleitet
             */
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);

            return Base64.getEncoder().encodeToString(cipher.doFinal(strPWCodieren.getBytes(StandardCharsets.UTF_8)));

        } catch (Exception e) {
            System.out.println("Fehler beim codieren: " + e);
        }
        return null;
    }

    /**
     * Dekodieren eines Passwortes mit einem Gipher-Objekt mit AES und 256 Bit l&auml;nge
     * @param strDecodieren = der zu Decodierende Wert
     * @param salt          = Zusatz zum decodieren
     * @return = String/null
     */
    public static String decodieren(String strDecodieren, String salt) {
        try {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            // Art der Verschlüsselung.
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strDecodieren)));
        }
        catch (Exception e) {
            System.out.println("Fehler beim decodieren: " + e);
        }
        return null;
    }
}