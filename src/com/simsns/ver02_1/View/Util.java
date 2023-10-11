package com.simsns.ver02_1.View;

import java.util.Scanner;

public class Util {


    Scanner scanner = new Scanner(System.in);
    private static Util instance;

    private Util () {}

    public static Util getInstance () {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }

    public int scan () {
        try {
            System.out.print("입력 >> ");
            String scan = scanner.nextLine();
            if (scan.isEmpty()) {
                throw new Exception ("EMPTY_INPUT");
            }
            return Integer.parseInt(scan);
        } catch (Exception e) {
            if (e.getMessage().equals("EMPTY_INPUT")) {
                System.out.println("공백은 입력할 수 없습니다.");
            } else if (e instanceof NumberFormatException){
                System.out.println("문자는 입력할 수 없습니다.");
            }
            System.out.println("다시 입력을 받습니다..");
            return scan();
        }
    }

    public int scanCanEmpty () {
        int result = -1;

        try {
            System.out.print("입력 >> ");
            String scan = scanner.nextLine();
            result = Integer.parseInt(scan);
        } catch (Exception e) {
            if (e instanceof NumberFormatException){
                System.out.println("문자는 입력할 수 없습니다.");
            }
            System.out.println("다시 입력을 받습니다..");
            return scan();
        }

        return result;
    }
}
