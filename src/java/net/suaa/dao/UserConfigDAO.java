package net.suaa.dao;

import net.suaa.base.GenericDAO;
import net.suaa.domain.UserConfig;
import org.springframework.stereotype.Repository;

@Repository("userConfigDAO")
public class UserConfigDAO extends GenericDAO<UserConfig> {
}
