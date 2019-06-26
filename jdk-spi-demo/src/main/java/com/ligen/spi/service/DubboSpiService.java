package com.ligen.spi.service;

public class DubboSpiService implements SpiService {
    @Override
    public void sayHelllo() {
        System.out.println("实现dubbo服务");
    }
}
