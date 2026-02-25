package statistics;

import typer.DataType;

public class Statistics {

    private long intCount = 0;
    private long floatCount = 0;
    private long stringCount = 0;

    private long intSum = 0;
    private Integer intMin = null;
    private Integer intMax = null;

    private double floatSum = 0.0;
    private Float floatMin = null;
    private Float floatMax = null;

    private Integer minLen = null;
    private Integer maxLen = null;

    private final StatsMode mode;

    public Statistics(StatsMode mode) {
        this.mode = mode;
    }

    public void statsCounter(DataType dataType, String str){

        switch(dataType){
            case INT:
                intCount++;
                if(mode == StatsMode.FULL){
                    int i = Integer.parseInt(str);
                    intSum += i;
                    intMin = (intMin == null) ? i : Math.min(intMin, i);
                    intMax = (intMax == null) ? i: Math.max(intMax, i);
                }
                break;

            case FLOAT:
                floatCount++;
                if(mode == StatsMode.FULL){
                    float f = Float.parseFloat(str);
                    floatSum += f;
                    floatMin = (floatMin == null) ? f : Math.min(floatMin, f);
                    floatMax = (floatMax == null) ? f : Math.max(floatMax, f);
                }
                break;

            case STRING:
                stringCount++;
                if(mode == StatsMode.FULL){
                    int len = str.length();
                    minLen = (minLen == null) ? len : Math.min(minLen, len);
                    maxLen = (maxLen == null) ? len : Math.max(maxLen, len);
                }
                break;
        }
    }

    public void printStats() throws StatisticsException{

        System.out.println("== Statistics ==");

        if (mode == StatsMode.SHORT) {
            System.out.println("integers: " + intCount);
            System.out.println("floats:   " + floatCount);
            System.out.println("strings:  " + stringCount);
            return;
        }

        System.out.println("-- integers --");
        System.out.println("count: " + intCount);
        if (intCount > 0) {
            System.out.println("min: " + intMin);
            System.out.println("max: " + intMax);
            System.out.println("sum: " + intSum);
            System.out.println("avg: " + ((double) intSum / (double) intCount));
        }

        System.out.println("-- floats --");
        System.out.println("count: " + floatCount);
        if (floatCount > 0) {
            System.out.println("min: " + floatMin);
            System.out.println("max: " + floatMax);
            System.out.println("sum: " + floatSum);
            System.out.println("avg: " + (floatSum / (double) floatCount));
        }

        System.out.println("-- strings --");
        System.out.println("count: " + stringCount);
        if (stringCount > 0) {
            System.out.println("minLen: " + minLen);
            System.out.println("maxLen: " + maxLen);
        }
    }
}
