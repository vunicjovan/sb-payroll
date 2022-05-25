package com.example.payroll.service;

import com.example.payroll.assembler.OrderModelAssembler;
import com.example.payroll.enums.Status;
import com.example.payroll.exception.OrderNotFoundException;
import com.example.payroll.model.Order;
import com.example.payroll.repository.OrderRepository;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public List<Order> list() {
        return this.repository.findAll();
    }

    public Order get(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Order create(Order order) {
        order.setStatus(Status.IN_PROGRESS);

        return this.repository.save(order);
    }

    public ResponseEntity<?> progress(Long id, Status status, OrderModelAssembler assembler) {
        Order order = this.repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        ResponseEntity<?> orderResponse = ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("Order marked as " + order.getStatus() + " cannot be " +
                                status.toString().toLowerCase())
                );

        if (order.getStatus() == Status.IN_PROGRESS) {
            order.setStatus(status);
            order = this.repository.save(order);

            orderResponse = ResponseEntity.ok(assembler.toModel(order));
        }

        return orderResponse;
    }
}
