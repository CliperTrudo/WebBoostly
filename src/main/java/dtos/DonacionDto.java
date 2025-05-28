package dtos;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO para representar una donación.
 *
 * Atributos:
 * - orderId: Identificador de la orden devuelto por PayPal.
 * - importe: Importe de la donación.
 * - moneda: Moneda de la donación (p.ej. "EUR").
 * - estado: Estado de la donación (p.ej. "CREATED", "CAPTURED").
 * - payerId: Identificador del pagador en PayPal.
 * - payerEmail: Correo electrónico del pagador.
 * - capturadoEn: Fecha y hora en que se capturó la donación.
 */
public class DonacionDto {

    private String orderId;
    private BigDecimal importe;
    private String moneda;
    private String estado;
    private String payerId;
    private String payerEmail;
    private Instant capturadoEn;

    /** Obtiene el identificador de la orden de PayPal. */
    public String getOrderId() {
        return orderId;
    }

    /** Establece el identificador de la orden de PayPal. */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /** Obtiene el importe de la donación. */
    public BigDecimal getImporte() {
        return importe;
    }

    /** Establece el importe de la donación. */
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    /** Obtiene la moneda de la donación. */
    public String getMoneda() {
        return moneda;
    }

    /** Establece la moneda de la donación. */
    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    /** Obtiene el estado de la donación. */
    public String getEstado() {
        return estado;
    }

    /** Establece el estado de la donación. */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /** Obtiene el identificador del pagador en PayPal. */
    public String getPayerId() {
        return payerId;
    }

    /** Establece el identificador del pagador en PayPal. */
    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    /** Obtiene el correo electrónico del pagador. */
    public String getPayerEmail() {
        return payerEmail;
    }

    /** Establece el correo electrónico del pagador. */
    public void setPayerEmail(String payerEmail) {
        this.payerEmail = payerEmail;
    }

    /** Obtiene la fecha y hora de captura de la donación. */
    public Instant getCapturadoEn() {
        return capturadoEn;
    }

    /** Establece la fecha y hora de captura de la donación. */
    public void setCapturadoEn(Instant capturadoEn) {
        this.capturadoEn = capturadoEn;
    }
}
