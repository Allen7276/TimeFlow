# TimeFlow

1. ## 一款用于显示时间的软件

   主要是当时看着别人在图书馆学习的时候，觉得这东西天然一股小资感。在软件商店下载的又充满了广告，于是自己学着用kotlin写了这么一段比较简洁的软件。

2. ## 交互逻辑：

   就是比较单纯的两张卡片分别包含时钟和分钟两个字段组成
    - 点击时钟的卡片，可以切换时间查看的进制。
    - 点击分钟的卡片，可以选择是否显示当前的年月日。

3. ## 其效果如下图所示

   <img src=".\show_img\img1.png" style="zoom:50%;" />

   <img src=".\show_img\img2.png" style="zoom:25%;" />

   这分别是在手机下横屏和竖屏的效果，平板上也是一样的使用逻辑，Android手机与Android平板均已适配。

   <img src=".\show_img\img3.png" style="zoom:100%;" />

4. ## 技术点

   项目使用了ViewModel、Flow等Android现在比较建议我们使用的技术实现监听时间变化，然后使用了广播保证每一分钟系统才推送一次刷新时间的消息给软件，这样也许会减少软件对系统内存的开销吧？当然这也是我自己的猜想，但不是必要的时候就减少一点对系统的消耗，不需要每一秒都跑去监听时间的变化。

5. ## 重新分层 命名规范

   2021/12

   暂时这样写着先 之后深入学习MVVM架构再进行修改

   2022/02/18

   目前已经修改了这一套架构的逻辑

   （V层 -> VM层 -> M层）Model使用单例模式进行构造提供给VM层 VM层使用工厂模式提供给V层

   划分清晰架构之后 项目就变得很清晰了 之后想增加内容也变得简单多了

6. ## Notification
    - 后续将不再维护该项目，迁移到[新项目](https://github.com/DIPENG-XU/TimeFlow-By-Compose)
      中，并将使用Jetpack Compose完成UI的构建