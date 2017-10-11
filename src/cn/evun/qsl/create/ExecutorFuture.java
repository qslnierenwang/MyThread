package cn.evun.qsl.create;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * 使用Future + Callable 创建线程
 * @author Qin.SiLiang
 *
 */
public class ExecutorFuture {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService poll = Executors.newFixedThreadPool(2);
		Future<String> future1 = poll.submit(new MyTask("任务1"));
		Future<String> future2 = poll.submit(new MyTask("任务2"));
		
		System.out.println("future1的执行结果是："+future1.get());
		System.out.println("future2的执行结果是："+future2.get());
		
		poll.shutdown();
	}
	
	static class  MyTask implements Callable<String> {

		private String taskName; 
		
		MyTask(String taskName){
			this.taskName = taskName;
		}
		
		@Override
		public String call() throws Exception {
			Thread.sleep(10);
			return this.taskName+":执行完成了";
		}
		
	}
}
