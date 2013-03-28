package com.xplusz.TestJPA.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.HashCodeBuilder;


@Entity
public class Account implements Serializable{

    private static final long serialVersionUID = 5787601662225954450L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(nullable = false, unique = true)
    private long accountId;
    
//    @OneToMany(cascade=CascadeType.ALL)
    @OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
//    @OneToMany(cascade=CascadeType.ALL, mappedBy="account", orphanRemoval=true)
    @JoinColumn(name="account_id")  //-- will not generate associated table.
    private Set<Character> characters = new HashSet<Character>(0);
    
    public long getId() {
        return id;
    }

    
    public void setId(long id) {
        this.id = id;
    }

    
    public long getAccountId() {
        return accountId;
    }

    
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    
    public Set<Character> getCharacters() {
        return characters;
    }

    
    public void setCharacters(Set<Character> characters) {
        this.characters = characters;
    }
    
    public void addCharacter(Character character){
        this.characters.add(character);
        character.setAccount(this);
    }
    
    public void removeCharacter(Character character){
        this.characters.remove(character);
        character.setAccount(null);
    }
    
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getClass()).append(getAccountId()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Account) {
            return ((Account) obj).getAccountId() == getAccountId();
        }
        return false;
    }
    

}
