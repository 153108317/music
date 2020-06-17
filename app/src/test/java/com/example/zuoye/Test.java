package com.example.zuoye;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入一个10进制数：");
        String temp=scanner.nextLine();
        int tow= Integer.parseInt(temp);
        System.out.println("输入10进制数为："+temp+"转2进制结果："+tow);

    }
}
