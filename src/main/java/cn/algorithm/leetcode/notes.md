

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
>class Node {
>  public int val;
>  public List<Node> neighbors;
>    }
>    ```
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



### [491. 递增子序列](https://leetcode-cn.com/problems/increasing-subsequences/)

###### label：子序列、dfs、bfs

#### 描述：

> 难度中等
>
> 给定一个整型数组, 你的任务是找到所有该数组的递增子序列，递增子序列的长度至少是2。
>
> **示例:**
>
> ```
> 输入: [4, 6, 7, 7]
> 输出: [[4, 6], [4, 7], [4, 6, 7], [4, 6, 7, 7], [6, 7], [6, 7, 7], [7,7], [4,7,7]]
> ```
>
> **说明:**
>
> 1. 给定数组的长度不会超过15。
> 2. 数组中的整数范围是 [-100,100]。
> 3. 给定数组中可能包含重复数字，相等的数字应该被视为递增的一种情况。


#### 方法一：dfs递归

##### 思路：

> 对序列从前往后深度优先搜索，搜索过程对数字进行去重，若搜索的当前数满足递增就加入结果集并继续向后搜索。

##### 复杂度：

> 时间复杂度：O()
>
> 空间复杂度：O()

##### 代码：

```java
List<List<Integer>> ret = new LinkedList<>();
public List<List<Integer>> findSubsequences1(int[] nums) {
    dfs(nums, -1, new ArrayList<>());
    return ret;
}

void dfs(int[] nums, int idx, List<Integer> cur) {
    // 当前递增长度序列长度大于0，就加入结果集
    if (cur.size() > 1) {
        ret.add(new ArrayList<>(cur));
    }
    // 在 [idx+1,  len -1] 范围内搜索下一个值，并借助set去重
    Set<Integer> set = new HashSet<>();
    for (int i = idx + 1; i < nums.length; i++) {
        if (set.contains(nums[i])) {
            continue;
        }
        set.add(nums[i]);
        // 出现递增序列则添加到结果集，并向下继续搜索
        if (idx == -1 || nums[i] >= nums[idx]) {
            cur.add(nums[i]);
            dfs(nums, i, cur);
            cur.remove(cur.size() - 1);
        }
    }
}
```

#### 方法二：bfs

##### 思路：

> 先组合两位长度的序列，组合时需要将序列的下一个索引放入队列中并将组合的数放入集合中保证添加的数不能重复，另外还需全局记录结果集中遍历到的位置，从队列中取出数据继续往后遍历看是否还有递增的数，有则将数据放入队列和结果集中并继续组合。

##### 复杂度：

> 时间复杂度：O()
>
> 空间复杂度：O()

##### 代码：

```java
public List<List<Integer>> findSubsequences(int[] nums) {
    List<List<Integer>> ret = new LinkedList<>();
    int len = nums.length, lastIndex = 0;
    // 记录顺序列表List<Integer>中最后索引
    Deque<Integer> queue = new LinkedList<>();
    Set<List<Integer>> set = new HashSet<>();
    // 组合两位数
    for (int i = 0; i < len; i++) {
        for (int j = i + 1; j < len; j++) {
            List<Integer> cur = new LinkedList<>();
            cur.add(nums[i]);
            cur.add(nums[j]);
            // 取的两位数相邻 || 之前有的组合不取
            if (nums[i] <= nums[j] && !set.contains(cur)) {
                set.add(cur);
                ret.add(cur);
                queue.offer(j + 1);
            }
        }
    }
    // 在前面组合数的基础上往后一位一位的尝试
    while (!queue.isEmpty()) {
        int pollIndex = queue.poll();
        int i = pollIndex;
        while (i < len) {
            List<Integer> cur = new LinkedList<>(ret.get(lastIndex));
            cur.add(nums[i]);
            if (nums[pollIndex - 1] <= nums[i] && !set.contains(cur)) {
                set.add(cur);
                ret.add(cur);
                queue.offer(i + 1);
            }
            i++;
        }
        lastIndex++;
    }
    return ret;
}
```



### [841. 钥匙和房间](https://leetcode-cn.com/problems/keys-and-rooms/)

###### label：dfs、bfs

#### 描述：

> 难度中等
>
> 有 `N` 个房间，开始时你位于 `0` 号房间。每个房间有不同的号码：`0，1，2，...，N-1`，并且房间里可能有一些钥匙能使你进入下一个房间。
> 在形式上，对于每个房间 `i` 都有一个钥匙列表 `rooms[i]`，每个钥匙 `rooms[i][j]` 由 `[0,1，...，N-1]` 中的一个整数表示，其中 `N = rooms.length`。 钥匙 `rooms[i][j] = v` 可以打开编号为 `v` 的房间。
> 最初，除 `0` 号房间外的其余所有房间都被锁住。
> 你可以自由地在房间之间来回走动。
> 如果能进入每个房间返回 `true`，否则返回 `false`。
> **示例 1：**
> ```
> 输入: [[1],[2],[3],[]]
> 输出: true
> 解释:  
> 我们从 0 号房间开始，拿到钥匙 1。
> 之后我们去 1 号房间，拿到钥匙 2。
> 然后我们去 2 号房间，拿到钥匙 3。
> 最后我们去了 3 号房间。
> 由于我们能够进入每个房间，我们返回 true。
> ```
> **示例 2：**
> ```
> 输入：[[1,3],[3,0,1],[2],[0]]
> 输出：false
> 解释：我们不能进入 2 号房间。
> ```
> **提示：**
>
> 1. `1 <= rooms.length <= 1000`
> 2. `0 <= rooms[i].length <= 1000`
> 3. 所有房间中的钥匙数量总计不超过 `3000`。


#### 方法一：dfs递归

##### 思路：

> dfs搜索每一条路径，标记访问过的房间，只要可以走完所有的房间就返回true，否则返回false。

##### 复杂度：

> 时间复杂度：O(n+m)，n为房间个数，m是房间钥匙总数
>
> 空间复杂度：O(n)，n为房间个数

##### 代码：

> ```java
> public boolean canVisitAllRooms(List<List<Integer>> rooms) {
>     int[] visitedRooms = new int[rooms.size()];
>     visitedRooms[0] = 1;
>     // 每个房间都访问过就返回true
>     return dfs(rooms, 0, visitedRooms) == rooms.size();
> }
> 
> int dfs(List<List<Integer>> rooms, int curRoom, int[] visitedRooms) {
>     List<Integer> nextRooms = rooms.get(curRoom);
>     int ret = 1;
>     for (int i = 0; i < nextRooms.size(); i++) {
>         int next = nextRooms.get(i);
>         // 访问过的房间就不再访问了
>         if (visitedRooms[next] == 1) {
>             continue;
>         }
>         // 将访问的房间标记为已访问
>         visitedRooms[next] = 1;
>         ret += dfs(rooms, next, visitedRooms);
>     }
>     return ret;
> }
> ```

#### 方法二：bfs

##### 思路：

> bfs搜索每一条路径，标记访问过的房间，只要可以走完所有的房间就返回true，否则返回false。

##### 复杂度：

> 时间复杂度：O(n+m)，n为房间个数，m是房间钥匙总数
>
> 空间复杂度：O(n)，n为房间个数

##### 代码：

```java
 public boolean canVisitAllRooms(List<List<Integer>> rooms) {
     int n = rooms.size(), num = 0;
     boolean[] vis = new boolean[n];
     Queue<Integer> que = new LinkedList<Integer>();
     vis[0] = true;
     que.offer(0);
     while (!que.isEmpty()) {
         int x = que.poll();
         num++;
         for (int it : rooms.get(x)) {
             if (!vis[it]) {
                 vis[it] = true;
                 que.offer(it);
             }
         }
     }
     return num == n;
 }
