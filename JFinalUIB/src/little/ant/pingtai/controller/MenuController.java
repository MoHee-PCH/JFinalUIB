package little.ant.pingtai.controller;

import little.ant.pingtai.annotation.Controller;
import little.ant.pingtai.model.Menu;
import little.ant.pingtai.service.MenuService;
import little.ant.pingtai.validator.MenuValidator;

import org.apache.log4j.Logger;

import com.jfinal.aop.Before;

@Controller(controllerKey = "/jf/menu")
public class MenuController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(MenuController.class);
	
	private String systemsIds;
	private String pIds;
	private String names;
	private int orderIds;
	private String operatorIds;
	
	public void index() {
		render("/pingtai/menu/tree.html");
	}

	public void treeData()  {
		String jsonText = MenuService.service.childNodeData(systemsIds, ids, getI18nPram());
		renderJson(jsonText);
	}
	
	@Before(MenuValidator.class)
	public void save() {
		ids = MenuService.service.save(pIds, names, orderIds, getI18nPram());
		renderText(ids);
	}
	
	@Before(MenuValidator.class)
	public void update() {
		MenuService.service.update(ids, pIds, names, getI18nPram());
		renderText(ids);
	}
	
	public void delete() {
		MenuService.service.delete(ids);
		renderText(ids);
	}

	public void getOperator(){
		Menu menu = Menu.dao.findById(ids);
		renderJson(menu);
	}

	public void setOperator(){
		MenuService.service.setOperator(ids, operatorIds);
		renderJson(ids);
	}
	
	/**
	 * 准备更新
	 */
	public void toEdit() {
		Menu menu = Menu.dao.findById(ids);
		setAttr("menu", menu);
		render("/pingtai/menu/edit.html");
	}
	
	/**
	 * 更新
	 */
	public void edit() {
		Menu menu = getModel(Menu.class);
		menu.update();
		menu = Menu.dao.findById(menu.getStr("ids"));
		redirect("/jf/menu?systemsIds=" + menu.getStr("systemsids"));
	}
}


