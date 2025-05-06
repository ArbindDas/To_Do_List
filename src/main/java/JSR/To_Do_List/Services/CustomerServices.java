package JSR.To_Do_List.Services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JSR.To_Do_List.Model.Customer;
import JSR.To_Do_List.Repository.CustomerRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


//@Slf4j  // Lombok annotation to enable logging
@Service
public class CustomerServices {

    private static final Logger log = LoggerFactory.getLogger(CustomerServices.class);

    @Autowired

    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        try {
            return customerRepository.findAll();
        } catch (Exception e) {
            log.error("Error fetching customers: {}", e.getMessage(), e); // Logs with stack trace
            // GET /api/customers
            // Status: 502 Bad Gateway
            // Body: "Error fetching customers: Failed to fetch customers."
            throw new RuntimeException("Failed to fetch customers."); // Propagate exception
        }
    }

    public void saveCustomer(Customer customer) {
        try {
            customerRepository.save(customer);
        } catch (Exception e) {
            log.error("Error saving customer: {}", e.getMessage(), e); // Corrected log syntax
            throw new RuntimeException("Failed to save customer", e); // Pass the exception as the second argument
        }
    }

    public Optional<Customer> findById(Long myId) {

        try {

            return customerRepository.findById(myId);

        } catch (Exception e) {
            log.error("Error fetching Customer by id : {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch customers.");
        }
    }

    public void deleteById(Long myId) {

        try {

            customerRepository.deleteById(myId);

        } catch (Exception e) {
            log.error("Error deleting Customer by id : {}", e.getMessage(), e);
            throw new RuntimeException("Failed to delete customers.");
        }
    }

    public Page<Customer> getAllCustomersWithPagination(Pageable pageable) {
        try {
            return customerRepository.findAll(pageable);
        } catch (Exception e) {
            log.error("Error fetching paginated customers: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch paginated customers.");
        }
    }

    // Search customers by first name
    public Page<Customer> searchCustomersByName(String search, Pageable pageable) {
        return customerRepository.findByFirstNameContainingIgnoreCase(search, pageable);
    }
}
