package com.challenge.dao;

import lombok.NonNull;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractDao<T> implements CoreDao<T> {
    protected final Map<UUID, T> ds = new ConcurrentHashMap<>();

    @Override
    public void insert(@NonNull final UUID id, @NonNull final T object) {
        ds.put(id, object);
    }

    @Override
    public Optional<T> find(@NonNull final UUID  id) {
        return Optional.ofNullable(ds.get(id));
    }

    @Override
    public void update(@NonNull final UUID id, @NonNull final T object)  {
        ds.replace(id,object);
    }
}
