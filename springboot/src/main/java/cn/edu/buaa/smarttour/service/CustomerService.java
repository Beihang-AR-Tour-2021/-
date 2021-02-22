package cn.edu.buaa.smarttour.service;

import cn.edu.buaa.smarttour.mappers.CustomerMapper;
import cn.edu.buaa.smarttour.model.Customer;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService extends ServiceImpl<CustomerMapper, Customer> {

    /**
     * 验证用户提交的登录表单
     * @param phone 用户的手机号
     * @param password 密码
     * @return 0代表验证通过，1代表用户不存在，2代表密码错误
     */
    public int verify(String phone, String password) {
        /**
         * 用法
         * QueryWrapper<表名> 对象 = new QueryWrapper();
         * https://www.cnblogs.com/zhaoyunlong/p/10853543.html
         */
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        /*
        SELECT *
        FROM Customer AS c
        WHERE c.phone = #{phone} AND c.password = #{password};
         */
        wrapper.eq("phone", phone);
        Customer customer = getOne(wrapper);
        // 判断是否存在对应用户
        if (customer == null) {
            return 1;
        } else {

            wrapper.eq("password", password);
            customer = getOne(wrapper);
            if (customer == null) return 2;
            else return 0;
        }
    }

    /**
     * 由手机号查询用户信息
     * 在"登录"用例中作为验证成功后信息完整返回用，暂时不检查用户是否存在
     * @param phone 用户的手机号
     * @return 用户信息实体类
     */
    public Customer getCustomerByPhone(String phone) {
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        return getOne(wrapper);
    }

    /**
     * 验证管理员口令信息
     * @param name 管理员账户名
     * @param password 密码
     * @return 信息是否正确
     */
    public boolean adminLogin(String name, String password) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("phone", name);
        queryWrapper.eq("password", password);
        queryWrapper.eq("role", 0);
        return getOne(queryWrapper) != null;
    }

}
