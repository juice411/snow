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
package vip.xiaonuo.biz.modular.edocDoc.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.TransPojo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.edocSpot.entity.EdocSpot;
import vip.xiaonuo.biz.modular.edocSupply.entity.EdocSupply;
import vip.xiaonuo.biz.modular.edoccategory.entity.EdocCategory;
import vip.xiaonuo.biz.modular.org.entity.BizOrg;
import vip.xiaonuo.biz.modular.user.entity.BizUser;

import java.util.Date;

/**
 * 档案实体
 *
 * @author juice
 * @date  2023/10/18 16:20
 **/
@Getter
@Setter
@TableName(value="edoc_doc", autoResultMap = true)
public class EdocDoc implements TransPojo {

    /** ID */
    @TableId
    @ApiModelProperty(value = "ID", position = 1)
    private String id;

    /** 档案名称 */
    @ApiModelProperty(value = "档案名称", position = 2)
    private String name;

    /** 档案编号 */
    @ApiModelProperty(value = "档案编号", position = 3)
    private String code;

    /** 类目id */
    @ApiModelProperty(value = "类目id", position = 4)
    @Trans(type = TransType.SIMPLE, target = EdocCategory.class, fields = "name", alias = "category", ref = "categoryName")
    private String categoryId;

    /** 保密级别 */
    @ApiModelProperty(value = "保密级别", position = 5)
    @Trans(type = TransType.DICTIONARY, key = "SECRET_LEVEL")
    private String secretLevel;

    /** 标签 */
    @ApiModelProperty(value = "标签", position = 6)
    private String tag;

    /** 来源id,档案来源 */
    @ApiModelProperty(value = "来源id,档案来源", position = 7)
    @Trans(type = TransType.SIMPLE, target = EdocSupply.class, fields = "name", alias = "supply", ref = "supplyName")
    private String supplyId;

    /** 档案架id,档案存放位置 */
    @ApiModelProperty(value = "档案架id,档案存放位置", position = 8)
    @Trans(type = TransType.SIMPLE, target = EdocSpot.class, fields = "name", alias = "spot", ref = "spotName")
    private String spotId;

    /** 部门id,默认取CREATE_USER所在部门 */
    @Trans(type = TransType.SIMPLE, target = BizOrg.class, fields = "name", alias = "org", ref = "orgName")
    @ApiModelProperty(value = "部门id,默认取CREATE_USER所在部门", position = 9)
    private String orgId;

    /** 档案状态 */
    @ApiModelProperty(value = "档案状态", position = 10)
    @Trans(type = TransType.DICTIONARY, key = "DOC_STATUS")
    private String status;

    /** 档案备注 */
    @ApiModelProperty(value = "档案备注", position = 11)
    private String remarks;

    /** 附件id列表 */
    @ApiModelProperty(value = "附件id列表", position = 12)
    private String fileIds;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 13)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 保存周期，单位：年 */
    @ApiModelProperty(value = "保存周期，单位：年", position = 14)
    private String keepPeriod;

    /** 销毁时间,创建时间+保存周期=销毁时间 */
    @ApiModelProperty(value = "销毁时间,创建时间+保存周期=销毁时间", position = 15)
    private Date destroyTime;

    /** 创建时间 */
    @ApiModelProperty(value = "归档时间", position = 16)
//    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "归档用户", position = 17)
    @TableField(fill = FieldFill.INSERT)
    @Trans(type = TransType.SIMPLE, target = BizUser.class, fields = "name", alias = "creatUser", ref = "creatName")
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 18)
//    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 19)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 借出id,档案借出机构 */
    @ApiModelProperty(value = "借出id,档案借出单位", position = 25)
    @Trans(type = TransType.SIMPLE, target = EdocSupply.class, fields = "name", alias = "borrow", ref = "borrowName")
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String borrowId;

    /** 借出周期，单位：天 */
    @ApiModelProperty(value = "借出周期，单位：天", position = 26)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String borrowPeriod;

    /** 借出时间 */
    @ApiModelProperty(value = "借出时间", position = 27)
//    @TableField(fill = FieldFill.INSERT)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private Date borrowTime;

    /** 归还时间 */
    @ApiModelProperty(value = "归还时间", position = 28)
//    @TableField(fill = FieldFill.INSERT)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private Date returnTime;

    @ApiModelProperty(value = "类目", position = 20)
    @TableField(exist = false)
    private String categoryName;

    @ApiModelProperty(value = "来源", position = 21)
    @TableField(exist = false)
    private String supplyName;

    @ApiModelProperty(value = "位置", position = 22)
    @TableField(exist = false)
    private String spotName;

    @ApiModelProperty(value = "部门", position = 23)
    @TableField(exist = false)
    private String orgName;

    @ApiModelProperty(value = "归档人", position = 24)
    @TableField(exist = false)
    private String creatName;

    @ApiModelProperty(value = "借出单位", position = 29)
    @TableField(exist = false)
    private String borrowName;
}
