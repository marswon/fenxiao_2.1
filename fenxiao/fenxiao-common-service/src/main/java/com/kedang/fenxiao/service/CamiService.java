package com.kedang.fenxiao.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kedang.fenxiao.entity.FXCami;
import com.kedang.fenxiao.repository.CamiDao;
import com.kedang.fenxiao.util.CollectionUtils;
import com.kedang.fenxiao.util.JpaQueryUtils;
import com.kedang.fenxiao.util.exception.ServiceException;

/**
 * Created by gegongxian on 16/10/19.
 */
@Component
public class CamiService {

    @Autowired
    private CamiDao camiDao;

    /**
     * 查询卡密
     *
     * @param params
     * @param pageable
     * @return
     */
    public Page<FXCami> findAllFXCami(Map<String, Object> params, Pageable pageable) {

        return camiDao.findAll(
                JpaQueryUtils.buildSpecification(FXCami.class, params), pageable);
    }

    /**
     * 批量保存分销卡密
     *
     * @param fxCami
     */
    public int saveFxCamiList(FXCami fxCami) {
        String camiStr = fxCami.getCamiStr();
        String camiStrCard = fxCami.getCamiStrCard();

        if (StringUtils.isBlank(camiStr)) {
            throw new ServiceException("卡密不能为空");
        }
        String[] camiData = camiStr.split(",");
        String[] camiStrCardData = camiStrCard.split(",");
        boolean validateCamiStrCard = false;
        if((fxCami.getBusinessType()+"").equals("3")&&null!=camiStrCardData&&camiStrCardData.length>1&&camiData.length!=camiStrCardData.length)
        {
            throw new ServiceException("卡密卡号不对应！");
        }
        if(null!=camiData&&null!=camiStrCardData&&camiData.length==camiStrCardData.length)
        {
            validateCamiStrCard = true;
        }
        if (null == camiData || camiData.length == 0) {
            throw new ServiceException("卡密不能为空2");
        }
        List<FXCami> fxcamiList = new ArrayList<FXCami>();
        int length = camiData.length;
        for (int i = 0; i < length; i++) {
            //判断卡密是否存在
            if (CollectionUtils.isNotEmpty(camiDao.findOneByCamiStr(camiData[i]))) {
                throw new ServiceException("该卡密已存在 [" + camiData[i] + " ]");
            }
            FXCami _tempFxcami = new FXCami();
            _tempFxcami.setFlowType(fxCami.getFlowType());
            _tempFxcami.setSize(fxCami.getSize());
            _tempFxcami.setStatus(0);//初始化状态未使用
            _tempFxcami.setCreateTime(new Date());
            _tempFxcami.setUpdateTime(new Date());
            _tempFxcami.setCamiStr(camiData[i]);
            _tempFxcami.setBusinessType(fxCami.getBusinessType());

            if(validateCamiStrCard){
                _tempFxcami.setCamiStrCard(camiStrCardData[i]);
            }
            fxcamiList.add(_tempFxcami);
        }
        if (CollectionUtils.isNotEmpty(fxcamiList)) {
            List<FXCami> _list = (List<FXCami>) camiDao.save(fxcamiList);
            if (CollectionUtils.isNotEmpty(_list)) {
                return _list.size();
            }
        }
        return 0;
    }

    @Transactional(readOnly=false)
	public FXCami update(FXCami fxCami)
	{
    	return camiDao.save(fxCami);
	}
}
