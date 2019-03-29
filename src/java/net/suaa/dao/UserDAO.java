package net.suaa.dao;

import net.suaa.base.GenericDAO;
import net.suaa.domain.User;
import org.springframework.stereotype.Repository;

@Repository("userDAO")
public class UserDAO extends GenericDAO<User> {
}
