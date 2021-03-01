package cn.edu.buaa.smarttour.controller;

import cn.edu.buaa.smarttour.model.Customer;
import cn.edu.buaa.smarttour.service.CustomerService;
import cn.edu.buaa.smarttour.utils.JWTUtil;
import cn.edu.buaa.smarttour.utils.RedisUtil;
import cn.edu.buaa.smarttour.utils.Response;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@Api(tags = {"用户相关接口"})
public class CustomerController {

    private CustomerService customerService;
    private RedisUtil redisUtil;

    @PostMapping("/session")
    @ApiOperation("用户登录")
    // phone获取手机号，password获取密码，通过verify方法判断是否登陆成功
    public ResponseEntity<Response> verify(@RequestBody LoginEntity loginEntity) {
        int code = customerService.verify(loginEntity.getPhone(), loginEntity.getPassword());
        switch (code) {
            case 0:
                Map<String, Object> resMap = new HashMap<>();
                Customer customer = customerService.getCustomerByPhone(loginEntity.getPhone());
                long currentTimeMillis = System.currentTimeMillis();
                String token = JWTUtil.createToken(customer.getPhone(), currentTimeMillis);
                redisUtil.set(customer.getPhone(), currentTimeMillis, 7 * 24 * 60 * 60);
                resMap.put("user", customer);
                resMap.put("token", token);
                return ResponseEntity.ok(new Response("登录成功！", resMap));
            case 1:
                return ResponseEntity.ok(new Response(404, "用户不存在，请先注册", null));
            case 2:
                return ResponseEntity.ok(new Response(403, "密码错误", null));
        }
        return ResponseEntity.ok(new Response(500, "服务器内部错误，请稍后再试", null));
    }

    @PostMapping("/user")
    @ApiOperation("用户注册")
    public ResponseEntity<Response> register(@RequestBody RegisterEntity info) {
        Customer customer = customerService.getCustomerByPhone(info.phone);
        if (customer == null) {
            boolean status = customerService.save(new Customer(null, info.name, info.phone, info.password, 1, info.age, info.sex));
            if (status) {
                return ResponseEntity.ok(new Response("注册成功！", null));
            } else {
                return ResponseEntity.ok(new Response(500, "服务器内部错误，请稍后再试", null));
            }
        } else {
            return ResponseEntity.ok(new Response(400, "账号已存在，请登录", null));
        }
    }

    @DeleteMapping("/session")
    @ApiOperation("用户登出")
    @RequiresRoles(value={"admin","user"}, logical = Logical.OR)
    public ResponseEntity<Response> logout(){
        // 获得请求这一接口的用户信息
        // 理论上一定可以获取成功
        // 因为这个接口必须通过鉴权才能访问到
        Customer customer = (Customer)SecurityUtils.getSubject().getPrincipal();
        if(customer != null){
            redisUtil.del(customer.getPhone());
            return ResponseEntity.ok(new Response("登出成功！"));
        } else {
            return ResponseEntity.ok(new Response(404,"用户未登录",null));
        }
    }

    @Data
    @AllArgsConstructor
    @ApiModel(value = "LoginEntity", description = "用户登录表单")
    static class LoginEntity {
        @ApiModelProperty(name = "phone", value = "手机号", required = true, example = "test01")
        private String phone;
        @ApiModelProperty(name = "password", value = "密码", required = true, example = "a12345")
        private String password;
    }

    @Data
    @AllArgsConstructor
    @ApiModel(value = "RegisterEntity", description = "用户注册表单")
    static class RegisterEntity {
        private String name;
        private String phone;
        private String password;
        private Integer age;
        private Integer sex;
    }

}
