package com.kedang.fenxiao.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.Servlets;

import com.kedang.fenxiao.entity.FXCami;
import com.kedang.fenxiao.service.CamiService;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.exception.ServiceException;
import com.kedang.fenxiao.util.po.ResultDo;

/**
 * Created by gegongxian on 16/10/19.
 */
@Controller
@RequestMapping(value = "cami")
public class CamiController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(CamiController.class);

    @Autowired
    private CamiService camiService;

    @RequestMapping(value = "openCamiView")
    public String openCamiView() {
        return "cami/camiView";
    }

    /**
     * 分页查询卡密
     *
     * @param request
     * @param page
     * @param rows
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "findFXCami")
    public Page<FXCami> findFXCami(HttpServletRequest request,
                                   @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                   @RequestParam(value = "rows", defaultValue = "10", required = false) int rows) {
        Page<FXCami> pageList = null;

        try {
            logger.info("====== start CamiController.findFXCami ======");
            Map<String, Object> searchParams = Servlets
                    .getParametersStartingWith(request, "search_");
            pageList = camiService.findAllFXCami(searchParams,
                    new PageRequest(page - 1, rows, Sort.Direction.DESC, "createTime"));
            logger.info("====== end CamiController.findFXCami ,res[pageList="
                    + pageList + "] ======");
        } catch (Exception e) {
            logger.error("CamiController.findFXCami error["
                    + e.getCause() + "]");
        }
        return pageList;
    }

    /**
     * 保存卡密
     *
     * @param fxCami
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveCami")
    public ResultDo saveCami(FXCami fxCami) {
        try {
            if (StringUtils.isEmpty(fxCami.getFlowType())) {
                return ResultFactory.getFailedResult("请选择充值类型");
            }
            if (StringUtils.isEmpty(fxCami.getCamiStr())) {
                return ResultFactory.getFailedResult("请输入卡密");
            }
            if (StringUtils.isEmpty(fxCami.getSize())) {
                return ResultFactory.getFailedResult("请选择面值大小");
            }
            if(StringUtils.isEmpty(fxCami.getBusinessType())){
                return ResultFactory.getFailedResult("请选择业务类型");
            }
            int result = camiService.saveFxCamiList(fxCami);
            return ResultFactory.getSuccessResult("成功添加卡密 [" + result + "] 条");
        } catch (ServiceException e) {
            return ResultFactory.getFailedResult(e.getMessage());
        }
    }
}
