package cn.edu.buaa.smarttour.admin;

import cn.edu.buaa.smarttour.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class adminTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void verifyTest() {
        int code = customerService.verify("111", "12");
        System.out.println(code);
    }
}
