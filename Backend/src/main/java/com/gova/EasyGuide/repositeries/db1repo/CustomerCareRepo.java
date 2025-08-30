package com.gova.EasyGuide.repositeries.db1repo;

import com.gova.EasyGuide.entities.db1.CustomerCare;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerCareRepo extends JpaRepository<CustomerCare,String> {
}
