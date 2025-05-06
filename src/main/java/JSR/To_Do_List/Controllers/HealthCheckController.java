package JSR.To_Do_List.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@RestController
public class HealthCheckController {


  

    // using record
    public record InnerHealthCheckController(String username) {
    }


    @GetMapping("/print")
    public InnerHealthCheckController printMyName(){

        return new InnerHealthCheckController("Arbind das");
    }

    // now using static class
    @Data
    public static class Health {
        private String user;
    }

    @GetMapping("/printUsernmae")

    public Health name(){


        Health health = new Health();
        health.setUser("Das Arbind");
        return health;

    }

}
