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
package vip.xiaonuo.biz.modular.edocDoc.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.edocDoc.entity.EdocDoc;
import vip.xiaonuo.biz.modular.edocDoc.mapper.EdocDocMapper;
import vip.xiaonuo.biz.modular.edocDoc.param.EdocDocAddParam;
import vip.xiaonuo.biz.modular.edocDoc.param.EdocDocEditParam;
import vip.xiaonuo.biz.modular.edocDoc.param.EdocDocIdParam;
import vip.xiaonuo.biz.modular.edocDoc.param.EdocDocPageParam;
import vip.xiaonuo.biz.modular.edocDoc.service.EdocDocService;
import vip.xiaonuo.biz.modular.edocHis.param.EdocHisAddParam;
import vip.xiaonuo.biz.modular.edocHis.service.EdocHisService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 档案Service接口实现类
 *
 * @author juice
 * @date  2023/10/18 16:20
 **/
@Service
public class EdocDocServiceImpl extends ServiceImpl<EdocDocMapper, EdocDoc> implements EdocDocService {

    @Resource
    private EdocHisService edocHisService;
    @Override
    public Page<EdocDoc> page(EdocDocPageParam edocDocPageParam) {
        QueryWrapper<EdocDoc> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(edocDocPageParam.getName())) {
            queryWrapper.lambda().like(EdocDoc::getName, edocDocPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(edocDocPageParam.getCode())) {
            queryWrapper.lambda().like(EdocDoc::getCode, edocDocPageParam.getCode());
        }
        if(ObjectUtil.isNotEmpty(edocDocPageParam.getCategoryId())) {
            queryWrapper.lambda().eq(EdocDoc::getCategoryId, edocDocPageParam.getCategoryId());
        }
        if(ObjectUtil.isNotEmpty(edocDocPageParam.getSecretLevel())) {
            queryWrapper.lambda().eq(EdocDoc::getSecretLevel, edocDocPageParam.getSecretLevel());
        }
        if(ObjectUtil.isNotEmpty(edocDocPageParam.getTag())) {
            queryWrapper.lambda().like(EdocDoc::getTag, edocDocPageParam.getTag());
        }
        if(ObjectUtil.isNotEmpty(edocDocPageParam.getSupplyId())) {
            queryWrapper.lambda().eq(EdocDoc::getSupplyId, edocDocPageParam.getSupplyId());
        }
        if(ObjectUtil.isNotEmpty(edocDocPageParam.getSpotId())) {
            queryWrapper.lambda().eq(EdocDoc::getSpotId, edocDocPageParam.getSpotId());
        }
        if(ObjectUtil.isNotEmpty(edocDocPageParam.getOrgId())) {
            queryWrapper.lambda().eq(EdocDoc::getOrgId, edocDocPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(edocDocPageParam.getStatus())) {
            queryWrapper.lambda().eq(EdocDoc::getStatus, edocDocPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(edocDocPageParam.getKeepPeriod())) {
            queryWrapper.lambda().ge(EdocDoc::getKeepPeriod, edocDocPageParam.getKeepPeriod());
        }
        if(ObjectUtil.isNotEmpty(edocDocPageParam.getStartDestroyTime()) && ObjectUtil.isNotEmpty(edocDocPageParam.getEndDestroyTime())) {
            queryWrapper.lambda().between(EdocDoc::getDestroyTime, edocDocPageParam.getStartDestroyTime(), edocDocPageParam.getEndDestroyTime());
        }
        if(ObjectUtil.isNotEmpty(edocDocPageParam.getStartCreateTime()) && ObjectUtil.isNotEmpty(edocDocPageParam.getEndCreateTime())) {
            queryWrapper.lambda().between(EdocDoc::getCreateTime, edocDocPageParam.getStartCreateTime(), edocDocPageParam.getEndCreateTime());
        }

        if(ObjectUtil.isNotEmpty(edocDocPageParam.getBorrowId())) {
            queryWrapper.lambda().eq(EdocDoc::getBorrowId, edocDocPageParam.getBorrowId());
        }
        if(ObjectUtil.isNotEmpty(edocDocPageParam.getBorrowPeriod())) {
            queryWrapper.lambda().ge(EdocDoc::getBorrowPeriod, edocDocPageParam.getBorrowPeriod());
        }
        if(ObjectUtil.isNotEmpty(edocDocPageParam.getStartBorrowTime()) && ObjectUtil.isNotEmpty(edocDocPageParam.getEndBorrowTime())) {
            queryWrapper.lambda().between(EdocDoc::getBorrowTime, edocDocPageParam.getStartBorrowTime(), edocDocPageParam.getEndBorrowTime());
        }
        if(ObjectUtil.isNotEmpty(edocDocPageParam.getStartReturnTime()) && ObjectUtil.isNotEmpty(edocDocPageParam.getEndReturnTime())) {
            queryWrapper.lambda().between(EdocDoc::getReturnTime, edocDocPageParam.getStartReturnTime(), edocDocPageParam.getEndReturnTime());
        }

        if(ObjectUtil.isAllNotEmpty(edocDocPageParam.getSortField(), edocDocPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(edocDocPageParam.getSortOrder());
            queryWrapper.orderBy(true, edocDocPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(edocDocPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(EdocDoc::getId);
        }

        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.lambda().in(EdocDoc::getOrgId, loginUserDataScope);
        } else {
            queryWrapper.lambda().eq(EdocDoc::getCreateUser, StpUtil.getLoginIdAsString());
        }

        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EdocDocAddParam edocDocAddParam) {
        //新增时如果不是归档状态直接返回错误提示
        if(!edocDocAddParam.getStatus().equals("DOC_STATUS_GATHER")){
            throw new CommonException("档案状态必须为归档状态");
        }
        EdocDoc edocDoc = BeanUtil.toBean(edocDocAddParam, EdocDoc.class);
        edocDoc.setOrgId(StpLoginUserUtil.getLoginUser().getOrgId());
        DateTime datetime = DateTime.now();
        edocDoc.setCreateTime(datetime);
        edocDoc.setDestroyTime(DateUtil.offset(datetime, DateField.YEAR, Integer.parseInt(edocDoc.getKeepPeriod())));
        resetBorrow(edocDoc);
        this.save(edocDoc);
        //同时记录档案历史
        EdocHisAddParam edocHisAddParam=new EdocHisAddParam();
        BeanUtil.copyProperties(edocDoc,edocHisAddParam);
        edocHisAddParam.setRemarks("初始归档");
        edocHisService.add(edocHisAddParam);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EdocDocEditParam edocDocEditParam) {
        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            if(!loginUserDataScope.contains(edocDocEditParam.getOrgId())) {
                throw new CommonException("您没有权限在该机构下修改操作，机构id：{}", edocDocEditParam.getOrgId());
            }
        } else {
            throw new CommonException("您没有权限在该机构下修改操作，机构id：{}", edocDocEditParam.getOrgId());
        }
        if(edocDocEditParam.getStatus().equals("DOC_STATUS_BORROW")){
            if(!ObjectUtil.isNotEmpty(edocDocEditParam.getBorrowId())){
                throw new CommonException("请选择档案借出单位！");
            }
            if(!ObjectUtil.isNotEmpty(edocDocEditParam.getBorrowPeriod())){
                throw new CommonException("档案借出周期不能为空！");
            }
            if(!NumberUtil.isNumber(edocDocEditParam.getBorrowPeriod())){
                throw new CommonException("档案借出周期为非法数字！");
            }
        }
        DateTime datetime = DateTime.now();
        EdocDoc edocDoc = this.queryEntity(edocDocEditParam.getId());

        //为记录历史做下准备
        EdocHisAddParam edocHisAddParam=new EdocHisAddParam();
        BeanUtil.copyProperties(edocDoc,edocHisAddParam);

        BeanUtil.copyProperties(edocDocEditParam, edocDoc);

        if(!edocHisAddParam.getStatus().equals(edocDoc.getStatus())){
            if(edocHisAddParam.getStatus().equals("DOC_STATUS_GATHER")){//源状态
                if(edocDoc.getStatus().equals("DOC_STATUS_BORROW")){//目标状态
                    edocDoc.setBorrowTime(datetime);
                    edocDoc.setReturnTime(DateUtil.offset(edocDoc.getBorrowTime(), DateField.DAY_OF_YEAR, Integer.parseInt(edocDoc.getBorrowPeriod())));

                    //历史放入新信息
                    BeanUtil.copyProperties(edocDoc, edocHisAddParam);
                    edocHisAddParam.setRemarks("归档->借出");


                }else if(edocDoc.getStatus().equals("DOC_STATUS_DESTROY")){//目标状态
                    resetBorrow(edocDoc);

                    edocHisAddParam.setRemarks("最终销毁");


                }else {
                    throw new CommonException("档案状态不符合操作逻辑！");
                }

            }else if(edocHisAddParam.getStatus().equals("DOC_STATUS_BORROW")){//源状态
                if(edocDoc.getStatus().equals("DOC_STATUS_RETURN")){//目标状态
                    //如果是归还状态，需要清空借出人、借出时间、借出周期、归还时间
                    resetBorrow(edocDoc);

                    edocHisAddParam.setRemarks("借出->归还");


                }else {
                    throw new CommonException("档案状态不符合操作逻辑！");
                }

            }else if(edocHisAddParam.getStatus().equals("DOC_STATUS_RETURN")){//源状态
                if(edocDoc.getStatus().equals("DOC_STATUS_BORROW")){//目标状态
                    edocDoc.setBorrowTime(datetime);
                    edocDoc.setReturnTime(DateUtil.offset(edocDoc.getBorrowTime(), DateField.DAY_OF_YEAR, Integer.parseInt(edocDoc.getBorrowPeriod())));

                    //历史放入新信息
                    BeanUtil.copyProperties(edocDoc, edocHisAddParam);
                    edocHisAddParam.setRemarks("归还->借出");


                }else if(edocDoc.getStatus().equals("DOC_STATUS_DESTROY")){//目标状态
                    resetBorrow(edocDoc);

                    edocHisAddParam.setRemarks("最终销毁");


                }else {
                    throw new CommonException("档案状态不符合操作逻辑！");
                }

            }else if(edocHisAddParam.getStatus().equals("DOC_STATUS_DESTROY")){//源状态

                throw new CommonException("档案状态不符合操作逻辑！");

            }

            edocHisAddParam.setCreateTime(datetime);
            edocHisAddParam.setCreateUser(StpUtil.getLoginIdAsString());
            edocHisAddParam.setStatus(edocDoc.getStatus());


            edocHisService.add(edocHisAddParam);

        }

        if(!edocDoc.getStatus().equals("DOC_STATUS_BORROW")){
            resetBorrow(edocDoc);
        }
        if(edocDoc.getStatus().equals("DOC_STATUS_BORROW")&&ObjectUtil.isNotEmpty(edocDoc.getBorrowPeriod())){
            //但借出周期是可以修改的，导致归还时间变动
            edocDoc.setReturnTime(DateUtil.offset(edocDoc.getBorrowTime(), DateField.DAY_OF_YEAR, Integer.parseInt(edocDoc.getBorrowPeriod())));
        }
        //同样，保存周期是可以修改的，从而导致销毁时间变动
        edocDoc.setDestroyTime(DateUtil.offset(edocDoc.getCreateTime(), DateField.YEAR, Integer.parseInt(edocDoc.getKeepPeriod())));
        edocDoc.setUpdateTime(datetime);

        this.updateById(edocDoc);

    }

    private void resetBorrow(EdocDoc edocDoc) {
        edocDoc.setBorrowId(null);
        edocDoc.setBorrowTime(null);
        edocDoc.setBorrowPeriod(null);
        edocDoc.setReturnTime(null);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EdocDocIdParam> edocDocIdParamList) {
        //数据范围校验
        List<String> edocDocIdList = CollStreamUtil.toList(edocDocIdParamList, EdocDocIdParam::getId);
        if(ObjectUtil.isNotEmpty(edocDocIdList)) {
            // 获取这些档案的的机构id集合
            Set<String> edocDocOrgIdList = this.listByIds(edocDocIdList).stream().map(EdocDoc::getOrgId).collect(Collectors.toSet());
            // 校验数据范围
            List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
            if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
                if(!loginUserDataScope.containsAll(edocDocOrgIdList)) {
                    throw new CommonException("您没有权限删除这些机构下的档案，机构id：{}", edocDocOrgIdList);
                }
            } else {
                throw new CommonException("您没有权限删除这些机构下的档案，机构id：{}", edocDocOrgIdList);
            }

            // 执行删除
            this.removeByIds(CollStreamUtil.toList(edocDocIdParamList, EdocDocIdParam::getId));
        }

    }

    @Override
    public EdocDoc detail(EdocDocIdParam edocDocIdParam) {
        return this.queryEntity(edocDocIdParam.getId());
    }

    @Override
    public EdocDoc queryEntity(String id) {
        EdocDoc edocDoc = this.getById(id);
        if(ObjectUtil.isEmpty(edocDoc)) {
            throw new CommonException("档案不存在，id值为：{}", id);
        }
        return edocDoc;
    }
}
