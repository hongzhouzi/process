package cn.examination.vipkid;

import java.util.*;

/**
 * @author: whz
 * @date: 2019/9/3
 **/
public class Main1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s[] = in.nextLine().replaceAll(" ","").split(",");
        List<Integer> zh = new ArrayList<>(s.length);
        List<Integer> fu = new ArrayList<>(s.length);
        for (int i=0; i<s.length; i++) {
            //正数
            if(Integer.valueOf(s[i]) > 0){
                zh.add(Integer.valueOf(s[i]));
            }else{//负数
                fu.add(Integer.valueOf(s[i]));
            }
        }
        zh.sort(Comparator.comparingInt(o -> o));
        fu.sort(Comparator.comparingInt(o -> o));
//        System.out.println(zh);
//        System.out.println(fu);
        int i=zh.size()-1, j=0, count=0;
       while ( fu.size() != j && -1 != i){
//            System.out.println("i:"+i+" j:"+j);
            //最大的正数和最大的负数相加大于0，正数偏大，正数指针左移动
            if(zh.get(i)+fu.get(j) > 0){
                i--;
            }else if(zh.get(i)+fu.get(j) < 0){//负数偏大，负数指针右移动
                j++;
            }else {
                count++;
                i--;
                j++;
            }
        }
        System.out.println(count);
    }
}
