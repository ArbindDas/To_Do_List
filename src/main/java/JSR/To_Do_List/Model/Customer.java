package JSR.To_Do_List.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "customer")
@Table(name = "customer_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    (
        name = "id"
    )
    private Long id;

    @NotBlank(message = "First name is required")
    @Size
    (
      min = 2, max = 50,
     message = "First name must be between 2 and 50 characters"
     )
    @Pattern
    (
     regexp = "^[A-Za-z]+$",
     message = "First name must contain only letters"
     )
    @Column
    (
     name = "first_name",
     nullable = false
     )
    private String firstName;



    @NotBlank(message = "Last name is required")
    @Size
        (
        min = 2, max = 50,
         message = "Last name must be between 2 and 50 characters"
         )
    @Pattern
     (
        regexp = "^[A-Za-z]+$",
     message = "Last name must contain only letters"
     )
    @Column(
        name = "last_name",
     nullable = false
     )
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column
    (
      name = "email", 
      nullable = false, unique = true

    )
    private String email;



    @NotBlank
    (message = "Phone number is required")
    @Pattern
    (
        regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$",
        message = "Phone number must be 10 digits and may include a country code"
     )
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;
}
