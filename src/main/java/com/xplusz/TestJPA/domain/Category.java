package com.xplusz.TestJPA.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


@Entity
public class Category {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    @ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="pointOfSale")
    private PointOfSale pointOfSale;
    
    private int priority;
    
    
    public int getPriority() {
        return priority;
    }


    
    public void setPriority(int priority) {
        this.priority = priority;
    }


    public Long getId() {
        return id;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public PointOfSale getPointOfSale() {
        return pointOfSale;
    }

    
    public void setPointOfSale(PointOfSale pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Category){
            Category category = (Category)obj;
            return new EqualsBuilder().append(this.name, category.getName()).isEquals();
        }
        return false;
    }
}
