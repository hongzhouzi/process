package cn.examination.kuaishou;

import java.util.Scanner;

/**
 * @author: whz
 * @date: 2019/8/25
 **/
public class BanBenHao {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        String [][] s = new String[n][2];
        for(int i=0; i<n; i++){
            s[i][0] = in.next();
            s[i][1] = in.next();
        }
        for(int i=0; i<n; i++){
            System.out.println(judge(s[i][0],s[i][1]));
        }
    }
    static boolean judge(String s1, String s2){
        int start0 = 0;
        int end0 = 0;
        //以s2为长的字符串，一旦s2中有小于就返回false
        for(int i=0; i<s2.length(); i++){
            //需要放置数组越界
            if((s1.length()-1)>=i && s2.charAt(i) < s1.charAt(i)){
                return false;
            }
            //后面为0，同一版本的情况
            if((s1.length()) == i && (s2.charAt(i)=='.')){
                start0 = i;
            }
            if((s2.length()-1) == i && (s2.charAt(i)=='0')){
                end0 = i;
            }
            if(s2.length()-1 <=i && (s2.charAt(i)!='0'&& s2.charAt(i)!='.')){
                return true;
            }
        }
        if(end0-start0 != s2.length()-s1.length()){
            return false;
        }
        return true;
    }
}
