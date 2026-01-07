package edu.univ.erp.auth;
import org.mindrot.jbcrypt.BCrypt;
public class PasswordUtil
{
    public static String hash(String pass)
    {
        return BCrypt.hashpw(pass,BCrypt.gensalt()); //generates Hash fro password
    }
    public static boolean check(String pass,String hash)
    {
        return BCrypt.checkpw(pass,hash); //checks hass with password
    }
}