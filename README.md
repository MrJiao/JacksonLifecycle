## Activity��Fragment��Presenter�������ڼ���


## ��Ŀ�ṹ

##### app ����Ŀ:
ʹ�÷�����demo

##### activityfragmentlifecycle Module:
activity��fragment���������ڼ���

������
- glide_lifecycle ����activity��fragment���������ڼ���
- global_lifecycle ���е�activity�������ڼ���


##### presentelifecycle Module: 
Presenter���������ڼ���


## ʹ��
����α���룬Ϊ�˷������

##### ����Activity/Fragment�������ڼ���

```
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_demo);
        Lifecycle.observe(this, new LifecycleCallbacks());
    }
```

##### ȫ���Ե�Activity�������ڼ���

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

##### Presenter�������ڿ���


```
public class PresenterDemoActivity extends Activity implements IPresenterCreator<PresenterDemoContracts.Presenter>{

    private PresenterDemoContracts.Presenter p;

    @Override
    public PresenterDemoContracts.Presenter create() {
        return new PresenterDemoPresenter();
    }

    @Override
    public void initPresenterFinished(PresenterDemoContracts.Presenter p) {
        this.p = p;//��ʼ����ɺ�ص������������󶨺�View��Presenter
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presenter_demo);
        Presenter.bind(this,this);//Ϊ�˰�activity��������
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
    
    //presenter�Լ��ķ���
    @Override
    public void loadDate() {
        //Do Something
        view.showDate("PresenterDemoPresenter");
    }
}
```








## ʵ��Ŀ�ļ�ԭ�����

#### 1. Activity��Fragment���������ڼ���

##### ʵ��Ŀ�ģ�

Activity��Frament�ڲ����������ص��ģ����Ƕ���һЩ���Ϊ��ʹ�÷����ǲ��ܹ��ÿ������ֶ��ڲ�ͬ�������������ָ�������ģ��������������������ǿ����ߵ�ѧϰ�ɱ��ͻ����ӣ�ʹ����Ҳ�ܲ����㡣

GlideͼƬ���������������һ���ܺõ����ӡ�������ֻ��Ҫ���Ʒ�������ȴ����Ҫ����ֹͣ������ΪGlide�ڳ�ʼ��ʱ�Ѿ�����ǰactivity��fragment����������ע�ᣬ�� activity��fragment��ͬ��������ʱ�Զ�����ֹͣ���������ָ���������ȴ�����ʹ�ü���򵥡�

����������⣬��һЩ���Դ������Ҫʹ����������ʱҲ���������ְ취�ﵽ�����Ч��

[����ʵ�ֵ�����](https://github.com/MrJiao/JacksonLifecycle/tree/master/activityfragmentlifecycle/src/main/java/com/jackson/activityfragmentlifecycle/glide_lifecycle)

[demo���ӵ�����](https://github.com/MrJiao/JacksonLifecycle/tree/master/app/src/main/java/com/jackson/jacksonlifecycle/glide_lifecycle_demo)


##### ԭ�������
ͨ��Headless Fragment��activity��fragment�󶨣�ͨ������Headless Fragment�������������ﵽ����Ŀ��activity��fragment�������ڵ�Ŀ�ġ�����Headless Fragment����Ľ��������аٶȡ�


#### 2. ȫ����Activity�������ڼ���

##### ʵ��Ŀ�ģ�
��һ��

��������android����ʱ����ÿ����Ŀ�ﶼ��һ��BaseActivity���ڲ���װһЩ���õķ������߻ص�(eg:initView()��initListener()��)������Ŀ������һ��ʱ������һЩactivity��Ϊ����ԭ���޷��̳�BaseActivity(eg:SlidingMenu ����̳�����Activity),java�ĵ��̳���������Ҳ���ò�дһ���µ���������activity����ͻᵼ�´����ͳһ���ܵ�Ӱ�졣

�����

�ܶ๲�Դ��붼����BaseActivity�ü̳�����Activity�����ܵ�Base����޸Ķ�ȫ���ܵ�Ӱ�죬ÿ��Base���޸Ķ�������ݼ̳������������࣬�����Ĵ��뻹�ǱȽ�Σ�յ�

���ԣ�BaseActivity���������������ʵ����һ�����¶���

##### ԭ�������

ʹ��BaseActivity��ԭ����������һ����Ҫ��BaseActivity����������дһЩ����ĵ���(eg:initView() initDate()������ʵ�ֵ�)�����ǹ��Է������������������ʹ�á�

���ڵڶ�����������ȫ���Գ�ȡһ��AcitvityHelper����Ϊ�������ṩ���õķ�����

���ڵ�һ�������ǾͿ���ͨ�����ⲿȫ���Եļ���activity�ص���ʵ��


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

[����ʵ�ֵ�����](https://github.com/MrJiao/JacksonLifecycle/tree/master/activityfragmentlifecycle/src/main/java/com/jackson/activityfragmentlifecycle/global_lifecycle)

[ʹ��demo������](https://github.com/MrJiao/JacksonLifecycle/tree/master/app/src/main/java/com/jackson/jacksonlifecycle/global_activity_lifecycle_demo)


#### 3. Presenter�������ڼ���

##### ʵ��Ŀ�ģ�
����Ŀ���󲿷��Ѿ�����mvpģʽ����p����Ϊ���ݽ������ȻҪ�𵽱�����ʱ���ݵ����á�presenter�����activity�����new Presenter()���������������ڱ�Ȼ��activityҪ�̡�

��Activity��Ϊ�ڴ治���ԭ�򱻻���ʱ��Presenter���������Ҫ��activity�ָ�ʱһ�����ݸ��½�����activity����ʱ��Presenter�������ڱ�����ڵ��ڵ�ǰactivity���������ڲſ��ԡ���Ȼ�������ݻָ���Presenter�ڲ�Ҳ��Ҫ������ǰ�󶨵�view����������������һЩ�߼�����ͺ��б�ҪΪpresenter�ṩ�������ڻص��Ϳ���presenter�������ڴ��ڵ��ڰ󶨵�view�����������ˡ�

##### ʵ��ԭ��
�ο� 

http://blog.chengdazhi.com/index.php/131 

http://www.cnblogs.com/mengdd/p/5988104.html
http://blog.csdn.net/kakaxi1o1/article/details/53159509#t6
http://blog.csdn.net/osle123/article/details/52755556
http://blog.csdn.net/yangdahuan/article/details/62225839

Ϊ�˴ﵽPresenter��������>=Activity/Fragment�������ڵ�Ŀ�ģ�ʹ��Loader��ΪPresenter���������ڹ�����󡣾���ο�����

ʵ��

Activity/Fragment�������� -> Hiddless Frameng�������� ->Loader -> Presenter

[����ʵ�ֵ�����](https://github.com/MrJiao/JacksonLifecycle/tree/master/presentelifecycle/src/main/java/com/jackson/presentelifecycle)

[ʹ��demo������](https://github.com/MrJiao/JacksonLifecycle/tree/master/app/src/main/java/com/jackson/jacksonlifecycle/presenter_lifecycle_demo)