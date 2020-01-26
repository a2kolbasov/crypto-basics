/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.utils.primesGenerator;

import java.math.BigInteger;

public class PrimitiveRootSearcher implements Runnable {
    private final BigInteger startPow, add, root, modulo;
    private final Thread mainThread;
    BigInteger lastPow;
    String message = "";

    private boolean isPrimitiveRoot() {
        // Функция Эйлера
        final BigInteger fi = modulo.subtract(BigInteger.ONE);
        // while (pow < modulo)
        message += "from " + startPow + ", add " + add;

        for (BigInteger pow = this.startPow; pow.compareTo(modulo) < 0; pow = pow.add(this.add)) {
            lastPow = pow;

            BigInteger remainder = root.modPow(pow, modulo);
            if (remainder.equals(BigInteger.ONE) && !pow.equals(fi))
                return false;
        }
        return true;
    }

    private PrimitiveRootSearcher(Thread mainThread, BigInteger root, BigInteger modulo, BigInteger startPow, BigInteger add) {
        this.startPow = startPow;
        this.add = add;
        this.root = root;
        this.modulo = modulo;
        this.mainThread = mainThread;
    }

    /*private Root(BigInteger root, BigInteger modulo) {
        this.startPow = this.add = BigInteger.ONE;
        this.root = root;
        this.modulo = modulo;
    }*/

    @Override
    public void run() {
        boolean result = isPrimitiveRoot();
        message += ", last " + lastPow + " - " + result;
        message = "root " + root + ", mod " + modulo + " ," + message;
        System.err.println(message);
        if (! result) {
            mainThread.interrupt();
        }
    }

    public static boolean isPrimitiveRoot(BigInteger root, BigInteger modulo) {
        final int THREADS = 256;

        Thread[] threads = new Thread[THREADS];
        for (int i = 0; i < THREADS; i++) {
            Thread thread = new Thread(
                    new PrimitiveRootSearcher(Thread.currentThread(), root, modulo, BigInteger.valueOf(i + 2), BigInteger.valueOf(THREADS))
            );
            thread.setDaemon(true);
            thread.start();
            threads[i] = thread;
        }

        try {
            for (Thread thread : threads)
                thread.join();
        } catch (InterruptedException e) {
            for (Thread thread : threads)
                thread.stop();
            System.err.println();
            return false;
        }
        System.err.println();
        return true;
    }


    public static void main(String[] args) {
        BigInteger
                mod = BigInteger.valueOf(31_020_623),
                root = BigInteger.valueOf(5);

        long time = System.currentTimeMillis();
        System.out.println(
                PrimitiveRootSearcher.isPrimitiveRoot(root, mod)
        );
        System.out.println(
                System.currentTimeMillis() - time
        );
    }
}
