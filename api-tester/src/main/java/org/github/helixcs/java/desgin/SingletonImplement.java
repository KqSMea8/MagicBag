package org.github.helixcs.java.desgin;

public class SingletonImplement {
    public static void main(String[] args) {
        LazySingletonWithDoubleCheck lazySingletonWithDoubleCheck1 = LazySingletonWithDoubleCheck.getInstance();
        LazySingletonWithDoubleCheck lazySingletonWithDoubleCheck2 = LazySingletonWithDoubleCheck.getInstance();
        System.out.println("Compare lazySingletonWithDoubleCheck1 with lazySingletonWithDoubleCheck2 :");
        System.out.println(lazySingletonWithDoubleCheck1==lazySingletonWithDoubleCheck2);
        LazySingletonWithInnerStaticClass lazySingletonWithInnerStaticClass = LazySingletonWithInnerStaticClass.getInstance();
        HungrySingleton hungrySingleton = HungrySingleton.getInstance();

    }
}

// double check 实现单例, 在类加载时，getInstance() 不会加载，在调用时才会加载，这种加载称为懒加载
// double check 防止 new LazySingletonWithDoubleCheck() 时指令重排
class LazySingletonWithDoubleCheck{
    private static  LazySingletonWithDoubleCheck lazySingleton;
    private LazySingletonWithDoubleCheck(){}
    public static LazySingletonWithDoubleCheck getInstance(){
        System.out.println("LazySingletonWithDoubleCheck loading ...");
        if(null==lazySingleton){
            synchronized (LazySingletonWithDoubleCheck.class){
                if(null==lazySingleton){
                    lazySingleton = new LazySingletonWithDoubleCheck();
                }
            }
        }
        return lazySingleton;
    }
}


// 静态内部类实现单例,
//class LazySingleton

class LazySingletonWithInnerStaticClass {

    private LazySingletonWithInnerStaticClass(){}
    public static LazySingletonWithInnerStaticClass getInstance(){
        System.out.println("LazySingletonWithInnerStaticClass loading .....");
        return InnerHolder.lazySingletonWithInnerStaticClass;
    }
    private static class InnerHolder{
        private static LazySingletonWithInnerStaticClass lazySingletonWithInnerStaticClass = new LazySingletonWithInnerStaticClass();
    }
}

// 饿汉单例
class HungrySingleton{
    private static HungrySingleton hungrySingleton = new HungrySingleton();
    private HungrySingleton(){}
    public static HungrySingleton getInstance(){
        return hungrySingleton;
    }
}