package com.example.payroll.service.interfaces;

import com.example.payroll.assembler.OrderModelAssembler;
import com.example.payroll.enums.Status;
import com.example.payroll.model.Order;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {

    List<Order> list();

    Order get(Long id);

    Order create(Order order);

    ResponseEntity<?> progress(Long id, Status status, OrderModelAssembler assembler);
}
