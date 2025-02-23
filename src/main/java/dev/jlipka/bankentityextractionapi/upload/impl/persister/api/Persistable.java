package dev.jlipka.bankentityextractionapi.upload.impl.persister.api;

import java.util.List;

public interface Persistable<T> {
    List<T> persist(List<T> entities);

}
