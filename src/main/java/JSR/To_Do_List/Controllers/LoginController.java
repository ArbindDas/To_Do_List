package JSR.To_Do_List.Controllers;

import JSR.To_Do_List.Model.AuthenticateUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("authenticateUser", new AuthenticateUser());
        return "login"; // Refers to src/main/resources/templates/login.html
    }


    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/login")
    public String handleLogin(@ModelAttribute AuthenticateUser authenticateUser, Model model) {
        System.out.println("Entered email: " + authenticateUser.getEmail());
        System.out.println("Entered password: " + authenticateUser.getPassword());

        // Check if email is empty
        if (authenticateUser.getEmail() == null || authenticateUser.getEmail().isEmpty()) {
            model.addAttribute("error", "Email cannot be empty.");
            return "login"; // Return to login page with error
        }

        // Create the authentication token
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(authenticateUser.getEmail(), authenticateUser.getPassword());

        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(token);
            if (authentication.isAuthenticated()) {
                return "redirect:/customers"; // Redirect to the customer page if authentication succeeds
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.out.println("Authentication failed: " + e.getMessage());

            // Add the AuthenticateUser object and error message to the model
            model.addAttribute("authenticateUser", authenticateUser);
            model.addAttribute("error", "Invalid credentials. Please try again.");
            return "login"; // Render the login page with the error
        }

        return "redirect:/login?error";  // Default error redirect
    }


}
