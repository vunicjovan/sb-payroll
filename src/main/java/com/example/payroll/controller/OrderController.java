package com.example.payroll.controller;

import com.example.payroll.assembler.OrderModelAssembler;
import com.example.payroll.enums.Status;
import com.example.payroll.model.Order;
import com.example.payroll.service.OrderService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    private final OrderModelAssembler assembler;

    public OrderController(OrderService service, OrderModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Order>> list() {
        List<EntityModel<Order>> orders = this.service.list()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                orders,
                linkTo(methodOn(OrderController.class).list()).withSelfRel()
        );
    }

    @GetMapping("/{id}")
    public EntityModel<Order> get(@PathVariable Long id) {
        return assembler.toModel(this.service.get(id));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Order>> create(@RequestBody Order order) {
        Order newOrder = this.service.create(order);

        return ResponseEntity
                .created(linkTo(methodOn(OrderController.class).get(newOrder.getId())).toUri())
                .body(this.assembler.toModel(newOrder));
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        return this.service.progress(id, Status.CANCELLED, this.assembler);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable Long id) {
        return this.service.progress(id, Status.COMPLETED, this.assembler);
    }
}
