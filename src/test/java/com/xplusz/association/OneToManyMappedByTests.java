package com.xplusz.association;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.xplusz.TestJPA.domain.Account;
import com.xplusz.TestJPA.domain.Character;


/**
 * Bidirection test.
 * PreConfiguration: used MappedBy
 * 
 * 
 * @author angelochen
 * @author timtang
 */
@ContextConfiguration(locations = { "classpath:META-INF/spring/app-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@Transactional
public class OneToManyMappedByTests {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private Account account;
    
    private Character character1;
    
    private Character character2;
    
    @Before
    public void setup(){
        account = new Account();
        account.setAccountId(1L);
        
        character1 = new Character();
        character1.setCharacterId(1L);
        character2 = new Character();
        character2.setCharacterId(2L);
        
        account.addCharacter(character1);
        account.addCharacter(character2);
        
    }
    
    /**
     * Will not remove the related entities when use mappedBy.
     * 
     * Use mappedBy will not insert into associated table.
     */
    @Test
    @Rollback(false)
    public void testSaveAccount(){
        entityManager.persist(account);
        
        
        for(Character character: account.getCharacters()){
            if(character.getCharacterId()==1){
                //if change unique key in entity can not remove the entity.
                character.setCharacterId(100L);
                account.getCharacters().remove(character);
            }
        }
        
        Assert.assertTrue(account.getCharacters().size()==2);
        
        entityManager.merge(account);
        
    }

}
