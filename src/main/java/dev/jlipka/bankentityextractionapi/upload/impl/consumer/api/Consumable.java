package dev.jlipka.bankentityextractionapi.upload.impl.consumer.api;

import java.nio.file.Path;
import java.util.List;

public interface Consumable<T> {
    List<T> read(Path pathToFile);
}
