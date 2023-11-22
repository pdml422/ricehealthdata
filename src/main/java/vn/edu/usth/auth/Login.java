package vn.edu.usth.auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Login {
    public static void main(String[] args){
            String Email, Password, query, fname = null, passDb = null;
            String SUrl, SUser, SPass;
            SUrl = "jdbc:mysql://100.96.184.148:3306/ricehealthdata";
            SUser = "g5";
            SPass = "ricehealthdata";
            int notFound = 0;

            Scanner login = new Scanner(System.in);
            System.out.println("Enter email and password:");

            String email = login.nextLine();
            String password = login.nextLine();

            try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(SUrl, SUser, SPass);
            Statement st = con.createStatement();
            if("".equals(email)){
                System.out.println("Enter your email");
            }else if("".equals(password)){
                System.out.println("Enter your password");
            }else {
                Email    = email;
                Password = password;

                query = "SELECT * FROM user WHERE email= '"+Email+"'";

                ResultSet rs = st.executeQuery(query);
                while(rs.next()){
                    passDb = rs.getString("password");
                    fname = rs.getString("username");
                    notFound = 1;
                }
                if(notFound == 1 && Password.equals(passDb)){
                    System.out.println("Welcome!");
                }else{
                    JOptionPane.showMessageDialog(new JFrame(), "Incorrect email or password", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }catch(Exception e){
            System.out.println("Error!" + e.getMessage());
        }

    }
}
