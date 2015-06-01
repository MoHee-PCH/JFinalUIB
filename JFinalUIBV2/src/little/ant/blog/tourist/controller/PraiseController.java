package little.ant.blog.tourist.controller;

import little.ant.platform.annotation.Controller;
import little.ant.platform.controller.BaseController;
import little.ant.blog.tourist.service.PraiseService;
import little.ant.blog.tourist.validator.PraiseValidator;
import little.ant.blog.model.Praise;

import org.apache.log4j.Logger;

import com.jfinal.aop.Before;

/**
 * XXX 管理	
 * 描述：
 * 
 * /jf/blog/tourist/praise
 * /jf/blog/tourist/praise/save
 * /jf/blog/tourist/praise/edit
 * /jf/blog/tourist/praise/update
 * /jf/blog/tourist/praise/view
 * /jf/blog/tourist/praise/delete
 * /common/praise/add.html
 * 
 */
@Controller(controllerKey = "/jf/blog/tourist/praise")
public class PraiseController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(PraiseController.class);
	
	/**
	 * 列表
	 */
	public void index() {
		PraiseService.service.list(splitPage);
		render("/blog/tourist/praise/list.html");
	}
	
	/**
	 * 保存
	 */
	@Before(PraiseValidator.class)
	public void save() {
		getModel(Praise.class).save();
		render("/blog/tourist/praise/add.html");
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {
		Praise praise = Praise.dao.findById(getPara());
		setAttr("praise", praise);
		render("/blog/tourist/praise/update.html");
	}
	
	/**
	 * 更新
	 */
	@Before(PraiseValidator.class)
	public void update() {
		getModel(Praise.class).update();
		redirect("/jf/blog/tourist/praise");
	}

	/**
	 * 查看
	 */
	public void view() {
		Praise praise = Praise.dao.findById(getPara());
		setAttr("praise", praise);
		render("/blog/tourist/praise/view.html");
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		String param = (getPara() == null ? ids : getPara());
		PraiseService.service.delete(param);
		redirect("/jf/blog/tourist/praise");
	}
	
}

