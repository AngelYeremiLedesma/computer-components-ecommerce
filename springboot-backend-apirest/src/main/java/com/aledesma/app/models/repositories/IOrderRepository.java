package com.aledesma.app.models.repositories;

import com.aledesma.app.models.entity.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IOrderRepository extends CrudRepository<Order,Long> {

    List<Order> findAllByCustomerId(Long customerId);
}
