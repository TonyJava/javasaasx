/*
* Powered By [javasaasx]
* Web Site: http://solrhome.com
* Github Code: https://github.com/choudou5
* License：MIT
* Since 2018 - 2020
*/
package com.choudou5.javasaasx.web.controller.sys;

import com.choudou5.base.page.PageResult;
import com.choudou5.base.util.AssertUtil;
import com.choudou5.base.util.StrUtil;
import com.choudou5.javasaasx.service.sys.SysRoleService;
import com.choudou5.javasaasx.service.sys.bo.SysRoleBo;
import com.choudou5.javasaasx.service.sys.bo.SysRoleQueryParam;
import com.choudou5.javasaasx.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Name：系统角色 Controller
 * @Author：xuhaowen
 * @Date：2018-02-22
 */
@Controller
@Scope("prototype")
@RequestMapping("/sys/sysRole")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService sysRoleService;


    /**
     * 对象绑定（表单提交时）
     * @param id
     * @return
     */
    @ModelAttribute
    public SysRoleBo get(@RequestParam(required=false) String id) {
        if (StrUtil.isNotBlank(id)){
            return sysRoleService.get(id);
        }else{
            return new SysRoleBo();
        }
    }

    /**
     * 列表
     * @param queryParam
     * @param req
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysRole:view")
    @RequestMapping(value = {"list", ""})
    public String list(SysRoleQueryParam queryParam, HttpServletRequest req, Model model) {
        PageResult<SysRoleBo> pageResult = sysRoleService.findPage(queryParam);
        model.addAttribute("pageResult", pageResult);
        return "/sys/sysRoleList";
    }

    /**
     * 查看
     * @param id
     * @param req
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysRole:view")
    @RequestMapping(value = {"view"}, method = RequestMethod.GET)
    public String view(String id, HttpServletRequest req, Model model) {
        SysRoleBo sysRoleBo = sysRoleService.get(id);
        model.addAttribute("sysRoleBo", sysRoleBo);
        return "/sys/sysRoleView";
    }

    /**
     * 编辑记录
     * @param id
     * @param req
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysRole:edit")
    @RequestMapping(value = "form", method = RequestMethod.GET)
    public String form(String id, HttpServletRequest req, Model model) {
        try {
            SysRoleBo bo = sysRoleService.get(id);
            AssertUtil.isNotNull(bo, "数据不存在！");
            model.addAttribute("sysRoleBo", bo);
        } catch (Exception e) {
            addMessage(model, e);
        }
        return "/sys/sysRoleForm";
    }

    /**
     * 保存记录
     * @param sysRoleBo
     * @param req
     * @param attributes
     * @return
     */
    @RequiresPermissions("sys:sysRole:edit")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public String save(SysRoleBo sysRoleBo, HttpServletRequest req, RedirectAttributes attributes) {
        //数据 验证
        if (!beanValidator(attributes, sysRoleBo))
            return returnFail(attributes);
        try {
            sysRoleService.save(sysRoleBo);
            return returnOK("保存成功！");
        } catch (Exception e) {
            return returnFail(e, "保存失败！");
        }
    }

    /**
     * 删除记录
     * @param id
     * @param req
     * @param attributes
     * @return
     */
    @RequiresPermissions("sys:sysRole:delete")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(String id, HttpServletRequest req, RedirectAttributes attributes) {
        try {
            sysRoleService.logicDeleteById(id);
            return returnOK("删除成功！");
        } catch (Exception e) {
            return returnFail(e, "删除失败！");
        }
    }

}
