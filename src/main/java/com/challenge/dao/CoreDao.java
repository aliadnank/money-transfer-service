package com.challenge.dao;

import lombok.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface CoreDao<T> {

    void insert(@NonNull final UUID id, @NonNull T account);

    Optional<T> find(@NonNull final UUID id);

    void update(@NonNull final UUID id, @NonNull final T account);
}
