package cn.edu.buaa.smarttour.controller;

import cn.edu.buaa.smarttour.model.Zone;
import cn.edu.buaa.smarttour.service.AdminService;
import cn.edu.buaa.smarttour.service.CustomerService;
import cn.edu.buaa.smarttour.utils.JWTUtil;
import cn.edu.buaa.smarttour.utils.RedisUtil;
import cn.edu.buaa.smarttour.utils.Response;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Api(tags = {"管理员相关接口"})
public class AdminController {

    private AdminService adminService;
    private CustomerService customerService;
    private RedisUtil redisUtil;

    @GetMapping("/zone")
    @ApiOperation("获取所有景区信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求成功！"),
            @ApiResponse(code = 404, message = "查询所有景区失败")
    })
    @RequiresRoles("admin")
    public ResponseEntity<Response> getAllZones(){
        List<Zone> zonelist = adminService.allZones();
        if(zonelist == null){
            return ResponseEntity.ok(new Response(404, "查询所有景区失败！"));
        }
        return ResponseEntity.ok(new Response(zonelist));
    }

    @PostMapping("/zone")
    @ApiOperation("添加新的景区")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "添加成功！"),
            @ApiResponse(code = 404, message = "添加景区失败")
    })
    @RequiresRoles("admin")
    // 添加景区时，前端不提供zid，于是需要新的实体来接收，一般用静态内部类即可
    public ResponseEntity<Response> addNewZones(@RequestBody ZoneEntity zoneEntity){
        Zone zone = new Zone(null, zoneEntity.getName(), zoneEntity.getInfo(), zoneEntity.getAddress(), zoneEntity.getTime(), zoneEntity.getPhoto_url());
        boolean result = adminService.addZone(zone);
        if (result) {
            return ResponseEntity.ok(new Response("添加成功！"));
        } else{
            return ResponseEntity.ok(new Response(404, "添加景区失败"));
        }
    }

    @PutMapping("/zone")
    @ApiOperation("修改景区信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "修改成功！"),
            @ApiResponse(code = 404, message = "修改景区失败")
    })
    @RequiresRoles("admin")
    public ResponseEntity<Response> editZone(@RequestBody Zone zone){
        boolean res = adminService.editZone(zone);
        if(res){
            return ResponseEntity.ok(new Response("修改成功！"));
        }
        else{
            return ResponseEntity.ok(new Response(404, "修改景区失败"));
        }
    }

    @DeleteMapping("/zone/{id}")
    @ApiOperation("删除景区")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="景区id",dataType="Long",paramType = "path",example="1")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "删除成功！"),
            @ApiResponse(code = 404, message = "删除景区失败")
    })
    @RequiresRoles("admin")
    // 删除景区只需知道它的id，在路径中传id即可
    public ResponseEntity<Response> deleteZone(@PathVariable("id") Long id){
        boolean res = adminService.deleteZone(id);
        if(res){
            return ResponseEntity.ok(new Response("删除成功！"));
        }
        else{
            return ResponseEntity.ok(new Response(404, "删除景区失败"));
        }
    }

    @PostMapping("/session/admin")
    @ApiOperation("管理员登录")
    public ResponseEntity<Response> login(@RequestBody AdminEntity loginEntity) {
        if (customerService.adminLogin(loginEntity.name, loginEntity.password)) {
            long currentTimeMillis = System.currentTimeMillis();
            String token = JWTUtil.createToken(loginEntity.name, currentTimeMillis);
            redisUtil.set(loginEntity.name, currentTimeMillis, 7 * 24 * 60 * 60);
            Map<String, Object> res = new HashMap<>();
            res.put("token", token);
            return ResponseEntity.ok(new Response("登录成功！", res));
        } else {
            return ResponseEntity.ok(new Response(403, "账户密码错误"));
        }
    }

    @Data
    @AllArgsConstructor
    @ApiModel(value = "AdminEntity", description = "管理员登录表单")
    static class AdminEntity {
        @ApiModelProperty(name = "name", value = "管理员账号", required = true, example = "adm1n")
        private String name;
        @ApiModelProperty(name = "password", value = "密码", required = true, example = "ART0ur")
        private String password;
    }

    @Data
    @AllArgsConstructor
    @ApiModel(value = "ZoneEntity")
    static class ZoneEntity {
        @ApiModelProperty(name = "name", value = "景区名称", required = true, example = "String")
        private String name;
        @ApiModelProperty(name = "info", value = "景区简介", required = true, example = "String")
        private String info;
        @ApiModelProperty(name = "address", value = "景区地址", required = true, example = "String")
        private String address;
        @ApiModelProperty(name = "time", value = "开发时间", required = true, example = "String")
        private String time;
        @ApiModelProperty(name = "photo_url", value = "实景链接", required = true, example = "String")
        private String photo_url;
    }

}
