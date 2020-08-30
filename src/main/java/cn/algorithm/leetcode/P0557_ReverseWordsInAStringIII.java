package cn.algorithm.leetcode;

public class P0557_ReverseWordsInAStringIII {
    public static void main(String[] args) {
        String s = "Let's take LeetCode contest";
        System.out.println(reverseWords(s));


        StringBuilder stringBuilder= new StringBuilder(s).insert(0, "AAA");
        stringBuilder.append("BBB");
        System.out.println(stringBuilder.toString());
    }

    static public String reverseWords(String s) {
        char as[] = s.toCharArray();
        int i = 0,j =0;
        for (; i < as.length; i++) {
            if(as[i] == ' '){
                swap(as,j,i-1);
                j = i+1;
            }
        }
        swap(as,j,as.length-1);
        return new String(as);
    }

    static void swap(char arr[], int l, int r){
        while (l < r){
            char temp = arr[l];
            arr[l++] = arr[r];
            arr[r--] = temp;
        }
    }


    static public String reverseWords1(String s) {
        int right=s.length()-1, left = 0;
        while (left<right){
        }
        return "";
    }
}
