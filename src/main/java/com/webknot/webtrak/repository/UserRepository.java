package com.webknot.webtrak.repository;

import com.webknot.webtrak.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * TODO - Change the class and package name according to service.
 * <p>
 * This is TemplateRepository class.
 * It will have methods to interact with data source.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(
            value = "SELECT * FROM users where emp_id = ?1 or email = ?2",
            nativeQuery = true)
    User checkIfUserExists(String empId, String email);

    User findByEmail(String email);

    User findByEmpId(String empId);
}
