package com.choudou5.javasaasx.service.gen.bo;

import com.choudou5.javasaasx.framework.constant.CommonConstant;
import org.hibernate.validator.constraints.Length;
import com.choudou5.javasaasx.framework.bean.BaseBo;

import java.io.Serializable;

/**
 * @Name：生成表字段样式 业务对象
 * @Author：xuhaowende@sina.cn
 * @Date：2018-01-17
 * @Site：http://solrhome.com
 * @License：MIT
 * @Copyright：xuhaowende@sina.cn (@Copyright 2018-2020)
 */
public class GenTableColumnStyleBo implements BaseBo {

    /**  ID */
	private String id;
    /**  表 */
	private String table;
    /**  字段 */
	private String column;
    /**  字段描述 */
	private String desc;
    /**  字段名 */
	private String fieldName;
    /**  是否为插入字段 */
	private String isInsert = CommonConstant.DATA_STATUS_NO;
    /**  是否编辑字段: 0=否，1=是 */
	private String isEdit = CommonConstant.DATA_STATUS_NO;
    /**  是否列表字段: 0=否，1=是 */
	private String isList = CommonConstant.DATA_STATUS_OK;
    /**  是否查询字段: 0=否，1=是 */
	private String isQuery = CommonConstant.DATA_STATUS_NO;
    /**  查询方式：（eq，neq，gt，lt，between，like） */
	private String queryType = "eq";
    /**  字段生成方案: （input、textarea、select、checkbox、radio、dialog） */
	private String showType = "input";
    /**  字典类型 */
	private String dicType = "";
    /**  排序 */
	private Integer sort = 1;

	public GenTableColumnStyleBo(){
	}

	public GenTableColumnStyleBo( String id ){
			setId(id);
	}

	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getTable() {
		return this.table;
	}
	@Length(min=0, max=64, message="表长度必须介于 0 和 64 之间")
	public void setTable(String table) {
		this.table = table;
	}

	public String getColumn() {
		return this.column;
	}
	@Length(min=0, max=64, message="字段长度必须介于 0 和 64 之间")
	public void setColumn(String column) {
		this.column = column;
	}

	public String getDesc() {
		return this.desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getFieldName() {
		return this.fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getIsInsert() {
		return this.isInsert;
	}
	public void setIsInsert(String isInsert) {
		this.isInsert = isInsert;
	}

	public String getIsEdit() {
		return this.isEdit;
	}
	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	public String getIsList() {
		return this.isList;
	}
	public void setIsList(String isList) {
		this.isList = isList;
	}

	public String getIsQuery() {
		return this.isQuery;
	}
	public void setIsQuery(String isQuery) {
		this.isQuery = isQuery;
	}

	public String getQueryType() {
		return this.queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getShowType() {
		return this.showType;
	}
	public void setShowType(String showType) {
		this.showType = showType;
	}

	public String getDicType() {
		return this.dicType;
	}
	public void setDicType(String dicType) {
		this.dicType = dicType;
	}

	public Integer getSort() {
		return this.sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
