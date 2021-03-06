package com.choudou5.javasaasx.base.bean;

import com.choudou5.javasaasx.base.util.SysSeqUtil;
import com.choudou5.javasaasx.base.util.UserUtil;

import java.util.Date;

/**
 * @Name：抽象 基础Po类
 * @Author：xuhaowende@sina.cn
 * @Date：2018-01-14 13:30
 * @Site：http://www.javasaas.top
 * @License：MIT
 */
public class AbstractBasePo implements BasePo {

    private String id;
    /** 创建人 */
    private String createBy;
    /** 创建时间 */
    private java.util.Date createTime;
    /** 修改人 */
    private String updateBy;
    /** 修改时间 */
    private java.util.Date updateTime;
    /** 删除标记 0=已删除，1=正常 */
    private String delFlag;

    protected boolean recordDataChange = true;

    protected static final String DEL_FLAG_YES = "0";
    protected static final String DEL_FLAG_NO = "1";

    public boolean isNew(){
        return id==null||id.length()==0;
    }


    /**
     * 插入之前执行方法，需要手动调用
     */
    public void preInsert(){
        preInsert(true);
    }

    /**
     * 插入之前执行方法，需要手动调用
     */
    public void preInsert(boolean autoId){
        if(autoId) {
            String id = SysSeqUtil.getIdStr();
            setId(id);
//        Long id = SysSeqUtil.generateId(SystemNames.SYSTEM, SystemNames.SUB_SYSTEM_ADMIN, TableNames.MODULE, getSqlTableName());
        }
        this.createBy = UserUtil.getUserId();
        this.createTime = new Date();
    }


    /**
     * 更新之前执行方法，需要手动调用
     */
    public void preUpdate(){
        this.updateBy = UserUtil.getUserId();
        this.updateTime = new Date();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public boolean isRecordDataChange() {
        return recordDataChange;
    }

    public void setRecordDataChange(boolean recordDataChange) {
        this.recordDataChange = recordDataChange;
    }
}
