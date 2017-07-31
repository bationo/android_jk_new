package com.hlct.android.bean;

/**
 * Created by lazylee on 2017/7/28.
 * EventBus 传递的时间
 *
 */

public class MessageEvent {
    private String publish;
    private String subscriber;
    private Object message;

    /**
     *
     * @param publish   事件的发布者
     * @param subscriber 事件的订阅者
     * @param message    发送的事件
     */
    public MessageEvent(String publish, String subscriber, Object message) {
        this.publish = publish;
        this.subscriber = subscriber;
        this.message = message;
    }

    public MessageEvent(Object message) {
        this.message = message;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
