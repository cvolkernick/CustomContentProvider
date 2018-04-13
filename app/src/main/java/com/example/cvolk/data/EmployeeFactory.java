package com.example.cvolk.data;

import com.example.cvolk.customcontentprovider.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeFactory {

    public static List<Employee> createEmployees() {
        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee(1, "Bob",
                63,
                "bob@company.com",
                "1111111111"));
        employees.add(new Employee(2, "Steve",
                38,
                "steve@company.com",
                "2222222222"));
        employees.add(new Employee(3, "Jane",
                24,
                "jane@company.com",
                "3333333333"));

        return employees;
    }
}
