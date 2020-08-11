
### [130. 被围绕的区域](https://leetcode-cn.com/problems/surrounded-regions/)

###### label：图连通性问题、dfs、bfs
#### 描述：

> 难度中等
>
> 给定一个二维的矩阵，包含 `'X'` 和 `'O'`（**字母 O**）。
> 找到所有被 `'X'` 围绕的区域，并将这些区域里所有的 `'O'` 用 `'X'` 填充。
> **示例:**
>
> ```
> X X X X
> X O O X
> X X O X
> X O X X
> ```
>
> 运行你的函数后，矩阵变为：
>
> ```
> X X X X
> X X X X
> X X X X
> X O X X
> ```
>
> **解释:**
>
> 被围绕的区间不会存在于边界上，换句话说，任何边界上的 `'O'` 都不会被填充为 `'X'`。 任何不在边界上，或不与边界上的 `'O'` 相连的 `'O'` 最终都会被填充为 `'X'`。如果两个元素在水平或垂直方向相邻，则称它们是“相连”的。

#### 方法一：dfs递归
##### 思路：

> 第一次遍历，对于边界的O和与之相邻的O用另一种符号替换，采用深度优先遍历；
>
> 第二次遍历，依次将另一种符号替换为O，O替换为X即可。

##### 复杂度：

> 时间复杂度：O(n*m)，其中n和m分别为矩阵的行和列数。在深度优先遍历过程中每个点最多被标记一次。
>
> 空间复杂度：O(n*m)，其中n和m分别为矩阵的行和列数。在深度优先遍历过程中栈开销。

##### 代码：
```java
class Solution {
    public void solve(char[][] board) {
        if (board.length == 0) {
            return;
        }
        int row = board.length, col = board[0].length;
        // 第一次遍历（dfs），将边界的O替换成临时符号
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                boolean isEdge = i == 0 || j == 0 || i == row - 1 || j == col - 1;
                if (isEdge && board[i][j] == 'O') {
                    dfs(board,i,j);
                }
            }
        }
        // 第二次遍历，依次将临时符号替换成O，O替换成X
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if(board[i][j] == '@'){
                    board[i][j] = 'O';
                }else if(board[i][j] == 'O'){
                    board[i][j] = 'X';
                }
            }
        }
    }

    void dfs(char[][] board, int i, int j) {
        // 出口
        if (i < 0 || j < 0
            || i >= board.length || j >= board[0].length
            || board[i][j] == 'X' || board[i][j] == '@') {
            return;
        }
        // 临时替换成@符号
        board[i][j] = '@';
        // 分别向上下左右遍历
        dfs(board, i - 1, j);
        dfs(board, i + 1, j);
        dfs(board, i, j - 1);
        dfs(board, i, j + 1);
    }
}
```
#### 方法二：dfs非递归
##### 思路：

> 由于递归和栈都具有回溯性，使用栈也能实现递归能实现的功能。
>
> 第一次遍历，对于边界的O和与之相邻的O用另一种符号替换，采用深度优先遍历；
> 第二次遍历，依次将另一种符号替换为O，O替换为X即可。

##### 复杂度：

> 时间复杂度：O(n*m)，其中n和m分别为矩阵的行和列数。在深度优先遍历过程中每个点最多被标记一次。
>
> 空间复杂度：O(n*m)，其中n和m分别为矩阵的行和列数。在深度优先遍历过程中栈开销。

