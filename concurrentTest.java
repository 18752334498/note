package demo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class concurrentTest {
    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {
        // 元素按FIFO原则进行排序．采用CAS操作，来保证元素的一致性
        // add remove offer poll peek，但是没有take put，因为不是阻塞队列
        ConcurrentLinkedQueue<User> linkedQueue = new ConcurrentLinkedQueue<>();
        linkedQueue.size(); // 遍历整个集合
        linkedQueue.isEmpty(); // 获取头部元素


        // 多线程操作共同的队列时不需要额外的同步，另外就是队列会自动平衡负载
        LinkedBlockingQueue<User> blockingQueue = new LinkedBlockingQueue<>();
        blockingQueue.size();
        blockingQueue.isEmpty(); // return size() == 0，调用了size()方法

        // 头部取，尾部放，满了阻塞，空了阻塞
        blockingQueue.take();
        blockingQueue.put(new User());

        // 头部取，尾部放，满了抛异常，没有抛异常
        blockingQueue.add(new User());
        blockingQueue.remove();

        // 尾部放，没有返回false
        blockingQueue.offer(new User());
        blockingQueue.offer(new User(), 10, TimeUnit.SECONDS);

        // 头部取，没有返回null
        blockingQueue.poll();
        blockingQueue.poll(10, TimeUnit.SECONDS);

        // 获取但不移除，没有返回null
        blockingQueue.peek();
        
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        
        //线程安全，并发，有序，代替TreeSet，通过ConcurrentSkipListMap实现的，而TreeSet是通过TreeMap实现的
        ConcurrentSkipListSet<String> set = new ConcurrentSkipListSet<>();
        
        //线程安全，并发，排序映射表，代替TreeMap
        ConcurrentSkipListMap<String, String> skipListMap = new ConcurrentSkipListMap<>();
    }
}
