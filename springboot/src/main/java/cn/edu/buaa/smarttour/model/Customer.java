package cn.edu.buaa.smarttour.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Customer {
    private Long cid;
    private String name;
    private String phone;
    private String password;
    private Integer role;
}
