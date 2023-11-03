/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.biz.modular.edocSpot.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fhs.core.trans.vo.TransPojo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 档案架实体
 *
 * @author juice
 * @date  2023/10/18 15:37
 **/
@Getter
@Setter
@TableName("edoc_spot")
public class EdocSpot implements TransPojo {

    /** ID */
    @TableId
    @ApiModelProperty(value = "ID", position = 1)
    private String id;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 2)
    private String name;

    /** 位置编号 */
    @ApiModelProperty(value = "位置编号", position = 3)
    private String code;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 4)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 5)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 6)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 7)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 8)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
