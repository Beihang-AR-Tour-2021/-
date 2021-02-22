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
                redisUtil.set(customer.getPhone(), currentTimeMillis, 60 * 30);
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

    @GetMapping("/hello")
    @RequiresRoles(value={"admin","user"}, logical = Logical.OR)
    public Object hello() {
        Customer principal = (Customer)SecurityUtils.getSubject().getPrincipal();
        return principal;
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
        @ApiModelProperty(name = "phone", value = "手机号", required = true, example = "String")
        private String phone;
        @ApiModelProperty(name = "password", value = "密码", required = true, example = "String")
        private String password;
    }

}
