package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.dto.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Component
public class HorarioDeFuncionamientoClinica implements ValidadorDeConsultas{
    public void validar(DatosAgendarConsulta datos) {
        var fechaConsulta = datos.fecha();
        var domingo = DayOfWeek.SUNDAY.equals(fechaConsulta.getDayOfWeek());

        // Horario límite de apertura y cierre
        var horaApertura = LocalTime.of(7, 0);  // 07:00
        var horaCierre = LocalTime.of(19, 0);   // 19:00

        // Validar si está fuera del horario permitido
        var fueraDeHorario = fechaConsulta.toLocalTime().isBefore(horaApertura)
                || fechaConsulta.toLocalTime().isAfter(horaCierre);

        if (domingo || fueraDeHorario) {
            throw new ValidationException("El horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas");
        }
    }
}
