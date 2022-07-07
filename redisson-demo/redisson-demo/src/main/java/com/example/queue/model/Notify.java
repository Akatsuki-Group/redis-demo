package com.example.queue.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yuancetian
 */
@Data
public class Notify<T> implements Serializable {
    private static final long serialVersionUID = -2607408744190906865L;
    private T obj;
    private long notifyId;
    private long delayTime;
    private Date nextTime;
}