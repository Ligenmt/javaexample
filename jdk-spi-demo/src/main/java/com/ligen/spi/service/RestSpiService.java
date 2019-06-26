package com.ligen.spi.service;

public class RestSpiService implements SpiService {
    @Override
    public void sayHelllo() {
        System.out.println("实现rest服务");
    }
}