```



### [1567. 乘积为正数的最长子数组长度](https://leetcode-cn.com/problems/maximum-length-of-subarray-with-positive-product/)

###### label：贪心、dp

#### 描述：

> 难度中等
> 给你一个整数数组 `nums` ，请你求出乘积为正数的最长子数组的长度。
> 一个数组的子数组是由原数组中零个或者更多个连续数字组成的数组。
> 请你返回乘积为正数的最长子数组长度。
> **示例 1：**
> ```
> 输入：nums = [1,-2,-3,4]
> 输出：4
> 解释：数组本身乘积就是正数，值为 24 。
> ```
> **示例 2：**
> ```
> 输入：nums = [0,1,-2,-3,-4]
> 输出：3
> 解释：最长乘积为正数的子数组为 [1,-2,-3] ，乘积为 6 。
> 注意，我们不能把 0 也包括到子数组中，因为这样乘积为 0 ，不是正数。
> ```
> **示例 3：**
> ```
> 输入：nums = [-1,-2,-3,0,1]
> 输出：2
> 解释：乘积为正数的最长子数组是 [-1,-2] 或者 [-2,-3] 。
> ```
> **示例 4：**
>
> ```
> 输入：nums = [-1,2]
> 输出：1
> ```
> **示例 5：**
> ```
> 输入：nums = [1,2,3,5,-6,4,0,10]
> 输出：4
> ```
> **提示：**
>
> - `1 <= nums.length <= 10^5`
> - `-10^9 <= nums[i] <= 10^9`


#### 方法一：贪心

##### 思路：

> 往后读取数据。出现0，负数统计清零；出现负数记录个数和第一个负数索引。计算最大值时，若有偶数个负数则max和当前索引减去初始索引（-1）比较；若有奇数个负数则max与索引减去第一个负数的索引比较。

##### 复杂度：

> 时间复杂度：O(n)，n数组长度
>
> 空间复杂度：O(1)

##### 代码：

```java
public int getMaxLen(int[] nums) {
    int max = 0, neg = 0, neg1Index = 0, lastIndex = -1;
    for (int i = 0, len = nums.length; i < len; i++) {
        int cur = nums[i];
        // 记录负数的个数
        if (cur < 0) {
            neg++;
            if (neg == 1) {
                neg1Index = i;
                continue;
            }
        }
        // 以0为分割符
        else if (cur == 0) {
            neg = neg1Index = 0;
            lastIndex = i;
        }
        // 偶数个负数则max和当前索引减去初始索引
        if (neg % 2 == 0) {
            max = Math.max(max, i - lastIndex);
        }
        // 奇数个负数则max与索引减去从第一个负数索引
        else {
            max = Math.max(max, i - neg1Index);
        }
    }
    return max;
}
```

#### 方法二：动态规划

##### 思路：

> todo，暂时有点懵

##### 复杂度：

> 时间复杂度：O(n)，n数组长度
>
> 空间复杂度：O(1)

##### 代码：

```java

