package com.example.payroll.controller;

import com.example.payroll.assembler.EmployeeModelAssembler;
import com.example.payroll.model.Employee;
import com.example.payroll.service.EmployeeService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    private final EmployeeModelAssembler assembler;

    EmployeeController(EmployeeService service, EmployeeModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Employee>> list() {
        List<EntityModel<Employee>> employees = this.service.list()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                employees,
                linkTo(methodOn(EmployeeController.class).list()).withSelfRel()
        );
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Employee employee) {
        EntityModel<Employee> entityModel = assembler.toModel(this.service.create(employee));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/{id}")
    public EntityModel<Employee> get(@PathVariable Long id) {
        return assembler.toModel(this.service.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replace(@PathVariable Long id, @RequestBody Employee newEmployee) {
        EntityModel<Employee> entityModel = assembler.toModel(this.service.replace(id, newEmployee));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        this.service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
