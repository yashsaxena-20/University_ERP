package edu.univ.erp.auth;
import edu.univ.erp.domain.User;
public class UserSession
{
    private static UserSession obj;
    private User user;
    private UserSession()
    {
    }
    public static UserSession get()
    {
        if(obj==null)
        {
            obj=new UserSession();
        }
        return obj;
    }
    public User getUser()
    {
        return user;
    }
    public void setUser(User user)
    {
        this.user=user;
    }
}