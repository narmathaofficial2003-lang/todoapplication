package dev.codeio.HelloWorld.repository;

import dev.codeio.HelloWorld.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<Object> findByEmail(String email);
}
