package vn.vccb.mssurveykpi.dao.impl;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import vn.vccb.mssurveykpi.util.CheckUtil;

import javax.persistence.EntityManager;

public abstract class DaoImpl {

    @Autowired
    private EntityManager entityManager;

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    protected Criteria createCriteria(Class clazz) {
        return createCriteria(clazz, null);
    }

    protected Criteria createCriteria(Class clazz, String alias) {
        Session session;

        session = getSession();

        if (CheckUtil.isNullOrEmpty(alias)) {
            return session.createCriteria(clazz);
        }

        return session.createCriteria(clazz, alias);
    }
}
