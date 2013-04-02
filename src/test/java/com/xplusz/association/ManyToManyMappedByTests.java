package com.xplusz.association;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.xplusz.TestJPA.domain.Item;
import com.xplusz.TestJPA.domain.Tag;

/**
 * 
 * @author angelochen
 * @author timtang
 */
@ContextConfiguration(locations = { "classpath:META-INF/spring/app-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ManyToManyMappedByTests {

    @PersistenceContext
    private EntityManager entityManager;
    
    private Item item1;
    
    private Tag tag1;
    
    private Tag tag2;
    
    @Before
    public void setup(){
        item1 = new Item();
        item1.setName("item1");
        tag1 = new Tag();
        tag1.setName("tag1");
        tag2 = new Tag();
        tag2.setName("tag2");
        item1.addTag(tag1);
        item1.addTag(tag2);
    }

    /**
     * only remove associated table relationship.
     * 
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testSaveItems() throws Exception {
        entityManager.persist(item1);
        
        // will flush SQL to database. but transaction will not commit.
        entityManager.flush();
        // clear entities in entity manager. entities become detached.
//        entityManager.clear();
        
        
//        item1.removeTag(tag1);
        // will not drop associated table relationship. cause tag is not owner.
//        tag1.getItems().remove(item1);
//        entityManager.merge(item1);
        
        // will reload entity from database while other change entity state in database.
//        entityManager.refresh(item1);
    }
    
    /**
     * Not persist items for unidirection relationship.
     * 
     * Item data will not be inserted.
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testSaveTags() throws Exception {
        entityManager.persist(tag1);
        entityManager.persist(tag2);
    }
    
    /**
     * Test whether delete all when update association with persist.
     * 
     * Will remove existing tag collection and reset the tag relationship.
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testResetTags(){
//        tag1.getItems().remove(item1);
//        tag2.getItems().remove(item1);
        entityManager.persist(item1);
        
        
        entityManager.flush();
        entityManager.clear();
             
        Set<Tag> tags = new HashSet<Tag>();
        Tag tag3 = new Tag();
        tag3.setName("tag3");
        tags.add(tag3);
        
        //reset tags
        item1.setTags(tags);
        
        entityManager.merge(item1);
    }
    
    /**
     * Test whether delete all when update association without persist.
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testResetTagsWithoutPersist(){

        Item item = (Item) entityManager.createQuery("from Item").getResultList().get(0);
        
        
        // assign managed entity to a transient entity.
        Tag tag1 = null;
        for(Tag tag: item.getTags()){
            if(tag.getName().equals("tag1")){
                tag1 = tag;
                break;
            }
        }
        
        
        // transient entity.
        Tag tag3 = new Tag();
        tag3.setName("tag3");
             
//        Set<Tag> tags = new HashSet<Tag>();
//        tags.add(tag1);
//        tags.add(tag3);
        //reset tags
//        item.setTags(tags);
        
        //will use the original set will not delete tag2 relation
        item.getTags().clear();
        item.addTag(tag1);
        item.addTag(tag3);
        
        entityManager.merge(item);
        
        
    }


}
