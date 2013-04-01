package com.xplusz.TestJPA.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
public class PointOfSale {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, mappedBy="pointOfSale")
//    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
//    @JoinColumn(name = "pointOfSale")
    @OrderBy("priority")
    private List<Category> categories = new ArrayList<Category>();

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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category category) {
        categories.add(category);
        category.setPointOfSale(this);
    }

    public void remove(Category category) {
        categories.remove(category);
        category.setPointOfSale(null);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PointOfSale) {
            PointOfSale pos = (PointOfSale) obj;
            return new EqualsBuilder().append(this.name, pos.getName()).isEquals();
        }
        return false;
    }

}
