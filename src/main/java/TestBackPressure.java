import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author taoer
 * @version 1.0.0
 * @Description
 */
public class TestBackPressure {
    public static void main(String[] args) {
        //testBPCrash();
       // testBPSolve();
        //testBPBuffer();
        //testBPDrop();
        //testCombineLatest();
    }


    /**
     *  理想输出：
     * W/TAG: start
     * W/TAG: ---->0
     * W/TAG: ---->1
     * W/TAG: ---->2
     * W/TAG: ---->3
     * W/TAG: ---->4
     * W/TAG: ---->5
     * W/TAG: ---->6
     * W/TAG: ---->7
     * W/TAG: ---->8
     * W/TAG: ---->9
     * W/TAG: ---->10
     * W/TAG: ---->11
     * W/TAG: ---->12
     * W/TAG: ---->13
     * W/TAG: ---->14
     * W/TAG: ---->15
     * W/TAG: ---->1218
     * W/TAG: ---->1219
     * W/TAG: ---->1220
     * ...
     *
     */
    /*private static void testBPDrop() {
        Observable.interval(1, TimeUnit.MILLISECONDS)
                .onBackpressureDrop()
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Long>() {

                    @Override
                    public void onStart() {
                        Log.w("TAG","start");
//                        request(1);
                    }

                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("ERROR",e.toString());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.w("TAG","---->"+aLong);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }*/

    /*private static void testBPBuffer() {
        Observable.interval(1, TimeUnit.MINUTES)

                .observeOn(Schedulers.newThread())
                //这个操作符简单理解就是把100毫秒内的事件打包成list发送
                .buffer(100,TimeUnit.MILLISECONDS)
                .subscribe(new Action1<List<Long>>() {
                    @Override
                    public void call(List<Long> aLong) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.w("TAG","---->"+aLong.size());
                    }
                });
    }*/

    private static void testBPSolve() {
        //被观察者将产生10000个事件
        Observable observable=Observable.range(1,10000);
        class MySubscriber extends Subscriber<Integer> {
            @Override
            public void onStart() {
                //一定要在onStart中通知被观察者先发送一个事件
                request(1);
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Integer n) {
                //处理完毕之后，在通知被观察者发送下一个事件
                request(1);

            }
        }

        observable.observeOn(Schedulers.newThread())
                .subscribe(new MySubscriber());

    }

    /*private static void testBPCrash() {
        //被观察者在主线程中，每1ms发送一个事件
        Observable.interval(1, TimeUnit.MILLISECONDS)
                //.subscribeOn(Schedulers.newThread())
                //将观察者的工作放在新线程环境中
                .observeOn(Schedulers.newThread())
                //观察者处理每10000ms才处理一个事件
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        try {
                            Thread.sleep(50000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.w("TAG","---->"+aLong);
                    }
                });
    }*/


}
