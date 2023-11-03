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
package vip.xiaonuo.biz.modular.edocSpot.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.biz.modular.edocSpot.entity.EdocSpot;
import vip.xiaonuo.biz.modular.edocSpot.param.EdocSpotAddParam;
import vip.xiaonuo.biz.modular.edocSpot.param.EdocSpotEditParam;
import vip.xiaonuo.biz.modular.edocSpot.param.EdocSpotIdParam;
import vip.xiaonuo.biz.modular.edocSpot.param.EdocSpotPageParam;
import vip.xiaonuo.biz.modular.edocSpot.service.EdocSpotService;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 档案架控制器
 *
 * @author juice
 * @date  2023/10/18 15:37
 */
@Api(tags = "档案架控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class EdocSpotController {

    @Resource
    private EdocSpotService edocSpotService;

    /**
     * 获取档案架分页
     *
     * @author juice
     * @date  2023/10/18 15:37
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取档案架分页")
    @SaCheckPermission("/biz/edocSpot/page")
    @GetMapping("/biz/edocSpot/page")
    public CommonResult<Page<EdocSpot>> page(EdocSpotPageParam edocSpotPageParam) {
        return CommonResult.data(edocSpotService.page(edocSpotPageParam));
    }

    /**
     * 添加档案架
     *
     * @author juice
     * @date  2023/10/18 15:37
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加档案架")
    @CommonLog("添加档案架")
    @SaCheckPermission("/biz/edocSpot/add")
    @PostMapping("/biz/edocSpot/add")
    public CommonResult<String> add(@RequestBody @Valid EdocSpotAddParam edocSpotAddParam) {
        edocSpotService.add(edocSpotAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑档案架
     *
     * @author juice
     * @date  2023/10/18 15:37
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑档案架")
    @CommonLog("编辑档案架")
    @SaCheckPermission("/biz/edocSpot/edit")
    @PostMapping("/biz/edocSpot/edit")
    public CommonResult<String> edit(@RequestBody @Valid EdocSpotEditParam edocSpotEditParam) {
        edocSpotService.edit(edocSpotEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除档案架
     *
     * @author juice
     * @date  2023/10/18 15:37
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除档案架")
    @CommonLog("删除档案架")
    @SaCheckPermission("/biz/edocSpot/delete")
    @PostMapping("/biz/edocSpot/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<EdocSpotIdParam> edocSpotIdParamList) {
        edocSpotService.delete(edocSpotIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取档案架详情
     *
     * @author juice
     * @date  2023/10/18 15:37
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取档案架详情")
    @SaCheckPermission("/biz/edocSpot/detail")
    @GetMapping("/biz/edocSpot/detail")
    public CommonResult<EdocSpot> detail(@Valid EdocSpotIdParam edocSpotIdParam) {
        return CommonResult.data(edocSpotService.detail(edocSpotIdParam));
    }
}
