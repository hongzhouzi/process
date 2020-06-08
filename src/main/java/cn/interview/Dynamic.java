package cn.interview;

import java.util.Scanner;

/**
 * @author weihongzhou
 * @date 2019/8/19
 */
public class Dynamic {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        System.out.println(climbStairs(n));
    }

    /**爬楼梯：
     * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
     * 递归解法
     * @param n
     * @return
     */
    static int climbStairs(int n){
        if(n==1 || n==2)
            return n;
        return climbStairs(n-1) + climbStairs(n-2);
    }

    /**
     * 动态规划解法:明确状态、写出状态转移方程
     * @param n
     * @return
     */
    static int climbStairs1(int n){
        if(n<=2)
            return n;
        int dp[] = new int[n+1];
        dp[0]=0; dp[1]=1; dp[2]=2;
        for(int i=3; i<=n; i++){
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }

    /**斐波那契数列解法：
     * 我们使用 dp 数组，其中 dp[i]=dp[i-1]+dp[i-2]。可以很容易通过分析得出 dp[i]dp[i] 其实就是第 i 个斐波那契数。
     * Fib(n)=Fib(n-1)+Fib(n-2)
     * 现在我们必须找出以 1 和 2 作为第一项和第二项的斐波那契数列中的第 n 个数，也就是说 Fib(1)=1 且 Fib(2)=2。
     * @param n
     * @return
     */
    static int climbStairs2(int n){
        if(n<=2)
            return n;
        int one = 1, two = 2;
        for(int i=3; i<=n; i++){
            //斐波那契数列递推公式  Fib(n)=Fib(n?1)+Fib(n?2)
            int three = one + two;
            //斐波那契数列往后挪一位
            one = two;
            two = three;
        }
        //因为在循环内把three赋值给two了，所以这儿直接返回two就OK
        return two;
    }

    /**
     *
     * @param n
     * @return
     */
    static int climbStairs3(int n){
        double sqrt5 = Math.sqrt(5);
        double fib = Math.pow((1+sqrt5)/2, n+1) - Math.pow((1-sqrt5)/5, n+1);
        return (int)(fib/sqrt5);
    }


    static int f(int grid[][]){
        int row = grid.length, col = grid[0].length;
        int dp[][] = new int[row][col];
        //从左上角开始
        for(int i=0; i<row-1;i++){
            for(int j=0; j<col-1; j++){
                dp[i][j] = grid[i][j] + Math.min(dp[i+1][j], dp[i][j+1]);
            }
        }
        return dp[row-1][col-1];
    }
    static int ff(int grid[][]){
        int row = grid.length, col = grid[0].length;
        int dp[][] = new int[row][col];
        //从右下角开始
        for(int i=row-1; i>=0;i--){
            for(int j=col-1; j>=0; j--){
                if(j!=col-1 && i!=row-1){//选择右边和上面较小的
                    dp[i][j] = grid[i][j] + Math.min(dp[i+1][j], dp[i][j+1]);
                }else if(j == col-1 && i!=row-1){//右边界，则只加下面的
                    dp[i][j] = grid[i][j] + dp[i+1][j];
                }else if(j!=col-1 && i==row-1){//下边界，则只加右边的
                    dp[i][j] = grid[i][j] + dp[i][j+1];
                }else{//dp[row-1][col-1]
                    dp[i][j] = grid[i][j];
                }
            }
        }
        return dp[0][0];
    }

    /**
     * 不用额外申请数组，但会破坏原始的数据
     * @param grid
     * @return
     */
    static int fff(int grid[][]){
        int row = grid.length, col = grid[0].length;
        //从右下角开始
        for(int i=row-1; i>=0;i--){
            for(int j=col-1; j>=0; j--){
                if(j!=col-1 && i!=row-1){//选择右边和上面较小的
                    grid[i][j] = grid[i][j] + Math.min(grid[i + 1][j],grid[i][j + 1]);
                }else if(j == col-1 && i!=row-1){//右边界，则只加下面的
                    grid[i][j] = grid[i][j] + grid[i+1][j];
                }else if(j!=col-1 && i==row-1){//下边界，则只加右边的
                    grid[i][j] = grid[i][j] + grid[i][j+1];
                }
            }
        }
        return grid[0][0];
    }

}

