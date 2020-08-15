​	

###  [20. 有效的括号](https://leetcode-cn.com/problems/valid-parentheses/)

###### label：有效括号、栈
#### 描述：

> 难度简单
>
> 给定一个只包括 `'('`，`')'`，`'{'`，`'}'`，`'['`，`']'` 的字符串，判断字符串是否有效。
>
> 有效字符串需满足：
>
> 1. 左括号必须用相同类型的右括号闭合。
> 2. 左括号必须以正确的顺序闭合。
>
> 注意空字符串可被认为是有效字符串。
>
> **示例 1:**
> ```
> 输入: "()"
> 输出: true
> ```
>
> **示例 2:**
> ```
> 输入: "()[]{}"
> 输出: true
> ```
>
> **示例 3:**
> ```
> 输入: "(]"
> 输出: false
> ```
>
> **示例 4:**
> ```
> 输入: "([)]"
> 输出: false
> ```
>
> **示例 5:**
> ```
> 输入: "{[]}"
> 输出: true
> ```

#### 方法一：栈+map存符号对
##### 思路：

> 依次将字符入栈，入栈前若遇到配对的符号就弹出配对符号，继续下个字符。最后栈中剩下的符号就是未配对的符号。符号对使用map存储。

##### 复杂度：

> 时间复杂度：O(n)
>
> 空间复杂度：O(n+m)，其中n为栈开销m为字符集个数。

##### 代码：
```java
public boolean isValid(String s) {
        int len = s.length();
        if (len == 0) {
            return true;
        }
        // 优化，奇数长度直接返回false
        if ((len % 2) == 1 ){
            return false;
        }
    	// 匹配的字符串对
        Map<Character, Character> characterMap = new HashMap<Character, Character>(8) {{
            put('(', ')');
            put('{', '}');
            put('[', ']');
        }};
        LinkedList<Character> stack = new LinkedList<>();
        stack.push(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            if (!stack.isEmpty() && characterMap.getOrDefault(stack.peek(), '0') == s.charAt(i)) {
                stack.pop();
            } else {
                stack.push(s.charAt(i));
            }
        }
        return stack.isEmpty();
}
```

#### 方法二：栈+多个判断符号对
##### 思路：

> 依次将字符入栈，入栈前判断是否为开始符号，若为开始符号则将其结束符号入栈，否则看栈顶元素是否为对应结束符号，是则继续下个字符，反之则不匹配可直接返回了。最后栈中剩下的符号就是未配对的符号。
>
> 这个与使用map存储符号对不同的是，在入栈时直接入对应的结束符号，判断是否匹配看结束符号是否相等就可以了，就不用找符号对了。

##### 复杂度：

> 时间复杂度：O(n)
>
> 空间复杂度：O(n)，其中n为栈开销。

##### 代码：
```java
public boolean isValid(String s) {
        LinkedList<Character> stack = new LinkedList<>();
        for (char c : s.toCharArray()) {
            if (c == '[') {
                stack.push(']');
            } else if (c == '(') {
                stack.push(')');
            } else if (c == '{') {
                stack.push('}');
            } else if (stack.isEmpty() || c != stack.pop()) {
                return false;
            }
        }
        return stack.isEmpty();
    }
```



### [43. 字符串相乘](https://leetcode-cn.com/problems/multiply-strings/)

###### label：大数乘法、大数加法、数学
#### 描述：

> 难度中等
>
> 给定两个以字符串形式表示的非负整数 `num1` 和 `num2`，返回 `num1` 和 `num2` 的乘积，它们的乘积也表示为字符串形式。
>
> **示例 1:**
>
> ```
> 输入: num1 = "2", num2 = "3"
> 输出: "6"
> ```
>
> **示例 2:**
>
> ```
> 输入: num1 = "123", num2 = "456"
> 输出: "56088"
> ```
>
> **说明：**
>
> 1. `num1` 和 `num2` 的长度小于110。
> 2. `num1` 和 `num2` 只包含数字 `0-9`。
> 3. `num1` 和 `num2` 均不以零开头，除非是数字 0 本身。
> 4. **不能使用任何标准库的大数类型（比如 BigInteger）**或**直接将输入转换为整数来处理**。
>


#### 方法一：做加法思想
##### 思路：

