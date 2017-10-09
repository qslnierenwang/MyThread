package cn.evun.qsl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

/**
 * 能够记录每个线程的执行时长的线程池
 * @author Qin.SiLiang
 *
 */
public class TimingThreadPool extends ThreadPoolExecutor{
	
	//将开始时间记录在ThreadLocal中，则在线程结束的时候可以获得到开始时间
	private final ThreadLocal<Map> threadLocalMap = new ThreadLocal<Map>();
	
	private final Logger log = Logger.getLogger("TimingThreadPool");
	
	private final AtomicLong numTasks = new AtomicLong();
	
	private final AtomicLong totalTime = new AtomicLong();
	
	protected void beforeExecute(Thread t , Runnable r) {
		super.beforeExecute(t, r);
		log.fine(String.format(" Thread %s : start %s ", t ,r));
		Map<String ,Object> data = new HashMap<String ,Object>();
		data.put("startTime", System.nanoTime());
		threadLocalMap.set(data);
	}
	
	protected void afterExecute(Throwable t , Runnable r){
		try {
			long endTime = System.nanoTime();
			long taskTime = endTime - (Long)threadLocalMap.get().get("startTime");
			numTasks.incrementAndGet();
			totalTime.addAndGet(taskTime);
			log.fine(String.format("Thread %s : end %s , time = %dns", t ,r ,taskTime));
		} finally {
			super.afterExecute(r, t);
		}
	}
	
//	protected void teminated(){
//		log.info(String.format("", args));
//	}
	
	public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

}
