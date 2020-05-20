package metodosjavaClass;


public class OperadoresLogicos {

    private double operando1 = 0, operando2 = 0;
    private double resultado;

    public void setOperando1(double operando1) {
        this.operando1 = operando1;
    }

    public void setOperando2(double operando2) {
        this.operando2 = operando2;
    }

    public String suma() {
        resultado = operando1 + operando2;
        return quitardecimal(resultado);
    }

    public String resta() {
        resultado = operando1 - operando2;
        return quitardecimal(resultado);
    }

    public String multiplicacion() {

        if (!isCero(operando1, operando2)) {
            resultado = operando1 * operando2;
        } else {
            resultado = 0;
        }
        return quitardecimal(resultado);
    }

    public String division() {
        if(!isCero(operando1, operando2)) {
            resultado = operando1 / operando2;
        }else{
            resultado = 0; 
        }
        return quitardecimal(resultado);
    }

    private Boolean isCero(double num1, double num2) {
        if (num1 == 0 || num2 == 0) {
            return true;
        }
        return false;
    }
    
    //Con este metodo quitamos los decimales cuando un número de como resultado 14.0 y si da lo contrario se deja el decimal
    private String quitardecimal(Double resul){
        String retorno="";
        retorno = Double.toString(resul);
        if(resul % 1 == 0){
            retorno = retorno.substring(0, retorno.length()-2);
        }
        return retorno;
    }

    @Override
    public String toString() {
        return "OperadoresLogicos{" + "operando1=" + operando1 + ", operando2=" + operando2 + ", resultado=" + resultado + '}';
    }

}
