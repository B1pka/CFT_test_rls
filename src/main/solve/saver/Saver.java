package saver;

import typer.DataType;

import java.io.*;
import java.nio.charset.Charset;
import java.util.EnumMap;
import java.util.Map;

public class Saver implements ISaver {

    private final File dir;
    private final String prefix;
    private final boolean append;
    private final Charset charset;

    private final Map<DataType, BufferedWriter> writers = new EnumMap<DataType, BufferedWriter>(DataType.class);
    private final Map<DataType, Boolean> disabled = new EnumMap<DataType, Boolean>(DataType.class);

    public Saver(String outputDirectory, String prefix, boolean append, Charset charset) throws SaveDataException{
        this.dir = new File(outputDirectory == null ? "." : outputDirectory);
        this.prefix = (prefix == null) ? "" : prefix;
        this.append = append;
        this.charset = charset;

        if (!dir.exists() && !dir.mkdirs()) {
            throw new SaveDataException("Невозможно создать директорию: " + dir.getAbsolutePath());
        }
        if (!dir.isDirectory()) {
            throw new SaveDataException("Не является директорией: " + dir.getAbsolutePath());
        }

        for (DataType dataType: DataType.values()) disabled.put(dataType, Boolean.FALSE);
    }

    @Override
    public void save(DataType dataType, String value) throws SaveDataException{
        if(disabled.get(dataType)) return;

        BufferedWriter writer = writers.get(dataType);
        try{
            if(writer == null){
                writer = open(dataType);
                writers.put(dataType, writer);
            }
            writer.write(value);
            writer.newLine();
        } catch (IOException e) {
            disabled.put(dataType, Boolean.TRUE);
            closeWriter(writers.remove(dataType));
            throw new SaveDataException("Ошибка записи для " + dataType + ": " + e.getMessage(), e);
        }
    }
    private BufferedWriter open(DataType dataType) throws FileNotFoundException {
        String name;
        switch(dataType){
            case INT:
                name = "integers.txt";
                break;

            case FLOAT:
                name = "floats.txt";
                break;

            case STRING:
                name = "strings.txt";
                break;

            default: throw new IllegalArgumentException("Неизвестный тип данных");
        }
        File file = new File(dir, prefix + name);

        OutputStream outputStream = new FileOutputStream(file, append);
        Writer writer = new OutputStreamWriter(outputStream, charset);
        return new BufferedWriter(writer, 64 * 1024);
    }

    private static void closeWriter(Writer writer) {
        if (writer == null) return;
        try { writer.close(); } catch (IOException ignored) {}
    }

    @Override
    public void close() throws IOException {
        IOException first = null;
        for (BufferedWriter w : writers.values()) {
            try { w.close(); } catch (IOException e) { if (first == null) first = e; }
        }
        writers.clear();
        if (first != null) throw first;
    }

}
