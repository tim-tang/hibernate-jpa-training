package com.xplusz.TestJPA.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //通过 @JoinTable 注解描述关联表和关联条件。其中一端定义为 owner, 另一段定义为 inverse(对关联表进行更新操作，这端被忽略)。
    @JoinTable(name = "Item_Tag", joinColumns = { @JoinColumn(name = "ITEM_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "TAG_ID", nullable = false, updatable = false) })
    private Set<Tag> tags = new HashSet<Tag>(0);

    public void addTag(Tag tag) {
        getTags().add(tag);
        tag.getItems().add(this);
    }

    public void removeTag(Tag tag) {
        getTags().remove(tag);
//        tag.getItems().remove(this);
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
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
            Item item = (Item) obj;
            return new EqualsBuilder().append(this.name, item.getName()).isEquals();
        }
        return false;
    }

}
