package com.eComApp.user.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor // This annotation generates a no-argument constructor for the class to allow for easy instantiation without needing to provide initial values for the fields.
@Entity(name = "Users_Table")
public class    User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private UserRole role = UserRole.CUSTOMER; // Default role is set to CUSTOMER

    //making relationship with address by adding addressId as foreign key
    //cascade = CascadeType.ALL means that any operation (like persist, merge, remove) performed on the User entity will also be applied to the associated Address entity. For example, if you delete a User, the associated Address will also be deleted.
    //orphanRemoval = true means that if an Address entity is no longer associated with any User (i.e., it becomes an "orphan"), it will be automatically removed from the database. This helps to maintain data integrity by ensuring that there are no Address records that are not linked to any User
    //address_id is the foreign key column in the Users_Table that references the id column in the Address_Table. This establishes a one-to-one relationship between User and Address, where each User can have one associated Address.
    @OneToOne(cascade = CascadeType.ALL , orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @CreationTimestamp // This annotation automatically sets the createdAt field to the current timestamp when a new User entity is created and persisted to the database.
    private LocalDateTime createdAt;

    @UpdateTimestamp // This annotation automatically updates the updatedAt field to the current timestamp whenever the User entity is updated and saved to the database. This allows you to keep track of when the User was last modified.
    private LocalDateTime updatedAt = LocalDateTime.now();
}
