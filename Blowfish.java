import javax.crypto.*;
import java.util.*;

class Blowfish {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        KeyGenerator kg = KeyGenerator.getInstance("Blowfish");
        SecretKey key = kg.generateKey();

        Cipher c = Cipher.getInstance("Blowfish");

        System.out.print("Enter text: ");
        String msg = sc.nextLine();

        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] enc = c.doFinal(msg.getBytes());
        System.out.println("Encrypted: " + Base64.getEncoder().encodeToString(enc));

        c.init(Cipher.DECRYPT_MODE, key);
        System.out.println("Decrypted: " + new String(c.doFinal(enc)));
    }
}




import java.security.*;

public class MD5 {
    public static void main(String[] args) throws Exception {
        String text = "Hello World";

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(text.getBytes());

        for (byte b : digest)
            System.out.printf("%02x", b);

    }
}
