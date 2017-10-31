# Exteneded Log library for Andoroid.

### Example:
```java
@MapClass(methods = {"test"}, prefixes = {"TEST"})
public class MainActivity
        extends Activity {

    /*  Will generate new class com.don11995.log.ValueMapper
        with method "setup" that mapping values to names.
        You need to add dependency
        annotationProcessor 'com.don11995.log:simple-log-processor:x.x.x'
     */
    @MapField("setup")
    public static final int SETUP_0 = 0;
    @MapField("setup")
    public static final int SETUP_1 = 1;
    @MapField("setup")
    public static final int SETUP_2 = 2;
    @MapField("setup")
    public static final int SETUP_3 = 3;
    @MapField("setup")
    public static final int SETUP_4 = 4;

    public static final String TEST_0 = "test0";
    public static final String TEST_1 = "test1";
    public static final String TEST_2 = "test2";
    public static final String TEST_3 = "test3";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //prints "onCreate()" with tag "MainActivity"
        SimpleLog.d();

        //prints "Test" with tag "MainActivity"
        SimpleLog.d("Test1");

        //prints "onCreate: Test" with tag "MainActivity"
        SimpleLog.fd("Test2");

        int a = SETUP_2;
        //prints "2" with tag "MainActivity"
        SimpleLog.e("Test3: %d", a);
        SimpleLog.e("Test4: " + a);

        // prints "SETUP_2" with tag "MainActivity"
        // Class ValueMapper will be auto generated from annotations
        // if annotation processor in dependencies
        SimpleLog.e("Test5: %s", ValueMapper.setup(a));

        String b = TEST_3;
        // prints "TEST_3" with tag "MainActivity"
        // Class ValueMapper will be auto generated from annotations
        // if annotation processor in dependencies
        SimpleLog.e("Test6: %s", ValueMapper.test(b));

        //prints
        //      --------Title--------
        //      String1
        //      String2
        //      ---------------------
        SimpleLog.w(new Group("Title")
                            .append("String1")
                            .append("String2"));

        NullPointerException e = new NullPointerException("Test exception");
        // print exception with full stack trace
        SimpleLog.e(e);
    }
}
```

### Usage:
```gradle
repositories {
        ...
        maven {url 'https://dl.bintray.com/personshelldon/maven/' }
    }

annotationProcessor 'com.don11995.log:simple-log-processor:1.1.4'
implemetation 'com.don11995.log:simple-log:1.1.4'
```