/*
* Powered By [javasaasx]
* Web Site: http://www.javasaas.top
* Github Code: https://github.com/choudou5
* License：MIT
* Since 2018 - 2020
*/
package com.choudou5.javasaasx.web.controller.sys;

import com.choudou5.base.annotation.ControllerDesc;
import com.choudou5.base.util.AssertUtil;
import com.choudou5.base.util.StrUtil;
import com.choudou5.base.util.tree.TreeHelper;
import com.choudou5.javasaasx.service.sys.SysOfficeService;
import com.choudou5.javasaasx.service.sys.SysUserService;
import com.choudou5.javasaasx.service.sys.vo.SysOfficeQueryParam;
import com.choudou5.javasaasx.service.sys.vo.SysOfficeVo;
import com.choudou5.javasaasx.service.sys.vo.SysUserVo;
import com.choudou5.javasaasx.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Name：系统机构 Controller
 * @Author：xuhaowen
 * @Date：2018-03-06
 */
@Controller
@Scope("prototype")
@RequestMapping("/sys/sysOffice")
public class SysOfficeController extends BaseController {

    @Autowired
    private SysOfficeService sysOfficeService;
    @Autowired
    private SysUserService sysUserService;


    /**
     * 对象绑定（表单提交时）
     * @param id
     * @return
     */
    @ModelAttribute
    public SysOfficeVo get(@RequestParam(required=false) String id) {
        if (StrUtil.isNotBlank(id)){
            return sysOfficeService.get(id);
        }else{
            return new SysOfficeVo();
        }
    }

    /**
     * @param queryParam
     * @param req
     * @param model
     * @return
     */
    @ControllerDesc(desc = "查看系统机构-列表", optType = "view")
    @RequiresPermissions("sys:sysOffice:view")
    @RequestMapping(value = {"list", ""})
    public String list(SysOfficeQueryParam queryParam, HttpServletRequest req, Model model) {
        queryParam.setDefParam(1, 200, "create_time", "desc");
        List<SysOfficeVo> leftList = sysOfficeService.findAll();
        Map<String, Object> leftTree = TreeHelper.buildTreeData(leftList, "总部");
        model.addAttribute("leftDataList", leftTree);
        List<SysUserVo> list = sysUserService.findAll();
        model.addAttribute("list", list);
        return "/sys/sysOfficeListTree";

//        List<SelectBo> list = sysOfficeService.getTableList();
//        model.addAttribute("leftDataList", list);
//        PageResult<SysOfficeVo> pageResult = sysOfficeService.findPage(queryParam);
//        model.addAttribute("pageResult", pageResult);
//        return "/sys/sysOfficeList";
    }

    /**
     * @param id
     * @param req
     * @param model
     * @return
     */
    @ControllerDesc(desc = "查看系统机构-详情", optType = "view")
    @RequiresPermissions("sys:sysOffice:view")
    @RequestMapping(value = {"view"}, method = RequestMethod.GET)
    public String view(String id, HttpServletRequest req, Model model) {
        SysOfficeVo sysOfficeVo = sysOfficeService.get(id);
        model.addAttribute("sysOfficeVo", sysOfficeVo);
        return "/sys/sysOfficeView";
    }

    /**
     * @param vo
     * @param req
     * @param model
     * @return
     */
    @ControllerDesc(desc = "编辑系统机构", optType = "edit")
    @RequiresPermissions("sys:sysOffice:edit")
    @RequestMapping(value = "form", method = RequestMethod.GET)
    public String form(SysOfficeVo vo, HttpServletRequest req, Model model) {
        try {
            AssertUtil.isNotNull(vo, "数据不存在");
            model.addAttribute("sysOfficeVo", vo);
        } catch (Exception e) {
            addMessage(model, e);
        }
        return "/sys/sysOfficeForm";
    }

    /**
     * @param sysOfficeBo
     * @param req
     * @param attributes
     * @return
     */
    @ControllerDesc(desc = "保存系统机构", optType = "save")
    @RequiresPermissions("sys:sysOffice:edit")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public String save(SysOfficeVo sysOfficeBo, HttpServletRequest req, RedirectAttributes attributes) {
        //数据 验证
        if (!beanValidator(attributes, sysOfficeBo))
            return returnFail(attributes);
        try {
            sysOfficeService.save(sysOfficeBo);
            return returnOK("保存成功");
        } catch (Exception e) {
            return returnFail(e, "保存失败");
        }
    }

    /**
     * @param id
     * @param req
     * @param attributes
     * @return
     */
    @ControllerDesc(desc = "删除系统机构", optType = "delete")
    @RequiresPermissions("sys:sysOffice:delete")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(String id, HttpServletRequest req, RedirectAttributes attributes) {
        try {
            sysOfficeService.logicDeleteById(id);
            return returnOK("删除成功");
        } catch (Exception e) {
            return returnFail(e, "删除失败");
        }
    }

}
