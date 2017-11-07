package com.github.murphye.customer;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping("/customer")
    Mono<Customer> setCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @GetMapping("/customer/{id}")
    Mono<Customer> getCustomer(@PathVariable String id) {
        return customerRepository.findById(UUID.fromString(id));
    }

    @GetMapping("/customer")
    Flux<Customer> getCustomersUpperCaseName() {
        return customerRepository.findAll().map(
                customer -> {
                    customer.setName(customer.getName().toUpperCase());
                    //System.out.println("Uppercase " + customer);
                    return customer;
                });
    }

    @GetMapping("/customers")
    Flux<String> namesByName(@RequestParam Mono<String> name) {

        Flux<Customer> result = customerRepository.findByName(name);
        return result.map(it -> it.getName());
    }
}