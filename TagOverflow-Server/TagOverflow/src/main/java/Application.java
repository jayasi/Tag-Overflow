import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Apoorv Singh on 3/28/2015.
 */
@Configuration
@ComponentScan("controllers services")
@EnableWebMvc
@EnableAutoConfiguration
public class Application {
    @Autowired
    private StringRedisTemplate template;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}