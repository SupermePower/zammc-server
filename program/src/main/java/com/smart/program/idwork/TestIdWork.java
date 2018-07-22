package com.smart.program.idwork;


import org.junit.Test;

/**
 * idwork
 * Created by baihe
 * ON 2018/4/17.
 */
public class TestIdWork {

    @Test
    public void testId(){
        DefaultIdWorker defaultIdWorker = new DefaultIdWorker();
        long id = defaultIdWorker.nextId();
        System.out.println(id);
    }
}
