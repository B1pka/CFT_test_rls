package options;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import statistics.StatsMode;

public class Options {

    private String outputDirectory = ".";
    private String prefix = "";
    private boolean append = false;
    private StatsMode statMode = StatsMode.SHORT;

    private final List<String> inputFiles = new ArrayList<>();

    public static Options fromArgs(String[] args) throws ParseOptionsException {
        Options config = new Options();
        config.parseArguments(args);

        if (config.inputFiles.isEmpty()) {
            throw new ParseOptionsException("Ошибка: не указаны входные файлы.");
        }
        try {
            config.validateInputFiles(config.inputFiles);
        } catch (ParseOptionsException e) {
            throw new ParseOptionsException(e.getMessage(), e);
        }

        try {
            config.validateOutputDirectory(config.outputDirectory);
        } catch (IllegalArgumentException e) {
            throw new ParseOptionsException(e.getMessage(), e);
        }

        return config;
    }

    private void parseArguments(String[] args) throws ParseOptionsException {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            switch (arg) {
                case "-o":
                    if (i + 1 < args.length) {
                        outputDirectory = args[++i];
                    } else {
                        throw new ParseOptionsException("Ошибка: путь к выходной директории не указан для опции -o");
                    }
                    break;
                case "-p":
                    if (i + 1 < args.length) {
                        prefix = args[++i];
                    } else {
                        throw new ParseOptionsException("Ошибка: префикс не указан для опции -p");
                    }
                    break;
                case "-a":
                    append = true;
                    break;
                case "-s":
                    statMode = StatsMode.SHORT;
                    break;
                case "-f":
                    statMode = StatsMode.FULL;
                    break;
                default:
                    inputFiles.add(arg);
                    break;
            }
        }
    }

    private void validateInputFiles(List<String> inputFiles) throws ParseOptionsException {
        for (String filePath : inputFiles) {
            validateInputFile(filePath);
        }
    }

    private void validateInputFile(String filePath) throws ParseOptionsException {
        File file = new File(filePath);

        if (!file.exists()) {
            throw new ParseOptionsException("Ошибка: файл не существует: " + filePath);
        }
    }



    private void validateOutputDirectory(String outputDirectory) {
        File dir = new File(outputDirectory);

        if (!dir.exists()) {
            throw new IllegalArgumentException("Ошибка: указанный путь для выходных файлов не существует: " + outputDirectory);
        }

        if (!dir.isDirectory()) {
            throw new IllegalArgumentException("Ошибка: указанный путь не является директорией: " + outputDirectory);
        }
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isAppend() {
        return append;
    }

    public StatsMode getStatisticsMode() {
        return statMode;
    }

    public List<String> getInputFiles() {
        return inputFiles;
    }
}