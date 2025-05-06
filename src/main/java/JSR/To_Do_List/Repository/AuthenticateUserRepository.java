package JSR.To_Do_List.Repository;

import JSR.To_Do_List.Model.AuthenticateUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional; 

@Repository
public interface AuthenticateUserRepository extends JpaRepository<AuthenticateUser, Long> {


    public Optional<AuthenticateUser> findByEmail(String email);
}
