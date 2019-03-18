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
        // Ԫ�ذ�FIFOԭ��������򣮲���CAS����������֤Ԫ�ص�һ����
        // add remove offer poll peek������û��take put����Ϊ������������
        ConcurrentLinkedQueue<User> linkedQueue = new ConcurrentLinkedQueue<>();
        linkedQueue.size(); // ������������
        linkedQueue.isEmpty(); // ��ȡͷ��Ԫ��


        // ���̲߳�����ͬ�Ķ���ʱ����Ҫ�����ͬ����������Ƕ��л��Զ�ƽ�⸺��
        LinkedBlockingQueue<User> blockingQueue = new LinkedBlockingQueue<>();
        blockingQueue.size();
        blockingQueue.isEmpty(); // return size() == 0��������size()����

        // ͷ��ȡ��β���ţ�������������������
        blockingQueue.take();
        blockingQueue.put(new User());

        // ͷ��ȡ��β���ţ��������쳣��û�����쳣
        blockingQueue.add(new User());
        blockingQueue.remove();

        // β���ţ�û�з���false
        blockingQueue.offer(new User());
        blockingQueue.offer(new User(), 10, TimeUnit.SECONDS);

        // ͷ��ȡ��û�з���null
        blockingQueue.poll();
        blockingQueue.poll(10, TimeUnit.SECONDS);

        // ��ȡ�����Ƴ���û�з���null
        blockingQueue.peek();
        
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        
        //�̰߳�ȫ�����������򣬴���TreeSet��ͨ��ConcurrentSkipListMapʵ�ֵģ���TreeSet��ͨ��TreeMapʵ�ֵ�
        ConcurrentSkipListSet<String> set = new ConcurrentSkipListSet<>();
        
        //�̰߳�ȫ������������ӳ�������TreeMap
        ConcurrentSkipListMap<String, String> skipListMap = new ConcurrentSkipListMap<>();
    }
}
