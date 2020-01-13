import rx.Observable;
import rx.Subscriber;
import rx.functions.Func2;

import java.util.concurrent.TimeUnit;

/**
 * @author taoer
 * @version 1.0.0
 * @Description
 */
public class TestCombining {
    public static void main(String[] args) {
        //testZip();
        //testMerg();
        //testStartWith();
        //testCombineLatest();
        testJoin();
    }

    private static void testJoin() {

        //输出[0,1,2,3]序列
        Observable ob = Observable.create(subscriber -> {
            for(int i = 0; i < 4 ; i++){
                subscriber.onNext(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //组合两个observable数据源
        Observable.just("Hello")
                .join(ob,s -> {
                    System.out.println(s);
                    return Observable.timer(3000, TimeUnit.MINUTES);
                },integer ->{
                    System.out.println(integer + "");
                    return  Observable.timer(2000,TimeUnit.MINUTES);
                },(s,integer) -> s + integer)
                .subscribe(o->System.out.println(o));

    }

    /**
     * 用于将两个Observable最近发射的数据根据Fun2的规则进行组合
     */
    private static void testCombineLatest() {
        Observable<Integer> first = Observable.just(1, 2, 3); //此处的3离后续数据项更近
        Observable<Integer> second = Observable.just(4, 5, 6);
        first.combineLatest(first, second, new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer arg0, Integer arg1) {
                System.out.println("argO:"+ arg0 + " argN:" + arg1);
                return arg0 + arg1;
            }
        }).subscribe(new Subscriber<Integer>() {

            public void onCompleted() {
                System.out.println("onCompleted():");
            }


            public void onError(Throwable e) {
                System.out.println("onError():" );
            }

            public void onNext(Integer integer) {
                System.out.println("onNext():" + integer );
            }
        });
    }

    /**
     * 用于在源Observable发射的数据前插入数据
     */
    private static void testStartWith() {
        Observable<Integer> first = Observable.just(1, 2, 3);
        Observable<Integer> second = Observable.just(4, 5, 6);
        first.startWith(second).subscribe(new Subscriber<Integer>() {

            public void onCompleted() {
                System.out.println("onCompleted():");
            }


            public void onError(Throwable e) {
                System.out.println("onError():" );
            }

            public void onNext(Integer integer) {
                System.out.println("onNext():" + integer );
            }
        });
    }

    /**
     * 将两个Observable发射的事件序列组合并成一个事件序列，就像一个Observable一样，按照时间戳
     */
    private static void testMerg() {
        Observable<Integer> odds = Observable.just(1, 2, 3);
        Observable<Integer> evens = Observable.just(4, 5, 6, 9);
        Observable.merge(odds,evens).subscribe(new Subscriber<Integer>() {

            public void onCompleted() {
                System.out.println("onCompleted():");
            }


            public void onError(Throwable e) {
                System.out.println("onError():" );
            }

            public void onNext(Integer integer) {
                System.out.println("onNext():" + integer );
            }
        });
    }

    /**
     * 用来合并两个Observable发射的数据项，根据Func2函数设置的规则生成一个新的数据项并发送
     * 当其中一个Observable发射数据结束或者出现异常后，
     * 另一个Observable也将停止发送
     */
    private static void testZip() {
        Observable<Integer> observable1 = Observable.just(11, 22, 13);
        Observable<Integer> observable2 = Observable.just(14, 11, 17, 9);
        Observable.zip(observable1, observable2, new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer data1, Integer data2) {
                return data1 + data2;
            }
        }).subscribe(new Subscriber<Integer>() {

            public void onCompleted() {
                System.out.println("onCompleted():");
            }


            public void onError(Throwable e) {
                System.out.println("onError():" );
            }

            public void onNext(Integer integer) {
                System.out.println("onNext():" + integer );
            }
        });
    }
}
