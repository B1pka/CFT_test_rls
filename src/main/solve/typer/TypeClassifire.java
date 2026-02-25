package typer;

public class TypeClassifire implements IClassify {

    @Override
    public DataType classify(String value) {
        if(isInteger(value)) return DataType.INT;
        if(isFloat(value)) return DataType.FLOAT;
        return DataType.STRING;
    }

    private boolean isInteger(String value){
        try{
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
    private boolean isFloat(String value){
        try{
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
