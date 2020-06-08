package cn.interview.base;

import java.util.*;

public class CollectionsDemo {
    public static void main(String[] args) {
        Hashtable hashtable = new Hashtable();
        HashMap hashMap = new HashMap();
String s1 = "wz";
String s2 = "zw";
String s3 = "hgebcijedg";
//LinkedList
        System.out.println("s1:"+s1.hashCode()+" s2:"+s2.hashCode()+" s3:"+s3.hashCode());
        Integer i1 = new Integer(1);
        Integer i2 = new Integer(1);
        HashMap<Integer,Integer> iMap = new HashMap<>();
        iMap.put(i1,1);
        iMap.put(i2,2);
        System.out.println(iMap.get(i1));
        System.out.println(iMap.get(i2));
    }
}
class Student{
    String name;
    String sex;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student stu = (Student) o;
        return Objects.equals(name, stu.name) &&
                Objects.equals(sex, stu.sex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sex);
    }
}