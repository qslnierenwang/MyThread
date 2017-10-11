package cn.evun.qsl.create;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
/**
 * 使用FutureTask + Callable 创建线程
 * @author Qin.SiLiang
 *
 */
public class ExecutorFutureTask {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorPool = Executors.newFixedThreadPool(2);
		MyFutureTask futureTask1 = new MyFutureTask(new MyTask("任务1"));
		MyFutureTask futureTask2 = new MyFutureTask(new MyTask("任务2"));
		
		executorPool.submit(futureTask1);
		executorPool.submit(futureTask2);
		
		System.out.println("future1的执行结果是："+futureTask1.get());
		System.out.println("future2的执行结果是："+futureTask2.get());
		
		executorPool.shutdown();
		
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
	
	static class  MyFutureTask extends FutureTask<String>{
		

		public MyFutureTask(Callable<String> callable) {
			super(callable);
		}

		@Override
		protected void done() {
			try {
				System.out.println(get());
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.done();
		}
		
		

	}
}
