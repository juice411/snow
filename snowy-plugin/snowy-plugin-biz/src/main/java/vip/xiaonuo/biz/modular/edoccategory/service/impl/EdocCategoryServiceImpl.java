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
package vip.xiaonuo.biz.modular.edoccategory.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.edocDoc.entity.EdocDoc;
import vip.xiaonuo.biz.modular.edocDoc.service.EdocDocService;
import vip.xiaonuo.biz.modular.edoccategory.entity.EdocCategory;
import vip.xiaonuo.biz.modular.edoccategory.mapper.EdocCategoryMapper;
import vip.xiaonuo.biz.modular.edoccategory.param.EdocCategoryAddParam;
import vip.xiaonuo.biz.modular.edoccategory.param.EdocCategoryEditParam;
import vip.xiaonuo.biz.modular.edoccategory.param.EdocCategoryIdParam;
import vip.xiaonuo.biz.modular.edoccategory.param.EdocCategoryPageParam;
import vip.xiaonuo.biz.modular.edoccategory.service.EdocCategoryService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 档案类目Service接口实现类
 *
 * @author juice
 * @date  2023/10/17 16:55
 **/
@Service
public class EdocCategoryServiceImpl extends ServiceImpl<EdocCategoryMapper, EdocCategory> implements EdocCategoryService {

