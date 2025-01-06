package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.dto.DatosAgendarConsulta;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.DayOfWeek;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class HorarioDeFuncionamientoClinicaTest {

    @Test
    public void testConsultaEnHorarioValido() {
        // Arrange
        HorarioDeFuncionamientoClinica validador = new HorarioDeFuncionamientoClinica();
        DatosAgendarConsulta datos = Mockito.mock(DatosAgendarConsulta.class);
        Mockito.when(datos.fecha()).thenReturn(LocalDateTime.of(2023, 1, 3, 10, 0)); // Martes 10:00 AM

        // Act & Assert
        validador.validar(datos); // No debería lanzar excepción
    }

    @Test
    public void testConsultaEnDomingo() {
        // Arrange
        HorarioDeFuncionamientoClinica validador = new HorarioDeFuncionamientoClinica();
        DatosAgendarConsulta datos = Mockito.mock(DatosAgendarConsulta.class);
        Mockito.when(datos.fecha()).thenReturn(LocalDateTime.of(2023, 1, 8, 10, 0)); // Domingo 10:00 AM

        // Act & Assert
        assertThrows(ValidationException.class, () -> validador.validar(datos),
                "El horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas");
    }

    @Test
    public void testConsultaAntesDeApertura() {
        // Arrange
        HorarioDeFuncionamientoClinica validador = new HorarioDeFuncionamientoClinica();
        DatosAgendarConsulta datos = Mockito.mock(DatosAgendarConsulta.class);
        Mockito.when(datos.fecha()).thenReturn(LocalDateTime.of(2023, 1, 3, 6, 59)); // Martes 6:59 AM

        // Act & Assert
        assertThrows(ValidationException.class, () -> validador.validar(datos),
                "El horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas");
    }

    @Test
    public void testConsultaDespuesDeCierre() {
        // Arrange
        HorarioDeFuncionamientoClinica validador = new HorarioDeFuncionamientoClinica();
        DatosAgendarConsulta datos = Mockito.mock(DatosAgendarConsulta.class);
        Mockito.when(datos.fecha()).thenReturn(LocalDateTime.of(2023, 1, 7, 20, 0)); // Sábado 20:00 (fuera del horario)

        // Act & Assert
        assertThrows(ValidationException.class, () -> validador.validar(datos),
                "El horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas");
    }

    @Test
    public void testConsultaJustoEnApertura() {
        // Arrange
        HorarioDeFuncionamientoClinica validador = new HorarioDeFuncionamientoClinica();
        DatosAgendarConsulta datos = Mockito.mock(DatosAgendarConsulta.class);
        Mockito.when(datos.fecha()).thenReturn(LocalDateTime.of(2023, 1, 3, 7, 0)); // Martes 7:00 AM

        // Act & Assert
        validador.validar(datos); // No debería lanzar excepción
    }

    @Test
    public void testConsultaJustoEnCierre() {
        // Arrange
        HorarioDeFuncionamientoClinica validador = new HorarioDeFuncionamientoClinica();
        DatosAgendarConsulta datos = Mockito.mock(DatosAgendarConsulta.class);
        Mockito.when(datos.fecha()).thenReturn(LocalDateTime.of(2023, 1, 3, 19, 0)); // Martes 19:00 PM

        // Act & Assert
        validador.validar(datos); // No debería lanzar excepción
    }


    @Test
    public void testConsultaJustoDespuesDeCierre() {
        HorarioDeFuncionamientoClinica validador = new HorarioDeFuncionamientoClinica();
        DatosAgendarConsulta datos = Mockito.mock(DatosAgendarConsulta.class);
        Mockito.when(datos.fecha()).thenReturn(LocalDateTime.of(2023, 1, 9, 19, 1)); // Lunes 19:01

        assertThrows(ValidationException.class, () -> validador.validar(datos),
                "La cita justo después del cierre no debería ser válida.");
    }
}
