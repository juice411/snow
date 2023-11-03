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
package vip.xiaonuo.biz.modular.edocHis.param;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 档案历史添加参数
 *
 * @author juice
 * @date  2023/10/29 19:11
 **/
@Getter
@Setter
public class EdocHisAddParam {
    /** 档案名称 */
    @ApiModelProperty(value = "档案名称", position = 2)
    private String name;

    /** 档案编号 */
    @ApiModelProperty(value = "档案编号", position = 3)
    private String code;

    /** 类目id */
    @ApiModelProperty(value = "类目id", position = 4)
    private String categoryId;

    /** 部门id,默认取CREATE_USER所在部门 */
    @ApiModelProperty(value = "部门id,默认取CREATE_USER所在部门", position = 5)
    private String orgId;

    /** 档案状态 */
    @ApiModelProperty(value = "档案状态", position = 6)
    private String status;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String remarks;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 8)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 操作时间 */
    @ApiModelProperty(value = "操作时间", position = 9)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 操作用户 */
    @ApiModelProperty(value = "操作用户", position = 10)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 借出id,档案借出单位 */
    @ApiModelProperty(value = "借出id,档案借出单位")
    private String borrowId;

}