    @Resource
    private EdocDocService edocDocService;
    @Override
    public Page<EdocCategory> page(EdocCategoryPageParam edocCategoryPageParam) {
        QueryWrapper<EdocCategory> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(edocCategoryPageParam.getParentId())) {
            queryWrapper.lambda().eq(EdocCategory::getParentId, edocCategoryPageParam.getParentId());
        }
        if(ObjectUtil.isNotEmpty(edocCategoryPageParam.getName())) {
            queryWrapper.lambda().like(EdocCategory::getName, edocCategoryPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(edocCategoryPageParam.getCode())) {
            queryWrapper.lambda().like(EdocCategory::getCode, edocCategoryPageParam.getCode());
        }
        if(ObjectUtil.isAllNotEmpty(edocCategoryPageParam.getSortField(), edocCategoryPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(edocCategoryPageParam.getSortOrder());
            queryWrapper.orderBy(true, edocCategoryPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(edocCategoryPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(EdocCategory::getSortCode);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EdocCategoryAddParam edocCategoryAddParam) {
        if (this.count(new LambdaQueryWrapper<EdocCategory>()
                .eq(EdocCategory::getName, edocCategoryAddParam.getName()))> 0) {
            throw new CommonException("存在重复的类目名称，名称为：{}", edocCategoryAddParam.getName());
        }
        EdocCategory edocCategory = BeanUtil.toBean(edocCategoryAddParam, EdocCategory.class);
        this.save(edocCategory);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EdocCategoryEditParam edocCategoryEditParam) {
        if (this.count(new LambdaQueryWrapper<EdocCategory>()
                .eq(EdocCategory::getName, edocCategoryEditParam.getName())
                .ne(EdocCategory::getId, edocCategoryEditParam.getId())) > 0) {
            throw new CommonException("存在重复的类目名称，名称为：{}", edocCategoryEditParam.getName());
        }
        EdocCategory edocCategory = this.queryEntity(edocCategoryEditParam.getId());
        BeanUtil.copyProperties(edocCategoryEditParam, edocCategory);
        this.updateById(edocCategory);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EdocCategoryIdParam> edocCategoryIdParamList) {
        List<String> orgIdList = CollStreamUtil.toList(edocCategoryIdParamList, EdocCategoryIdParam::getId);
        List<EdocCategory> allOrgList = this.list();
        // 获取所有子类目
        List<String> toDeleteOrgIdList = CollectionUtil.newArrayList();
        orgIdList.forEach(orgId -> toDeleteOrgIdList.addAll(this.getChildListById(allOrgList, orgId, true).stream()
                .map(EdocCategory::getId).collect(Collectors.toList())));
        // 类目下有档案不能删除（直属类目）
        boolean hasOrgUser = edocDocService.count(new LambdaQueryWrapper<EdocDoc>().in(EdocDoc::getCategoryId, toDeleteOrgIdList)) > 0;
        if(hasOrgUser) {
            throw new CommonException("请先删除类目下的档案");
        }
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(edocCategoryIdParamList, EdocCategoryIdParam::getId));
    }

    @Override
    public EdocCategory detail(EdocCategoryIdParam edocCategoryIdParam) {
        return this.queryEntity(edocCategoryIdParam.getId());
    }

    @Override
    public EdocCategory queryEntity(String id) {
        EdocCategory edocCategory = this.getById(id);
        if(ObjectUtil.isEmpty(edocCategory)) {
            throw new CommonException("档案类目不存在，id值为：{}", id);
        }
        return edocCategory;
    }

    public void execRecursionFindChild(List<EdocCategory> originDataList, String id, List<EdocCategory> resultList) {
        originDataList.forEach(item -> {
            if(item.getParentId().equals(id)) {
                resultList.add(item);
                execRecursionFindChild(originDataList, item.getId(), resultList);
            }
        });
    }

    public void execRecursionFindParent(List<EdocCategory> originDataList, String id, List<EdocCategory> resultList) {
        originDataList.forEach(item -> {
            if(item.getId().equals(id)) {
                EdocCategory parent = this.getById(originDataList, item.getParentId());
                if(ObjectUtil.isNotEmpty(parent)) {
                    resultList.add(parent);
                }
                execRecursionFindParent(originDataList, item.getParentId(), resultList);
            }
        });
    }

    @Override
    public List<EdocCategory> getParentAndChildListById(List<EdocCategory> originDataList, String id, boolean includeSelf) {
        List<EdocCategory> parentListById = this.getParentListById(originDataList, id, false);
        List<EdocCategory> childListById = this.getChildListById(originDataList, id, true);
        parentListById.addAll(childListById);
        return parentListById;
    }

    @Override
    public List<EdocCategory> getChildListById(List<EdocCategory> originDataList, String id, boolean includeSelf) {
        List<EdocCategory> resultList = CollectionUtil.newArrayList();
        execRecursionFindChild(originDataList, id, resultList);
        if(includeSelf) {
            EdocCategory self = this.getById(originDataList, id);
            if(ObjectUtil.isNotEmpty(self)) {
                resultList.add(self);
            }
        }
        return resultList;
    }

    @Override
    public List<EdocCategory> getParentListById(List<EdocCategory> originDataList, String id, boolean includeSelf) {
        List<EdocCategory> resultList = CollectionUtil.newArrayList();
        execRecursionFindParent(originDataList, id, resultList);
        if(includeSelf) {
            EdocCategory self = this.getById(originDataList, id);
            if(ObjectUtil.isNotEmpty(self)) {
                resultList.add(self);
            }
        }
        return resultList;
    }

    /* ====档案类目部分所需要用到的选择器==== */

    @Override
    public List<Tree<String>> categoryTreeSelector() {
        LambdaQueryWrapper<EdocCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        // 定义机构集合
        Set<EdocCategory> categorySet = CollectionUtil.newHashSet();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            // 获取所有类目
            List<EdocCategory> allOrgList = this.list();
            categorySet.addAll(allOrgList);
            List<String> loginUserDataScopeFullList = categorySet.stream().map(EdocCategory::getId).collect(Collectors.toList());
        } else {
            return CollectionUtil.newArrayList();
        }
        lambdaQueryWrapper.orderByAsc(EdocCategory::getSortCode);
        List<EdocCategory> bizOrgList = this.list(lambdaQueryWrapper);
        List<TreeNode<String>> treeNodeList = bizOrgList.stream().map(bizOrg ->
                        new TreeNode<>(bizOrg.getId(), bizOrg.getParentId(), bizOrg.getName(), bizOrg.getSortCode()))
                .collect(Collectors.toList());
        return TreeUtil.build(treeNodeList, "0");
    }

    @Override
    public EdocCategory getById(List<EdocCategory> originDataList, String id) {
        int index = CollStreamUtil.toList(originDataList, EdocCategory::getId).indexOf(id);
        return index == -1?null:originDataList.get(index);
    }

    /*@Override
    public EdocCategory getParentById(List<EdocCategory> originDataList, String id) {
        EdocCategory self = this.getById(originDataList, id);
        return ObjectUtil.isNotEmpty(self)?self:this.getById(originDataList, self.getParentId());
    }

    @Override
    public EdocCategory getChildById(List<EdocCategory> originDataList, String id) {
        int index = CollStreamUtil.toList(originDataList, EdocCategory::getParentId).indexOf(id);
        return index == -1?null:originDataList.get(index);
    }*/
    @Override
    public List<Tree<String>> tree() {
        // 获取所有机构
        List<EdocCategory> allOrgList = this.list();
        // 定义机构集合
        Set<EdocCategory> bizOrgSet = CollectionUtil.newHashSet();
        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
//            loginUserDataScope.forEach(orgId -> bizOrgSet.addAll(this.getParentListById(allOrgList, orgId, true)));

            bizOrgSet.addAll(allOrgList);
        } else {
            return CollectionUtil.newArrayList();
        }
        List<TreeNode<String>> treeNodeList = bizOrgSet.stream().map(bizOrg ->
                        new TreeNode<>(bizOrg.getId(), bizOrg.getParentId(),
                                bizOrg.getName(), bizOrg.getSortCode()).setExtra(JSONUtil.parseObj(bizOrg)))
                .collect(Collectors.toList());
        return TreeUtil.build(treeNodeList, "0");
    }
}
