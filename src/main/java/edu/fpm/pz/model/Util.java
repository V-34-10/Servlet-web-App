package edu.fpm.pz.model;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Util {
    public static int getID(List<Integer> list) {
        int index = ThreadLocalRandom.current().nextInt(0, list.size());
        return list.get(index);
    }
}
