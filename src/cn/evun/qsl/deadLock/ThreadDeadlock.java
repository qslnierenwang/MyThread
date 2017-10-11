package cn.evun.qsl.deadLock;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * 测试死锁程序（一个线程执行两个线程的任务）
 * @author Qin.SiLiang
 *
 */
public class ThreadDeadlock {
	
	
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService poll = Executors.newSingleThreadExecutor();
		Future<String> future1 = poll.submit(new ReadPageTask("任务1"));
		Future<String> future2 = poll.submit(new ReadPageTask("任务2"));
		
		System.out.println(future1.get() + "这个是page" + future2.get());
		poll.shutdown();
	}
	
	static class  ReadPageTask implements Callable<String> {

		private String taskName; 
		
		ReadPageTask(String taskName){
			this.taskName = taskName;
		}
		
		@Override
		public String call() throws Exception {
			Thread.sleep(10000);
			return this.taskName+":执行完成了";
		}
		
	}
}
