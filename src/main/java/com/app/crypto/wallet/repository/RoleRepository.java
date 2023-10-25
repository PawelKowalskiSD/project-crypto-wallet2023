package com.app.crypto.wallet.repository;

import com.app.crypto.wallet.domain.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    List<Role> findAll();
    Optional<Role> findByRoleId(long id);
    List<Role> findRoleByRoleName(String name);
}
