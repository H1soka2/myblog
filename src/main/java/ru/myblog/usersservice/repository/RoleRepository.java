package ru.myblog.usersservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.myblog.commonlib.user.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(Role role);
}
