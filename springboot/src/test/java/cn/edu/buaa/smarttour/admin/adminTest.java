package cn.edu.buaa.smarttour.admin;

import cn.edu.buaa.smarttour.model.Zone;
import cn.edu.buaa.smarttour.service.AdminService;
import cn.edu.buaa.smarttour.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class adminTest {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private AdminService adminService;

    @Test
    public void verifyTest() {
        int code = customerService.verify("111", "12");
        System.out.println(code);
    }

    @Test
    public void addZoneTest() {
        Zone zone = new Zone(null, "测试1", "11", "11", "11", "11");
        System.out.println(adminService.addZone(zone));
    }

    @Test
    public void deleteZoneTest() {
        System.out.println(adminService.deleteZone(1L));
    }
}
