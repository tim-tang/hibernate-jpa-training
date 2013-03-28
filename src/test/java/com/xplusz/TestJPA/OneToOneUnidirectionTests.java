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

import com.xplusz.TestJPA.domain.Character;
import com.xplusz.TestJPA.domain.Wallet;



/**
 * Unidirection test.
 * PreConfiguration:
 * 
 * Comment character in entity wallet. 
 * 
 * @author angelochen
 * @author timtang
 *
 */
@ContextConfiguration(locations = { "classpath:META-INF/spring/app-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@Transactional
public class OneToOneUnidirectionTests {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private Character character;
    
    @Before
    public void setup(){
        // initial character
        character = new Character();
        character.setCharacterId(100000L);
        
        //initial wallet
        Wallet wallet = new Wallet();
        wallet.setCurrency("RMTC");
        wallet.setHardBalance(1L);
        wallet.setSoftBalance(1L);
        
        //set wallet to character
        character.setWallet(wallet);
        
    }
    
    /**
     * Generate 2 insertion SQL. 
     */
    @Test
    @Rollback(false)
    public void testSave(){
        entityManager.persist(character);       
    }
    
    /**
     * Generate 2 SQL. (N+1)
     */
    @Test
    @Rollback(false)
    public void testGet(){
        List<Character> characters = entityManager.createQuery("from Character").getResultList();
        Character character = characters.get(0);
        Wallet wallet = null;
        if(character!=null){
            wallet = character.getWallet();
            wallet.getCurrency();
        }       
    }
    
    /**
     * Generate 1 SQL. 
     */
    @Test
    @Rollback(false)
    public void testGetJoin(){
        List<Character> characters = entityManager.createQuery("from Character c join fetch c.wallet").getResultList();
        Character character = characters.get(0);
        Wallet wallet = null;
        if(character!=null){
            wallet = character.getWallet();
            wallet.getCurrency();
        } 
    }
    
    /**
     * Generate 2 deletion SQL.
     */
    @Test
    @Rollback(false)
    public void testRemove(){
        List<Character> characters = entityManager.createQuery("from Character").getResultList();
        Character character = characters.get(0);
        if(character!=null){
            entityManager.remove(character);
        }
    }
}
