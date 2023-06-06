/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2.Model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author jomav
 */
@Entity
@Table(name = "TBLS_JUGADOR")
@NamedQueries({
    @NamedQuery(name = "Jugador.findAll", query = "SELECT j FROM Jugador j"),
    @NamedQuery(name = "Jugador.findByJrNombre", query = "SELECT j FROM Jugador j WHERE j.jrNombre = :jrNombre"),
    @NamedQuery(name = "Jugador.findByJrContrasena", query = "SELECT j FROM Jugador j WHERE j.jrContrasena = :jrContrasena"),
    @NamedQuery(name = "Jugador.findByJrNivelesganados", query = "SELECT j FROM Jugador j WHERE j.jrNivelesganados = :jrNivelesganados"),
    @NamedQuery(name = "Jugador.findByJrNivelguardado", query = "SELECT j FROM Jugador j WHERE j.jrNivelguardado = :jrNivelguardado"),
    @NamedQuery(name = "Jugador.findByJrId", query = "SELECT j FROM Jugador j WHERE j.jrId = :jrId"),
    @NamedQuery(name = "Jugador.findByJrNivelrespaldo", query = "SELECT j FROM Jugador j WHERE j.jrNivelrespaldo = :jrNivelrespaldo")})
public class Jugador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "JR_NOMBRE")
    private String jrNombre;
    @Basic(optional = false)
    @Column(name = "JR_CONTRASENA")
    private String jrContrasena;
    @Basic(optional = false)
    @Column(name = "JR_NIVELESGANADOS")
    private short jrNivelesganados;
    @Column(name = "JR_NIVELGUARDADO")
    private String jrNivelguardado;
    @Id
    @Basic(optional = false)
    @Column(name = "JR_ID")
    private Integer jrId;
    @Column(name = "JR_NIVELRESPALDO")
    private String jrNivelrespaldo;

    public Jugador() {
    }

    public Jugador(Integer jrId) {
        this.jrId = jrId;
    }

    public Jugador(String jrNombre, String jrContrasena, short jrNivelesganados) {
        this.jrId = jrId;
        this.jrNombre = jrNombre;
        this.jrContrasena = jrContrasena;
        this.jrNivelesganados = jrNivelesganados;
    }

    public String getJrNombre() {
        return jrNombre;
    }

    public void setJrNombre(String jrNombre) {
        this.jrNombre = jrNombre;
    }

    public String getJrContrasena() {
        return jrContrasena;
    }

    public void setJrContrasena(String jrContrasena) {
        this.jrContrasena = jrContrasena;
    }

    public short getJrNivelesganados() {
        return jrNivelesganados;
    }

    public void setJrNivelesganados(short jrNivelesganados) {
        this.jrNivelesganados = jrNivelesganados;
    }

    public String getJrNivelguardado() {
        return jrNivelguardado;
    }

    public void setJrNivelguardado(String jrNivelguardado) {
        this.jrNivelguardado = jrNivelguardado;
    }

    public Integer getJrId() {
        return jrId;
    }

    public void setJrId(Integer jrId) {
        this.jrId = jrId;
    }

    public String getJrNivelrespaldo() {
        return jrNivelrespaldo;
    }

    public void setJrNivelrespaldo(String jrNivelrespaldo) {
        this.jrNivelrespaldo = jrNivelrespaldo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jrId != null ? jrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jugador)) {
            return false;
        }
        Jugador other = (Jugador) object;
        if ((this.jrId == null && other.jrId != null) || (this.jrId != null && !this.jrId.equals(other.jrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto2.Model.Jugador[ jrId=" + jrId + " ]";
    }
    
}
