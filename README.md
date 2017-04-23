# ButterKnife
![MacDown logo](https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492972414478&di=18c1366c0a4c2865851e37971eb87978&imgtype=0&src=http%3A%2F%2Fwww.bz55.com%2Fuploads%2Fallimg%2F160701%2F140-160F1151U8.jpg)

## 解决问题
* setContentView()
* findViewById()
* setOnClickListener()

## 基本思路
* 注解
* 动态编译
* 反射

## 使用方法

### Activity
* @BindView(R.layout.activity_main)绑定contentView
* 通过@BindId(R.id.tv)绑定view的id
* 动态代理的方式绑定@BindClick({ R.id.tv, R.id.tv1, R.id.tv2 })view的Click事件
* BFProxy.injectActivity(this);注入contentView事件
* BFProxy.injectActivityId(this);注入id事件
* BFProxy.injectActivityClick(this);注入click事件

		@BindView(R.layout.activity_main)
		public class MainActivity extends AppCompatActivity {
		    @BindId(R.id.tv)
		    public TextView tv;
		    @BindId(R.id.tv1)
		    public TextView tv1;
		    @BindId(R.id.tv2)
		    public TextView tv2;
		
		    @Override
		    protected void onCreate(Bundle savedInstanceState) {
		        super.onCreate(savedInstanceState);
		        BFProxy.injectActivity(this);
		        BFProxy.injectActivityId(this);
		        BFProxy.injectActivityClick(this);
		        tv.setText("tv");
		        tv1.setText("tv1");
		        tv2.setText("tv2");
		    }
		
		    @BindClick({ R.id.tv, R.id.tv1, R.id.tv2 })
		    public void send(View view) {
		        switch (view.getId()) {
		            case R.id.tv:
		                Toast.makeText(MainActivity.this, "tv被点击了", Toast.LENGTH_SHORT).show();
		                break;
		            case R.id.tv1:
		                Toast.makeText(MainActivity.this, "tv1被点击了", Toast.LENGTH_SHORT).show();
		                break;
		            case R.id.tv2:
		                Toast.makeText(MainActivity.this, "tv2被点击了", Toast.LENGTH_SHORT).show();
		                break;
		        }
		    }
		}
		
### Fragment
Fragment用法与Activity相同

 * BFProxy.injectFragmentId(this, view);注入id
 * BFProxy.injectFragmentClick(this, view);注入点击事件

		@BindViewf(R.layout.fragment_main)
		public class MainFragment extends Fragment {
		    @BindIdf(R.id.btn1)
		    public TextView btn1;
		    @BindIdf(R.id.btn2)
		    public TextView btn2;
		
		    @Nullable
		    @Override
		    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
		            @Nullable Bundle savedInstanceState) {
		        View view = BFProxy.injectFragment(this);
		        BFProxy.injectFragmentId(this, view);
		        BFProxy.injectFragmentClick(this, view);
		        return view;
		    }
		
		    @BindClickf({ R.id.btn1, R.id.btn2 })
		    public void send(View view) {
		        if (view.getId() == R.id.btn1) {
		            System.out.println("btn1");
		        } else {
		            System.out.println("btn2");
		        }
		    }
		}

### 内部类ViewHolder

	 View v = View.inflate(getActivity(), R.layout.activity_main2, null);
	 ViewHolder holder = new ViewHolder(v);
	 
	 public static class ViewHolder {
        @BindIdf(R.id.tv2)
        public TextView tv2;
        @BindIdf(R.id.tv3)
        public TextView tv3;

        public ViewHolder(View view) {
            BFProxy.injectViewF(this, view);
            tv2.setText("viewholder2");
            tv3.setText("viewholder3");
        }
    }
    
## 注意事项
所有声明修饰符必须为public，否则编译报错。

## Dependency
	dependencies {
	        classpath 'com.android.tools.build:gradle:2.2.0'
	        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.4'
	        // NOTE: Do not place your application dependencies here; they belong
	        // in the individual module build.gradle files
	    }
	    
	ext.deps = [
	        javapoet    : 'com.squareup:javapoet:1.8.0',
	        auto_service: 'com.google.auto.service:auto-service:1.0-rc2',
	        appcompat   : 'com.android.support:appcompat-v7:25.3.1'
	]
	

















