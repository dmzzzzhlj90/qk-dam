package com.qk.dm.dataipici;

import com.alibaba.datax.core.Engine;

public class EngineTest {
    public static void main(String[] args) {
        System.setProperty("datax.home", "/Users/daomingzhu/Documents/datax");
        String[] datxArgs = {"-job", "/Users/daomingzhu/qk-dam/qk-dam/qike-dm-dataingestion/src/main/resources/stream2stream.json", "-mode", "standalone", "-jobid", "-1"};
        try {
            //从这里启动
            Engine.entry(datxArgs);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}