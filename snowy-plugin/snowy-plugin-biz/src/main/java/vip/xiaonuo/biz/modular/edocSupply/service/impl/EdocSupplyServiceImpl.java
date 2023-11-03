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
package vip.xiaonuo.biz.modular.edocSupply.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.edocDoc.entity.EdocDoc;
import vip.xiaonuo.biz.modular.edocDoc.service.EdocDocService;
import vip.xiaonuo.biz.modular.edocSupply.entity.EdocSupply;
import vip.xiaonuo.biz.modular.edocSupply.mapper.EdocSupplyMapper;
import vip.xiaonuo.biz.modular.edocSupply.param.EdocSupplyAddParam;
import vip.xiaonuo.biz.modular.edocSupply.param.EdocSupplyEditParam;
import vip.xiaonuo.biz.modular.edocSupply.param.EdocSupplyIdParam;
import vip.xiaonuo.biz.modular.edocSupply.param.EdocSupplyPageParam;
import vip.xiaonuo.biz.modular.edocSupply.service.EdocSupplyService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * 档案来源Service接口实现类
 *
 * @author juice
 * @date  2023/10/18 15:41
 **/
@Service
public class EdocSupplyServiceImpl extends ServiceImpl<EdocSupplyMapper, EdocSupply> implements EdocSupplyService {

    @Resource
    private EdocDocService edocDocService;
    @Override
    public Page<EdocSupply> page(EdocSupplyPageParam edocSupplyPageParam) {
        QueryWrapper<EdocSupply> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(edocSupplyPageParam.getName())) {
            queryWrapper.lambda().like(EdocSupply::getName, edocSupplyPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(edocSupplyPageParam.getContactUser())) {
            queryWrapper.lambda().like(EdocSupply::getContactUser, edocSupplyPageParam.getContactUser());
        }
        if(ObjectUtil.isNotEmpty(edocSupplyPageParam.getTel())) {
            queryWrapper.lambda().like(EdocSupply::getTel, edocSupplyPageParam.getTel());
        }
        if(ObjectUtil.isNotEmpty(edocSupplyPageParam.getStartCreateTime()) && ObjectUtil.isNotEmpty(edocSupplyPageParam.getEndCreateTime())) {
            queryWrapper.lambda().between(EdocSupply::getCreateTime, edocSupplyPageParam.getStartCreateTime(), edocSupplyPageParam.getEndCreateTime());
        }
        if(ObjectUtil.isAllNotEmpty(edocSupplyPageParam.getSortField(), edocSupplyPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(edocSupplyPageParam.getSortOrder());
            queryWrapper.orderBy(true, edocSupplyPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(edocSupplyPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(EdocSupply::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EdocSupplyAddParam edocSupplyAddParam) {
        if (this.count(new LambdaQueryWrapper<EdocSupply>()
                .eq(EdocSupply::getName, edocSupplyAddParam.getName()))> 0) {
            throw new CommonException("存在重复的来源名称，名称为：{}", edocSupplyAddParam.getName());
        }
        EdocSupply edocSupply = BeanUtil.toBean(edocSupplyAddParam, EdocSupply.class);
        this.save(edocSupply);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EdocSupplyEditParam edocSupplyEditParam) {
        if (this.count(new LambdaQueryWrapper<EdocSupply>()
                .eq(EdocSupply::getName, edocSupplyEditParam.getName())
                .ne(EdocSupply::getId, edocSupplyEditParam.getId())) > 0) {
            throw new CommonException("存在重复的来源名称，名称为：{}", edocSupplyEditParam.getName());
        }
        EdocSupply edocSupply = this.queryEntity(edocSupplyEditParam.getId());
        BeanUtil.copyProperties(edocSupplyEditParam, edocSupply);
        this.updateById(edocSupply);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EdocSupplyIdParam> edocSupplyIdParamList) {
        List<String> edocSupplyIdList = CollStreamUtil.toList(edocSupplyIdParamList, EdocSupplyIdParam::getId);
        // 档案架下有档案不能删除
        boolean hasOrgUser = edocDocService.count(new LambdaQueryWrapper<EdocDoc>().in(EdocDoc::getSupplyId, edocSupplyIdList)) > 0;
        if(hasOrgUser) {
            throw new CommonException("请先删除档案来源下的档案");
        }
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(edocSupplyIdParamList, EdocSupplyIdParam::getId));
    }

    @Override
    public EdocSupply detail(EdocSupplyIdParam edocSupplyIdParam) {
        return this.queryEntity(edocSupplyIdParam.getId());
    }

    @Override
    public EdocSupply queryEntity(String id) {
        EdocSupply edocSupply = this.getById(id);
        if(ObjectUtil.isEmpty(edocSupply)) {
            throw new CommonException("档案来源不存在，id值为：{}", id);
        }
        return edocSupply;
    }
}
