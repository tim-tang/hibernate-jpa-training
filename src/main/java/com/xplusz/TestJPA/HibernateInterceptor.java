package com.xplusz.TestJPA;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for hibernate interceptor.
 */
public class HibernateInterceptor extends EmptyInterceptor {

    private Logger logger = LoggerFactory.getLogger(HibernateInterceptor.class.getSimpleName());

    private static final long serialVersionUID = -6142366297876169168L;

    /**
     * (non-Javadoc)
     * 
     * @see org.hibernate.EmptyInterceptor#onFlushDirty(java.lang.Object,
     *      java.io.Serializable, java.lang.Object[], java.lang.Object[],
     *      java.lang.String[], org.hibernate.type.Type[])
     */
    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
            String[] propertyNames, Type[] types) {

        logger.info("====== OnFlushDirty Fired======");
        return false;

    }

    /**
     * (non-Javadoc)
     * 
     * @see org.hibernate.EmptyInterceptor#onDelete(Object,
     *      java.io.Serializable, Object[], String[], org.hibernate.type.Type[])
     */
    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        logger.info("======= OnDelete Fired ======");
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.hibernate.EmptyInterceptor#onSave(Object, java.io.Serializable,
     *      Object[], String[], org.hibernate.type.Type[])
     */
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        logger.info("====== OnSave Fired =====");
        return false;
    }

}
