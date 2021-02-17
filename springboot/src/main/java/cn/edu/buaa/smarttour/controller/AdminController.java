package cn.edu.buaa.smarttour.controller;

import cn.edu.buaa.smarttour.mappers.ZoneMapper;
import cn.edu.buaa.smarttour.model.Customer;
import cn.edu.buaa.smarttour.model.Zone;
import cn.edu.buaa.smarttour.service.AdminService;
import cn.edu.buaa.smarttour.utils.Response;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Api(tags = {"管理员相关接口"})
public class AdminController {

    private AdminService adminService;

    @GetMapping("/zone")
    @ApiOperation("获取所有景区信息")
    public Map<String, Object> getAllZones(){
        Map<String, Object> map = new HashMap<>();
        List<Zone> zonelist = adminService.allZones();
        if(zonelist == null){
            map.put("response", "failed");
            map.put("msg", "get all zones failed");
            return map;
        }
        map.put("response", "success");
        map.put("msg", "Search success");
        map.put("info", zonelist);
        return map;
    }

    @PostMapping("/zone")
    @ApiOperation("添加新的景区")
    @ApiResponses(value = {
            @ApiResponse(code = 1001, message = "添加成功！"),
            @ApiResponse(code = 1004, message = "添加景区失败")
    })
    // 添加景区时，前端不提供zid，于是需要新的实体来接收，一般用静态内部类即可
    public ResponseEntity<Response> addNewZones(@RequestBody ZoneEntity zoneEntity){
        Zone zone = new Zone(null, zoneEntity.getName(), zoneEntity.getInfo(), zoneEntity.getAddress(), zoneEntity.getTime(), zoneEntity.getPhoto_url());
        boolean result = adminService.addZone(zone);
        if (result) {
            return ResponseEntity.ok(new Response("添加成功！"));
        } else{
            return ResponseEntity.ok(new Response(1004, "添加景区失败"));
        }
    }

    @PutMapping("/zone")
    @ApiOperation("修改景区信息")
    public Map<String, Object> editZone(@RequestBody Zone zone){
        Map<String, Object> map = new HashMap<>();
        boolean res = adminService.editZone(zone);
        if(res){
            map.put("response", "failed");
            map.put("msg", "edit zone failed.");
            return map;
        }
        else{
            map.put("response", "success");
            map.put("msg", "edit zone successful.");
            return map;
        }
    }

    @DeleteMapping("/zone/{id}")
    @ApiOperation("删除景区")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="景区id",dataType="Long",paramType = "path",example="1")
    })
    // 删除景区只需知道它的id，在路径中传id即可
    public Map<String, Object> deleteZone(@PathVariable("id") Long id){
        Map<String, Object> map = new HashMap<>();
        boolean res = adminService.deleteZone(id);
        if(res){
            map.put("response", "failed");
            map.put("msg", "edit zone failed.");
            return map;
        }
        else{
            map.put("response", "success");
            map.put("msg", "edit zone successful.");
            return map;
        }
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