> 按照传统方式用递等式方式，先依次相乘再相加，在相加过程中可以用往后补零的方式或者相加时错位。在做大数相加时主要把进位、长度不一致加0、处理错位时要注意在按位加之前初始化时把偏移量处理好。

##### 复杂度：

> 时间复杂度：O(n*m)，其中nm分别为字符串num1和num2的长度
>
> 空间复杂度：O(1)

##### 代码：
```java
public String multiply(String num1, String num2) {
        if ("0".equals(num1) || "0".equals(num2)) {
            return "0";
        }
        int len1 = num1.length(), len2 = num2.length();
        StringBuilder sum = new StringBuilder();
        for (int i = len1 - 1; i >= 0; i--) {
            StringBuilder sb = new StringBuilder("0");
            for (int j = len2 - 1; j >= 0; j--) {
                sb = addStrings(sb + "", ((num2.charAt(j) - '0') * (num1.charAt(i) - '0')) + "", len2 - j - 1);
            }
            sum = addStrings(sum + "", sb + "", len1 - i - 1);
        }
        return sum.toString();
}

StringBuilder addStrings(String num1, String num2, int offset) {
        if (offset < 0) {
            throw new RuntimeException("偏移量不支持负数");
        }
        StringBuilder sb = new StringBuilder();
        int len1 = num1.length();
        int len2 = num2.length();
        int carry = 0;
        // 在j初始化时需要把偏移量处理好
        for (int i = len1 - 1, j = len2 - 1 + offset; i >= 0 || j >= 0; i--, j--) {
            int a = carry + (i >= 0 ? num1.charAt(i) - '0' : 0) + (j >= 0 && j < len2 ? num2.charAt(j) - '0' : 0);
            sb.append(a % 10);
            carry = a / 10;
        }
        if (carry > 0) {
            sb.append(carry);
        }
        return sb.reverse();
}
```


#### 方法二：做乘法思想
##### 思路：

> 先逐位相乘，将乘得的值放在指定位置，同位置上的值累加；累加后处理进位，每个位置上的值超过一位数就要向前进位；若前导为0（乘得的值长度为n+m）则需进行处理，最后转为字符串即可。
>
> 关键要找准乘后值位数的关系，n长度和m长度的值乘后的值长度为n+m或n+m-1。对应位置关系为**arr[i+j+1] += num1[i] * num2[j]**，arr为存放乘得数字的数组，i j 分别为字符串中字符的对应位置。

##### 复杂度：

> 时间复杂度：O(n*m)，其中nm分别为字符串num1和num2的长度
>
> 空间复杂度：O(n+m)

##### 代码：
```java
public String multiply(String num1, String num2) {
        if ("0".equals(num1) || "0".equals(num2)) {
            return "0";
        }
        int len1 = num1.length(), len2 = num2.length();
        int[] ans = new int[len1 + len2];
        // 先逐位相乘，将值放在指定位置，同位置上的数累加
        for (int i = len1 - 1; i >= 0; i--) {
            int x = num1.charAt(i) - '0';
            for (int j = len2 - 1; j >= 0; j--) {
                int y = num2.charAt(j) - '0';
                ans[i + j + 1] += x * y;
            }
        }
        // 处理进位
        for (int i = len1 + len2 - 1; i > 0; i--) {
            ans[i - 1] += ans[i] / 10;
            ans[i] %= 10;
        }
        // 处理前导
        int index = ans[0] == 0 ? 1 : 0;
        StringBuffer sb = new StringBuffer();
        while (index < ans.length) {
            sb.append(ans[index++]);
        }
        return sb.toString();
}
```



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


### [133. 克隆图](https://leetcode-cn.com/problems/clone-graph/)

###### label：无向图的遍历、dfs、bfs
#### 描述：

