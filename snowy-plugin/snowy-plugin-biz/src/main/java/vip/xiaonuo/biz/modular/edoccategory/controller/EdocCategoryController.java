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
package vip.xiaonuo.biz.modular.edoccategory.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.tree.Tree;
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
import vip.xiaonuo.biz.modular.edoccategory.entity.EdocCategory;
import vip.xiaonuo.biz.modular.edoccategory.param.EdocCategoryAddParam;
import vip.xiaonuo.biz.modular.edoccategory.param.EdocCategoryEditParam;
import vip.xiaonuo.biz.modular.edoccategory.param.EdocCategoryIdParam;
import vip.xiaonuo.biz.modular.edoccategory.param.EdocCategoryPageParam;
import vip.xiaonuo.biz.modular.edoccategory.service.EdocCategoryService;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 档案类目控制器
 *
 * @author juice
 * @date  2023/10/17 16:55
 */
@Api(tags = "档案类目控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class EdocCategoryController {

    @Resource
    private EdocCategoryService edocCategoryService;

    /**
     * 获取档案类目分页
     *
     * @author juice
     * @date  2023/10/17 16:55
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取档案类目分页")
    @SaCheckPermission("/biz/edocCategory/page")
    @GetMapping("/biz/edocCategory/page")
    public CommonResult<Page<EdocCategory>> page(EdocCategoryPageParam edocCategoryPageParam) {
        return CommonResult.data(edocCategoryService.page(edocCategoryPageParam));
    }

    /**
     * 添加档案类目
     *
     * @author juice
     * @date  2023/10/17 16:55
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加档案类目")
    @CommonLog("添加档案类目")
    @SaCheckPermission("/biz/edocCategory/add")
    @PostMapping("/biz/edocCategory/add")
    public CommonResult<String> add(@RequestBody @Valid EdocCategoryAddParam edocCategoryAddParam) {
        edocCategoryService.add(edocCategoryAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑档案类目
     *
     * @author juice
     * @date  2023/10/17 16:55
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑档案类目")
    @CommonLog("编辑档案类目")
    @SaCheckPermission("/biz/edocCategory/edit")
    @PostMapping("/biz/edocCategory/edit")
    public CommonResult<String> edit(@RequestBody @Valid EdocCategoryEditParam edocCategoryEditParam) {
        edocCategoryService.edit(edocCategoryEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除档案类目
     *
     * @author juice
     * @date  2023/10/17 16:55
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除档案类目")
    @CommonLog("删除档案类目")
    @SaCheckPermission("/biz/edocCategory/delete")
    @PostMapping("/biz/edocCategory/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<EdocCategoryIdParam> edocCategoryIdParamList) {
        edocCategoryService.delete(edocCategoryIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取档案类目详情
     *
     * @author juice
     * @date  2023/10/17 16:55
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取档案类目详情")
    @SaCheckPermission("/biz/edocCategory/detail")
    @GetMapping("/biz/edocCategory/detail")
    public CommonResult<EdocCategory> detail(@Valid EdocCategoryIdParam edocCategoryIdParam) {
        return CommonResult.data(edocCategoryService.detail(edocCategoryIdParam));
    }

    @ApiOperation("获取类目树选择器")
    @SaCheckPermission("/biz/edocCategory/edocCategoryTreeSelector")
    @GetMapping("/biz/edocCategory/edocCategoryTreeSelector")
    public CommonResult<List<Tree<String>>> categoryTreeSelector() {
        return CommonResult.data(edocCategoryService.categoryTreeSelector());
    }

    /**
     * 获取类目树
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("获取类目树")
    @SaCheckPermission("/biz/edocCategory/tree")
    @GetMapping("/biz/edocCategory/tree")
    public CommonResult<List<Tree<String>>> tree() {
        return CommonResult.data(edocCategoryService.tree());
    }

}
