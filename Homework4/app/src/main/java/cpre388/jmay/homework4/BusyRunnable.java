package cpre388.jmay.homework4;

import android.os.Process;

/**
 * Created by jmay on 2017-11-01.
 */

class BusyRunnable implements Runnable {

    public Thread mThread = null;
    private RunActivity mParent;

    public BusyRunnable(RunActivity parent) {
        mParent = parent;
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        mThread = Thread.currentThread();

        //noinspection StatementWithEmptyBody
        for (int i = 0; i < 500000000; i++) {
            // nothing
        }
        mParent.taskEnded();
    }
}
