package net.suaa.domain;

import net.suaa.core.annotation.Lock;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="school_user")
public class User extends Identity
        implements UserDetails {
    private static final long serialVersionUID = 8026813053768023527L;
    //用户名
    private String userName;
    //真实名称
    private String trueName;
    //密码
    @Lock
    private String password;
    //用户角色
    private String userRole;
    //年龄
    @Column(columnDefinition = "int default 0")
    private int years;
    //性别
    private int sex;
    //有限
    private String email;
    //配置
    @OneToOne(mappedBy = "user")
    private UserConfig config;

    @Transient
    private Map<String, List<Res>> roleResources;

    @Transient
    private GrantedAuthority[] authorities = new GrantedAuthority[0];

    //角色
    @ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY)
    @JoinTable(name = "school_user_role", joinColumns = {@javax.persistence.JoinColumn(name = "user_id")}, inverseJoinColumns = {@javax.persistence.JoinColumn(name = "role_id")})
    private Set<Role> roles = new TreeSet();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "school_user_classify", joinColumns = {@javax.persistence.JoinColumn(name = "user_id")}, inverseJoinColumns = {@javax.persistence.JoinColumn(name = "classify_id")})
    private List<Classify> cus = new ArrayList();

    @OneToMany(mappedBy = "user")
    private List<News> news = new ArrayList<>();
    public Set<Role> getRoles() {
        return roles;
    }

    public List<Classify> getCus() {
        return cus;
    }

    public String getUserName() {
        return userName;
    }
    public String getUsername(){
        return this.userName;
    }
    public boolean isEnabled(){
        return true;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserConfig getConfig() {
        return config;
    }

    public void setConfig(UserConfig config) {
        this.config = config;
    }

    public GrantedAuthority[] getAuthoritiess(){
        return this.authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public void setAuthorities(GrantedAuthority[] authorities) {
        this.authorities = authorities;
    }


    public GrantedAuthority[] get_all_Authorities(){
        List grantedAuthorities = new ArrayList(
                this.roles.size());
        for (Role role : this.roles){
            grantedAuthorities
                    .add(new SimpleGrantedAuthority(role.getRoleCode()));
        }
        return (GrantedAuthority[])grantedAuthorities.toArray(new GrantedAuthority[this.roles.size()]);
    }

    public GrantedAuthority[] get_common_Authorities(){
        List grantedAuthorities = new ArrayList(
                this.roles.size());
        for (Role role : this.roles){
            if (!role.getType().equals("ADMIN"))
                grantedAuthorities.add(
                        new SimpleGrantedAuthority(role
                                .getRoleCode()));
        }
        return
                (GrantedAuthority[])grantedAuthorities
                        .toArray(new GrantedAuthority[grantedAuthorities.size()]);
    }

    public String getAuthoritiesString(){
        List authorities = new ArrayList();
        for (GrantedAuthority authority : getAuthorities()){
            authorities.add(authority.getAuthority());
        }
        return StringUtils.join(authorities.toArray(), ",");
    }

    public boolean isAccountNonExpired(){
        return true;
    }

    public boolean isAccountNonLocked(){
        return true;
    }

    public boolean isCredentialsNonExpired(){
        return true;
    }

    public Map<String, List<Res>> getRoleResources(){
        if (this.roleResources == null){
            this.roleResources = new HashMap();

            for (Role role : this.roles){
                String roleCode = role.getRoleCode();
                List<Res> ress = role.getReses();
                for (Res res : ress){
                    String key = roleCode + "_" + res.getType();
                    if (!this.roleResources.containsKey(key)){
                        this.roleResources.put(key, new ArrayList());
                    }
                    ((List)this.roleResources.get(key)).add(res);
                }
            }
        }

        return this.roleResources;
    }


}
