/**
 * 
 */
package com.xplusz.criteria;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author timtang
 *
 */
@ContextConfiguration(locations = { "classpath:META-INF/spring/app-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class CriteriaTests {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    
    @Test
    @Rollback(false)
    public void testCriteria(){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        //TODO:
    }
}
