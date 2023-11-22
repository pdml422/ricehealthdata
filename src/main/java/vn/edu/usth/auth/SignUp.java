package vn.edu.usth.auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SignUp {
    public static void main(String[] args){
        String username, name, Email, Password, role, query;
        String SUrl, SUser, SPass;
        SUrl = "jdbc:mysql://100.96.184.148:3306/ricehealthdata";
        SUser = "g5";
        SPass = "ricehealthdata";

        Scanner signup = new Scanner(System.in);
        System.out.println("Enter email - username/name - password");

        String email = signup.nextLine();
        String fname = signup.nextLine();
        String password = signup.nextLine();


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

    }//GEN-LAST:event_SignUpBtnActionPerformed
}

