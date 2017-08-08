package com.hlct.android.uhf;

/**
 * Created by lazylee on 2017/7/19.
 */

public class EPC {
    private int id;
    private String epc;
    private int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEpc() {
        return epc;
    }

    public void setEpc(String epc) {
        this.epc = epc;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "EPC{" +
                "id=" + id +
                ", epc='" + epc + '\'' +
                ", count=" + count +
                '}';
    }
}
