# 响应式编程概述
##1.什么是响应式编程 
一种基于异步流数据概念的编程模式：类似于河流概念，该河流可被过滤，合并，观察等
##2.关键概念 
事件：现实中事件映射到软件中
##3.使用场景 
UI(通用)

##微软响应式扩展 
* 函数响应编程
* 微软RX

#RxJava是什么？
* 异步数据处理库
* 扩展的观察者模式：扩展了OnCompleted(),OnError()和事件通知，组合而不是嵌套 

###特点： 
* < 1MB Jar
* 轻量级框架
* 支持Java 8 lambda
* 支持Java 6+ & Android 2.3+
* 支持同步和异步


#observable创建操作符总结：

* Create:常用的操作符，需要实现回调，然后在回掉中实现业务逻辑
* Just:Create操作符的简写，直接在操作符参数内，发送相应的内容
* From:创建列表型对象，选择相应的内容发送
* Defer：在进行订阅后，才发送相应的内容
* range:从某个数据范围，创建Observable
* repeat:创建一个具有重复发送次数的Observable


#observable转换操作符总结：

* map:进行简单的数据转换，如将整型转换为字符串创建new Func1<Integer, String>()实现相关方法
* flatmap:将一个列表items，内容转换平铺发射
* groupBy:将items分组发射
* Buffer:对多个items，进行缓存发射，缓存的个数即为Buffer参数
* Scan:扫描全部items，并提供相应的累加和及当前的对象
* Window:将items进行划分，存储到window中，进行发射


#obervable组合操作符：寻找数据项结果，将结果发射给观察者

* Zip:设置一定的规则组合两个数据源，然后发射
* Merge:按照时间的先后顺序对两个数据源进行组合发射
* StartWith:在当前的Obervalbe之前插入一个数据项发射
* CombineLatest:通过设置的规则组合两个数据源中相邻的数据项发射
* Join:结合两个Observable发射的数据，基于时间窗口（自定义原则）选择待集合的数据项
* SwitchOnNext:将多个数据源转换为一个数据源，然后发射此数据源中的数据项

#observable过滤操作符：寻找数据项结果，将结果发射给观察者

* Debounce:只发射一类数据项，此项数据定义为：此数据后在特定的时间内没有发射任何数据项
* Distinct:去掉重复的数据项进行发射
* ElementAt:取出数据项列表中，某一项数据发射，通过索引参数
* Filter:设置过滤条件，进行数据项过滤发射
* First：发射数据项中，第一条数据项
* IgnoreElement:直接忽略数据项，进行回调，不回调onNext()方法
* Last:发射数据项中，最后一条数据项
* Sample:每隔一个时间间隔，对数据项进行采集发射
* Skip(Skiplast):跳过由参数设置的多个数据项，如skip(2)指跳过前两项
* Take(Takelast):选择由参数设置的多个数据项，如take(2)指选择前两项
    


#observable错误操作符：

####Catch: 根据不同的操作符进行相应的异常发生时的数据项发射操作
       1.onErrorReturn：指示Observable在发生异常时发射一特定的数据项，并正常终止
       2.onErrorResumeNext：指示Observable在发射异常时发射第二个Observable序列
       3.onExceptionResumeNext：指示Observable在发射异常时继续发射数据项，并能获取到异常类型
####Retry: 在数据源发生异常时，重试订阅，即执行subscribe方法
       1.Retry：指异常发生时，进行重试订阅，不记录异常
       2.RetryWhen:指异常发生时，进行重试订阅，并记录异常，通过操作符的时间
                   参数，延迟一个时间后再发送异常


#非阻塞I/O操作

##一、图片保存

示例代码：

    public static void storeBitmap(Context context,Bitmap bitmap,
    String filename){
    //创建io线程进行图片操作
    Schedulers.io().createWorker().schedule(() ->{
    blockingStoreBitmap(context,bitmap,filename);
    });
    }

##二、两种方式比较
    
* 阻塞操作：单线程中运行，先执行storeBitmap()函数，再进行UI操作
* 非阻塞操作：主线程中开启新线程，UI操作和storeBitmap()函数同时执行
    
    





#Schedulers(调度器)

###一、什么是Schedulers
Schedulers是RxJava以一种较简洁的方法解决多线程问题的机制

###二、Schedulers种类
- .io():用于I/O操作
- .computation():用于计算操作，例如buffer,debounce()等
- .immediate():允许在当前线程执行指定的工作，如timeout(),timeInterval()
- .newThread():指定任务启动一个新线程
- .trampoline():将会按序处理队列，并运行队列中每一个任务，如repeat()，retry()
 
###三、什么是AndroidSchedulers
AndroidSchedulers是RxAndroid库提供在安卓平台的调度器(指定观察者在主线程)

###四、AndroidSchedulers示例
示例代码：   
    
    getApps()
    .onBackpressureBuffer()
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(new observer<AppInfo>){[...]}


#SubscribeOn and ObserveOn

* ObserveOn()方法用于每个Subscriber(Observer)对象，指定相应的观察者线程
* SubscribeOn()方法用于指定相应的被观察者线程

###网络请求实例

    DataManager.getRetrofitService()
       .getHomeData("homeDataVersion", new HashMap<String,String>)
       .subscribeOn(Schedulers.io())指定网络请求在io线程上
       .observeOn(AndroidSchedulers.mainThread())指定数据返回在主线程上
       .subscribe(subscriber)
    


