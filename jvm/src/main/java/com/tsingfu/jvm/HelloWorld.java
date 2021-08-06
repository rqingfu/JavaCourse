package com.tsingfu.jvm;

import org.springframework.stereotype.Component;
import sun.misc.Launcher;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

/**
 * @author tsingfu
 */
@Component
public class HelloWorld {

    public void sayHello(String name) {
        System.out.println("hi," + name + "!");
        System.out.println("print classLoader url:");
        printURLForBootstrapClassLoader();
    }

    private void printURLForBootstrapClassLoader() {
        System.out.println("BootstrapClassLoader");
        URL[] urls = Launcher.getBootstrapClassPath().getURLs();
        for (URL url: urls) {
            System.out.println(url);
        }
        ClassLoader classLoader = HelloWorld.class.getClassLoader().getParent();
        printURLForClassLoader(classLoader);
        classLoader = HelloWorld.class.getClassLoader();
        printURLForClassLoader(classLoader);
    }
    
    private void printURLForClassLoader(ClassLoader cl) {
        Object ucp = insightFiled(cl, "ucp");
        Object path = insightFiled(ucp, "path");
        ArrayList ps =(ArrayList) path;
        for (Object p: ps) {
            System.out.println(p.toString());
        }
    }

    private Object insightFiled(Object obj, String fName) {
        Field f = null;
        try {
            if (obj instanceof URLClassLoader) {
                f = URLClassLoader.class.getDeclaredField(fName);
            } else {
                f = obj.getClass().getDeclaredField(fName);
            }
            f.setAccessible(true);
            return f.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
