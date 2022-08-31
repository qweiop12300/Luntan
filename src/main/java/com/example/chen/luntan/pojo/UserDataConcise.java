package com.example.chen.luntan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("user_data_concise")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDataConcise {
    private String avatar;
    private String name;
    private String sex;
    private long type;
}
