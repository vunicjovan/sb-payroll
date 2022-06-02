package com.example.payroll.scheduled;

import com.example.payroll.repository.EmployeeRepository;
import com.example.payroll.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledLogger {

    private final Logger logger = LoggerFactory.getLogger(ScheduledLogger.class);

    private EmployeeRepository employeeRepository;
    private OrderRepository orderRepository;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public ScheduledLogger(EmployeeRepository employeeRepository, OrderRepository orderRepository) {
        this.employeeRepository = employeeRepository;
        this.orderRepository = orderRepository;
    }

    private void scheduledLog() {
        String now = dateFormat.format(new Date());

        logger.info("There are {} employees at {}", employeeRepository.findAll().size(), now);
        logger.info("There are {} orders at {}", orderRepository.findAll().size(), now);
    }

    @Scheduled(cron = "0/10 * * * * ?")
    public void logBySchedule() {
        scheduledLog();
    }
}
