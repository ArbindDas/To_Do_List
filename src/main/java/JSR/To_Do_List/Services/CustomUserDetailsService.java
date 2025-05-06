package JSR.To_Do_List.Services;

import JSR.To_Do_List.Model.AuthenticateUser;
import JSR.To_Do_List.Repository.AuthenticateUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthenticateUserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(AuthenticateUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("xeena email : "+email);
        AuthenticateUser user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

            System.out.println("User found: " + user.getEmail());  // Log user details for debugging
            System.out.println("user password : "+user.getPassword());

        return User.builder()
                   .username(user.getEmail())
                   .password(user.getPassword())
                   .build();
  
       
    }

   
}
