package net.suaa.dao;

import net.suaa.base.GenericDAO;
import net.suaa.domain.News;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository("newsDAO")
public class NewsDAO extends GenericDAO<News> {
}
