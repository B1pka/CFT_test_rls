package parser;

import typer.IClassify;
import typer.DataType;
import saver.Saver;
import statistics.Statistics;
import saver.SaveDataException;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

public class DataParser implements IPars {

    private final IClassify classifire;
    private final Saver saver;
    private final Statistics stats;
    private final Charset charset;

    public DataParser(IClassify classifire, Saver saver, Statistics stats, Charset charset) throws ParseDataException{
        this.classifire = classifire;
        this.saver = saver;
        this.stats = stats;
        this.charset = charset;
    }

    @Override
    public void parser(List<String> inputFiles) throws ParseDataException{
        for(String path : inputFiles){
            File file = new File(path);
            if(!file.isFile()){
                System.err.println("Не является файлом: " + path);
                continue;
            }
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), charset),
                    64 * 1024)) {
                String line;
                while((line = reader.readLine()) != null){
                    if(line.trim().isEmpty()) continue;
                    String raw = line.trim();

                    DataType type = classifire.classify(raw);

                    try{
                        saver.save(type, raw);
                        stats.statsCounter(type, raw);
                    } catch (SaveDataException e) {
                        System.err.println("Ошибка записи " + type + "; " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.err.println("Ошибка чтения: " + path + "; " + e.getMessage());
            }
        }
    }
}
