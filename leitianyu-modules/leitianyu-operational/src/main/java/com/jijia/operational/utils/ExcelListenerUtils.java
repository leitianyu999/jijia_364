package com.jijia.operational.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class ExcelListenerUtils {

    public static <T> AnalysisEventListener<T> getListener(Consumer<List<T>> consumer,int threshold) {
        return new AnalysisEventListener<T>() {
            private LinkedList<T> linkedList = new LinkedList<T>();

            @Override
            public void invoke(T t, AnalysisContext analysisContext) {
                linkedList.add(t);
                if (linkedList.size() == threshold) {
                    consumer.accept(linkedList);
                    linkedList = null;
                    linkedList = new LinkedList<T>();
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                if (linkedList.size() > 0) {
                    consumer.accept(linkedList);
                }
            }
        };
    }


    public static <T> AnalysisEventListener<T> getListener(Consumer<List<T>> consumer) {
        return getListener(consumer, 50);
    }


}
