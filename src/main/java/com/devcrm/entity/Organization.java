package com.devcrm.entity;

import jakarta.persistence.*;

/**
 * Hibernate entity representing an Organization.
 * Uses GenerationType.IDENTITY for MySQL AUTO_INCREMENT primary keys.
 * Database operations are managed via {@link com.devcrm.repository.OrganizationRepository}.
 */
@Entity
@Table(name = "organizations")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "registration_number")
    public String registrationNumber;

    public Organization() {
    }

    public Organization(String name) {
        this.name = name;
    }

    public Organization(String name, String registrationNumber) {
        this.name = name;
        this.registrationNumber = registrationNumber;
    }
}
