package proyecto2.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.scene.control.DatePicker;

public class Formato {

    public Formato() {
    }

    public boolean validarCampoTexto(String campo, int tamanoMaximo) {
        if (campo == null || campo.isEmpty()) {
            return false;
        }
        if (campo.length() > tamanoMaximo) {
            return false;
        }
        if (!campo.matches("[A-Za-z0-9\\s]+")) {
            return false;
        }
        return true;
    }

    public boolean validarCampoNumero(String campo, int tamanoMaximo) {
        if (campo == null || campo.isEmpty()) {
            return false;
        }
        if (campo.length() > tamanoMaximo) {
            return false;
        }
        if (!campo.matches("\\d+")) {
            return false;
        }
        return true;
    }

    public boolean validarCampoCorreo(String campo, int tamanoMaximo) {
        if (campo == null || campo.isEmpty()) {
            return false;
        }
        if (campo.length() > tamanoMaximo) {
            return false;
        }
        if (!campo.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")) {
            return false;
        }
        return true;
    }

    public boolean validarCampoFecha(DatePicker boxFechaNac) {
        if (boxFechaNac != null) {
            LocalDate localDate = boxFechaNac.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);
            Date fechaActual = new Date();
            if (date.after(fechaActual)) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean validarCampoFechaCita(DatePicker boxFechaNac) {
        if (boxFechaNac != null) {
            LocalDate localDate = boxFechaNac.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);
            Date fechaActual = new Date();
            if (date.before(fechaActual)) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
