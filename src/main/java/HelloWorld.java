import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;

import java.util.List;

/**
 * @author taoer
 * @version 1.0.0
 * @Description
 */
public class HelloWorld {
    static String  values;
    public static void main(String[] args) {
            //create();
            //testTransform1();
            //testTransform2();
            //testTransform3();
            //buffer();
            scan();

        }

    private static void scan() {
        Observable.range(1,5).scan(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer sum, Integer arg0) {
                return  sum + arg0;   //agr0为扫描当前的值，sum为扫描当前累加和,即此处返回的sum = sum + arg0
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer data) {
                System.out.println("onNext:  " + data);
            }
        });
    }

    private static void buffer() {
        Observable.range(1,5).buffer(3).subscribe(new Observer<List<Integer>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Integer> integers) {
                System.out.println("onNext:  " + integers);
            }
        });
    }

    private static void testTransform3() {
        Observable.just(1,2,3,4,5).groupBy(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                return integer % 2;
            }
        }).subscribe(new Observer<GroupedObservable<Integer, Integer>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GroupedObservable<Integer, Integer> arg0) {
                arg0.subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer data) {
                        System.out.println("group:" + arg0.getKey() + "  data  " + data);
                    }
                });
            }
        });
    }

    private static void testTransform2() {
        //将一个列表，内容转换为字符串平铺发射
        Observable.just(1,2,3,4,5).flatMap(new Func1<Integer,Observable<? extends String>>() {
            @Override
            public Observable call(Integer arg0) {
                return Observable.just(arg0 + "");
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext: ---->" + s);
            }
        });
    }

    private static void testTransform1() {
        Observable.just(123).map(new Func1<Integer, String>() { //将数字123转换为字符串
            @Override
            public String call(Integer arg0) {
                return arg0 + "";
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onNext(String s) {
             System.out.println("onNext: ---->" + s);
            }
        });
    }

    private static void create(){
          Observable  observable = Observable.range(1,5).repeat(2);
            observable.subscribe(new Subscriber<Integer>() {

                @Override
                public void onCompleted() {
                    System.out.println("onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    System.out.println("onError");
                }

                @Override
                public void onNext(Integer s) {
                    System.out.println("onNext: ----->" + s);
                }

            });
        }
    }

