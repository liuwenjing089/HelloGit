package myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyChooseLoadBalancerRule {

    @Bean
    public IRule chooseRule(){
        return new RandomRule();
    }
}
