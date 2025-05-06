package JSR.To_Do_List.Controllers;

import JSR.To_Do_List.Model.AuthenticateUser;
import JSR.To_Do_List.Services.AuthenticateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class SignupController {

    @Autowired
    private AuthenticateUserService authenticateUserService;

    private static final Logger logger = LoggerFactory.getLogger(SignupController.class); // Logger should be declared at the class level

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("authenticateUser", new AuthenticateUser());
        return "signup";
    }

    @PostMapping("/signup")
    public String handleSignup(@ModelAttribute AuthenticateUser authenticateUser  , Model model) {
        try {
            if (authenticateUser.getFullName().isEmpty() || authenticateUser.getFullName().isBlank() ||
                    authenticateUser.getEmail().isEmpty() || authenticateUser.getEmail().isBlank() ||
                    authenticateUser.getPassword().isEmpty() || authenticateUser.getPassword().isBlank()) {

                logger.error("User authentication failed due to empty or blank fields: FullName, Email, or Password.");
            } else {
                authenticateUserService.save(authenticateUser); // No manual encryption here
                return "redirect:/login";
            }

        } catch (Exception e) {
            logger.error("An error occurred during user signup: ", e); // Log the exception if something goes wrong
            model.addAttribute("errorMessage", "An error occurred. Please try again.");
        }
        return "signup"; // Stay on the signup page
    }
}
