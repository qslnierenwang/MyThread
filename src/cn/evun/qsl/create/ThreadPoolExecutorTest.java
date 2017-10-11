package cn.evun.qsl.create;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.plaf.synth.SynthSeparatorUI;
/**
 * 使用ThreadPoolExecutor实现线程连接池
 * 线程池使用测试
 *	主要测试线程池的，coreSize,maxSize,以及队列接收任务的情况
 */
public class ThreadPoolExecutorTest {
	
	
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ThreadPoolExecutor executorPool = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS ,new ArrayBlockingQueue<Runnable>(5));
		for (int i = 0; i < 15; i++) {
			MyTask myTask = new MyTask(i);
			executorPool.execute(myTask);
			String threadNum = "线程池中的线程数目：" + executorPool.getPoolSize() + ",";
			String unDoneWorkNum = "队列中等待执行的任务：" + executorPool.getQueue().size() + ",";
			String doneWorkNum = "已经执行完毕的任务数目：" + executorPool.getCompletedTaskCount() + ",";
			System.out.println(threadNum + unDoneWorkNum + doneWorkNum);
		}
		executorPool.shutdown();
	}
	
	static class  MyTask implements Runnable {

		private int taskNum; 
		
		MyTask(int taskName){
			this.taskNum = taskName;
		}

		@Override
		public void run() {
			System.out.println("正在执行task" + taskNum);
			try {
				Thread.currentThread().sleep(4000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("task" + taskNum + "执行完毕");
		}
		
		
	}
}
