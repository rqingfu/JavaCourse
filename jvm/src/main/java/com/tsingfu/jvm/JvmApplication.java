package com.tsingfu.jvm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


@SpringBootApplication
public class JvmApplication implements CommandLineRunner {

	@Autowired
	private HelloWorld helloWorld;

	@Autowired
	private MyClassLoader myClassLoader;

	public static void main(String[] args) {
		new SpringApplication(JvmApplication.class).run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		helloWorld.sayHello("class Loader");

		Class<?> xlass = myClassLoader.findClass("Hello.xlass");
		Constructor<?> constructor = xlass.getConstructor();
		if (null != constructor) {
			Object obj = constructor.newInstance();
			Method[] methods = xlass.getDeclaredMethods();
			for (Method m: methods) {
				if (Modifier.isPublic(m.getModifiers())
						&& m.getParameterCount() == 0) {
					m.invoke(obj);
				}
			}
		}
	}
}
