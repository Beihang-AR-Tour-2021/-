package cn.edu.buaa.smarttour.service;

import cn.edu.buaa.smarttour.mappers.ZoneMapper;
import cn.edu.buaa.smarttour.model.Zone;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService extends ServiceImpl<ZoneMapper, Zone> {

    /**
     * 获得所有的景区信息，用于admin查询
     * @return 返回景区列表
     */
    public List<Zone> allZones(){
        // AdminService继承了ServiceImpl之后增加了许多数据操作的方法
        // 可直接使用，文档见：https://mp.baomidou.com/guide/crud-interface.html
        return list();
    }

    /**
     * 增加景区
     * @param newZone 新景区信息实体类
     * @return 返回增添结果
     */
    public boolean addZone(Zone newZone){
        // AdminService继承了ServiceImpl之后增加了许多数据操作的方法
        // 可直接使用，文档见：https://mp.baomidou.com/guide/crud-interface.html
        return save(newZone);
    }

    /**
     * 修改景区信息
     * @param editZone 待修改的景区信息
     * @return 返回修改状态
     */
    public boolean editZone(Zone editZone){
        UpdateWrapper<Zone> updateWrapper = new UpdateWrapper<Zone>();
        updateWrapper.eq("zid", editZone.getZid());
        updateWrapper.set("name", editZone.getName());
        updateWrapper.set("info", editZone.getInfo());
        updateWrapper.set("address", editZone.getAddress());
        updateWrapper.set("time", editZone.getTime());
        updateWrapper.set("photo_url", editZone.getPhoto_url());

        // AdminService继承了ServiceImpl之后增加了许多数据操作的方法
        // 可直接使用，文档见：https://mp.baomidou.com/guide/crud-interface.html
        return updateById(editZone);
    }

    /**
     * 删除景区信息
     * @param id 待删除的景区id
     * @return 返回删除状态
     */
    public boolean deleteZone(Long id){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("zid", id);
        // AdminService继承了ServiceImpl之后增加了许多数据操作的方法
        // 可直接使用，文档见：https://mp.baomidou.com/guide/crud-interface.html
        return remove(queryWrapper);
    }
}
