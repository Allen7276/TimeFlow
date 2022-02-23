###  1.3.1 | 02/23/2022
- requestWindowFeature(Window.FEATURE_NO_TITLE)
  window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
  设置MainActivity的Layout布局文件全屏化 去掉Android上面的状态栏对于整体页面的风格影响
  
- MVVM架构的详细划分
  将V层、VM层以及M层进行明确的划分
  V层使用VM层向外暴露的LiveData进行回调监听
  VM层从M层获取数据，再将数据保存在自己的LiveData中，让V层进行监听
  M层为获取数据的一层

### 1.3.0 | 12/23/2021
- MVVM架构的初步划分

### 1.2 | 12/18/2021
- 增加24小时进制的选项，用户可以使用24小时进制或12小时进制进行显示时间
- 使用广播进行接受时间变化的消息，时区变化等内容应用可以第一时间接收到并做出响应

### 1.0 | 12/17/2021
- 初始代码的架构构造