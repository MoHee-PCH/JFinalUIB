package com.platform.plugin;

import java.util.List;

import com.jfinal.log.Log;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.platform.annotation.Table;
import com.platform.mvc.base.BaseModel;
import com.platform.tools.ToolClassSearch;

/**
 * 表自动注册注解扫描，绑定table和model
 * @author 董华健  dongcb678@163.com
 */
public class TableScan implements IPlugin {

	private static final Log log = Log.getLog(TableScan.class);

	protected ActiveRecordPlugin arp;
	protected String configName;

	public TableScan(String configName, ActiveRecordPlugin arp) {
		this.arp = arp;
		this.configName = configName;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean start() {
		// 查询所有BaseModel的子类
		List<Class<?>> modelClasses = ToolClassSearch.search(BaseModel.class);

		// 循环处理自动注册映射
		for (Class model : modelClasses) {
			// 获取注解对象
			Table tableBind = (Table) model.getAnnotation(Table.class);
			if (tableBind == null) {
				log.warn(model.getName() + "继承了BaseModel，但是没有注解绑定表名，请检查是否已经手动绑定 ！！！");
				throw new RuntimeException(model.getName() + "继承了BaseModel，但是没有注解绑定表名，请检查是否已经手动绑定 ！！！");
			}

			// 获取映射属性
			String dataSourceName = tableBind.dataSourceName().trim();
			String tableName = tableBind.tableName().trim();
			String pkName = tableBind.pkName().trim();
			if (dataSourceName.equals("") || tableName.equals("") || pkName.equals("")) {
				log.error(model.getName() + "注解错误，数据源、表名、主键名为空 ！！！");
				throw new RuntimeException(model.getName() + "注解错误，数据源、表名、主键名为空 ！！！");
			}

			// 映射注册
			if (configName == null) {
				log.error(model.getName() + "ActiveRecordPlugin configName不能为null ！！！");
				throw new RuntimeException(model.getName() + "ActiveRecordPlugin configName不能为null ！！！");
			}
			if (arp == null) {
				log.error(model.getName() + "ActiveRecordPlugin不能为null ！！！");
				throw new RuntimeException(model.getName() + "ActiveRecordPlugin不能为null ！！！");
			}
			if (dataSourceName.equals(configName)) {
				arp.addMapping(tableName, pkName, model);
				log.debug("Model注册： model = " + model + ", tableName = " + tableName + ", pkName: " + pkName);
			}
		}
		return true;
	}

	@Override
	public boolean stop() {
		return true;
	}

}
