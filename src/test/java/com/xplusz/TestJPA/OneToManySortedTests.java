package com.xplusz.TestJPA;

import static org.junit.Assert.fail;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.xplusz.TestJPA.domain.Category;
import com.xplusz.TestJPA.domain.PointOfSale;


/**
 * @author timtang
 *
 */
@ContextConfiguration(locations = { "classpath:META-INF/spring/app-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@Transactional
public class OneToManySortedTests {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private PointOfSale pos;
    
    private Category category1;
    
    private Category category2;
    
    @Before
    public void setup(){
        pos = new PointOfSale();
        pos.setName("pos");
        
        category1 = new Category();
        category1.setName("category1");
        category1.setPriority(1);
        
        category2 = new Category();
        category2.setName("category2");
        category2.setPriority(2);
        
        pos.addCategory(category1);
        pos.addCategory(category2);
    }
    
    
    @Test
    @Rollback(true)
    public void testMappedBy(){
        entityManager.persist(pos);
        entityManager.flush();
    }
    
    /**
     * Not use mappedBy.
     */
    @SuppressWarnings("unchecked")
    @Test
    @Rollback(true)
    public void testPriority(){
        entityManager.persist(pos);
        
        // hibernate 1 level cache.
        // flush cache into database.
        entityManager.flush();
        // clear cache.
        entityManager.clear();
        
        List<PointOfSale> list =  entityManager.createQuery("from PointOfSale").getResultList();
        
        if(!CollectionUtils.isEmpty(list)){
            PointOfSale result = list.get(0);
            Assert.assertTrue(result.getCategories().get(0).getPriority()==1);
        }else{
            fail();
        }
    }
    
    /**
     * Not use mappedBy.
     * 
     * Do not generate associated table data.
     */
    @SuppressWarnings("unchecked")
    @Test
    @Rollback(true)
    public void testPriorityWithMappedBy(){
        
        entityManager.persist(pos);
//        entityManager.persist(category1);
//        entityManager.persist(category2);
        
        entityManager.flush();
        entityManager.clear();
        
        List<PointOfSale> list =  entityManager.createQuery("from PointOfSale").getResultList();
        
        if(!CollectionUtils.isEmpty(list)){
            PointOfSale result = list.get(0);
            Assert.assertTrue(result.getCategories().get(0).getPriority()==1);
        }else{
            fail();
        }
        
    }

}
