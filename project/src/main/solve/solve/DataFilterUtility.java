package solve;

import options.Options;
import options.ParseOptionsException;
import parser.DataParser;
import parser.ParseDataException;
import typer.IClassify;
import typer.TypeClassifire;
import saver.Saver;
import saver.SaveDataException;
import statistics.Statistics;
import statistics.StatisticsException;


import java.nio.charset.StandardCharsets;

public class DataFilterUtility {

    public static void main(String[] args) {
        try{
            Options options = Options.fromArgs(args);
            IClassify classifire = new TypeClassifire();
            Statistics statistics = new Statistics(options.getStatisticsMode());
            Saver saver = new Saver(options.getOutputDirectory(), options.getPrefix(), options.isAppend(), StandardCharsets.UTF_8);
            DataParser pars = new DataParser(classifire, saver, statistics, StandardCharsets.UTF_8);
            pars.parser(options.getInputFiles());
            statistics.printStats();

        } catch (ParseOptionsException e) {
            System.err.println("Ошибка при считывании параметров\n" + e.getMessage());
        } catch (SaveDataException e) {
            System.err.println("Ошибка при сокранении данных\n" + e.getMessage());
        } catch (ParseDataException e){
            System.err.println("Ошибка при парсинге данных\n" + e.getMessage());
        } catch (StatisticsException e){
            System.err.println("Ошибка при выводе статистики\n" + e.getMessage());
        }
    }
}
