package com.ligen.spi.service;

public class RmiSpiService implements SpiService {
    @Override
    public void sayHelllo() {
        System.out.println("实现rmi服务");
    }
}
