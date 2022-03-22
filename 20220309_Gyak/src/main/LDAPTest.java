package main;


import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchResults;

public class LDAPTest {
	
    private static Scanner input = new Scanner(System.in);
	
	@SuppressWarnings("unchecked")
    public static void main(String[] args) {
		final String baseDN = "ou=csop_HU,ou=ev2022,ou=meinfo,dc=maxcrc,dc=com";
        final String neptune = readNCode();
        final String passwd = readPasswd();

        boolean connected = ldapAuth("193.6.5.58", baseDN, neptune, passwd);

        if (connected) {
            System.out.println("You are now root!");
        } else {
            System.out.println("Better luck next time, pal!");

        }
    }

    public static boolean ldapAuth(String host, String baseDN, String neptune, String passwd) {
        try {
            var ldapConnectionBuilder = new Connection.ConnectionBuilder(host, "cn=" + neptune + "," + baseDN,
                    passwd.getBytes());
            ldapConnectionBuilder.build();
            return true;
        } catch (LDAPException e) {
            return false;
        }
    }

    public static String readNCode() {
        boolean ok;
        String ncode = new String();
        Pattern ncodePattern = Pattern.compile("^[a-zA-Z\\d]{6}$", Pattern.CASE_INSENSITIVE);
        do {
            ok = true;
            System.out.println("Enter your Neptune code: ");
            ncode = input.nextLine();
            Matcher matcher = ncodePattern.matcher(ncode);
            ok = matcher.find();
            if (!ok) {
                System.out.println("Bad input format!");
            }
        } while (!ok);

        return ncode;
    }

    public static String readPasswd() {
        String passwd = new String();
        System.out.println("Enter your password: ");
        passwd = input.nextLine();
        return passwd;
    }
    
}
