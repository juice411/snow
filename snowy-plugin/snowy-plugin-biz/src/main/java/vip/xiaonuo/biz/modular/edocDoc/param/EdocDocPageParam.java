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

/**
 * 档案查询参数
 *
 * @author juice
 * @date  2023/10/18 16:20
 **/
@Getter
@Setter
public class EdocDocPageParam {

    /** 当前页 */
    @ApiModelProperty(value = "当前页码")
    private Integer current;

    /** 每页条数 */
    @ApiModelProperty(value = "每页条数")
    private Integer size;

    /** 排序字段 */
    @ApiModelProperty(value = "排序字段，字段驼峰名称，如：userName")
    private String sortField;

    /** 排序方式 */
    @ApiModelProperty(value = "排序方式，升序：ASCEND；降序：DESCEND")
    private String sortOrder;

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

    /** 档案名称 */
    @ApiModelProperty(value = "档案名称")
    private String name;

    /** 档案编号 */
    @ApiModelProperty(value = "档案编号")
    private String code;

    /** 类目id */
    @ApiModelProperty(value = "类目id")
    private String categoryId;

    /** 保密级别 */
    @ApiModelProperty(value = "保密级别")
    private String secretLevel;

    /** 标签 */
    @ApiModelProperty(value = "标签")
    private String tag;

    /** 来源id,档案来源 */
    @ApiModelProperty(value = "来源id,档案来源")
    private String supplyId;

    /** 档案架id,档案存放位置 */
    @ApiModelProperty(value = "档案架id,档案存放位置")
    private String spotId;

    /** 部门id,默认取CREATE_USER所在部门 */
    @ApiModelProperty(value = "部门id,默认取CREATE_USER所在部门")
    private String orgId;

    /** 档案状态 */
    @ApiModelProperty(value = "档案状态")
    private String status;

    /** 保存周期，单位：年 */
    @ApiModelProperty(value = "保存周期，单位：年")
    private String keepPeriod;

    /** 销毁时间,创建时间+保存周期=销毁时间开始 */
    @ApiModelProperty(value = "销毁时间,创建时间+保存周期=销毁时间开始")
    private String startDestroyTime;

    /** 销毁时间,创建时间+保存周期=销毁时间结束 */
    @ApiModelProperty(value = "销毁时间,创建时间+保存周期=销毁时间结束")
    private String endDestroyTime;

    /** 创建时间开始 */
    @ApiModelProperty(value = "创建时间开始")
    private String startCreateTime;

    /** 创建时间结束 */
    @ApiModelProperty(value = "创建时间结束")
    private String endCreateTime;

    /** 借出id,档案借出单位 */
    @ApiModelProperty(value = "借出id,档案借出单位")
    private String borrowId;

    /** 借出周期，单位：天 */
    @ApiModelProperty(value = "借出周期，单位：天")
    private String borrowPeriod;

    /** 借出时间开始 */
    @ApiModelProperty(value = "借出时间开始")
    private String startBorrowTime;

    /** 借出时间结束 */
    @ApiModelProperty(value = "借出时间结束")
    private String endBorrowTime;

    /** 归还时间开始 */
    @ApiModelProperty(value = "归还时间开始")
    private String startReturnTime;

    /** 归还时间结束 */
    @ApiModelProperty(value = "归还时间结束")
    private String endReturnTime;

}
