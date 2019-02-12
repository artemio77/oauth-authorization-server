package com.derevets.artem.repository;

import com.derevets.artem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    void delete(User user);

    void deleteById(UUID id);

    List<User> findAll();


    Optional<User> findById(UUID id);

    User save(User user);

    User findByEmail(String email);

    @Query(value = "SELECT ua.email FROM user_accounts ua WHERE ua.verification_code = :verification_code", nativeQuery = true)
    String checkUniqueVerificationCode(@Param("verification_code") Long verificationCode);

}
