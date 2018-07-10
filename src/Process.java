import java.math.BigDecimal;
import java.math.RoundingMode;

class Process {
    private double arrivalTime;

    private double duration;
    private double remaining;
    private double lastCheckTime;

    private double allocationTime; // time that memory is allocated to process
    private double startTime; // time that a server starts running this process
    private double finishTime;


    Process(double arrivalTime, double duration) {
        this.arrivalTime = arrivalTime;
        lastCheckTime = arrivalTime;

        this.duration = duration;
        this.remaining = duration;
    }


    double getArrivalTime() {
        return arrivalTime;
    }

    double getAllocationTime() {
        return allocationTime;
    }

    double getFinishTime() {
        return finishTime;
    }

    void setFinishTime(double finishTime) {
        this.finishTime = finishTime;
    }

    void setAllocationTime(double allocationTime) {
        this.allocationTime = allocationTime;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    double getDuration() {
        return duration;
    }

    public double getRemaining() {
        return remaining;
    }

    public void subtractRemaining(double amount) {
        this.remaining -= amount;
    }

    public double getLastCheckTime() {
        return lastCheckTime;
    }

    public void setLastCheckTime(double lastCheckTime) {
        this.lastCheckTime = lastCheckTime;
    }

    @Override
    public String toString() {
        return "arrival = " + round(arrivalTime, 4) +
                ", alloc time = " + round(allocationTime, 4) +
                ", start time = " + round(startTime, 3) +
                ", finish time = " + round(finishTime, 4);
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
