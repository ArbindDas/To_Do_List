package JSR.To_Do_List.Controllers;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactForm {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Subject cannot be empty")
    @Size(max = 100, message = "Subject cannot exceed 100 characters")
    private String subject;

    @NotBlank(message = "Message cannot be empty")
    @Size(max = 500, message = "Message cannot exceed 500 characters")
    private String message;
}
