package cn.edu.buaa.smarttour.service;


import cn.edu.buaa.smarttour.mappers.ZoneMapper;
import cn.edu.buaa.smarttour.model.Customer;
import cn.edu.buaa.smarttour.model.Zone;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminService extends ServiceImpl<ZoneMapper, Zone> {

    @Autowired
    private ZoneMapper zoneMapper;

    /**
     * 获得所有的景区信息，用于admin查询
     * @return 返回景区列表
     */
    public List<Zone> allZones(){
        List<Zone> zoneList = zoneMapper.selectList(null);
        return zoneList;
    }

    /**
     * 增加景区
     * @param newZone
     * @return 返回增添结果
     */
    public int addZone(Zone newZone){
        return zoneMapper.insert(newZone);
    }

    /**
     * 修改景区信息
     * @param editZone
     * @return 返回修改状态
     */
    public int editZone(Zone editZone){
        UpdateWrapper<Zone> updateWrapper = new UpdateWrapper<Zone>();
        updateWrapper.eq("zid", editZone.getZid());
        updateWrapper.set("name", editZone.getName());
        updateWrapper.set("info", editZone.getInfo());
        updateWrapper.set("address", editZone.getAddress());
        updateWrapper.set("time", editZone.getTime());
        updateWrapper.set("photo_url", editZone.getPhoto_url());

        return baseMapper.update(null, updateWrapper);
    }

    /**
     * 删除景区信息
     * @param deleteZone
     * @return 返回删除状态
     */
    public int deleteZone(Zone deleteZone){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("zid",deleteZone.getZid());
        return zoneMapper.deleteByMap(hashMap);
    }
}
