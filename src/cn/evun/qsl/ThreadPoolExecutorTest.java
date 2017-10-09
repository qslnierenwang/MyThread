package cn.evun.qsl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolExecutorTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService pool = Executors.newCachedThreadPool();
		if( pool instanceof ThreadPoolExecutor){
			((ThreadPoolExecutor)pool).setCorePoolSize(10);
			System.out.println("重新设置连接池大小成功");
		}else{
			throw new AssertionError("assumption error");
		}
	}

}
