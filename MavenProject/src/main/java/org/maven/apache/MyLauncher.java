package org.maven.apache;

import java.io.IOException;

import org.maven.apache.spring.SpringConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 这个Class的存在 是为了欺骗 JRE 因为这个 Class 没有继承 Javafx 的 Application Class， 让 JVM
 * 错误的认为这个项目并非是一个 JAVAFX 的项目， 从而不在 RUNTIME 里面找 JAVAFX componenet
 * 
 * 另一条好处 是能让你直接在你的IDE 里面直接运行 JAVAFX 程序， 摆脱在CONSOLE里面输入 JAVAFX：RUN 之类的繁琐步骤
 * 
 * 但是！！！ 这么做之后， 在所有涉及到定义Main Class 或者要输入Main Class的路径的行为里， 该需要输入的路径或者Class
 * 将会是本Class。 "org.maven.apache.MyLauncher"
 * 
 * @author james
 *
 */
public class MyLauncher {
	public static final ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		App.main(args);
	}

}
