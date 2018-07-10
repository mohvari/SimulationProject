import java.util.Random;

class RandGen {
    static double normal(double mu, double sigma) {
        Random random = new Random();
        double a = random.nextDouble();
        double b = random.nextDouble();

        return mu + sigma * Math.sqrt(-2 * Math.log(a)) * Math.cos(2 * Math.PI * b);
    }

    static double exp(double param) { // param = 1 / mean
        Random random = new Random();
        double a = random.nextDouble();

        return - Math.log(1 - a) * param;
    }

    static double poisson(double alpha) {
        int n = 0;
        double p = 1.;
        double r;
        Random random = new Random();


        while (true) {
            r = random.nextDouble();
            p *= r;

            if (p < Math.pow(Math.E, - alpha))
                return n;

            n++;
        }


    }
}
