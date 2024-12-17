package io.anoopsimon.fincraft.repository;

import io.anoopsimon.fincraft.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
