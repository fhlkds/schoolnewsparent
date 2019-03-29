package net.suaa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "school_userconfig")
public class UserConfig extends Identity{


    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    public User getUser(){
        return this.user;
    }

    public void setUser(User user){
        this.user = user;
    }

}
