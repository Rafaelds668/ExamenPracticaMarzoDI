package com.example.examenpracticadi.Controller;

import com.example.examenpracticadi.Domain.Usuario;
import com.example.examenpracticadi.Domain.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private ChoiceBox<String> ChoiceSexo;
    @FXML
    private TextField txtPeso;
    @FXML
    private TextField txtEdad;
    @FXML
    private TextField txtTalla;
    @FXML
    private ChoiceBox<String> choiceActividad;
    @FXML
    private TextArea txtObservacion;
    @FXML
    private Label labelInfo;
    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnDescargar;

    private ObservableList<Usuario> observableList;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> datos = FXCollections.observableArrayList();
        datos.addAll("Hombre", "Mujer");
        ChoiceSexo.setItems(datos);

        ObservableList<String> tipos = FXCollections.observableArrayList();
        tipos.addAll("Sedentario", "Moderado", "Activo", "Muy Activo");
        choiceActividad.setItems(tipos);
    }

    @FXML
    public void guardar(ActionEvent actionEvent) {
        if (txtNombre.getText().isEmpty() || ChoiceSexo.getValue() == null || txtPeso.getText().isEmpty() ||
                txtTalla.getText().isEmpty() || txtEdad.getText().isEmpty() || choiceActividad.getValue() == null ||
                txtObservacion.getText().isEmpty()) {
            labelInfo.setText("Error, no ha introducido todos los campos");
        }else {
            String nombreCliente = txtNombre.getText();
            String sexoCliente = ChoiceSexo.getValue().toString();
            Double pesoCliente = Double.parseDouble(txtPeso.getText());
            Double tallaCliente = Double.parseDouble(txtTalla.getText());
            Integer edadCliente = Integer.valueOf(txtEdad.getText());
            String tipoActividad = choiceActividad.getValue().toString();
            Double metabolismoBasal;//Ger
            Double metatabolismoBasalEnergetico; //GET

            // Primer calculo segun el sexo
            if (sexoCliente == "Hombre") {
                metabolismoBasal = 66.473 + 13.751 * pesoCliente + 5.0033 * tallaCliente - 6.755 * edadCliente;
                if (tipoActividad == "Sedentario") {
                    metatabolismoBasalEnergetico  = metabolismoBasal * 1.3;
                } else if (tipoActividad == "Moderado") {
                    metatabolismoBasalEnergetico  = metabolismoBasal * 1.6;
                } else if (tipoActividad == "Activo") {
                    metatabolismoBasalEnergetico  = metabolismoBasal * 1.7;
                } else {
                    metatabolismoBasalEnergetico  = metabolismoBasal * 2.1;
                }
            } else {
                metabolismoBasal = 655.0955 + 9.463 * pesoCliente + 1.8496 * tallaCliente - 4.6756 * edadCliente;

                if (tipoActividad == "Sedentario") {
                    metatabolismoBasalEnergetico = metabolismoBasal * 1.3;
                } else if (tipoActividad == "Moderado") {
                    metatabolismoBasalEnergetico = metabolismoBasal * 1.5;
                } else if (tipoActividad == "Activo") {
                    metatabolismoBasalEnergetico = metabolismoBasal * 1.6;
                } else {
                    metatabolismoBasalEnergetico = metabolismoBasal * 1.9;
                }
            }

            labelInfo.setText("El cliente " + nombreCliente + " tiene un GER de " + metabolismoBasal + " y un GET de " + metatabolismoBasalEnergetico);
        }
    }

    @FXML
    public void descargar(ActionEvent actionEvent) throws SQLException, JRException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/informacioncliente", "root", "");
        JasperPrint jasperPrint = JasperFillManager.fillReport("datos_usuario.jasper", null, connection);

        //Mostrar el inform en una ventana
        JasperViewer.viewReport(jasperPrint, false);

        JRPdfExporter exp = new JRPdfExporter();
        exp.setExporterInput(new SimpleExporterInput(jasperPrint));
        exp.setExporterOutput(new SimpleOutputStreamExporterOutput("datos_Usuario.pdf"));
        exp.setConfiguration(new SimplePdfReportConfiguration());
        exp.exportReport();

    }


}