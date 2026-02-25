package saver;

import typer.DataType;

import java.io.IOException;

public interface ISaver extends AutoCloseable {

    void save(DataType dataType, String value) throws SaveDataException;

    @Override
    default void close() throws IOException {

    }
}