> 难度中等
>
> 给你无向 **[连通](https://baike.baidu.com/item/连通图/6460995?fr=aladdin)** 图中一个节点的引用，请你返回该图的 [**深拷贝**](https://baike.baidu.com/item/深拷贝/22785317?fr=aladdin)（克隆）。
>
> 图中的每个节点都包含它的值 `val`（`int`） 和其邻居的列表（`list[Node]`）。
>
> ```
> class Node {
>  public int val;
>  public List<Node> neighbors;
>    }
> ```
>
> **测试用例格式：**
>
> 简单起见，每个节点的值都和它的索引相同。例如，第一个节点值为 1（`val = 1`），第二个节点值为 2（`val = 2`），以此类推。该图在测试用例中使用邻接列表表示。
>
> **邻接列表** 是用于表示有限图的无序列表的集合。每个列表都描述了图中节点的邻居集。
>
> 给定节点将始终是图中的第一个节点（值为 1）。你必须将 **给定节点的拷贝** 作为对克隆图的引用返回。

#### 写在前面
>  图的遍历与树最大的不同之处在于，若根据边找需遍历的点会陷入死循环，所以在遍历过程需要用一种数据结构记录已经被遍历的点。可以使用哈希表存储已遍历的点，若点中的值是具有唯一性的就可以用节点的值作为键，或者用节点作为键也行，但需要重写hash函数。


#### 方法一：dfs递归
##### 思路：

> 递归遍历图的所有领接点，遍历过程每遍历一个节点就将其加入哈希表中，标识该节点已遍历完，每个节点都遍历完了就返回值。

##### 复杂度：

> 时间复杂度：O(n)，其中n为节点数。
>
> 空间复杂度：O(n)，其中n节点数。主要为哈希表标识节点是否已遍历开销。

##### 代码：
```java
class Solution {
    public Node cloneGraph(Node node) {
        Map<Integer, Node> lookup = new HashMap<>();
        return dfs(node, lookup);
    }
    Node dfs(Node node, Map<Integer,Node> nodeMap){
        if(node == null){
            return null;
        }
        // 若已经遍历过该节点就直接返回
        if (nodeMap.containsKey(node.val)){
            return nodeMap.get(node.val);
        }
        Node clone = new Node(node.val, new ArrayList<>());
        // 标识该节点已遍历过
        nodeMap.put(node.val, clone);
        for (Node neighbor : node.neighbors) {
            clone.neighbors.add(dfs(neighbor,nodeMap));
        }
        return clone;
    }
}
```

#### 方法二：bfs
##### 思路：

> 使用一个队列来存储待遍历的领接点，依次遍历即可。
>

> 时间复杂度：O(n)，其中n为节点数。
>
> 空间复杂度：O(n)，其中n节点数。主要为哈希表标识节点是否已遍历开销。

##### 代码：
```java
class Solution {
	public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }
        Node clone = new Node(node.val, new ArrayList<>());
        Map<Integer, Node> nodeMap = new HashMap<>();
        nodeMap.put(node.val, clone);
        Deque<Node> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            Node curNode = queue.poll();
            for (Node neighbor : curNode.neighbors) {
                // 未遍历过，加入队列，标识已遍历（初始化领接点，此时不知道有哪些领接点每到添加的时候）
                if (!nodeMap.containsKey(neighbor.val)) {
                    queue.offer(neighbor);
                    nodeMap.put(neighbor.val, new Node(neighbor.val, new ArrayList<>()));
                }
                // 已经遍历过，取到该值，将领接点添加进去
                nodeMap.get(curNode.val).neighbors.add(nodeMap.get(neighbor.val));
            }
        }
        return clone;
    }
}
```





### [415. 字符串相加](https://leetcode-cn.com/problems/add-strings/)
###### label：大数相加、双指针

#### 描述：

> 难度简单
>
> 给定两个字符串形式的非负整数 `num1` 和`num2` ，计算它们的和。
>
>  **提示：**
>
> 1. `num1` 和`num2` 的长度都小于 5100
> 2. `num1` 和`num2` 都只包含数字 `0-9`
> 3. `num1` 和`num2` 都不包含任何前导零
> 4. **你不能使用任何內建 BigInteger 库， 也不能直接将输入的字符串转换为整数形式**

#### 方法一：
##### 思路：

> 同时倒序遍历两个字符串，遍历到的索引超出范围相加时就补零，还需注意处理好进位。

##### 复杂度：

> 时间复杂度：O(n)，n 为num1和num2中较长的长度
>
> 空间复杂度：O(1)

##### 代码：
```java
public String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        for (int i = num1.length() - 1, j = num2.length() - 1; i >= 0 || j >= 0; i--, j--) {
            int a = carry + (i >= 0 ? num1.charAt(i)-'0' : 0) + (j >= 0 ? num2.charAt(j)-'0' : 0);
            sb.append(a % 10);
            carry = a / 10;
        }
        if(carry > 0){
            sb.append(carry);
        }
        return sb.reverse().toString();
}
```







### [632. 最小区间](https://leetcode-cn.com/problems/smallest-range-covering-elements-from-k-lists/)


###### label：堆、优先队列、哈希表+滑动窗口
#### 描述：

> 难度困难
>
> 你有 `k` 个升序排列的整数列表。找到一个**最小**区间，使得 `k` 个列表中的每个列表至少有一个数包含在其中。
>
> 我们定义如果 `b-a < d-c` 或者在 `b-a == d-c` 时 `a < c`，则区间 [a,b] 比 [c,d] 小。
>
> **示例：**
>
> ```
> 输入：[[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
> 输出：[20,24]
> 解释： 
> 列表 1：[4, 10, 15, 24, 26]，24 在区间 [20,24] 中。
> 列表 2：[0, 9, 12, 20]，20 在区间 [20,24] 中。
> 列表 3：[5, 18, 22, 30]，22 在区间 [20,24] 中。
>
> ```
>
> **提示：**
>
> - 给定的列表可能包含重复元素，所以在这里升序表示 >= 。
> - 1 <= `k` <= 3500
> - -105 <= `元素的值` <= 105
> - **对于使用Java的用户，请注意传入类型已修改为List<List<Integer>>。重置代码模板后可以看到这项改动。**

#### 方法一：优先队列
##### 思路：

> 问题转换：k个列表中找最小区间，使每个列表都至少有个数在该区间。转化为：**从k个列表中各取一个数，使得k个数中的最大值和最小值的差值最小。**
>
> 由于k个列表都是升序排列的，因此在每个列表中维护一个指针，通过指针得到列表中的元素，指针右移之后的元素一定大于等于之前的元素**使用最小优先队列（最小堆实现）维护k个指针指向元素的最小值，同时维护堆中元素的最大值。**初始时k指针都指向下标0，最大值为所有下标0元素中的最大值，每次从堆中取出最小值，根据最大和最小值计算当前区间，若当前区间小于最小区间（初始化为元素的边界范围）则更新最小区间， 然后将对应列表的指针右移，并更新堆中元素、堆中最大值。
>
> 退出条件：某一个列表指针超出该列表索引，则说明该列表已遍历完，堆中不会再有该列表中的值，因此退出循环。

##### 复杂度：

> 时间复杂度：O(nk logk)，其中n是列表平均长度，k是列表数量，所有指针移动次数最多是nk次，更新优先队列时间复杂度logk。
>
> 空间复杂度：O(k)，其中k是列表数量，用于优先队列中维护k个元素。

##### 代码：
```java
public int[] smallestRange(List<List<Integer>> nums) {
        int rangeLeft = 0, rangeRight = Integer.MAX_VALUE;
        int minRange = rangeRight - rangeLeft;
        int max = Integer.MIN_VALUE;
        int size = nums.size();
        // 存放索引的指针
        int next[] = new int[size];
  		// 创建最小优先队列，是下面注释部分的简写
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> nums.get(o).get(next[o])));
        /*PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return nums.get(o1).get(next[o1]) - nums.get(o2).get(next[o2]);
            }
        });*/
        // 初始化最小优先队列中元素和其中最大值
        for (int i = 0; i < size; i++) {
            priorityQueue.offer(i);
            max = Math.max(max, nums.get(i).get(0));
        }
        while (true) {
            int minIndex = priorityQueue.poll();
            int curRange = max - nums.get(minIndex).get(next[minIndex]);
            // 当前范围小于最小范围，更新最小范围
            if (curRange < minRange) {
                minRange = curRange;
                rangeLeft = nums.get(minIndex).get(next[minIndex]);
                rangeRight = max;
            }
            // 指针右移
            next[minIndex]++;
            // 某列表遍历完就退出
            if (next[minIndex] == nums.get(minIndex).size()) {
                break;
            }
            priorityQueue.offer(minIndex);
            max = Math.max(max, nums.get(minIndex).get(next[minIndex]));
        }
        return new int[]{rangeLeft, rangeRight};
}
```



###  [xx.模板](https://leetcode-cn.com//)

###### label：xx、yy
#### 描述：

> 难度简单
>
> xxx

#### 方法一：XX
##### 思路：

> XX。

##### 复杂度：

> 时间复杂度：O(n)
>
> 空间复杂度：O(n)，其中n为栈开销。

##### 代码：
```java
code
```