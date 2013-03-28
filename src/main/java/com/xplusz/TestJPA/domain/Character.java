package com.xplusz.TestJPA.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
public class Character implements Serializable {

    private static final long serialVersionUID = 8870531252608920252L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private long characterId;

    @OneToOne(cascade = CascadeType.ALL)
    private Wallet wallet;
    
    @ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="account_id")
    private Account account;
    
    public Account getAccount(){
        return this.account;
    }
    
    public void setAccount(Account account){
        this.account = account;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCharacterId() {
        return characterId;
    }

    public void setCharacterId(long characterId) {
        this.characterId = characterId;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getClass()).append(getCharacterId()).toHashCode();
        //return new HashCodeBuilder().append(getId()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Character) {
            //return ((Character) obj).getId() == getId();
            return ((Character) obj).getCharacterId() == getCharacterId();
        }
        return false;
    }

}
