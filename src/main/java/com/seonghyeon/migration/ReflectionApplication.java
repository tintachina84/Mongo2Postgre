package com.seonghyeon.migration;

import com.seonghyeon.migration.service.Mongo2Postgre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.Id;

@SpringBootApplication
public class ReflectionApplication implements CommandLineRunner {

	@Autowired
	private Mongo2Postgre mongo2Postgre;

	public static void main(String[] args) {
		SpringApplication.run(ReflectionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.mongo2Postgre.execMigration();

//		Practice practice = new Practice();
//
//		Object obj = practice;
//
//		Class c = obj.getClass();
//		Method[] m = c.getDeclaredMethods();
//		Field[] f = c.getDeclaredFields();
//		Constructor[] cs = c.getDeclaredConstructors();
//		Annotation[] a = c.getDeclaredAnnotations();
//		Class[] inter = c.getInterfaces();
//		Class superClass = c.getSuperclass();
//
//		for (int i = 0; i < m.length; i++) {
//			System.out.println("### method : " + m[i].toString());
//		}
//
//		for (int i = 0; i < f.length; i++) {
//			System.out.println("### field " + f[i].toString());
//			System.out.println("   ### annotation " + f[i].getAnnotation(Id.class));
//		}
//
//		for (int i = 0; i < cs.length; i++) {
//			System.out.println("### constructor " + cs[i].toString());
//		}
//
//		for (int i = 0; i < a.length; i++) {
//			System.out.println("### annotation " + a[i].toString());
//		}
//
//		for (int i = 0; i < inter.length; i++) {
//			System.out.println("### interface " + inter[i].toString());
//		}
//
//		System.out.println("### super class : " + superClass.getName().toString());
//
//		c.getMethod("sayHello", new Class[] { String.class }).invoke(obj, "Hello!");
//		c.getMethod("sayBye", new Class[] { String.class }).invoke(obj, "Bye!");
	}
}

class Practice {

	Practice() {}
	Practice (String name, int age) {}

	@Id
	private String name;
	private int age;

	public String asdf;

	public void sayHello(String hello) {
		System.out.println(hello);
	}

	public void sayBye(String bye) {
		System.out.println(bye);
	}
}