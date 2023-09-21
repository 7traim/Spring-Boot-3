package med.voll.api.domain.direccion;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {

    private String urbanizacion;
    private String calle;
    private String distrito;
    private String codigopostal;
    private String complemento;
    private String numero;
    private String provincia;
    private String ciudad;

    public Direccion(DatosDireccion direccion) {
        this.urbanizacion=direccion.urbanizacion();
        this.calle=direccion.calle();
        this.distrito=direccion.distrito();
        this.codigopostal=direccion.codigopostal();
        this.complemento=direccion.complemento();
        this.numero=direccion.numero();
        this.provincia=direccion.provincia();
        this.ciudad=direccion.ciudad();

    }

    public Direccion actualizarDatos(DatosDireccion direccion) {
        this.urbanizacion=direccion.urbanizacion();
        this.calle=direccion.calle();
        this.distrito=direccion.distrito();
        this.codigopostal=direccion.codigopostal();
        this.complemento=direccion.complemento();
        this.numero=direccion.numero();
        this.provincia=direccion.provincia();
        this.ciudad=direccion.ciudad();

        return this;
    }
}
