##### 代码：
```java
class Solution {
    public void solve(char[][] board) {
        if (board.length == 0) {
            return;
        }
        int row = board.length, col = board[0].length;
        // 第一次遍历（dfs），将边界的O替换成临时符号
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                boolean isEdge = i == 0 || j == 0 || i == row - 1 || j == col - 1;
                if (isEdge && board[i][j] == 'O') {
                    dfsStack(board,i,j);
                }
            }
        }
        // 第二次遍历，依次将临时符号替换成O，O替换成X
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if(board[i][j] == '@'){
                    board[i][j] = 'O';
                }else if(board[i][j] == 'O'){
                    board[i][j] = 'X';
                }
            }
        }
    }

    void dfsStack(char[][] board, int i, int j) {
        LinkedList<Pos> stack = new LinkedList<>();
        stack.push(new Pos(i, j));
        board[i][j] = '@';
        while (!stack.isEmpty()) {
            Pos cur = stack.peek();
            // up
            if (cur.i - 1 >= 0 && board[cur.i - 1][cur.j] == 'O') {
                stack.push(new Pos(cur.i - 1, cur.j));
                board[cur.i - 1][cur.j] = '@';
                continue;
            }
            // down
            if (cur.i + 1 < board.length && board[cur.i + 1][cur.j] == 'O') {
                stack.push(new Pos(cur.i + 1, cur.j));
                board[cur.i + 1][cur.j] = '@';
                continue;
            }
            // left
            if (cur.j - 1 >= 0 && board[cur.i][cur.j - 1] == 'O') {
                stack.push(new Pos(cur.i, cur.j - 1));
                board[cur.i][cur.j - 1] = '@';
                continue;
            }
            // right
            if (cur.j + 1 < board[0].length && board[cur.i][cur.j + 1] == 'O') {
                stack.push(new Pos(cur.i, cur.j + 1));
                board[cur.i][cur.j + 1] = '@';
                continue;
            }
            // 若上下左右都搜索不到，本次搜索结束，弹栈
            stack.pop();
        }
    }
     class Pos{
        int i;
        int j;
        Pos(int i, int j){
            this.i = i;
            this.j = j;
        }
    }
}
```

#### 方法三：bfs
##### 思路：

> 与深度优先遍历使用栈实现不同的是，dfs**上下左右搜索只要搜到满足该条件（就入栈）**就顺着该方向继续搜索，而bfs是将**上下左右满足条件的都入队列**。
>
> 第一次遍历，对于边界的O和与之相邻的O用另一种符号替换，采用广度优先遍历；
> 第二次遍历，依次将另一种符号替换为O，O替换为X即可。

##### 复杂度：

> 时间复杂度：O(n*m)，其中n和m分别为矩阵的行和列数。在广度优先遍历过程中每个点最多被标记一次。
>
> 空间复杂度：O(n*m)，其中n和m分别为矩阵的行和列数。在广度优先遍历过程中队列开销。

##### 代码：
```java
class Solution {
    public void solve(char[][] board) {
        if (board.length == 0) {
            return;
        }
        int row = board.length, col = board[0].length;
        // 第一次遍历（bfs），将边界的O替换成临时符号
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                boolean isEdge = i == 0 || j == 0 || i == row - 1 || j == col - 1;
                if (isEdge && board[i][j] == 'O') {
                    bfs(board, i, j);
                }
            }
        }
        // 第二次遍历，依次将临时符号替换成O，O替换成X
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if(board[i][j] == '@'){
                    board[i][j] = 'O';
                }else if(board[i][j] == 'O'){
                    board[i][j] = 'X';
                }
            }
        }
    }

    void bfs(char[][] board, int i, int j) {
        LinkedList<Pos> queue = new LinkedList<>();
        queue.push(new Pos(i, j));
        board[i][j] = '@';
        while (!queue.isEmpty()) {
            Pos cur = queue.poll();
            // up
            if (cur.i - 1 >= 0 && board[cur.i - 1][cur.j] == 'O') {
                queue.offer(new Pos(cur.i - 1, cur.j));
                board[cur.i - 1][cur.j] = '@';
            }
            // down
            if (cur.i + 1 < board.length && board[cur.i + 1][cur.j] == 'O') {
                queue.offer(new Pos(cur.i + 1, cur.j));
                board[cur.i + 1][cur.j] = '@';
            }
            // left
            if (cur.j - 1 >= 0 && board[cur.i][cur.j - 1] == 'O') {
                queue.offer(new Pos(cur.i, cur.j - 1));
                board[cur.i][cur.j - 1] = '@';
            }
            // right
            if (cur.j + 1 < board[0].length && board[cur.i][cur.j + 1] == 'O') {
                queue.offer(new Pos(cur.i, cur.j + 1));
                board[cur.i][cur.j + 1] = '@';
            }
        }
    }
    
     class Pos{
        int i;
        int j;
        Pos(int i, int j){
            this.i = i;
            this.j = j;
        }
    }
}
```


