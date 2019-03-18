package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import util.ThreadTools;

public class ForkJoinTest {

	public static void main(String[] args) {
		long end = 10000l;
//		singleThread(1, end);
		forkJoinThread(1, end);
	}

	public static void singleThread(long start, long end) {
		long sum = 0;
		long startTime = System.currentTimeMillis();
		for (long i = start; i <= end; i++) {
			ThreadTools.Sleep(1);
			sum += i;
		}
		System.out.println("总和： " + sum);
		System.out.println("时间： " + (System.currentTimeMillis() - startTime));
	}

	public static void forkJoinThread(long start, long end) {
//		ForkJoinPool pool = new ForkJoinPool();			//	5000 运行线程数=CPU
//		ForkJoinPool pool = new ForkJoinPool(5);		//	5000 运行线程数=5
		ForkJoinPool pool = new ForkJoinPool(10);		//	2500
//		ForkJoinPool pool = ForkJoinPool.commonPool();	//	7500 运行线程数=3

		/*
		 * ForkJoinPools类有一个静态方法commonPool()， 这个静态方法所获得的ForkJoinPools实例是由整个应用进程共享的，
		 * 并且它适合绝大多数的应用系统场景。 使用commonPool通常可以帮助应用程序中多种需要进行归并计算的任务共享计算资源，
		 * 从而使后者发挥最大作用（ForkJoinPools中的工作线程在闲置时会被缓慢回收，并在随后需要使用时被恢复）
		 */
//		ForkJoinPool pool = ForkJoinPool.commonPool();

		AddMethod task = new AddMethod(start, end);

		// submit有返回值---RecursiveTask，execute无返回值---RecursiveAction
		// 当任务提交调用submit()方法的那一刻，任务已经在执行，有四种情况

		pool.submit(task);

//		System.out.println(Thread.currentThread().getName() + "  开始睡眠。。。");
//		ThreadTools.Sleep(1000);
//		System.out.println(Thread.currentThread().getName() + "  睡眠结束。。。");
		try {

			// join()
			// get()，get(long timeout, TimeUnit unit)
			// 阻塞线程，等待任务结束，并获得它们的结果
			long startTime = System.currentTimeMillis();
			while (!task.isDone()) {
				Thread.sleep(25);
				System.out.println(pool.getStealCount() + " : " + pool.getActiveThreadCount() + " : " + pool.getQueuedTaskCount()
						+ " : " + pool.getParallelism() + " : " + pool.getPoolSize() + " : " + pool.getRunningThreadCount());
			}
			System.out.println("总和： " + task.get());
			System.out.println("时间： " + (System.currentTimeMillis() - startTime));

			// 检查是否有异常
			if (task.isCompletedAbnormally()) {
				System.out.println(task.getException());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

class AddMethod extends RecursiveTask<Long> {

	private static final long serialVersionUID = -619299345511482868L;
	private long yuzhi = 1000l;
	private long start;
	private long end;

	public AddMethod(long start, long end) {
		this.start = start;
		this.end = end;
	}

	@Override
	protected Long compute() {
		List<AddMethod> tasks = new ArrayList<>();
		long sum = 0;
		if ((this.end - this.start) < this.yuzhi) {
			for (long i = this.start; i <= this.end; i++) {
				ThreadTools.Sleep(2);
				sum += i;
			}
		} else {
			long mid = (this.start + this.end) / 2;
			AddMethod left = new AddMethod(this.start, mid);
			AddMethod right = new AddMethod(mid + 1, this.end);
			tasks.add(left);
			tasks.add(right);
			
//			异步方法fork()基本不采用，比invokeAll()时间多
//			left.fork();
//			right.fork();
//			sum = left.join() + right.join();
		}
		if (!tasks.isEmpty()) {
			for (AddMethod add : invokeAll(tasks)) {
				sum += add.join();
			}
		}

		return sum;
	}
}
