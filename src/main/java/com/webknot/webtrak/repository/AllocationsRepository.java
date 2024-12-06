package com.webknot.webtrak.repository;

import com.webknot.webtrak.entity.Allocation;
import com.webknot.webtrak.entity.Project;
import com.webknot.webtrak.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TODO - Change the class and package name according to service.
 * <p>
 * This is TemplateRepository class.
 * It will have methods to interact with data source.
 */
@Repository
public interface AllocationsRepository extends JpaRepository<Allocation, Long> {

    @Query(
            value = "SELECT * FROM allocations where user_id = ?1",
            nativeQuery = true)
    List<Allocation> findByUserId(Long userId);


    @Query(
            value = "SELECT * FROM allocations where user_id = ?1 and project_id = ?2",
            nativeQuery = true)
    Allocation findByUserIdAndProjectId(Long userId, Long projectId);

    @Query(
            value = "SELECT * FROM allocations where user_id = ?1 and project_code = ?2",
            nativeQuery = true)
    Allocation findByUserIdAndProjectCode(Long userId, String projectCode);

    List<Allocation> findByProject(Project project);

    List<Allocation> findByUser(User user);
}
