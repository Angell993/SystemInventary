package calculadoraControladorVista;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import metodosjavaClass.OperadoresLogicos;
import static metodosjavaClass.VentanaRootPane.closeVentana;


public class FXMLCalculadoraController implements Initializable {

    @FXML
    private void cerrarVentana(ActionEvent event) {
        closeVentana(event);
    }

    private String pantallaDigito, respuesta;
    private char operador;
    private double operador1 = 0, operador2 = 0;
    private final OperadoresLogicos op = new OperadoresLogicos();
    
    // botones de números de calculadora
    @FXML
    Button btn1;
    @FXML
    Button btn2;
    @FXML
    Button btn3;
    @FXML
    Button btn4;
    @FXML
    Button btn5;
    @FXML
    Button btn6;
    @FXML
    Button btn7;
    @FXML
    Button btn8;
    @FXML
    Button btn9;
    @FXML
    Button btn0;

    //Botones de operadores logicos
    @FXML
    Button btnSuma;
    @FXML
    Button btnRestar;
    @FXML
    Button btnMultiplicar;
    @FXML
    Button btnDividir;
    @FXML
    Button btnIgual;
    @FXML
    Button btnComa;
    @FXML
    Button btnBorrar;

    /*Aumentar celdas GridPane*/
    
    
    //TextFaild total
    @FXML
    TextField total;

    //metodos de cada boton
    @FXML
    private void clic_cero(ActionEvent event) {
        pantallaDigito = total.getText() + btn0.getText();
        total.setText(pantallaDigito);
    }

    @FXML
    private void clic_uno(ActionEvent event) {
        pantallaDigito = total.getText() + btn1.getText();
        total.setText(pantallaDigito);
    }

    @FXML
    private void clic_dos(ActionEvent event) {
        pantallaDigito = total.getText() + btn2.getText();
        total.setText(pantallaDigito);
    }

    @FXML
    private void clic_tres(ActionEvent event) {
        pantallaDigito = total.getText() + btn3.getText();
        total.setText(pantallaDigito);
    }

    @FXML
    private void clic_cuatro(ActionEvent event) {
        pantallaDigito = total.getText() + btn4.getText();
        total.setText(pantallaDigito);
    }

    @FXML
    private void clic_cinco(ActionEvent event) {
        pantallaDigito = total.getText() + btn5.getText();
        total.setText(pantallaDigito);
    }

    @FXML
    private void clic_seis(ActionEvent event) {
        pantallaDigito = total.getText() + btn6.getText();
        total.setText(pantallaDigito);
    }

    @FXML
    private void clic_siete(ActionEvent event) {
        pantallaDigito = total.getText() + btn7.getText();
        total.setText(pantallaDigito);
    }

    @FXML
    private void clic_ocho(ActionEvent event) {
        pantallaDigito = total.getText() + btn8.getText();
        total.setText(pantallaDigito);
    }

    @FXML
    private void clic_nueve(ActionEvent event) {
        pantallaDigito = total.getText() + btn9.getText();
        total.setText(pantallaDigito);
    }

    @FXML
    private void clic_coma(ActionEvent event) {
        // con este if estoy controlando de que si hay un punto en la cadena ya no vuelva a poner el punto
        if (!(this.total.getText().contains("."))) {
            pantallaDigito = total.getText() + btnComa.getText();
            total.setText(pantallaDigito);
        }

    }

    //metodos de operadores logicos
    @FXML
    private void clic_suma(ActionEvent event) {

        try {
            if (isNumero(total.getText())) {
                operador1 = Double.parseDouble(total.getText());
                op.setOperando1(operador1);
                total.setText("");
                operador = 's';
            } else {
                operador1 = Double.parseDouble(respuesta);
                total.setText("");
                operador = 's';
            }
        } catch (Exception e) {
            textoVacio();
            operador = 's';
        }
    }

    @FXML
    private void clic_resta(ActionEvent event) {
        try {
            if (isNumero(pantallaDigito)) {
                operador1 = Double.parseDouble(total.getText());
                op.setOperando1(operador1);
                total.setText("");
                operador = 'r';
            } else {
                operador1 = Double.parseDouble(respuesta);
                total.setText("");
                operador = 'r';
            }
        } catch (Exception e) {
            textoVacio();
            operador = 'r';
        }

    }

    @FXML
    private void clic_multiplicar(ActionEvent event) {
        try {
            if (isNumero(pantallaDigito)) {
                operador1 = Double.parseDouble(total.getText());
                op.setOperando1(operador1);
                total.setText("");
                operador = 'm';
            } else {
                operador1 = Double.parseDouble(respuesta);
                total.setText("");
                 operador = 'm';
            }
        } catch (Exception e) {
            textoVacio();
            operador = 'm';
        }

    }

    @FXML
    private void clic_dividir(ActionEvent event) {
        try {
            if (isNumero(pantallaDigito)) {
                operador1 = Double.parseDouble(total.getText());
                op.setOperando1(operador1);
                total.setText("");
                operador = 'd';
            } else {
                operador1 = Double.parseDouble(respuesta);
                total.setText("");
                operador = 'd';
            }
        } catch (Exception e) {
            textoVacio();
            operador = 'd';
        }
    }
    
    @FXML
    private void clic_borrar(ActionEvent event) {
        this.operador1 = 0;
        this.operador2 = 0;
        this.operador = ' ';
        this.pantallaDigito = " ";
        total.setText(pantallaDigito);
    }

    @FXML
    private void clic_igual(ActionEvent event) {
        if (total.getText().isEmpty()) {
            total.setText("");
        } else {
            operacionesLogicas();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //calculadoraGridPane.add(btn0, 0, 0,2,3);
    }

    private void operacionesLogicas() {
        switch (operador) {
            case 's':
                operador2 = Double.parseDouble(pantallaDigito);                
                op.setOperando2(operador2);
                total.setText(op.suma());                
                System.out.println(op.toString());
                break;
            case 'r':
                operador2 = Double.parseDouble(pantallaDigito);                
                op.setOperando2(operador2);
                total.setText(op.resta());       
                System.out.println(op.toString());
                break;
            case 'm':
                operador2 = Double.parseDouble(pantallaDigito);                
                op.setOperando2(operador2);
                total.setText(op.multiplicacion());
                System.out.println(op.toString());
                break;
            case 'd':
                operador2 = Double.parseDouble(pantallaDigito);                
                op.setOperando2(operador2);
                total.setText(op.division());
                System.out.println(op.toString());
                break;
        }
    }
    
    private void textoVacio() {
        pantallaDigito = "0";
        operador1 = Double.parseDouble(pantallaDigito);
        op.setOperando1(operador1);
        total.setText("");
    }

    private Boolean isNumero(String numero) {
        try {
            Double.parseDouble(numero);
        } catch (NumberFormatException e) {
            System.out.println("El valor ingresado no es número");
            return false;
        }
        return true;
    }
}
