package com.app.crypto.wallet.repository;

import com.app.crypto.wallet.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Transactional
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUserId(long id);
    @Override
    List<User> findAll();
    Optional<User> findByUsername(String username);

    Optional<User> findByMailAddressee(String mailAddressee);
}
