>Reference: 

# 算法可视化

采用MVC模式

Model 对应的数据由于差异性，需要每次修改

```java
/**
 * Viewer
 */
public class AlgoFrame extends JFrame {
```

```java
/**
 * Controller
 */
public class AlgoVisualizer {
```



## 随机模拟问题

### 一个有意思的分钱问题

房间里有100个人，每人都有100元钱，他们在玩一个游戏。每轮游戏中，每个人都要拿出一元钱随机给另一个人，最后这100个人的财富分布是怎样的？

>[Counterintuitive problem: Everyone in a room keeps giving dollars to random others. You’ll never guess what happens next.](http://www.decisionsciencenews.com/2017/06/19/counterintuitive-problem-everyone-room-keeps-giving-dollars-random-others-youll-never-guess-happens-next/)

<img src="https://gitee.com/gaoyi-ai/image-bed/raw/master/images/money.gif" alt="money" style="zoom: 33%;" />

1. 虽然可以缩小这个delay的值，但是缩小这个delay的值其实是有一定的限度的
    如果想快速的模拟很长时间以后，这个结果是怎样的，那么单纯的缩小这个delay 的值是没有用的

- [x] 现在的动画逻辑其实就相当于是每进行绘制一次，然后进行一轮游戏，每一次更新不仅仅更新一轮，而更新k 轮，这样就达到了加快模拟的目的

2. 那么钱最多的人，他的财富值将显示在我们的这个窗口的右侧，此时就能大概齐的看出来这个财富的分布；从大家都是一个水平慢慢的产生了变化，有了非常大的不同，而且这个变化整体呢还似乎不仅仅是线性的这里呢有一个弧度，它确实倾向于是一个幂指数这样的一个分布

    但是这里大家要注意一个问题，虽然这是一个幂指数的分布，不过由于已经排完序，所以其实每一次运行这个程序，这个所谓的财富最多的这个人，他不一定是同一个人，而是具有一定随机性的

- [x] 所以这个实验只能说明这样的模拟这个问题的结果会让财富成一个幂指数这样的形状进行分布，但这不代表每一次值最大的是同样一个人

```java
	// TODO: 编写自己的动画逻辑
    public void run(){

        while(true){

            // 改进2：是否排序
            Arrays.sort(money);
            frame.render(money);
            AlgoVisHelper.pause(DELAY);
            
            // 改进1：每一帧执行的轮数
            for(int k = 0 ; k < 50 ; k ++){
                for(int i = 0 ; i < money.length; i ++){
                    // 改进3：允许money为负值
                    //if(money[i] > 0){
                        int j = (int)(Math.random() * money.length);
                        money[i] -= 1;
                        money[j] += 1;
                    //}
                }
            }
        }
    }
```

事实上，这个模拟将一定呈现出这种不稳定的形式，而不会是呈现出最终大家所有的人的钱都是差不多的情况，那么这具体是什么原因呢，一个相对比较简单的解释是可以把这一百个人，每个人手里有多少的钱，这样的一个事情看作是一个状态，那么大家可以想象一下这个状态的数字是巨大的，有一百个人人在初始的情况下，每人有一百块钱，那么一共就一万块钱，那么状态总数其实就是将这一万块钱分给一百个人，一共有多少个分法，那么这是一个比较经典的数学问题，也可以看作是一个整数划分的问题，事实上这个状态的数量是非常大的，那么经过这种随机模拟之后，最终的结果一定是在这，随机的状态中的一个状态，那么在这里大家要注意，对于这个状态空间来说，所有的人的钱都差不多，这样的状态只是很小的一部分，而大部分状态一定呈现出，有的人的钱非常多，有的人的钱非常少，这样的一个不稳定的状态，只不过具体谁的钱多，谁的钱少是不一样的

也正因为如此，大家可以理解成，最终模拟出来的结果就是在这个状态空间中抽取一个状态，那么事实上得到的是一个不稳定的状态，这个概率是非常高的，那么刚才的这个解释呢不算非常的精确

> 事实上呢这是一个非常经典的物理学的热学研究的场景
> 在这样的一个空间中能量是固定的，那么在能量的传递过程中，最终的结果一定是，使得熵越来越大，那么熵这个概念实际上就是在描述这个空间中的无序程度，也就是这个空间将越来越无序
> 呈现出这样的状态

### 蒙特卡洛算法

通过大量的随机样本。来了解一个系统，进而得到所要计算的值

那么在这里大家要注意蒙特卡罗方法，使用大量的随机样本来去获得我们所要计算的这个值。但是获得的这个值不一定是真值。
而是一个近似值。那么事实上摩托卡罗方法就是利用这样的一个原理，在一些问题中我们可以使用大量的随机样本去模拟。
我们的样本量越大，最终模拟出来的值就相应的会越准确。

假设我们现在并不知道Π的值的话，希望你获得一个Π的近似值的话，那应该怎么做呢？
在这种时候我们就可以使用蒙特卡罗的方法。大家可以看在这个图中其实是一个正方形。在这个正方形中有一个直径和这个正方形的边长一样的圆。那么首先我们来看在这个图形中，圆和这个正方形之间的关系。

- 圆的面积=PI * R * R
- 方形面积=(2 * R) * (2 * R)=4 * R * R
- PI=4 * 圆 / 方

圆的面积，我们怎么获得呢？在这里就使用蒙特卡罗的方法来近似的模拟圆的面积。

<img src="https://gitee.com/gaoyi-ai/image-bed/raw/master/images/image-20201212191218103.png" alt="image-20201212191218103" style="zoom:50%;" />

### 三门问题

参赛者会看见三扇关闭的门，其中有一扇门的后面呢是一辆汽车。相当于就是一个大奖。现在呢如果参赛者选中了后面有车的那扇门的话，就可以赢得这辆汽车。而另外的两扇门后面呢什么都没有。
但是在这里问题是参赛者不是简单的选一扇门，然后看自己中没中奖，而是有这样的一个环节。当参赛者选定一扇门的时候。在开启这扇门之前，主持人会先开启剩下的两扇门中的一扇门。并且呢这扇门的背后肯定是没有汽车的。比如说你选中了是a 门，于是主持人会开启，比如说b 门，并且告诉你b 门后头没有汽车。此时这个奖品要么在你选的这个a 门的后面，要么在剩下的这。这个c 门的后面现在主持人会问一个问题，给你另外一次机会，问你是否要改变你的选择。

<img src="https://gitee.com/gaoyi-ai/image-bed/raw/master/images/image-20201212200958969.png" alt="image-20201212200958969" style="zoom:67%;" />

```java
public class ThreeGatesExperiment {

    private int N;

    public ThreeGatesExperiment(int N){

        if(N <= 0)
            throw new IllegalArgumentException("N must be larger than 0!");

        this.N = N;
    }

    public void run(boolean changeDoor){

        int wins = 0;
        for(int i = 0 ; i < N ; i ++)
            if(play(changeDoor))
                wins ++;

        System.out.println(changeDoor ? "Change" : "Not Change");
        System.out.println("winning rate: " + (double)wins/N);
    }

    private boolean play(boolean changeDoor){

        // Door 0, 1, 2
        int prizeDoor = (int)(Math.random() * 3);
        int playerChoice = (int)(Math.random() * 3);

        if( playerChoice == prizeDoor)
            return changeDoor ? false : true;
        else
            return changeDoor ? true : false;
    }

    public static void main(String[] args) {

        int N = 10000000;
        ThreeGatesExperiment exp = new ThreeGatesExperiment(N);

        exp.run(true);
        System.out.println();
        exp.run(false);
    }
}
```

## 排序算法可视化

### Selection Sort

<img src="https://gitee.com/gaoyi-ai/image-bed/raw/master/images/selection_sort.gif" alt="selection_sort" style="zoom: 50%;" />

这个动画首先每一次在选择排序的过程中，都会去寻找后面未排序部分的一个最小值。而前面的部分则是已排序的部分。已排序的部分，以一种特殊的这种红色标识出来，而未排序的部分呢是这种灰色

第二点在扫描后面的这个未排序的数组的过程中，每次扫描都有一个浅蓝色的扫描的过程。同时在这个扫描的过程中。每一次当前找到的这个最小值，都用一种深蓝色表示出来

核心代码：

```java
    private void run(){

        setData(0, -1, -1);

        for( int i = 0 ; i < data.N() ; i ++ ){
            // 寻找[i, n)区间里的最小值的索引
            int minIndex = i;
            setData(i, -1, minIndex);

            for( int j = i + 1 ; j < data.N() ; j ++ ){
                setData(i, j, minIndex);

                if( data.get(j) < data.get(minIndex) ){
                    minIndex = j;
                    setData(i, j, minIndex);
                }
            }

            data.swap(i , minIndex);
            setData(i+1, -1, -1);
        }

        setData(data.N(),-1,-1);
    }

    // 一旦这个赋值完成之后，相应的也要发生一次绘制
    // 即一旦关注的那个变量发生改变，就进行一次渲染
    private void setData(int orderedIndex, int currentCompareIndex, int currentMinIndex){
        data.orderedIndex = orderedIndex;
        data.currentCompareIndex = currentCompareIndex;
        data.currentMinIndex = currentMinIndex;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }
```

选择排序是交换最少的排序。每一次都从后面被排序的部分选出一个最小的元素。和前面的元素进行交换。那么如果有n 个数据的话，选择排序只需要交换n 次。
即使是对于O(nlogn) 这样级别的排序算法，比如说归并排序、快速排序或者是堆排序，它所需要的交换次数。都不会像选择排序这样稳定在O(n)这个级别。

那么交换最少意味着什么？如果交换这个操作是非常耗时的话，选择排序就成为了最优的选择。

