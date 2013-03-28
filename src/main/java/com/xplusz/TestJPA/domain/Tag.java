package com.xplusz.TestJPA.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
//     @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//     @JoinTable(name = "Item_Tag", joinColumns = {
//     @JoinColumn(name = "TAG_ID", nullable = false, updatable = false) },
//     inverseJoinColumns = { @JoinColumn(name = "ITEM_ID",
//     nullable = false, updatable = true) })
    private Set<Item> items = new HashSet<Item>(0);

    public void addImage(Item item) {
        getItems().add(item);
        item.getTags().add(this);
    }

    public void removeImage(Item item) {
        getItems().remove(item);
        item.getTags().remove(this);
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
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

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).toHashCode();
        
        
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Item) {
            Tag tag = (Tag) obj;
            return new EqualsBuilder().append(this.name, tag.getName()).isEquals();
        }
        return false;
    }

}
