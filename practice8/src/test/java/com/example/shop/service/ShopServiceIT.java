package com.example.shop.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.example.shop.model.*;
import com.example.shop.repository.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class ShopServiceIT {

    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private ShopService shopService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    private Customer testCustomer;
    private Cash testCashPayment;
    private Order testOrder;

    private void assertOrdersEqual(Order expected, Order actual) {
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getStatus()).isEqualTo(expected.getStatus());
        assertThat(actual.getDate()).isEqualToIgnoringNanos(expected.getDate());
        assertThat(actual.getCustomer().getId()).isEqualTo(expected.getCustomer().getId());
        assertThat(actual.getPayment().getId()).isEqualTo(expected.getPayment().getId());
    }

    @BeforeEach
    @Transactional
    void setUp() throws Exception {
        orderRepository.deleteAll();
        customerRepository.deleteAll();
        paymentRepository.deleteAll();

        testCustomer = new Customer();
        testCustomer.setName("New Customer");
        Address address = new Address();
        address.setCity("Pavlodar");
        address.setStreet("Gagarina");
        testCustomer.setAddress(address);
        testCustomer = customerRepository.save(testCustomer);

        testCashPayment = new Cash();
        testCashPayment.setAmount(100.50);
        testCashPayment.setStatus(PaymentStatus.COMPLETED);
        testCashPayment.setCashTendered(100.50f);

        testOrder = new Order();
        testOrder.setCustomer(testCustomer);
        testOrder.setPayment(testCashPayment);
        testOrder.setDate(LocalDateTime.now());
        testOrder.setStatus("COMPLETED");
        testOrder = orderRepository.save(testOrder);
    }

    @Test
    void shouldFindOrdersByAddress() {
        Address searchAddress = new Address();
        searchAddress.setCity("Pavlodar");
        searchAddress.setStreet("Gagarina");

        OrderSearch criteria = new OrderSearch();
        criteria.setAddress(searchAddress);

        List<Order> result = shopService.findOrdersByCriteria(criteria);

        assertThat(result).hasSize(1);
        assertOrdersEqual(testOrder, result.getFirst());
    }

    @Test
    void shouldFindOrdersByPaymentType() {
        OrderSearch criteria = new OrderSearch();
        criteria.setPayment(Cash.class);

        List<Order> result = shopService.findOrdersByCriteria(criteria);

        assertThat(result).hasSize(1);
        assertOrdersEqual(testOrder, result.getFirst());
    }

    @Test
    void shouldFindOrdersByTimeInterval() {
        OrderSearch criteria = new OrderSearch();
        criteria.setFromDate(LocalDateTime.now().minusDays(1));
        criteria.setToDate(LocalDateTime.now().plusDays(1));

        List<Order> result = shopService.findOrdersByCriteria(criteria);

        assertThat(result).hasSize(1);
        assertOrdersEqual(testOrder, result.getFirst());
    }

    @Test
    void shouldFindOrdersByCustomerName() {
        OrderSearch criteria = new OrderSearch();
        criteria.setCustomerName("New Customer");

        List<Order> result = shopService.findOrdersByCriteria(criteria);

        assertThat(result).hasSize(1);
        assertOrdersEqual(testOrder, result.getFirst());
    }

    @Test
    void shouldFindOrdersByPaymentStatus() {
        OrderSearch criteria = new OrderSearch();
        criteria.setPaymentStatus(PaymentStatus.COMPLETED);

        List<Order> result = shopService.findOrdersByCriteria(criteria);

        assertThat(result).hasSize(1);
        assertOrdersEqual(testOrder, result.getFirst());
    }

    @Test
    void shouldFindOrdersByOrderStatus() {
        OrderSearch criteria = new OrderSearch();
        criteria.setOrderStatus("COMPLETED");

        List<Order> result = shopService.findOrdersByCriteria(criteria);

        assertThat(result).hasSize(1);
        assertOrdersEqual(testOrder, result.getFirst());
    }

    @Test
    void shouldNotFindOrdersWithWrongCriteria() {
        OrderSearch criteria = new OrderSearch();
        criteria.setOrderStatus("CANCELLED");

        List<Order> result = shopService.findOrdersByCriteria(criteria);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldCombineMultipleCriteria() {
        OrderSearch criteria = new OrderSearch();
        criteria.setCustomerName("New Customer");
        criteria.setOrderStatus("COMPLETED");
        criteria.setPaymentStatus(PaymentStatus.COMPLETED);

        List<Order> result = shopService.findOrdersByCriteria(criteria);

        assertThat(result).hasSize(1);
        assertOrdersEqual(testOrder, result.getFirst());
    }
}