package thread;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class FindDirsFiles extends RecursiveAction {

	private static final long serialVersionUID = 1L;
	private File path;// 当前任务需要搜寻的目录

	public FindDirsFiles(File path) {
		this.path = path;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ForkJoinPool pool = new ForkJoinPool();
			FindDirsFiles task = new FindDirsFiles(new File("D:\\"));

			pool.execute(task);
			
			//线程池在执行任务之后，一定要join（）或者get（），来阻塞当前线程，完成forkjoin任务
			System.out.println("Task is Running......");
			long startTime = System.currentTimeMillis();
			task.join();
			System.out.println("耗时： " + (System.currentTimeMillis() - startTime));
			System.out.println("Task end");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void compute() {

		List<FindDirsFiles> subTasks = new ArrayList<>();

		File[] files = path.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					subTasks.add(new FindDirsFiles(file));
				} else {
					if (file.getAbsolutePath().endsWith("rar")) {
						System.out.println("文件：" + file.getAbsolutePath());
					}
				}
			}
			if (!subTasks.isEmpty()) {
				invokeAll(subTasks);	// 将集合中所有的任务添加到WorkQueue队列，因为没有结果返回，所以不需要join
			}
//			if (!subTasks.isEmpty()) {
//				for (FindDirsFiles subTask : invokeAll(subTasks)) {
//					subTask.join();
//				}
//			}
		}
	}
}
