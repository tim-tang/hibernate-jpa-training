package com.xplusz.TestJPA;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.xplusz.TestJPA.domain.Account;
import com.xplusz.TestJPA.domain.Tag;

/**
 * Introduce to JPA state transition.
 * 
 * @author timtang
 *
 */

@ContextConfiguration(locations = { "classpath:META-INF/spring/app-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@Transactional
public class JPAStateTransition {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private Tag tag;
    
    @Before
    public void setup(){
        tag = new Tag();
        tag.setName("tag1");
        
    }
    
    /**
     * Can not persist detached object. 
     */
    @Test
    @Rollback(false)
    public void testPersist(){
        entityManager.persist(tag);
        entityManager.detach(tag);
        entityManager.persist(tag);
        entityManager.flush();
    }
    
    /**
     * If remove a detached entity will throw IllegalArgumentException. 
     */
    @Test
    @Rollback(false)
    public void testRefresh(){
        entityManager.persist(tag);
        entityManager.detach(tag);
        // record is managed state.
//        Tag record = (Tag) entityManager.createQuery("from Tag").getResultList().get(0);
        entityManager.refresh(tag);
    }
    
    /**
     * If remove a detached entity will throw IllegalArgumentException. 
     * 
     * 
     */
    @Test
    @Rollback(false)
    public void testRemove(){
        entityManager.persist(tag);
        entityManager.detach(tag);
        entityManager.remove(tag);
    }
    
    /**
     * Detached or transient object will be saveOrUpdated.
     */
    @Test
    @Rollback(false)
    public void testMerge(){
//        entityManager.persist(tag);
        Tag record = (Tag) entityManager.createQuery("from Tag").getResultList().get(0);
        entityManager.detach(record);
        record.setName("33");
        entityManager.merge(record);
    }
}
