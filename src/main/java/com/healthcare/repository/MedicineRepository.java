package com.healthcare.repository;

import com.healthcare.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    List<Medicine> findByNameContainingIgnoreCase(String name);
    List<Medicine> findByCategoryIgnoreCase(String category);
    List<Medicine> findByNameContainingIgnoreCaseAndCategoryIgnoreCase(String name, String category);
}
