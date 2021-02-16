package cn.edu.buaa.smarttour.controller;

import cn.edu.buaa.smarttour.model.Customer;
import cn.edu.buaa.smarttour.service.CustomerService;
import cn.edu.buaa.smarttour.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@Api(tags = {"用户相关接口"})
public class CustomerController {

    private CustomerService customerService;

    @PostMapping("/session")
    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone",value = "用户的手机号",required = true,paramType = "body",dataType = "String"),
            @ApiImplicitParam(name = "password",value = "账户密码",required = true,paramType = "body",dataType = "String")
    })
    public ResponseEntity<Response> verify(@RequestBody Map<String, Object> body) {
        int code = customerService.verify((String) body.get("phone"), (String) body.get("password"));
        switch (code) {
            case 0:
                Customer customer = customerService.getCustomerByPhone((String) body.get("phone"));
                return ResponseEntity.ok(new Response("登录成功！", customer));
            case 1:
                return ResponseEntity.ok(new Response(1002, "用户不存在，请先注册", null));
            case 2:
                return ResponseEntity.ok(new Response(1003, "密码错误", null));
        }
        return ResponseEntity.ok(new Response(500, "服务器内部错误，请稍后再试", null));
    }

}
