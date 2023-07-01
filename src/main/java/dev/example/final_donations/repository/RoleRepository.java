package dev.example.final_donations.repository;

import dev.example.final_donations.models.ERole;
import dev.example.final_donations.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
