package com.example.payroll.config;

import com.example.payroll.enums.Status;
import com.example.payroll.model.Employee;
import com.example.payroll.model.Order;
import com.example.payroll.repository.EmployeeRepository;
import com.example.payroll.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SpringConfig {

    private static final Logger logger = LoggerFactory.getLogger(SpringConfig.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRepository orderRepository) {
        return args -> {
            // Preloaded employees
            employeeRepository.save(new Employee("Bilbo", "Baggins", "burglar"));
            employeeRepository.save(new Employee("Frodo", "Baggins", "thief"));

            // Preloaded orders
            orderRepository.save(new Order("Lenovo Laptop", Status.IN_PROGRESS));
            orderRepository.save(new Order("HP Laptop", Status.CANCELLED));
            orderRepository.save(new Order("Dell Laptop", Status.COMPLETED));

            // Log preloaded data
            employeeRepository.findAll().forEach(e -> logger.info("Preloaded " + e));
            orderRepository.findAll().forEach(o -> logger.info("Preloaded " + o));
        };
    }
}
