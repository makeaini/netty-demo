package com.pppcar.netty.thread;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ThreadPool {
	private ExecutorService executorService;
	/* 默认池中线程数 */
	public static int worker_num = 5;
	private static ThreadPool instance = ThreadPool.getInstance();

	private ThreadPool() {
		// cpu数量+1
		worker_num = Runtime.getRuntime().availableProcessors() + 1;
		executorService = Executors.newFixedThreadPool(worker_num);
	}

	public static synchronized ThreadPool getInstance() {
		if (instance == null)
			return new ThreadPool();
		return instance;
	}

	public synchronized void destroy() {
		executorService.shutdown();
	}

}
