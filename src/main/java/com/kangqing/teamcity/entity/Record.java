package com.kangqing.teamcity.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author kangqing
 * @since 2023/8/27 19:57
 */
@Getter
@Setter
@Table("record")
public class Record implements Serializable {

    @Serial
    private static final long serialVersionUID = -557517290757724418L;

    @Id(keyType = KeyType.Auto)
    private Long id;

    @NotBlank(message = "name不能为空")
    @Length(max = 20, message = "name长度不能超过20")
    private String name;
    // 记录类型 0=出账，1=入账
    @Max(value = 1, message = "仅限0或1")
    @Min(value = 0, message = "仅限0或1")
    private Integer type;

    // 账单用途描述
    @NotBlank(message = "comment不能为空")
    @Length(max = 20, message = "comment长度不能超过20")
    private String comment;
    // 关系来源 0=康，1=我
    @Max(value = 1, message = "仅限0或1")
    @Min(value = 0, message = "仅限0或1")
    private Integer friends;

    @Positive(message = "money必须是正整数") //正整数
    private Integer money;
    // 创建时间
    private String createTime;
}
