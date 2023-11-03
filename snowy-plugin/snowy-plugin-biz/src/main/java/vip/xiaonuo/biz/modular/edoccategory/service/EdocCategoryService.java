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
package vip.xiaonuo.biz.modular.edoccategory.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.edoccategory.entity.EdocCategory;
import vip.xiaonuo.biz.modular.edoccategory.param.EdocCategoryAddParam;
import vip.xiaonuo.biz.modular.edoccategory.param.EdocCategoryEditParam;
import vip.xiaonuo.biz.modular.edoccategory.param.EdocCategoryIdParam;
import vip.xiaonuo.biz.modular.edoccategory.param.EdocCategoryPageParam;

import java.util.List;

/**
 * 档案类目Service接口
 *
 * @author juice
 * @date  2023/10/17 16:55
 **/
public interface EdocCategoryService extends IService<EdocCategory> {

    /**
     * 获取档案类目分页
     *
     * @author juice
     * @date  2023/10/17 16:55
     */
    Page<EdocCategory> page(EdocCategoryPageParam edocCategoryPageParam);

    /**
     * 添加档案类目
     *
     * @author juice
     * @date  2023/10/17 16:55
     */
    void add(EdocCategoryAddParam edocCategoryAddParam);

    /**
     * 编辑档案类目
     *
     * @author juice
     * @date  2023/10/17 16:55
     */
    void edit(EdocCategoryEditParam edocCategoryEditParam);

    /**
     * 删除档案类目
     *
     * @author juice
     * @date  2023/10/17 16:55
     */
    void delete(List<EdocCategoryIdParam> edocCategoryIdParamList);

    /**
     * 获取档案类目详情
     *
     * @author juice
     * @date  2023/10/17 16:55
     */
    EdocCategory detail(EdocCategoryIdParam edocCategoryIdParam);

    /**
     * 获取档案类目详情
     *
     * @author juice
     * @date  2023/10/17 16:55
     **/
    EdocCategory queryEntity(String id);

    /**
     * 根据id获取父子数据列表
     *
     * @author xuyuxiang
     * @date 2022/8/15 14:55
     **/
    List<EdocCategory> getParentAndChildListById(List<EdocCategory> originDataList, String id, boolean includeSelf);

    /**
     * 根据id获取所有的子数据列表
     *
     * @author xuyuxiang
     * @date 2022/8/15 14:55
     **/
    List<EdocCategory> getChildListById(List<EdocCategory> originDataList, String id, boolean includeSelf);

    /**
     * 根据id获取所有的父数据列表
     *
     * @author xuyuxiang
     * @date 2022/8/15 14:55
     **/
    List<EdocCategory> getParentListById(List<EdocCategory> originDataList, String id, boolean includeSelf);

    /**
     * 根据id获取数据
     *
     * @author xuyuxiang
     * @date 2022/8/15 14:55
     **/
    EdocCategory getById(List<EdocCategory> originDataList, String id);

    //EdocCategory getParentById(List<EdocCategory> originDataList, String id);

    /**
     * 根据id获取子数据
     *
     * @author xuyuxiang
     * @date 2022/8/15 14:55
     **/
    //EdocCategory getChildById(List<EdocCategory> originDataList, String id);

    /**
     * 获取类目树选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    List<Tree<String>> categoryTreeSelector();

    /**
     * 获取类目树
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    List<Tree<String>> tree();
}
