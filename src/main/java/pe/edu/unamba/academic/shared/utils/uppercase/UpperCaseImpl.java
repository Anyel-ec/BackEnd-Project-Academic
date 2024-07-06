package pe.edu.unamba.academic.shared.utils.uppercase;

public class UpperCaseImpl implements UpperCase {
    public String upperCase(String texto){
        if (texto == null) {
            return "";
        }
        return texto.toUpperCase();
    }
}
