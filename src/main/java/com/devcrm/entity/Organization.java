package com.devcrm.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Hibernate entity representing an Organization.
 * Uses Quarkus Panache for built-in Active Record CRUD operations.
 * 'id' is automatically inherited from PanacheEntity (Long ID, auto-incremented).
 */
@Entity
@Table(name = "organizations")
public class Organization extends PanacheEntity {

    @Column(name = "name", nullable = false)
    public String name;

    public Organization() {
    }

    public Organization(String name) {
        this.name = name;
    }

    /**
     * Find an organization by its name.
     */
    public static Organization findByName(String name) {
        return find("name", name).firstResult();
    }
}
