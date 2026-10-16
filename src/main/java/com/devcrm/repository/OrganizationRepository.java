package com.devcrm.repository;

import com.devcrm.entity.Organization;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

/**
 * Repository for Organization entity providing database access operations
 * using Quarkus Panache Repository pattern.
 */
@ApplicationScoped
public class OrganizationRepository implements PanacheRepository<Organization> {

    /**
     * Find an organization by its exact name.
     *
     * @param name exact name of the organization
     * @return matching Organization or null if not found
     */
    public Organization findByName(String name) {
        return find("name", name).firstResult();
    }

    /**
     * Find an organization by its exact name returning an Optional.
     *
     * @param name exact name of the organization
     * @return Optional containing Organization if found, or empty Optional
     */
    public Optional<Organization> findByNameOptional(String name) {
        return find("name", name).firstResultOptional();
    }

    /**
     * Find an organization by its registration number.
     *
     * @param registrationNumber registration number of the organization
     * @return matching Organization or null if not found
     */
    public Organization findByRegistrationNumber(String registrationNumber) {
        return find("registrationNumber", registrationNumber).firstResult();
    }

    /**
     * Find an organization by its registration number returning an Optional.
     *
     * @param registrationNumber registration number of the organization
     * @return Optional containing Organization if found, or empty Optional
     */
    public Optional<Organization> findByRegistrationNumberOptional(String registrationNumber) {
        return find("registrationNumber", registrationNumber).firstResultOptional();
    }
}
