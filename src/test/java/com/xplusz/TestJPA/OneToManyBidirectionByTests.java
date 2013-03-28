package com.xplusz.TestJPA;

import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
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
 * PreConfiguration: not used MappedBy
 * 
 * 
 * @author angelochen
 * @author timtang
 *
 */
@ContextConfiguration(locations = { "classpath:META-INF/spring/app-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@Transactional
public class OneToManyBidirectionByTests {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private Account account;
    
    @Before
    public void setup(){
        account = new Account();
        account.setAccountId(1L);
        Character character1 = new Character();
        character1.setCharacterId(1L);
        Character character2 = new Character();
        character2.setCharacterId(2L);
        
        Set<Character> characters = new HashSet<Character>();
        characters.add(character1);
        characters.add(character2);
        
        account.setCharacters(characters);
        
    }
    
    @Test
    @Rollback(false)
    public void testSaveWithInvalidHashCode(){
        System.out.println("=======" + account.getCharacters().size() + "=========");
        entityManager.persist(account);
    }
    
    /**
     * PreConfiguration --
     *    Make sure the hash code is correct in the entity Character.
     *    1. not set orphanRemoval=true to demo set null 
     *    2. set orphanRemoval=true to demo orphanRemove.
     */
    @Test
    @Rollback(false)
    public void testSave(){
        entityManager.persist(account);
        entityManager.flush();
        entityManager.clear();
//        List<Account> accounts = entityManager.createQuery("from Account a left join fetch a.characters").getResultList();
        List<Account> accounts = entityManager.createQuery("from Account").getResultList();
        Account account = accounts.get(0);
        if(account!=null){
            Assert.assertTrue(account.getCharacters().size()==2);
        }else{
            fail();
        }
        
        Character character3 = new Character();
        character3.setCharacterId(3L);
        
        Character character1 = null;
        for(Character character: account.getCharacters()){
            if(character.getCharacterId()==1){
                character1 = character;
            }
        }
        
        account.getCharacters().add(character3);
        account.getCharacters().remove(character1);
        
        entityManager.merge(account);
             
    }
    
    @Test
    @Rollback(false)
    @Ignore
    public void testSaveBidirection(){
        // build bidirection relationship.
//        for(Character character: account.getCharacters()){
//            character.setAccount(account);
//        }
        entityManager.persist(account);
        Character character = account.getCharacters().iterator().next();
        
        Assert.assertTrue(character.getAccount()==null);
    }
    
    /**
     * Follow by the results of testSave. Should modify the id of account.
     * 
     * Merge method will persist/update instance.
     */
    @Test
    @Rollback(false)
    @Ignore
    public void testSaveWhenResetCharacters(){
        
        
        Account account = new Account();
        account.setAccountId(1L);
        
        //TODO: modify the id here
        account.setId(211L);
           
        Character character3 = new Character();
        character3.setCharacterId(4L);
              
        Set<Character> characters = new HashSet<Character>();
        characters.add(character3);
        account.setCharacters(characters);
        
        entityManager.merge(account);
    }

}
