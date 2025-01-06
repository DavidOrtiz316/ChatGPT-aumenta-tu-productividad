package med.voll.api.domain.paciente.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import med.voll.api.domain.direccion.DatosDireccion;


public record DatosRegistroPaciente(
        @NotBlank
        String nombre,
        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 0, max = 15)
        String telefono,

        @Pattern(regexp = "^[1-9]\\d{5,9}$", message = "El documento debe tener entre 6 y 10 dígitos.")
        @NotBlank
        String documento,

        @NotNull @Valid DatosDireccion direccion) {
}
