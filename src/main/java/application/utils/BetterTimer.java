package application.utils;

import java.util.Date;

public class BetterTimer {
	
	private long startTime;
	private long elapsedTime;
	private long period;
	private long state;
	private boolean running = false;
		
	public BetterTimer( long duration ) {
		this.period = duration;
		this.state = period / 1000;
	}
	
	public void start() {
		running = true;
		startTime = System.currentTimeMillis();
		elapsedTime = 0L;
		update();
	}
	
	public void update() {
		if( !running ) return;
		elapsedTime = ( new Date() ).getTime() - startTime;
		state = ( period - elapsedTime ) / 1000;
		if( elapsedTime > period ) {
			stop();
		}
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public long getState() {
		return state;
	}
	
	public void stop() {
		running = false;
	}
		
	public static long seconds( int s ) {
		long r = s;
		r *= 1000;
		return r;
	}
}
