## Activity、Fragment、Presenter生命周期监听


## 项目结构

##### app 主项目:
使用方法的demo

##### activityfragmentlifecycle Module:
activity和fragment的生命周期监听

包划分
- glide_lifecycle 单个activity、fragment的生命周期监听
- global_lifecycle 所有的activity生命周期监听


##### presentelifecycle Module: 
Presenter的生命周期监听


## 使用
部分伪代码，为了方便理解

##### 单个Activity/Fragment生命周期监听

```
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_demo);
        Lifecycle.observe(this, new LifecycleCallbacks());
    }
```

##### 全局性的Activity生命周期监听

```
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        GlobalLifeCycle
                .init(this)
                .registerCallbacks(new InitCallBack());
    }
}
```

##### Presenter生命周期控制


```
public class PresenterDemoActivity extends Activity implements IPresenterCreator<PresenterDemoContracts.Presenter>{

    private PresenterDemoContracts.Presenter p;

    @Override
    public PresenterDemoContracts.Presenter create() {
        return new PresenterDemoPresenter();
    }

    @Override
    public void initPresenterFinished(PresenterDemoContracts.Presenter p) {
        this.p = p;//初始化完成后回调这个方法传入绑定好View的Presenter
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presenter_demo);
        Presenter.bind(this,this);//为了绑定activity生命周期
    }

}
```


```
public class PresenterDemoPresenter  implements ILifeCyclePresenter<PresenterDemoContracts.View>,PresenterDemoContracts.Presenter {

    private PresenterDemoContracts.View view;

    @Override
    public void onAttachView(PresenterDemoContracts.View view) {
        this.view = view;
    }

    @Override
    public void onInitFinished() {}

    @Override
    public void onStop() {}

    @Override
    public void onDestroyed() {}
    
    //presenter自己的方法
    @Override
    public void loadDate() {
        //Do Something
        view.showDate("PresenterDemoPresenter");
    }
}
```








## 实现目的及原理分析

#### 1. Activity、Fragment的生命周期监听

##### 实现目的：

Activity和Frament内部是有生命回调的，但是对于一些组件为了使用方便是不能够让开发者手动在不同生命周期里调用指定方法的，如果真有这样的组件，那开发者的学习成本就会增加，使用上也很不方便。

Glide图片加载组件，它就是一个很好的例子。开发者只需要控制发送请求，却不需要控制停止请求。因为Glide在初始化时已经将当前activity、fragment的生命周期注册，在 activity、fragment不同生命周期时自动进行停止网络请求或恢复网络请求等处理，让使用极其简单。

除了组件以外，有一些共性代码必须要使用生命周期时也可以用这种办法达到解耦的效果

[具体实现点这里](https://github.com/MrJiao/JacksonLifecycle/tree/master/activityfragmentlifecycle/src/main/java/com/jackson/activityfragmentlifecycle/glide_lifecycle)

[demo例子点这里](https://github.com/MrJiao/JacksonLifecycle/tree/master/app/src/main/java/com/jackson/jacksonlifecycle/glide_lifecycle_demo)


##### 原理分析：
通过Headless Fragment与activity或fragment绑定，通过监听Headless Fragment的生命周期来达到监听目标activity或fragment生命周期的目的。对于Headless Fragment更多的解释请自行百度。


#### 2. 全局性Activity生命周期监听

##### 实现目的：
其一：

我们在做android开发时基本每个项目里都有一个BaseActivity，内部封装一些常用的方法或者回调(eg:initView()，initListener()等)，但项目开发到一定时候，总有一些activity因为种种原因无法继承BaseActivity(eg:SlidingMenu 必须继承它的Activity),java的单继承特性我们也不得不写一个新的其他基类activity，这就会导致代码的统一性受到影响。

其二：

很多共性代码都放入BaseActivity让继承它的Activity都会受到Base类的修改而全部受到影响，每次Base的修改都必须兼容继承它的所有子类，这样的代码还是比较危险的

所以，BaseActivity里做过多的事情其实不是一件好事儿。

##### 原理分析：

使用BaseActivity的原因有两个，一是需要在BaseActivity生命周期中写一些必须的调用(eg:initView() initDate()让子类实现等)；二是共性方法，让子类更加容易使用。

对于第二个，我们完全可以抽取一个AcitvityHelper对象为其子类提供易用的方法。

对于第一个，我们就可以通过在外部全局性的监听activity回调来实现


```
public class Application{
    ....
    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        synchronized (mActivityLifecycleCallbacks) {
            mActivityLifecycleCallbacks.add(callback);
        }
    }
    ....
}
```

[具体实现点这里](https://github.com/MrJiao/JacksonLifecycle/tree/master/activityfragmentlifecycle/src/main/java/com/jackson/activityfragmentlifecycle/global_lifecycle)

[使用demo点这里](https://github.com/MrJiao/JacksonLifecycle/tree/master/app/src/main/java/com/jackson/jacksonlifecycle/global_activity_lifecycle_demo)


#### 3. Presenter生命周期监听

##### 实现目的：
新项目绝大部分已经采用mvp模式，而p层作为数据交互层必然要起到保存临时数据的作用。presenter如果在activity里进行new Presenter()，那它的生命周期必然比activity要短。

但Activity因为内存不足等原因被回收时，Presenter里的数据需要在activity恢复时一并传递给新建立的activity，这时候Presenter生命周期必须大于等于当前activity的生命周期才可以。当然除了数据恢复，Presenter内部也需要监听当前绑定的view的生命周期来处理一些逻辑，这就很有必要为presenter提供生命周期回调和控制presenter生命周期大于等于绑定的view的生命周期了。

##### 实现原理
参考 

http://blog.chengdazhi.com/index.php/131 

http://www.cnblogs.com/mengdd/p/5988104.html
http://blog.csdn.net/kakaxi1o1/article/details/53159509#t6
http://blog.csdn.net/osle123/article/details/52755556
http://blog.csdn.net/yangdahuan/article/details/62225839

为了达到Presenter生命周期>=Activity/Fragment生命周期的目的，使用Loader作为Presenter的生命周期管理对象。具体参考连接

实现

Activity/Fragment生命周期 -> Hiddless Frameng生命周期 ->Loader -> Presenter

[具体实现点这里](https://github.com/MrJiao/JacksonLifecycle/tree/master/presentelifecycle/src/main/java/com/jackson/presentelifecycle)

[使用demo点这里](https://github.com/MrJiao/JacksonLifecycle/tree/master/app/src/main/java/com/jackson/jacksonlifecycle/presenter_lifecycle_demo)