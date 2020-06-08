package cn.interview.base;

public class FullPermutation {
    private int a[] = new int[50];    //声明存放数据的数组
    private int length = 0;
    //构造函数
    public FullPermutation(int length)
    {
        this.length = length;
        for(int i=1;i<=length;i++)
            a[i] = i;
    }

    //执行全排列算法
    public void perm1(int n)
    {
        if(n == length)
            this.dispArray();    //到最底层时输出排列结果
        else
        {
            for(int i=n;i<=length;i++)
            {
                this.swap(i, n);  //交换两数的值
                perm1(n + 1);    //递归执行perm1
                this.swap(i, n);    //恢复位置
            }
        }
    }

    //交换数组中两数的值
    public void swap(int x, int y)
    {
        int t = a[x];
        a[x] = a[y];
        a[y] = t;
    }

    //输出排列
    public void dispArray()
    {
        for(int i=1;i<=length;i++)
            System.out.print(a[i]);
        System.out.println();
    }
}