```



## 一笔画问题

#### 说明

> 给定一个 n 个点 m 条边的图，要求从指定的顶点出发，经过所有的边恰好一次（可以理解为给定起点的「一笔画 」问题），使得路径的字典序最小。
>
> 这种「一笔画」问题与欧拉图或者半欧拉图有着紧密的联系，下面给出定义：
>
> - 通过图中所有边恰好一次且行遍所有顶点的通路称为欧拉通路。
> - 通过图中所有边恰好一次且行遍所有顶点的回路称为欧拉回路。
> - 具有欧拉回路的无向图称为欧拉图。
> - 具有欧拉通路但不具有欧拉回路的无向图称为半欧拉图。

### [332. 重新安排行程](https://leetcode-cn.com/problems/reconstruct-itinerary/)

###### label：图、一笔画、dfs


#### 描述：

> 难度中等
>
> 给定一个机票的字符串二维数组 `[from, to]`，子数组中的两个成员分别表示飞机出发和降落的机场地点，对该行程进行重新规划排序。所有这些机票都属于一个从 JFK（肯尼迪国际机场）出发的先生，所以该行程必须从 JFK 开始。
>
> **说明:**
>
> 1. 如果存在多种有效的行程，你可以按字符自然排序返回最小的行程组合。例如，行程 ["JFK", "LGA"] 与 ["JFK", "LGB"] 相比就更小，排序更靠前
>
> 2. 所有的机场都用三个大写字母表示（机场代码）。
>
> 3. 假定所有机票至少存在一种合理的行程。
>
>    **示例 1:**
>
>    ```
>    输入: [["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]
>    输出: ["JFK", "MUC", "LHR", "SFO", "SJC"]
>    ```
>
>    **示例 2:**
>
>    ```
>    输入: [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
>    输出: ["JFK","ATL","JFK","SFO","ATL","SFO"]
>    解释: 另一种有效的行程是 ["JFK","SFO","ATL","JFK","ATL","SFO"]。但是它自然排序更大更靠后。
>    ```

#### 方法一：

##### 思路：

> 先将数据集添加到map中，因为一个键可能存储多个值，所以将值存在列表中，但题目中还要求了顺序，所以存储时使用优先队列的数据结构存储，在存放时就对数据集排好序。接下来就使用深度优先搜索找下个节点，当map中找不到下个节点时就说明到递归出口了，此次就将节点的值加入结果集中，注意需要使用头插法添加，递归时从下往上添加先添加的要在最后面。

##### 复杂度：

> 时间复杂度：O(m log m)，m为边的数量，对于每一条边需要log m的时间去删除它，所以最终序列长度为m+1，与n无关。
>
> 空间复杂度：O(m)，m为边的数量，存储每一条边。

##### 代码：

```java
LinkedList<String>                 ret        = new LinkedList<>();
Map<String, PriorityQueue<String>> ticketsMap = new HashMap<>();

public List<String> findItinerary(List<List<String>> tickets) {
    for (List<String> ticket : tickets) {
        String src = ticket.get(0);
        String tar = ticket.get(1);
        if (!ticketsMap.containsKey(src)) {
            ticketsMap.put(src, new PriorityQueue<>());
        }
        ticketsMap.get(src).add(tar);
        /*if (ticketsMap.containsKey(src)) {
                ticketsMap.get(src).add(tar);
            } else {
                // 使用优先队列，添加进去时会排序
                ticketsMap.put(src, new PriorityQueue<String>() {{
                    add(tar);
                }});
            }*/
    }
    dfs("JFK");
    return ret;
}

void dfs(String src) {
    PriorityQueue<String> list = ticketsMap.get(src);
    while (list != null && list.size() > 0) {
        dfs(list.poll());
    }
    // 头插法添加，递归时从下往上添加先添加的要在最后面
    ret.addFirst(src);
}
```



## 字符串

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


###  [459. 重复的子字符串](https://leetcode-cn.com/problems/repeated-substring-pattern/)

###### label：字符串、子串

#### 描述：

> 难度简单
>
> 给定一个非空的字符串，判断它是否可以由它的一个子串重复多次构成。给定的字符串只含有小写英文字母，并且长度不超过10000。
>
> **示例 1:**
>
> ```
> 输入: "abab"
> 
> 输出: True
> 
> 解释: 可由子字符串 "ab" 重复两次构成。
> ```
>
> **示例 2:**
>
> ```
> 输入: "aba"
> 
> 输出: False
> ```
>
> **示例 3:**
>
> ```
> 输入: "abcabcabcabc"
> 
> 输出: True
> 
> 解释: 可由子字符串 "abc" 重复四次构成。 (或者子字符串 "abcabc" 重复两次构成。)
> ```

#### 方法一：双倍字符串
##### 思路：

> 将字符串拼接成双倍，从双倍字符串中找原字符串，若不是重复子串构成，则找到的索引处等于原字符串长度，则若能找到则下标必定小于原字符串长度。

##### 复杂度：

> 

##### 代码：
```java
public boolean repeatedSubstringPattern1(String s) {
    return (s + s).indexOf(s, 1) != s.length();
}
```

#### 方法二：KMP算法

##### 思路：

> todo

##### 复杂度：

> 时间复杂度：O(n)
>
> 空间复杂度：O(n)

##### 代码：

```java
public boolean repeatedSubstringPattern(String s) {
    return kmp(s + s, s);
}

public boolean kmp(String query, String pattern) {
    int n = query.length();
    int m = pattern.length();
    int[] fail = new int[m];
    Arrays.fill(fail, -1);
    for (int i = 1; i < m; ++i) {
        int j = fail[i - 1];
        while (j != -1 && pattern.charAt(j + 1) != pattern.charAt(i)) {
            j = fail[j];
        }
        if (pattern.charAt(j + 1) == pattern.charAt(i)) {
            fail[i] = j + 1;
        }
    }
    int match = -1;
    for (int i = 1; i < n - 1; ++i) {
        while (match != -1 && pattern.charAt(match + 1) != query.charAt(i)) {
            match = fail[match];
        }
        if (pattern.charAt(match + 1) == query.charAt(i)) {
            ++match;
            if (match == m - 1) {
                return true;
            }
        }
    }
    return false;
}
```



### [17. 电话号码的字母组合](https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/)



###### label：字符串、队列
#### 描述：

> 难度中等
>
> 给定一个仅包含数字 `2-9` 的字符串，返回所有它能表示的字母组合。
>
> 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
>
> ![img](https://assets.leetcode-cn.com/aliyun-lc-upload/original_images/17_telephone_keypad.png)
>
> **示例:**
>
> ```
> 输入："23"
> 输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
> ```

#### 方法一：递归
##### 思路：

> 将字符串放入结果集中，往下递归遍历时将结果集的数据取出来拼接后再放入结果集，直到结束。

##### 复杂度：

> 时间复杂度：O()
>
> 空间复杂度：O()

##### 代码：
```java
Map<Character, String> dictMap = new HashMap<Character, String>() {{
    put('2', "abc");
    put('3', "def");
    put('4', "ghi");
    put('5', "jkl");
    put('6', "mno");
    put('7', "pqrs");
    put('8', "tuv");
    put('9', "wxyz");
}};
public List<String> letterCombinations(String digits) {
    if (digits.length() < 1) {
        return new ArrayList<>();
    }
    List<String> ret = new LinkedList<>();
    // 把第一组数据加入结果集
    String dict = dictMap.get(digits.charAt(0));
    for (int i = 0; i < dict.length(); i++) {
        ret.add(dict.charAt(i) + "");
    }
    if (digits.length() == 1) {
        return ret;
    }
    // 递归遍历
    return recursion(digits, 1, ret);
}

List<String> recursion(String digits, int idx, List<String> res) {
    if (digits.length() == idx) {
        return res;
    }
    String dict = dictMap.get(digits.charAt(idx));
    List<String> ret = new LinkedList<>();
    // 从结果集中取出组合
    for (int i = 0; i < dict.length(); i++) {
        for (String s : res) {
            ret.add(s + dict.charAt(i));
        }
    }
    return recursion(digits, idx + 1, ret);
}
```

#### 方法二：递归优化

##### 思路：

> 在方法一的基础上对字符串相关处理优化，使用String对字符串进行拼接操作很耗内存，这儿用StringBuilder优化，并且将

##### 复杂度：

> 时间复杂度：O()
>
> 空间复杂度：O()

##### 代码：

```java
Map<Character, String> dictMap = new HashMap<Character, String>() {{
    put('2', "abc");
    put('3', "def");
    put('4', "ghi");
    put('5', "jkl");
    put('6', "mno");
    put('7', "pqrs");
    put('8', "tuv");
    put('9', "wxyz");
}};
public List<String> letterCombinations(String digits) {
    List<String> res = new ArrayList<>();
    if (digits == null || digits.length() == 0) {
        return res;
    }
    dfs(new StringBuilder(), digits, 0, res);
    return res;
}
// 使用StringBuilder优化，直到递归出口时才转为String放入结果集
void dfs(StringBuilder sb, String digits, int n, List<String> res) {
    if (n == digits.length()) {
        res.add(sb.toString());
        return;
    }
    String s = dictMap.get(digits.charAt(n));
    // 递归过程
    for (int i = 0; i < s.length(); i++) {
        sb.append(s.charAt(i));
        dfs(sb, digits, n + 1, res);
        sb.deleteCharAt(sb.length() - 1);
    }
}
```

#### 方法三：使用队列

##### 思路：

> 将待拼接的字符串放在队列中，每次遍历到下个数字时都从队列中取出上次放入的字符串，然后将字符串拼接上放入队列直到遍历结束。

##### 复杂度：

> 时间复杂度：O()
>
> 空间复杂度：O()

##### 代码：

```java
 public List<String> letterCombinations(String digits) {
     LinkedList<String> ret = new LinkedList<>();
     if(digits.length() == 0){
         return ret;
     }
     ret.add("");
     for (int i = 0; i < digits.length(); i++) {
         String dict = dictMap.get(digits.charAt(i));
         while (!ret.isEmpty() && ret.peek().length() == i) {
             String poll = ret.poll();
             for (int j = 0; j < dict.length(); j++) {
                 ret.offer(poll + dict.charAt(j));
             }
         }
     }
     return ret;
 }
```




## 树

#### 说明

> 本专题主要是对树的问题归纳总结

#### 名词解释

##### 高度

> **高度：**对于任意节点n,**n 的高度为从 n 到一片树叶的最长路径长**，所有**树叶的高度为0**。
>
> **树的高度：** 树所有节点的最大深度。

##### 深度

> **树的深度：** 树所有节点的最大深度。

##### 遍历

> **广度优先遍历：**又称层次遍历，遍历时从根节点一层一层的往下层遍历，遍历过程使用**队列**结构存储待遍历节点（从队列中取遍历的节点，并将其子节点放入队列中，队列为空时即遍历完了）。
>
> **深度优先遍历：**先序遍历、中序遍历、后序遍历。其中先中后序指的是**根节点的遍历顺序**，先序即：先根再左最后右。深度优先遍历过程需要**回溯**，因此使用**递归**实现dfs最常见，也可以使用**栈**实现。

##### 树的种类

> **满二叉树：** 除叶子节点外，所有节点均含有 2 个子树的树。
>
> **完全二叉树：** 有2^k - 1个节点的满二叉树。
>
> **哈夫曼树（最优二叉树）：** 带权路径最短的二叉树。
>
> **平衡二叉树(AVL)：** 它是一 棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树，同时，平衡二叉树必定是二叉搜索树。
>
> **二叉查找树（二叉搜索树、BST）**
>
> 若任意节点的左子树不空，则左子树上所有节点的值均小于它的根节点的值；
> 若任意节点的右子树不空，则右子树上所有节点的值均大于它的根节点的值；
> 任意节点的左、右子树也分别为二叉查找树；
> 没有键值相等的节点。
>
> **红黑树：**
>
> 红黑树是一颗特殊的二叉查找树，除了二叉查找树的要求外，它还具有以下特性：
>
>  - 每个节点或者是黑色，或者是红色。
>
>  - 根节点是黑色。
>
>  - 每个叶子节点（NIL）是黑色。 [注意：这里叶子节点，是指为空(NIL或NULL)的叶子节点！]
>
>  - 如果一个节点是红色的，则它的子节点必须是黑色的。
>
>  - 从一个节点到该节点的子孙节点的所有路径上包含相同数目的黑节点。
>
>    



### [107. 二叉树的层次遍历 II](https://leetcode-cn.com/problems/binary-tree-level-order-traversal-ii/)

###### label：树高度

##### 描述

> 难度简单
>
> 给定一个二叉树，返回其节点值自底向上的层次遍历。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
>
> 例如：
> 给定二叉树 `[3,9,20,null,null,15,7]`,
>
> ```
>     3
>    / \
>   9  20
>     /  \
>    15   7
> ```
>
> 返回其自底向上的层次遍历为：
>
> ```java
> [
>   [15,7],
>   [9,20],
>   [3]
> ]
> ```

#### 方法一 bfs

##### 思路

> 用两个queue存放待遍历的数据，一个queue存放父节点，一个queue存放子节点，父节点队列空了时将子节点的queue放在父节点。

##### 复杂度分析

> 时间复杂度：O(n)
>
> 空间复杂度：O(n)

##### 代码

```java
public List<List<Integer>> levelOrderBottom(TreeNode root) {
    LinkedList<List<Integer>> ret = new LinkedList<>();
    if (Objects.isNull(root)) {
        return ret;
    }
    LinkedList<TreeNode> queueFather = new LinkedList<>();
    LinkedList<TreeNode> queueSon = new LinkedList<>();
    List<Integer> temp = new LinkedList<>();
    queueFather.offer(root);
    while (!queueFather.isEmpty() || !queueSon.isEmpty()) {
        // 父节点队列空了时数据放入结果集，并将子节点的queue放在父节点
        if (queueFather.isEmpty()) {
            ret.addFirst(temp);
            temp = new LinkedList<>();
            while (!queueSon.isEmpty()) {
                queueFather.offer(queueSon.poll());
            }
        }
        //1、把当前节点移除队列，输出内容
        TreeNode cur = queueFather.poll();
        temp.add(cur.val);
        //2、若存在左孩子，就将其加入队列
        if (Objects.nonNull(cur.left)) {
            queueSon.offer(cur.left);
        }
        //3、若存在右孩子，就将其加入队列
        if (Objects.nonNull(cur.right)) {
            queueSon.offer(cur.right);
        }
    }
    ret.addFirst(temp);
    return ret;
}
```

#### 方法二 bfs

##### 思路

> bfs遍历，队列循环中再套循环从队列中取出元素，将当前层次的遍历完将对应层次的值添加到对应层次的list中。

##### 复杂度分析

> 时间复杂度：O(n)
>
> 空间复杂度：O(n)

##### 代码

```java
public List<List<Integer>> levelOrderBottom(TreeNode root) {
    List<List<Integer>> levelOrder = new LinkedList<>();
    if (root == null) {
        return levelOrder;
    }
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
        List<Integer> level = new ArrayList<>();
        int size = queue.size();
        // 将当前层次的遍历完
        for (int i = 0; i < size; i++) {
            TreeNode node = queue.poll();
            level.add(node.val);
            TreeNode left = node.left, right = node.right;
            if (left != null) {
                queue.offer(left);
            }
            if (right != null) {
                queue.offer(right);
            }
        }
        // 头插到结果集中
        levelOrder.add(0, level);
    }
    return levelOrder;
}
```



#### 方法三 dfs

##### 思路

> dfs遍历，遍历时将对应层次的值添加到对应层次的list中。

##### 复杂度分析

> 时间复杂度：O(n)
>
> 空间复杂度：O(n)

##### 代码

```java
public List<List<Integer>> levelOrderBottom1(TreeNode root) {
    List<List<Integer>> ret = new ArrayList<>();
    dfs(root, ret, 0);
    return ret;
}

void dfs(TreeNode root, List<List<Integer>> ret, int level) {
    if (Objects.isNull(root)) {
        return;
    }
    // 头插法，添加到头部
    if (ret.size() == level) {
        ret.add(0, new ArrayList<>());
    }
    // 在对应层次将值添加到结果集
    ret.get(ret.size() - 1 - level).add(root.val);
    dfs(root.left, ret, level + 1);
    dfs(root.right, ret, level + 1);
}
```





### [110. 平衡二叉树](https://leetcode-cn.com/problems/balanced-binary-tree/)

###### label：树高度

##### 描述

> 难度简单
>
> 给定一个二叉树，判断它是否是高度平衡的二叉树。
>
> 本题中，一棵高度平衡二叉树定义为：
>
> 一个二叉树*每个节点* 的左右两个子树的高度差的绝对值不超过1。

#### 方法一

##### 思路

> 自顶向下，计算出树左右两边的最大高度，保证左右两边的高度差小于2，同时满足左右两边的子树也满足平衡。

##### 复杂度分析

> 时间复杂度：O(n^2)，n为树的节点数。由于是自顶向下计算
>
> 空间复杂度：O(n)，n为树的节点个数，递归调用过程每个节点都需要调用。

##### 代码

```java
public boolean isBalanced(TreeNode root) {
    if (root == null) {
        return true;
    }
    return Math.abs(getHeight(root.left) - getHeight(root.right)) < 2
        && isBalanced(root.left)
        && isBalanced(root.right);
}

int getHeight(TreeNode root) {
    if (root == null) {
        return 0;
    }
    return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
}
```


#### 方法二

##### 思路

> 自底向上的思路，关键优化的地方在于计算左右子树高度上，计算高度后就判断是否平衡，每个节点都只遍历一次。而方法一中从顶向下计算时，对每个节点都会从顶向下，就多了重复的计算。

##### 复杂度分析

> 时间复杂度：O(n)，n为树的节点数。
>
> 空间复杂度：O(n)，n为树的节点个数，递归调用过程每个节点都需要调用。

##### 代码

```java
public boolean isBalanced1(TreeNode root) {
    return helper(root) != -1;
}
int helper(TreeNode root){
    if(Objects.isNull(root)){
        return 0;
    }
    int left = helper(root.left);
    int right = helper(root.right);
    return Math.abs(left - right) > 1 || left == -1 || right == -1
        ? -1
        : Math.max(left,right) + 1;
}
```



### [111. 二叉树的最小深度](https://leetcode-cn.com/problems/minimum-depth-of-binary-tree/)

###### label：树深度

##### 描述

> 难度简单
>
> 给定一个二叉树，找出其最小深度。
>
> 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
>
> **说明:** 叶子节点是指没有子节点的节点。
>
> **示例:**
>
> 给定二叉树 `[3,9,20,null,null,15,7]`,
>
> ```
>     3
>    / \
>   9  20
>     /  \
>    15   7
> ```
>
> 返回它的最小深度  2.


#### 方法一

##### 思路

> 递归计算树的深度，需要注意的是当左子树或者右子树为空时最小深度是另一个不为空子树的最小深度。

##### 复杂度分析

> 时间复杂度：O(n)，n 是树的节点。
>
> 空间复杂度：O(h)，h是树的最高度。

##### 代码

```java
public int minDepth(TreeNode root) {
    if (Objects.isNull(root)) {
        return 0;
    }
    int left = minDepth(root.left);
    int right= minDepth(root.right);
    // 当根节点的左子树或右子树为空时（left或right为0），最小深度是另一边的最小深度
     return Objects.isNull(root.left) || Objects.isNull(root.right) 
            ? Math.max(left, right) + 1  
            : Math.min(left, right) + 1;
}
```




### [109. 有序链表转换二叉搜索树](https://leetcode-cn.com/problems/convert-sorted-list-to-binary-search-tree/)

###### label：二叉搜索树、双指针

##### 描述

> 难度中等
>
> 给定一个单链表，其中的元素按升序排序，将其转换为高度平衡的二叉搜索树。
>
> 本题中，一个高度平衡二叉树是指一个二叉树*每个节点* 的左右两个子树的高度差的绝对值不超过 1。
>
> **示例:**
>
> ```
> 给定的有序链表： [-10, -3, 0, 5, 9],
> 
> 一个可能的答案是：[0, -3, 9, -10, null, 5], 它可以表示下面这个高度平衡二叉搜索树：
> 
>       0
>      / \
>    -3   9
>    /   /
>  -10  5
> ```


#### 方法一

##### 思路

> 由双指针找到链表中间的数，然后把链表左边的给左子树，链表右边的给右子树，递归构造树。

##### 复杂度分析

> 时间复杂度：O(n logn)，其中 n 是链表的长度。设长度为 n 的链表构造二叉搜索树的时间为T(n)，递推式为 T(n)=2⋅T(n/2)+O(n)，根据主定理，T(n)=O(n logn)。
>
> 空间复杂度：O(log n)，这里只计算除了返回答案之外的空间。平衡二叉树的高度为 O(logn)，即为递归过程中栈的最大深度，也就是需要的空间。

##### 代码

```java
public TreeNode sortedListToBST(ListNode head) {
    if(head == null){
        return null;
    }
    if(head.next == null){
        return new TreeNode(head.val);
    }
    // 双指针找到链表中间节点（慢指针走一步快指针走两步，快指针到末尾时结束循环）
    ListNode slow = head, quick = head, pre = null;
    while (quick != null && quick.next != null){
        // 这句要放在slow移动前面，记录上次的slow位置，不包含此次的，因为截取的是此次之前的slow
        pre = slow;
        slow = slow.next;
        quick = quick.next.next;
    }
    // 把head从中间截断，操作head指向的地址
    pre.next = null;

    // 此时head为原始的前半段，slow为中间部分
    TreeNode root = new TreeNode(slow.val);
    root.left = sortedListToBST(head);
    root.right = sortedListToBST(slow.next);
    return root;
}
```
#### 方法二

##### 思路

> 方法一的时间复杂度的瓶颈在于寻找中位数节点。由于**构造出的二叉搜索树的中序遍历结果就是链表本身**，因此我们可以将分治和中序遍历结合起来，减少时间复杂度。
>
> 在分治遍历时先不着急找出链表的中位数节点，用个占位节点，中序遍历到该节点时再填充对应的值。

##### 复杂度分析

> 时间复杂度：O(n)，n为树的节点数。由于是自顶向下计算。
>
> 空间复杂度：O(log n)，n为链表长度，主要为递归过程开销。

##### 代码

```java
ListNode globalHead;

public TreeNode sortedListToBST(ListNode head) {
    globalHead = head;
    int length = getLength(head);
    return buildTree(0, length - 1);
}

public int getLength(ListNode head) {
    int ret = 0;
    while (head != null) {
        ++ret;
        head = head.next;
    }
    return ret;
}
// 中序遍历的方式构建树
public TreeNode buildTree(int left, int right) {
    if (left > right) {
        return null;
    }
    int mid = (left + right + 1) / 2;
    TreeNode root = new TreeNode();
    // 先左子树
    root.left = buildTree(left, mid - 1);
    // 再值
    root.val = globalHead.val;
    globalHead = globalHead.next;
    // 再右子树
    root.right = buildTree(mid + 1, right);
    return root;
}
```



## 最大化最小值问题
### 说明
> 本专题主要说明**最大化最小值和最小化最大值问题**，这种直接去找不太好找，但可以确定的是**最终结果有确定的范围**，那么我们就对这个范围内的值进行**验证**即可，验证过程使用二分查找加速。
>
> 最大化最小值：二分搜索验证某范围的中间值符合要求后，需最大化最小值，则**验证更大的值**是否符合要求。**缩小小值**范围。
>
> 最小化最大值：二分搜索验证某范围的中间值符合要求后，需最小化最大值，则**验证更小的值**是否符合要求。**缩小大值**范围。

#### 最大化最小值模板

```java
public int func(int[] nums, int m) {
    // 1.初始化二分搜索边界（todo 不同题目初始化的边界不同）
    int left = 0, right = 1000000007;
    // 2.开始二分搜索+验证；两个数相邻时就结束条件
    while (right - left > 1){
        int mid = (right - left) / 2 + left;
        // 4.缩小验证范围，最小化最大值：验证符合要求后，验证有没有更小的符合要求的值，则缩小大值范围
        if (check(nums, mid, m)) {
            right = mid;
        } else {
            left = mid;
        }
    }
    //right是验证通过的，而left是验证未通过，两个碰面的就说明范围缩小到left和right，而right验证通过，left验证未通过，故返回right
    return right;
}
// 3.验证mid值是否符合要求（不同题目验证逻辑不同）
boolean check(int[] nums,int mid, int m){
    int count = 0;
    for (int i = 0; i < nums.length; i++) {
        // todo
    }
    return count <= m;
}
```

#### 最小化最大值模板

```java
public int func(int[] nums, int m) {
    // 1.初始化二分搜索边界（todo 不同题目初始化的边界不同）
    int left = 0, right = 1000000007;
    // 2.开始二分搜索+验证；两个数相邻时就结束条件
    while (right - left > 1){
        int mid = (right - left) / 2 + left;
        // 4.缩小验证范围，最大化最小值：验证符合要求后，验证有没有更大的符合要求的值，则缩小小值范围
        // ！！！注意与最大化最小值区别
        if(check(position,mid,m)){
            left = mid;
        } else {
            right = mid;
        }
    }
    // left是验证过的，right是未通过验证的，两个碰面的就说明范围缩小到left和right，而left验证通过，right验证未通过，故返回left
    // ！！！注意与最大化最小值区别
    return left;
}
// 3.验证mid值是否符合要求（不同题目验证逻辑不同）
boolean check(int[] nums,int mid, int m){
    int count = 0;
    for (int i = 0; i < nums.length; i++) {
        // todo
    }
    return count <= m;
}
```





### [410. 分割数组的最大值](https://leetcode-cn.com/problems/split-array-largest-sum/)

###### label：最大化最小值
#### 描述：

> 难度困难
>
> 给定一个非负整数数组和一个整数 *m*，你需要将这个数组分成 *m* 个非空的连续子数组。设计一个算法使得这 *m* 个子数组各自和的最大值最小。
>
> **注意:**
> 数组长度 *n* 满足以下条件:
>
> - 1 ≤ *n* ≤ 1000
> - 1 ≤ *m* ≤ min(50, *n*)
>
> **示例:**
>
> ```
> 输入:
> nums = [7,2,5,10,8]
> m = 2
> 输出:
> 18
> 解释:
> 一共有四种方法将nums分割为2个子数组。
> 其中最好的方式是将其分为[7,2,5] 和 [10,8]，
> 因为此时这两个子数组各自的和的最大值为18，在所有情况中最小。
> ```

#### 方法一：二分查找
##### 思路：

> 求解最大化最小值问题整体步骤分为4步，可直接套用模板，主要不同的地方是验证二分查找的值是否符合要求时的逻辑不同。
>
> 1. 确定二分查找的范围(注意：二分只能查询有序的)
> 2. 开始二分查找
> 3. 验证二分查找的值是否符合要求
> 4. 缩小验证范围，最后确定返回值

##### 复杂度：

> 时间复杂度：O(n * log nums )，n为数组长度，验证过程开销；nums为二分搜索的范围长度。
>
> 空间复杂度：O(1)

##### 代码：
```java
public int splitArray(int[] nums, int m) {
    // 1.初始化二分搜索边界
    int left = 0, right = 0, len = nums.length;
    for (int i = 0; i < len; i++) {
        if(left < nums[i]){
            left = nums[i];
        }
        right += nums[i];
    }
    // 2.开始二分搜索+验证；两个数相邻时就结束条件
    while (right - left > 1){
        int mid = (right - left) / 2 + left;
        // 4.缩小验证范围，最小化最大值：验证符合要求后，验证有没有更小的符合要求的值，则缩小大值范围
        if (check(nums, mid, m)) {
            right = mid;
        } else {
            left = mid;
        }
    }
    //right是验证通过的，而left是验证未通过，两个碰面的就说明范围缩小到left和right，而right验证通过，left验证未通过，故返回right
    return right;
}
// 3.验证mid值是否符合要求，不同题目差异主要也就在这儿
boolean check(int[] nums,int mid, int m){
    // count需要初始化为1而不是0，因为统计的是分割后的个数，分割1次会分割成2份，分割2次回分割成3份
    int count = 1, sum = 0;
    for (int i = 0; i < nums.length; i++) {
        if(sum + nums[i] > mid){
            sum = nums[i];
            count++;
        }else {
            sum += nums[i];
        }
    }
    return count <= m;
}
```





### [LCP 12. 小张刷题计划](https://leetcode-cn.com/problems/xiao-zhang-shua-ti-ji-hua/)

###### label：最小值

#### 描述：

> 难度中等
>
> 为了提高自己的代码能力，小张制定了 `LeetCode` 刷题计划，他选中了 `LeetCode` 题库中的 `n` 道题，编号从 `0` 到 `n-1`，并计划在 `m` 天内**按照题目编号顺序**刷完所有的题目（注意，小张不能用多天完成同一题）。
>
> 在小张刷题计划中，小张需要用 `time[i]` 的时间完成编号 `i` 的题目。此外，小张还可以使用场外求助功能，通过询问他的好朋友小杨题目的解法，可以省去该题的做题时间。为了防止“小张刷题计划”变成“小杨刷题计划”，小张每天最多使用一次求助。
>
> 我们定义 `m` 天中做题时间最多的一天耗时为 `T`（小杨完成的题目不计入做题总时间）。请你帮小张求出最小的 `T`是多少。 
>
> **示例 1：**
>
> > 输入：`time = [1,2,3,3], m = 2`
> >
> > 输出：`3`
> >
> > 解释：第一天小张完成前三题，其中第三题找小杨帮忙；第二天完成第四题，并且找小杨帮忙。这样做题时间最多的一天花费了 3 的时间，并且这个值是最小的。
>
> **示例 2：**
>
> > 输入：`time = [999,999,999], m = 4`
> >
> > 输出：`0`
> >
> > 解释：在前三天中，小张每天求助小杨一次，这样他可以在三天内完成所有的题目并不花任何时间。
>
>  
>
> **限制：**
>
> - `1 <= time.length <= 10^5`
> - `1 <= time[i] <= 10000`
> - `1 <= m <= 1000`


#### 方法一：二分查找
##### 思路：

> 验证+二分查找。
>
> 1. 确定二分查找的范围
> 2. 开始二分查找
> 3. 验证二分查找的值是否符合要求
> 4. 缩小验证范围，最后确定返回值

##### 复杂度：

> 时间复杂度：O(n * log nums )，n为数组长度，验证过程开销；nums为二分搜索的范围长度。
>
> 空间复杂度：O(1)

##### 代码：
```java
public int minTime(int[] time, int m) {
    int len = time.length;
    // 全部求助小杨，自己不用花时间
    if(len <= m){
        return 0;
    }

    // 1.初始化二分搜索边界（不同题目初始化的边界不同）
    int left = 0, right = 0;
    for (int i = 0; i < time.length; i++) {
        right += time[i];
    }
    // 2.开始二分搜索+验证；两个数相邻时就结束条件
    while (right - left > 1){
        int mid = (right - left) / 2 + left;
        // 4.缩小验证范围，最小化最大值：验证符合要求后，验证有没有更小的符合要求的值，则缩小大值范围
        if (check(time, mid, m)) {
            right = mid;
        } else {
            left = mid;
        }
    }
    //right是验证通过的，而left是验证未通过，两个碰面的就说明范围缩小到left和right，而right验证通过，left验证未通过，故返回right
    return right;
}
// 3.验证mid值是否符合要求（不同题目验证逻辑不同）
boolean check(int[] nums, int mid,int m){
    // 每组划分 m 的最大和，贪心划分看有多少组
    int count = 1, curSum = 0, curMax = 0;
    for (int i = 0; i < nums.length; i++) {
        curMax = curMax < nums[i] ? nums[i] : curMax;
        if(curSum - curMax + nums[i] <= mid) {
            curSum += nums[i];
        }else {
            curSum = nums[i];
            curMax = nums[i];
            count++;
        }
    }
    return count <= m;
}
```



### [1552. 两球之间的磁力](https://leetcode-cn.com/problems/magnetic-force-between-two-balls/)

###### label：最小化最大值
#### 描述：

> 难度中等
>
> 在代号为 C-137 的地球上，Rick 发现如果他将两个球放在他新发明的篮子里，它们之间会形成特殊形式的磁力。Rick 有 `n` 个空的篮子，第 `i` 个篮子的位置在 `position[i]` ，Morty 想把 `m` 个球放到这些篮子里，使得任意两球间 **最小磁力** 最大。
>
> 已知两个球如果分别位于 `x` 和 `y` ，那么它们之间的磁力为 `|x - y|` 。
>
> 给你一个整数数组 `position` 和一个整数 `m` ，请你返回最大化的最小磁力。
>
> **示例 1：**
>
> ![img](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/08/16/q3v1.jpg)
>
> ```
> 输入：position = [1,2,3,4,7], m = 3
> 输出：3
> 解释：将 3 个球分别放入位于 1，4 和 7 的三个篮子，两球间的磁力分别为 [3, 3, 6]。最小磁力为 3 。我们没办法让最小磁力大于 3 。
> ```
>
> **示例 2：**
>
> ```
> 输入：position = [5,4,3,2,1,1000000000], m = 2
> 输出：999999999
> 解释：我们使用位于 1 和 1000000000 的篮子时最小磁力最大。
> ```
>
> **提示：**
>
> - `n == position.length`
> - `2 <= n <= 10^5`
> - `1 <= position[i] <= 10^9`
> - 所有 `position` 中的整数 **互不相同** 。
> - `2 <= m <= position.length`


#### 方法一：二分查找
##### 思路：

> 求解最大化最小值问题整体步骤分为4步，可直接套用模板，主要不同的地方是验证二分查找的值是否符合要求时的逻辑不同。
>
> 1. 确定二分查找的范围
> 2. 开始二分查找
> 3. 验证二分查找的值是否符合要求
> 4. 缩小验证范围，最后确定返回值

##### 复杂度：

> 时间复杂度：O(n * log nums )，n为数组长度，验证过程开销；nums为二分搜索的范围长度。
>
> 空间复杂度：O(1)

##### 代码：
```java
public int maxDistance(int[] position, int m) {
    Arrays.sort(position);
    // 1.确定二分查找范围
    int left = 0, right = position[position.length - 1];
    // 2.开始二分查找
    while (right - left > 1 ) {
        int mid = (right - left) / 2 + left;
        // 4.缩小验证范围，最大化最小值：验证符合要求后，验证有没有更大的符合要求的值，则缩小小值范围
        if(check(position,mid,m)){
            left = mid;
        } else {
            right = mid;
        }
    }
    // left是验证过的，right是未通过验证的，两个碰面的就说明范围缩小到left和right，而left验证通过，right验证未通过，故返回left
    return left;
}
// 3.验证二分查找的值是否符合要求，不同题目差异主要也就在这儿
boolean check(int[] position, int mid, int m) {
    // 因为1 <= position[i] <= 10^9，且根据题意肯定要算入第一个数
    // 所以起点从0开始，在验证时把第一个元素加上，后面依次加上间隔数
    int l = 0, count = 0;
    for (int i = 0; i < position.length; i++) {
        // 当前数是否大于等于偏移间隔数，这儿需要取到小于号
        if (l <= position[i]) {
            l = mid + position[i];
            count++;
        }
    }
    return count >= m;
}
```





时间复杂度：O(n logn)，其中 n 是链表的长度。设长度为 n 的链表构造二叉搜索树的时间为T(n)，递推式为 T(n)=2⋅T(n/2)+O(n)，根据主定理，T(n)=O(n logn)。

空间复杂度：O(log n)，这里只计算除了返回答案之外的空间。平衡二叉树的高度为 O(logn)，即为递归过程中栈的最大深度，也就是需要的空间。



## 岛屿问题

> 这类问题是遍历矩阵并计算统计之类的，主要在于遍历时把边界条件处理好，否则不小心就陷入了无限递归。

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
        recursion
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
        recursion
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


###  [529. 扫雷游戏](https://leetcode-cn.com/problems/minesweeper/)

###### label：矩阵遍历、dfs
#### 描述：

> 难度中等
>
> 让我们一起来玩扫雷游戏！给定一个代表游戏板的二维字符矩阵。 **'M'** 代表一个**未挖出的**地雷，**'E'** 代表一个**未挖出的**空方块，**'B'** 代表没有相邻（上，下，左，右，和所有4个对角线）地雷的**已挖出的**空白方块，**数字**（'1' 到 '8'）表示有多少地雷与这块**已挖出的**方块相邻，**'X'** 则表示一个**已挖出的**地雷。
>
> 现在给出在所有**未挖出的**方块中（'M'或者'E'）的下一个点击位置（行和列索引），根据以下规则，返回相应位置被点击后对应的面板：
>
> 1. 如果一个地雷（'M'）被挖出，游戏就结束了- 把它改为 **'X'**。
> 2. 如果一个**没有相邻地雷**的空方块（'E'）被挖出，修改它为（'B'），并且所有和其相邻的**未挖出**方块都应该被递归地揭露。
> 3. 如果一个**至少与一个地雷相邻**的空方块（'E'）被挖出，修改它为数字（'1'到'8'），表示相邻地雷的数量。
> 4. 如果在此次点击中，若无更多方块可被揭露，则返回面板。
>
> **示例 1：**
>
> ```
> 输入: 
> 
> [['E', 'E', 'E', 'E', 'E'],
>  ['E', 'E', 'M', 'E', 'E'],
>  ['E', 'E', 'E', 'E', 'E'],
>  ['E', 'E', 'E', 'E', 'E']]
> 
> Click : [3,0]
> 
> 输出: 
> 
> [['B', '1', 'E', '1', 'B'],
>  ['B', '1', 'M', '1', 'B'],
>  ['B', '1', '1', '1', 'B'],
>  ['B', 'B', 'B', 'B', 'B']]
> 
> 解释:
> ```
>
> **示例 2：**
>
> ```
> 输入: 
> 
> [['B', '1', 'E', '1', 'B'],
>  ['B', '1', 'M', '1', 'B'],
>  ['B', '1', '1', '1', 'B'],
>  ['B', 'B', 'B', 'B', 'B']]
> 
> Click : [1,2]
> 
> 输出: 
> 
> [['B', '1', 'E', '1', 'B'],
>  ['B', '1', 'X', '1', 'B'],
>  ['B', '1', '1', '1', 'B'],
>  ['B', 'B', 'B', 'B', 'B']]
> 
> 解释:
> ```
>
> **注意：**
>
> 1. 输入矩阵的宽和高的范围为 [1,50]。
> 2. 点击的位置只能是未被挖出的方块 ('M' 或者 'E')，这也意味着面板至少包含一个可点击的方块。
> 3. 输入面板不会是游戏结束的状态（即有地雷已被挖出）。
> 4. 简单起见，未提及的规则在这个问题中可被忽略。例如，当游戏结束时你不需要挖出所有地雷，考虑所有你可能赢得游戏或标记方块的情况。

#### 方法一：DFS遍历
##### 思路：

> 先判断第一种情况，直接点到地雷处；其他情况对矩阵进行深度优先遍历，先遍历周围8个方向是否存在的雷数，若周围存在雷，就终止对该路径的搜索。若不存在雷就向四周路径继续dfs遍历，直到所有点都遍历完。

##### 复杂度：

> 时间复杂度：O(n*m)
>
> 空间复杂度：O(n*m)，栈开销。

##### 代码：
```java
/**
  * 定义8个方向
  */
static int[] dx = {0, 0, 1, -1, 1, 1, -1, -1};
static int[] dy = {1, -1, 0, 0, 1, -1, -1, 1};
static public char[][] updateBoard(char[][] board, int[] click) {
    // 1.如果一个地雷（'M'）被挖出，游戏就结束了- 把它改为 'X'。
    if(board[click[0]][click[1]] == 'M'){
        board[click[0]][click[1]] = 'X';
        return board;
    }else {
        dfs(board,click[0],click[1]);
    }
    return board;
}

static void dfs(char[][] board, int x, int y){
    // 2.如果一个没有相邻地雷的空方块（'E'）被挖出，修改它为（'B'），并且所有和其相邻的未挖出方块都应该被递归地揭露。
    // 3.如果一个至少与一个地雷相邻的空方块（'E'）被挖出，修改它为数字（'1'到'8'），表示相邻地雷的数量。
    // 4.如果在此次点击中，若无更多方块可被揭露，则返回面板。

    // 递归终止条件：周围有雷修改位置的雷数，终止对该路径的搜索；该位置已经遍历过，字符为B或数字
    /* if(board[x][y] == 'B' || (board[x][y]>1+'0' && board[x][y] <= '0'+9)){
            return;
        }*/
    int count = 0;
    for (int i = 0; i < 8; i++) {
        int ix = x + dx[i];
        int iy = y + dy[i];
        if(ix < 0 || ix >= board.length || iy < 0 || iy >= board[0].length ){
            continue;
        }
        if(board[ix][iy] == 'M'){
            count++;
        }
    }
    // 填充数字
    if(count > 0){
        board[x][y] = (char) (count + '0');
        return;
    }
    // 若周围没有雷，置为B并向四周遍历
    board[x][y] = 'B';
    for (int i = 0; i < 8; i++) {
        int ix = x + dx[i];
        int iy = y + dy[i];
        // 注意要加上该点!=E，表示该点已遍历过的就不dfs了；否则就需要加上最前面的递归出口判断
        if(ix < 0 || ix >= board.length || iy < 0 || iy >= board[0].length || board[ix][iy]!='E'){
            continue;
        }
        dfs(board,ix,iy);
    }
}
```



##  [xx.模板](https://leetcode-cn.com//)

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