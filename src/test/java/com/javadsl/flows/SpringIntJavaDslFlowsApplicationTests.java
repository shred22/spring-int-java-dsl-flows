package com.javadsl.flows;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;


class SpringIntJavaDslFlowsApplicationTests {

    @Test
    void contextLoads() throws JsonProcessingException {



		String inputString = "2020-04-11 13:06:16.996  INFO 5366 --- [nio-8088-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 12 ms";
        System.out.println("\n\n\n\n\n\n"+inputString.matches("(.*)nio(.*)"));

    }

}
