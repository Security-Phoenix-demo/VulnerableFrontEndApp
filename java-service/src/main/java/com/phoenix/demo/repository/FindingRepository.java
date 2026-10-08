package com.phoenix.demo.repository;

import com.phoenix.demo.model.Finding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FindingRepository extends JpaRepository<Finding, Long> {
    List<Finding> findByPackageNameContainingIgnoreCase(String packageName);

    @Query("select f from Finding f where f.cvssScore >= ?1 order by f.cvssScore desc")
    List<Finding> findCritical(double threshold);
}
