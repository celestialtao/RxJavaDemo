import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

import java.util.concurrent.TimeUnit;

/**
 * @author taoer
 * @version 1.0.0
 * @Description
 */
public class TestFiltering {
    public static void main(String[] args) {
        // testDebounce();
        // testDistinct();
        // testElementAt();
        //testFilter();
        // testFirst();
        //testIgoreElements();
        //testLast();
        //testSample();
        //testSkip();
        testTake();



    }

    private static void testTake() {
        Observable.just(1,2,3,1,2).take(2).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted():");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError():");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext():" + integer);
            }
        });
    }

    private static void testSkip() {
        Observable.just(1,2,3,1,2).last().subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted():" + "\n");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError():" + "\n");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext():" + integer + "\n");
            }
        });
    }

    private static void testSample() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                //每间隔一秒发送一个数据
                try{
                    for(int i = 0; i < 10; i++){
                        Thread.sleep(1000);
                        subscriber.onNext(i);
                    }
                    subscriber.onCompleted();
                }catch (Exception e){
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        }).sample(4, TimeUnit.SECONDS).subscribe(new Subscriber<Integer>() {
            //通过时间间隔1秒过滤item,单位为SECONDS
            @Override
            public void onCompleted() {
                System.out.println("onCompleted():" + "\n");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError():" + "\n");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext():" + integer + "\n");
            }
        });
    }


    private static void testLast() {
            Observable.just(1,2,3,1,2).last().subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted():" + "\n");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError():" + "\n");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext():" + integer + "\n");
            }
        });
    }

    private static void testIgoreElements() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(123);
                subscriber.onCompleted();
               // throw new NullPointerException();
            }
        }).ignoreElements().subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted():" + "\n");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError():" + "\n");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext():" + integer + "\n");
            }
        });
    }

    private static void testFirst() {
        Observable.just(1,2,3,1,2).first().subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted():" + "\n");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError():" + "\n");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext():" + integer + "\n");
            }
        });
    }

    private static void testFilter() {
        Observable.just(1,2,3,1,2).distinct().filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer arg0) { //设置发送的数据大于2
                return arg0 > 2;
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted():" + "\n");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError():" + "\n");
            }

            @Override
            public void onNext(Integer data) {
                System.out.println("onNext():" + data + "\n");
            }
        });
    }

    private static void testElementAt() {
        Observable.just(1,2,3,1,2).elementAt(2).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted():" + "\n");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError():" + "\n");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext():" + integer + "\n");
            }
        });
    }

    private static void testDistinct() {
        Observable.just(1,2,3,1,2).distinct().subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted():" + "\n");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError():" + "\n");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext():" + integer + "\n");
            }
        });
    }

    private static void testDebounce() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                //每间隔一秒发送一个数据
                try{
                    for(int i = 0; i < 10; i++){
                        Thread.sleep(1000);
                        subscriber.onNext(i);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).debounce(1, TimeUnit.SECONDS).subscribe(new Subscriber<Integer>() {
           //通过时间间隔1秒过滤item,单位为SECONDS
            @Override
            public void onCompleted() {
                System.out.println("onCompleted():" + "\n");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError():" + "\n");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext():" + integer + "\n");
            }
        });
    }
}
