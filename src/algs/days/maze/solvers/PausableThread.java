package algs.days.maze.solvers;

public class PausableThread extends Thread {
	
	int sleepTime = 250;
	
	/** Is processing temporarily suspended. */
	boolean paused;
	
	public void pause(boolean b) {
		paused = b;
	}

	/** On exceptionally large mazes, want to eliminate sleep. */
	public void eliminateSleep(boolean flag) { 
		if (flag) {
			sleepTime = 5;
		} else {
			sleepTime = 250;
		}
	}
		
	/** Delay if paused. Only external action unpauses, once in while loop. */
	void checkPaused() {
		while (paused) {
			try {
				this.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean isPaused() {
		return paused;
	}
}
