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
package vip.xiaonuo.biz.modular.edocHis.controller;

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
import vip.xiaonuo.biz.modular.edocHis.entity.EdocHis;
import vip.xiaonuo.biz.modular.edocHis.param.EdocHisAddParam;
import vip.xiaonuo.biz.modular.edocHis.param.EdocHisEditParam;
import vip.xiaonuo.biz.modular.edocHis.param.EdocHisIdParam;
import vip.xiaonuo.biz.modular.edocHis.param.EdocHisPageParam;
import vip.xiaonuo.biz.modular.edocHis.service.EdocHisService;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 档案历史控制器
 *
 * @author juice
 * @date  2023/10/29 19:11
 */
@Api(tags = "档案历史控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class EdocHisController {

    @Resource
    private EdocHisService edocHisService;

    /**
     * 获取档案历史分页
     *
     * @author juice
     * @date  2023/10/29 19:11
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取档案历史分页")
    @SaCheckPermission("/biz/edocHis/page")
    @GetMapping("/biz/edocHis/page")
    public CommonResult<Page<EdocHis>> page(EdocHisPageParam edocHisPageParam) {
        return CommonResult.data(edocHisService.page(edocHisPageParam));
    }

    /**
     * 添加档案历史
     *
     * @author juice
     * @date  2023/10/29 19:11
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加档案历史")
    @CommonLog("添加档案历史")
    @SaCheckPermission("/biz/edocHis/add")
    @PostMapping("/biz/edocHis/add")
    public CommonResult<String> add(@RequestBody @Valid EdocHisAddParam edocHisAddParam) {
        edocHisService.add(edocHisAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑档案历史
     *
     * @author juice
     * @date  2023/10/29 19:11
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑档案历史")
    @CommonLog("编辑档案历史")
    @SaCheckPermission("/biz/edocHis/edit")
    @PostMapping("/biz/edocHis/edit")
    public CommonResult<String> edit(@RequestBody @Valid EdocHisEditParam edocHisEditParam) {
        edocHisService.edit(edocHisEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除档案历史
     *
     * @author juice
     * @date  2023/10/29 19:11
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除档案历史")
    @CommonLog("删除档案历史")
    @SaCheckPermission("/biz/edocHis/delete")
    @PostMapping("/biz/edocHis/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<EdocHisIdParam> edocHisIdParamList) {
        edocHisService.delete(edocHisIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取档案历史详情
     *
     * @author juice
     * @date  2023/10/29 19:11
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取档案历史详情")
    @SaCheckPermission("/biz/edocHis/detail")
    @GetMapping("/biz/edocHis/detail")
    public CommonResult<EdocHis> detail(@Valid EdocHisIdParam edocHisIdParam) {
        return CommonResult.data(edocHisService.detail(edocHisIdParam));
    }
}
