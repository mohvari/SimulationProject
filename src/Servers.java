import java.util.ArrayList;
import java.util.Random;

class PreServer1 {
    private ArrayList<Process> queue;

    private double lastQueueChangeTime;
    private double weightedQueueLength;
    private double idleTime;

    private int capacity;

    PreServer1(String[] ableToProcess, double mu, double sigma) {
        queue = new ArrayList<>();

        lastQueueChangeTime = 0.;
        weightedQueueLength = 0.;
        idleTime = 0.;

        capacity = 100;
    }

    double getWeightedQueueLength() {
        return weightedQueueLength;
    }

    double getIdleTime() {
        return idleTime;
    }

    void addProcess(Process p, double curTime) {

//        weightedQueueLength += queue.size() * (curTime - lastQueueChangeTime);
//        if (queue.size() == 0)
//            idleTime += (curTime - lastQueueChangeTime);

//        lastQueueChangeTime = curTime;

        if (queue.size() == 0) {
            queue.add(0, p);
            p.setStartTime(curTime);
            p.setLastCheckTime(curTime);
        }else {
            queue.get(0).subtractRemaining(curTime - queue.get(0).getLastCheckTime());
            queue.get(0).setLastCheckTime(curTime);

            if (queue.get(0).getRemaining() > p.getDuration()) { // replace new process
                queue.add(0, p);
                p.setStartTime(curTime);
                p.setLastCheckTime(curTime);
            } else { // continue with old process
                queue.add(p);
            }
        }
    }

    double nextChange() {
        if (queue.isEmpty())
            return Double.POSITIVE_INFINITY;
        else {
            return queue.get(0).getLastCheckTime() + queue.get(0).getRemaining();
        }
    }

    Process finishProcess(double curTime) {

//        weightedQueueLength += queue.size() * (curTime - lastQueueChangeTime);
//        lastQueueChangeTime = curTime;

        Process head = queue.get(0);
        queue.remove(0);

        if (queue.size() > 0) { // find process with minimum remaining time
            int minIndex = -1;
            double minAmount = Double.POSITIVE_INFINITY;

            for (int i = 0; i < queue.size(); i++) {
                if (queue.get(i).getRemaining() < minAmount) {
                    minAmount = queue.get(i).getRemaining();
                    minIndex = i;
                }
            }

            Process tmpProcess = queue.get(minIndex);
            queue.remove(minIndex);
            queue.add(0, tmpProcess);

            queue.get(0).setStartTime(curTime);
            queue.get(0).setLastCheckTime(curTime);

        }


        return head;
    }

}


class PreServer2 {
    private ArrayList<Process> queue;

    private double lastQueueChangeTime;
    private double weightedQueueLength;
    private double idleTime;

    private int capacity;

    PreServer2(String[] ableToProcess, double mu, double sigma) {
        queue = new ArrayList<>();

        lastQueueChangeTime = 0.;
        weightedQueueLength = 0.;
        idleTime = 0.;

        capacity = 12;
    }

    double getWeightedQueueLength() {
        return weightedQueueLength;
    }

    double getIdleTime() {
        return idleTime;
    }

    void addProcess(Process p, double curTime) {

//        weightedQueueLength += queue.size() * (curTime - lastQueueChangeTime);
//        if (queue.size() == 0)
//            idleTime += (curTime - lastQueueChangeTime);

//        lastQueueChangeTime = curTime;

        queue.add(p);

        if (queue.size() == 0) {
            p.setStartTime(curTime);
            p.setLastCheckTime(curTime);
        }
    }

    double nextChange() {
        if (queue.isEmpty())
            return Double.POSITIVE_INFINITY;
        else {
            return queue.get(0).getLastCheckTime() + queue.get(0).getRemaining();
        }
    }

    Process finishProcess(double curTime) {

//        weightedQueueLength += queue.size() * (curTime - lastQueueChangeTime);
//        lastQueueChangeTime = curTime;

        Process head = queue.get(0);
        queue.remove(0);

        if (queue.size() > 0) { // find a random process
            int index = new Random().nextInt(queue.size());

            Process tmpProcess = queue.get(index);
            queue.remove(index);
            queue.add(0, tmpProcess);

            queue.get(0).setStartTime(curTime);
            queue.get(0).setLastCheckTime(curTime);

        }


        return head;
    }

}


class MainServer {
    private ArrayList<Process> queue;

    private double lastQueueChangeTime;
    private double weightedQueueLength;
    private double idleTime;

    private int capacity;

    MainServer(String[] ableToProcess, double mu, double sigma) {
        queue = new ArrayList<>();

        lastQueueChangeTime = 0.;
        weightedQueueLength = 0.;
        idleTime = 0.;

        capacity = 16;
    }

    double getWeightedQueueLength() {
        return weightedQueueLength;
    }

    double getIdleTime() {
        return idleTime;
    }

    void addProcess(Process p, double curTime) {

//        weightedQueueLength += queue.size() * (curTime - lastQueueChangeTime);
//        if (queue.size() == 0)
//            idleTime += (curTime - lastQueueChangeTime);

//        lastQueueChangeTime = curTime;

        int m = queue.size();

        for (Process process :
                queue) {
            process.subtractRemaining((curTime - process.getLastCheckTime()) / m);
            process.setLastCheckTime(curTime);
        }

        queue.add(p);

        p.setStartTime(curTime);
        p.setLastCheckTime(curTime);

    }

    double nextChange(double curTime) {
        int m = queue.size();

        if (queue.isEmpty())
            return Double.POSITIVE_INFINITY;
        else {
            double minAmount = Double.POSITIVE_INFINITY;
            for (Process process: queue) {
                process.subtractRemaining((curTime - process.getLastCheckTime()) / m);
                process.setLastCheckTime(curTime);

                if (process.getRemaining() / m < minAmount)
                    minAmount = process.getRemaining() / m;
            }

            return queue.get(0).getLastCheckTime() + minAmount;
//            return queue.get(0).getLastCheckTime() + queue.get(0).getRemaining();
        }
    }

    Process finishProcess(double curTime) {

//        weightedQueueLength += queue.size() * (curTime - lastQueueChangeTime);
//        lastQueueChangeTime = curTime;

        int m = queue.size();
        int minIndex = -1;
        double minAmount = Double.POSITIVE_INFINITY;

        for (int i = 0; i < m; i++) {
            Process process = queue.get(i);

            process.subtractRemaining((curTime - process.getLastCheckTime()) / m);
            process.setLastCheckTime(curTime);

            if (process.getRemaining() < minAmount) {
                minAmount = process.getRemaining();
                minIndex = i;
            }
        }

        Process finished = queue.get(minIndex);
        queue.remove(minIndex);

        return finished;
    }

}


