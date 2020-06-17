package com.example.lib;

import java.util.Scanner;

public class MyClass {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入一个10进制数：");
        String temp=scanner.nextLine();
//        String temp="65";
        int a = Integer.parseInt(temp);
        String tow= Integer.toBinaryString(a);
        System.out.println("输入10进制数为:"+temp+"输出2进制结果:"+tow);

    }
}
