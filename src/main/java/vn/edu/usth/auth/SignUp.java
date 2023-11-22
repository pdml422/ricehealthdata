package vn.edu.usth.auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SignUp {
    public static void main(String[] args) throws Exception {
        String username, name, Email, Password, role, query;
        String SUrl, SUser, SPass;
        SUrl = "jdbc:mysql://100.96.184.148:3306/ricehealthdata";
        SUser = "g5";
        SPass = "ricehealthdata";

        Scanner signup = new Scanner(System.in);
        System.out.println("Enter email - username/name - password");

        String email = signup.nextLine();
        String fname = signup.nextLine();
        String originalText = signup.nextLine();

        SecretKey secretKey = generateSecretKey();

        // Encrypt the text
        String password = encrypt(originalText, secretKey);


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(SUrl, SUser, SPass);
            Statement st = con.createStatement();
            if("".equals(fname)){
                JOptionPane.showMessageDialog(new JFrame(), "Username is require", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }else if("".equals(email)){
                JOptionPane.showMessageDialog(new JFrame(), "Email Address is require", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }else if("".equals(password)){
                JOptionPane.showMessageDialog(new JFrame(), "Password is require", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }else {
                username = fname;
                name = fname;
                Email    = email;
                Password = password;
                role = "user";

                System.out.println(Password);
                System.out.println("New account has been created successfully!");

                query = "INSERT INTO user(username, email, password, name, role)"+
                        "VALUES('"+username+"', '"+Email+"' , '"+Password+"', '"+name+"', '"+role+"')";

                st.execute(query);
            }
        }catch(Exception e){
            System.out.println("Error!" + e.getMessage());
        }

    }
    private static SecretKey generateSecretKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }

    private static String encrypt(String text, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

}

