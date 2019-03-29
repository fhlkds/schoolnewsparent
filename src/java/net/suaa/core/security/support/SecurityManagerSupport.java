package net.suaa.core.security.support;

import net.suaa.domain.Res;
import net.suaa.domain.Role;
import net.suaa.domain.User;
import net.suaa.service.IResService;
import net.suaa.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

public class SecurityManagerSupport implements UserDetailsService,net.suaa.core.security.SecurityManager{
    @Autowired
    private IUserService userService;

    @Autowired
    private IResService resService;

    public UserDetails loadUserByUsername(String data) throws UsernameNotFoundException, DataAccessException {
        String[] list = data.split(",");
        String userName = list[0];
        String loginRole = "user";
        if (list.length == 2){
            loginRole = list[1];
        }
        Map params = new HashMap();
        String hSql_username = "select obj from User obj where obj.userName =:userName ";
        String hSql_mobile = "select obj from User obj where obj.mobile =:mobile ";
        params.put("mobile", userName);
        List users = this.userService.query(hSql_mobile, params, -1, -1);
        if (users.isEmpty()){
            params.clear();
            params.put("userName", userName);
            users = this.userService.query(hSql_username, params, -1, -1);
        }
        if (users.isEmpty()){
            throw new UsernameNotFoundException("User " + userName +
                    " has no GrantedAuthority");
        }
        User user = (User)users.get(0);
        Set authorities = new HashSet();
        if ((!user.getRoles().isEmpty()) && (user.getRoles() != null)){
            Iterator roleIterator = user.getRoles().iterator();
            while (roleIterator.hasNext()){
                Role role = (Role)roleIterator.next();
                if (loginRole.equalsIgnoreCase("ADMIN")){
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
                            role.getRoleCode().toUpperCase());
                    authorities.add(grantedAuthority);
                }else if (!role.getType().equals("ADMIN")){
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
                            role.getRoleCode().toUpperCase());
                    authorities.add(grantedAuthority);
                }
            }
        }

        GrantedAuthority[] auths = new GrantedAuthority[authorities.size()];
        user.setAuthorities((GrantedAuthority[])authorities.toArray(auths));

        return user;
    }

    public Map<String, String> loadUrlAuthorities(){
        Map<String, String> urlAuthorities = new HashMap<String, String>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("type", "URL");
        List<Res> urlResources = this.resService.query(
                "select obj from Res obj where obj.type = :type", params, -1,
                -1);
        for (Res res : urlResources){
            urlAuthorities.put(res.getValue(), res.getRoleAuthorities());
        }

        return urlAuthorities;
    }

}
