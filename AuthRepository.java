package edu.univ.erp.auth;
import edu.univ.erp.domain.User;
import edu.univ.erp.data.DbConnection;
import java.sql.*;
public class AuthRepository
{
    public User getUser(String name)
    {
        try
        {
            Connection c=DbConnection.getAuth();
            PreparedStatement s=c.prepareStatement("SELECT * FROM users_auth WHERE username=?");
            s.setString(1,name);
            ResultSet rs=s.executeQuery();
            if(rs.next())
            {
                User u=new User(rs.getLong("user_id"),rs.getString("role"));
                u.setHash(rs.getString("password_hash"));
                return u;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public long addUser(String name,String hash,String role)
    {
        long id=0;
        try
        {
            Connection c=DbConnection.getAuth();
            PreparedStatement s=c.prepareStatement("INSERT INTO users_auth (username,password_hash,role) VALUES (?,?,?)",Statement.RETURN_GENERATED_KEYS);
            s.setString(1,name);
            s.setString(2,hash);
            s.setString(3,role);
            s.executeUpdate();
            ResultSet rs=s.getGeneratedKeys();
            if(rs.next())
            {
                id=rs.getLong(1);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return id;
    }
    public boolean updateHash(long uid,String newHash)
    {
        try
        {
            Connection c=DbConnection.getAuth();
            PreparedStatement s=c.prepareStatement("UPDATE users_auth SET password_hash=? WHERE user_id=?");
            s.setString(1,newHash);
            s.setLong(2,uid);
            return s.executeUpdate()>0;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public String getHashById(long uid)
    {
        try
        {
            Connection c=DbConnection.getAuth();
            PreparedStatement s=c.prepareStatement("SELECT password_hash FROM users_auth WHERE user_id=?");
            s.setLong(1,uid);
            ResultSet rs=s.executeQuery();
            if(rs.next())
            {
                return rs.getString("password_hash");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public String getUsernameById(long uid)
    {
        try
        {
            Connection c=DbConnection.getAuth();
            PreparedStatement s=c.prepareStatement("SELECT username FROM users_auth WHERE user_id=?");
            s.setLong(1,uid);
            ResultSet rs=s.executeQuery();
            if(rs.next())
            {
                return rs.getString("username");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "Unknown Instructor"; // Default fallback
    }
    public void updateLastLogin(long uid)
    {
        try
        {
            Connection c=DbConnection.getAuth();
            PreparedStatement s=c.prepareStatement("UPDATE users_auth SET last_login=NOW() WHERE user_id=?");
            s.setLong(1,uid);
            s.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}