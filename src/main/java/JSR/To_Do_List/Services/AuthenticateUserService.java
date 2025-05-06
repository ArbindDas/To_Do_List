package JSR.To_Do_List.Services;

import JSR.To_Do_List.Model.AuthenticateUser;
import JSR.To_Do_List.Repository.AuthenticateUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateUserService {

    @Autowired
    private AuthenticateUserRepository authenticateUserRepository;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // ✅ Use shared encoder
  
    public AuthenticateUser save(AuthenticateUser authenticateUser) {
        String encryptedPassword = passwordEncoder.encode(authenticateUser.getPassword().trim());
        authenticateUser.setPassword(encryptedPassword);
        
        return authenticateUserRepository.save(authenticateUser);
    }

    public boolean authenticate(String email, String password) {
        // AuthenticateUser user = authenticateUserRepository.findByEmail(email);
        AuthenticateUser user = authenticateUserRepository.findByEmail(email)
    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        if (user != null) {
            boolean match = passwordEncoder.matches(password.trim(), user.getPassword());
            
            System.out.println("Stored hash: " + user.getPassword());
            System.out.println("Raw password: " + password);
            System.out.println("email : " + user.getEmail());
            System.out.println("Password match: " + match);
            return match;
        }
        System.out.println("User not found for email: " + email);
        return false;
    }


   
    

}
