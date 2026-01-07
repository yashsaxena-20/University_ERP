package edu.univ.erp.auth;
import edu.univ.erp.domain.User;
public class AuthService
{
    public User login(String name,String pass)
    {
        AuthRepository repo=new AuthRepository();
        User u=repo.getUser(name);
        if(u!=null)
        {
            if(PasswordUtil.check(pass,u.getHash()))
            {
                repo.updateLastLogin(u.getUid());
                return u;
            }
        }
        return null;
    }
    public String changePassword(long uid,String oldPass,String newPass)
    {
        AuthRepository repo=new AuthRepository();
        String currentHash=repo.getHashById(uid); 
        if(PasswordUtil.check(oldPass,currentHash))
        {
            String newHash=PasswordUtil.hash(newPass);
            if(repo.updateHash(uid,newHash))
            {
                return "SUCCESS";
            }
            return "DATABASE_ERROR";
        }
        return "OLD_PASS_INCORRECT";
    }
}