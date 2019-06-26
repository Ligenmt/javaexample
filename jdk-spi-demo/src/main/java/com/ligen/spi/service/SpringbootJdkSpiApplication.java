package com.ligen.spi.service;


import java.util.Iterator;
import java.util.ServiceLoader;


public class SpringbootJdkSpiApplication {

    public static void main(String[] args) {
        ServiceLoader<SpiService> spiLoader = ServiceLoader.load(SpiService.class);
        Iterator<SpiService> iterator = spiLoader.iterator();
        while (iterator.hasNext()) {
            SpiService next = iterator.next();
            next.sayHelllo();
        }
    }
}
