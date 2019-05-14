package com.ibaiqiu.demo;

public class Study {

	public static String temp = "Jessica";
	
	public void func1() {
		int num = 2;
		temp = "Penny";
		System.out.println(num);
	}
	
	public void func2() {
		temp = "Simba";
	}
	
	public static void main(String[] args) {
		Study study = new Study();
		study.func1();
		study.func2();
		System.out.println(temp);
	}
}
