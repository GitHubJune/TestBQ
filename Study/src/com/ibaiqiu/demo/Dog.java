package com.ibaiqiu.demo;

import java.lang.reflect.Method;

public class Dog {

	public static void main(String[] args) {
		Class cls;
		try {
			cls = Class.forName("com.ibaiqiu.demo.Animal");
			Object obj = cls.newInstance();	
			Method method = cls.getDeclaredMethod("test", null);
			method.setAccessible(true);
			method.invoke(obj, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}	
	
}
