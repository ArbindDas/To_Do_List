package JSR.To_Do_List.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import JSR.To_Do_List.Model.Customer;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    Page<Customer> findByFirstNameContainingIgnoreCase(String search, Pageable pageable);

//   Optional<Customer>findByEmail(String email);

    
}
