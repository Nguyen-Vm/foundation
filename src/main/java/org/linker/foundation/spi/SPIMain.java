package org.linker.foundation.spi;

import java.util.ServiceLoader;

public class SPIMain {

    public static void main(String[] args) {
        ServiceLoader<Ishot> shots = ServiceLoader.load(Ishot.class);
        for (Ishot shot : shots) {
            shot.shot();
        }
    }
}
