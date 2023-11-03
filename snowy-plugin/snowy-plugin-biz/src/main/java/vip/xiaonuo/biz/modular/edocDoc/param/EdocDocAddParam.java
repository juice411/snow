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
package vip.xiaonuo.biz.modular.edocDoc.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 档案添加参数
 *
 * @author juice
 * @date  2023/10/18 16:20
 **/
@Getter
@Setter
public class EdocDocAddParam {

    /** 档案名称 */
    @ApiModelProperty(value = "档案名称", required = true, position = 2)
    @NotBlank(message = "name不能为空")
    private String name;

    /** 档案编号 */
    @ApiModelProperty(value = "档案编号", position = 3)
    private String code;

    /** 类目id */
    @ApiModelProperty(value = "类目id", required = true, position = 4)
    @NotBlank(message = "categoryId不能为空")
    private String categoryId;

    /** 保密级别 */
    @ApiModelProperty(value = "保密级别", position = 5)
    private String secretLevel;

    /** 标签 */
    @ApiModelProperty(value = "标签", position = 6)
    private String tag;

    /** 来源id,档案来源 */
    @ApiModelProperty(value = "来源id,档案来源", required = true, position = 7)
    @NotBlank(message = "supplyId不能为空")
    private String supplyId;

    /** 档案架id,档案存放位置 */
    @ApiModelProperty(value = "档案架id,档案存放位置", required = true, position = 8)
    @NotBlank(message = "spotId不能为空")
    private String spotId;

    /** 部门id,默认取CREATE_USER所在部门 */
    @ApiModelProperty(value = "部门id,默认取CREATE_USER所在部门", position = 9)
    private String orgId;

    /** 档案状态 */
    @ApiModelProperty(value = "档案状态", required = true, position = 10)
    @NotBlank(message = "status不能为空")
    private String status;

    /** 档案备注 */
    @ApiModelProperty(value = "档案备注", position = 11)
    private String remarks;

    /** 附件id列表 */
    @ApiModelProperty(value = "附件id列表", position = 12)
    private String fileIds;

    /** 保存周期，单位：年 */
    @ApiModelProperty(value = "保存周期，单位：年", required = true,position = 14)
    @NotBlank(message = "keepPeriod不能为空")
    private String keepPeriod;

    /** 借出id,档案借出单位 */
    @ApiModelProperty(value = "借出id,档案借出单位")
    private String borrowId;

    /** 借出周期，单位：天 */
    @ApiModelProperty(value = "借出周期，单位：天")
    private String borrowPeriod;
}
