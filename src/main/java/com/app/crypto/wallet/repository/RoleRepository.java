package com.app.crypto.wallet.repository;

import com.app.crypto.wallet.domain.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Transactional
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    List<Role> findAll();
    Optional<Role> findByRoleId(long id);
    Optional<Role> findByRoleName(String name);
}
