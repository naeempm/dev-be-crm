package com.devcrm.resource;

import com.devcrm.entity.Organization;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.List;

@Path("/api/organizations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Organization Resource", description = "Operations for managing Organizations connected to MySQL")
public class OrganizationResource {

    @GET
    @Operation(summary = "Get all organizations", description = "Retrieves a list of all organizations stored in the database")
    @APIResponse(
        responseCode = "200",
        description = "List of organizations",
        content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = Organization.class))
    )
    public List<Organization> getAll() {
        return Organization.listAll();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get organization by ID", description = "Fetches a single organization record by its primary key ID")
    @APIResponses(value = {
        @APIResponse(
            responseCode = "200",
            description = "Organization found",
            content = @Content(schema = @Schema(implementation = Organization.class))
        ),
        @APIResponse(responseCode = "404", description = "Organization not found")
    })
    public Response getById(@Parameter(description = "ID of the organization", required = true) @PathParam("id") Long id) {
        Organization organization = Organization.findById(id);
        if (organization == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(organization).build();
    }

    @GET
    @Path("/search")
    @Operation(summary = "Search organization by name", description = "Finds an organization matching the specified exact name")
    @APIResponses(value = {
        @APIResponse(
            responseCode = "200",
            description = "Organization matching the name found",
            content = @Content(schema = @Schema(implementation = Organization.class))
        ),
        @APIResponse(responseCode = "400", description = "Missing or empty 'name' query parameter"),
        @APIResponse(responseCode = "404", description = "No organization found matching the name")
    })
    public Response getByName(@Parameter(description = "Exact name of the organization", required = true) @QueryParam("name") String name) {
        if (name == null || name.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Query parameter 'name' is required")
                    .build();
        }
        Organization organization = Organization.findByName(name.trim());
        if (organization == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(organization).build();
    }

    @POST
    @Transactional
    @Operation(summary = "Create an organization", description = "Creates and persists a new organization in MySQL")
    @APIResponses(value = {
        @APIResponse(
            responseCode = "201",
            description = "Organization successfully created",
            content = @Content(schema = @Schema(implementation = Organization.class))
        ),
        @APIResponse(responseCode = "400", description = "Invalid request payload")
    })
    public Response create(Organization organization) {
        if (organization == null || organization.name == null || organization.name.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Organization name is required").build();
        }
        organization.id = null;
        organization.persist();
        return Response.created(URI.create("/api/organizations/" + organization.id)).entity(organization).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update an organization", description = "Updates the fields of an existing organization")
    @APIResponses(value = {
        @APIResponse(
            responseCode = "200",
            description = "Organization successfully updated",
            content = @Content(schema = @Schema(implementation = Organization.class))
        ),
        @APIResponse(responseCode = "404", description = "Organization not found")
    })
    public Response update(@Parameter(description = "ID of the organization to update", required = true) @PathParam("id") Long id,
                           Organization updated) {
        Organization entity = Organization.findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (updated != null && updated.name != null) {
            entity.name = updated.name;
        }
        return Response.ok(entity).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete an organization", description = "Deletes an organization record from MySQL by its ID")
    @APIResponses(value = {
        @APIResponse(responseCode = "204", description = "Organization successfully deleted"),
        @APIResponse(responseCode = "404", description = "Organization not found")
    })
    public Response delete(@Parameter(description = "ID of the organization to delete", required = true) @PathParam("id") Long id) {
        boolean deleted = Organization.deleteById(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
