package com.park.smet_k.bauman_gis.utils;

import android.support.annotation.Nullable;

/**
 * Класс, позволяющий реализовать удобную отписку от получения результатов асинхронных операций.
 * Например, отписку от сетевого запроса при завершении Activity или старте другого запроса, когда
 * результат первого уже не нужен.
 */
public class ListenerHandler<T> {
    private T listener;

    ListenerHandler(final T listener) {
        this.listener = listener;
    }

    @Nullable
    T getListener() {
        return listener;
    }

    public void unregister() {
        listener = null;
    }
}
