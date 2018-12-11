package com.springboot.garage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springboot.garage.model.SpringBootGarageModel;

@Repository
public interface SpringBootGarageRepository extends JpaRepository<SpringBootGarageModel,Long> {

}
