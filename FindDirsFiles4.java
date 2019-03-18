package thread;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import util.ThreadTools;

public class FindDirsFiles4 extends RecursiveTask<List<String>> {

	private static final long serialVersionUID = 1L;
	private File path;// 当前任务需要搜寻的目录

	public FindDirsFiles4(File path) {
		this.path = path;
	}

	public static void main(String[] args) {
		try {
//			ForkJoinPool pool = new ForkJoinPool();				// 5000 核心线程数4个，运行线程4个
//			ForkJoinPool pool = new ForkJoinPool(5);			// 4850 核心线程数5个，运行线程5个
			ForkJoinPool pool = ForkJoinPool.commonPool();		// 5500 核心线程数3个，运行线程3个
			FindDirsFiles4 task = new FindDirsFiles4(new File("D:\\"));

			pool.submit(task);

			long startTime = System.currentTimeMillis();
			while (!task.isDone()) {
				Thread.sleep(25);
				System.out.println(pool.getStealCount() + " : " + pool.getActiveThreadCount() + " : " + pool.getQueuedTaskCount()
						+ " : " + pool.getParallelism() + " : " + pool.getPoolSize() + " : " + pool.getRunningThreadCount());
			}
			List<String> list = task.join();
			System.out.println("耗时： " + (System.currentTimeMillis() - startTime));
//			list.forEach(System.out::println);

			System.out.println("Task end");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected List<String> compute() {
		List<String> list = new ArrayList<>(); // 存放任务的集合
		List<FindDirsFiles4> tasks = new ArrayList<>(); // 结果集

		File[] files = path.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					FindDirsFiles4 task = new FindDirsFiles4(file);
					tasks.add(task);
				} else {
					// 遇到文件，检查
					if (file.getAbsolutePath().endsWith("rar")) {
						//System.out.println(Thread.currentThread().getName() + "   文件：" + file.getAbsolutePath());
						list.add(file.getAbsolutePath());
					}
				}
			}
			if (!tasks.isEmpty()) {
				for (FindDirsFiles4 subTask : invokeAll(tasks)) {
					list.addAll(subTask.join());
				}
			}
		}
		return list;
	}
}
