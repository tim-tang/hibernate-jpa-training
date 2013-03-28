package com.xplusz.TestJPA.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.apache.commons.lang.builder.HashCodeBuilder;


@Entity
public class Wallet implements Serializable{

    private static final long serialVersionUID = 1060330615210368841L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private String currency;

    private Long hardBalance;

    private Long softBalance;
    
    @OneToOne
    private Character character;

    
    public long getId() {
        return id;
    }

    
    public void setId(long id) {
        this.id = id;
    }

    
    public String getCurrency() {
        return currency;
    }

    
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    
    public Long getHardBalance() {
        return hardBalance;
    }

    
    public void setHardBalance(Long hardBalance) {
        this.hardBalance = hardBalance;
    }

    
    public Long getSoftBalance() {
        return softBalance;
    }

    
    public void setSoftBalance(Long softBalance) {
        this.softBalance = softBalance;
    }
    
    public Character getCharacter(){
        return this.character;
    }
    
    public void setCharacter(Character character){
        this.character = character;
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getClass()).append(getId()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Wallet) {
            return ((Wallet) obj).getId() == getId();
        }
        return false;
    }

}
