package com.gova.EasyGuide.service.db1.Users;


import com.gova.EasyGuide.entities.db1.CustomerCare;
import com.gova.EasyGuide.repositeries.db1repo.CustomerCareRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerCareImpl {

    @Autowired
    private CustomerCareRepo customerCareRepo;

   public void saveCustomerCare(CustomerCare customerCare)
    {
        customerCareRepo.save(customerCare);
    }
}
