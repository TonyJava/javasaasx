/*
* Powered By [javasaasx]
* Web Site: http://solrhome.com
* Github Code: https://github.com/choudou5
* License：MIT
* Since 2018 - 2020
*/
package com.choudou5.javasaasx.web.controller.log;

import com.choudou5.base.annotation.ControllerDesc;
import com.choudou5.base.bean.BetweenBean;
import com.choudou5.base.page.PageResult;
import com.choudou5.base.util.StrUtil;
import com.choudou5.javasaasx.service.log.SysOperationLogService;
import com.choudou5.javasaasx.service.log.bo.SysOperationLogBo;
import com.choudou5.javasaasx.service.log.bo.SysOperationLogQueryParam;
import com.choudou5.javasaasx.web.controller.BaseController;
import com.choudou5.javasaasx.web.util.RequestUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Name：系统操作日志 Controller
 * @Author：xuhaowen
 * @Date：2018-02-28
 */
@Controller
@Scope("prototype")
@RequestMapping("/log/sysOperationLog")
public class SysOperationLogController extends BaseController {

    @Autowired
    private SysOperationLogService sysOperationLogService;


    /**
     * 对象绑定（表单提交时）
     * @param id
     * @return
     */
    @ModelAttribute
    public SysOperationLogBo get(@RequestParam(required=false) String id) {
        if (StrUtil.isNotBlank(id)){
            return sysOperationLogService.get(id);
        }else{
            return new SysOperationLogBo();
        }
    }

    /**
     * @param queryParam
     * @param req
     * @param model
     * @return
     */
    @ControllerDesc(desc = "查看系统操作日志-列表", optType = "view")
    @RequiresPermissions("log:sysOperationLog:view")
    @RequestMapping(value = {"list", ""})
    public String list(SysOperationLogQueryParam queryParam, HttpServletRequest req, Model model) {
        queryParam.setOrderDefParam("create_time", "desc");
        BetweenBean betweenBean = RequestUtil.getRangeDateParameter(req, "rangeCreateDate", " - ");
        if(betweenBean != null)
            queryParam.addExtendParam("create_time", betweenBean.toSql());
        PageResult<SysOperationLogBo> pageResult = sysOperationLogService.findPage(queryParam);
        model.addAttribute("pageResult", pageResult);
        model.addAttribute("rangeCreateDate", RequestUtil.getStrParameter(req, "rangeCreateDate"));
        return "/log/sysOperationLogList";
    }

    /**
     * @param id
     * @param req
     * @param model
     * @return
     */
    @ControllerDesc(desc = "查看系统操作日志-详情", optType = "view")
    @RequiresPermissions("log:sysOperationLog:view")
    @RequestMapping(value = {"view"}, method = RequestMethod.GET)
    public String view(String id, HttpServletRequest req, Model model) {
        SysOperationLogBo sysOperationLogBo = sysOperationLogService.get(id);
        model.addAttribute("sysOperationLogBo", sysOperationLogBo);
        return "/log/sysOperationLogView";
    }

}