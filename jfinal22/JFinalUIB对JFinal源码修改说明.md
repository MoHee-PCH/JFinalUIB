

1.增加sql打印格式化

	SqlReporter的55行，String info = "Sql: " + ToolSqlFormatter.format(String.valueOf(args[0]));

2.增加sql打印参数

	修改Dialect类，由于Oracle重写了打印方法，所以还修改了OracleDialect类
	
3.升级jetty9运行项目（暂时退回jfinal自带的jetty版本）
	
	修改JettyServer类的88-99行
	
4.FreeMarker过时方法处理

	FreeMarkerRender.java
	
5.处理distinct分页支持《2.1中暂未添加》

	修改 Db.java 和 DbPro.java， 增加 paginateDistinct 方法
	
6.在Model.java中修改update方法
	
	处理乐观锁，增加了更新sql语句的版本号条件
	
7.视图render增加耗时计算

	ActionHandler.java
	
8.视图RenderFactory增加构造参数

	可以指定下载文件名称，而不是使用默认文件名作为下载名称
	
9.Controller.java
	
	78行增加获取urlPara方法

10.Validator.java

	intercept方法，去掉final，支持BaseValidator重写此方法，增加service实例注入
	并把validator声明为类成员变量，增加instance方法实例化验证器,方便子类访问
	
	