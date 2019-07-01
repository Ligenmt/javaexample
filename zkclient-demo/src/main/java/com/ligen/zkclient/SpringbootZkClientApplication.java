package com.ligen.zkclient;


import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class SpringbootZkClientApplication {

    static ZkClient zkClient;

    public static void main(String[] args) throws InterruptedException {

        zkClient = new ZkClient("192.168.0.91:2181", Integer.MAX_VALUE);
        listen();
        zkClient.createPersistent("/super", "1234");
        Thread.sleep(1000L);
        zkClient.writeData("/super", "456", -1);
        Thread.sleep(1000L);
        zkClient.delete("/super");
        Thread.sleep(1000L);
    }

    public static void listen() {
        //监听数据的变化
        zkClient.subscribeDataChanges("/super", new IZkDataListener() {
            @Override
            public void handleDataChange(String path, Object data) throws Exception {
                System.out.println("更新的节点为" + path);
                System.out.println("更新的数据为" + data);
            }

            @Override
            public void handleDataDeleted(String path) throws Exception {
                System.out.println("删除的节点为" + path);
            }
        });

    }
}
