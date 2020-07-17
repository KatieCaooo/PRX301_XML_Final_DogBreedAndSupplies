/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thu.dtos;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author katherinecao
 */
@Entity
@Table(name = "temperament")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Temperament.findAll", query = "SELECT t FROM Temperament t")
    , @NamedQuery(name = "Temperament.findByIdtemperament", query = "SELECT t FROM Temperament t WHERE t.idtemperament = :idtemperament")
    , @NamedQuery(name = "Temperament.findByContent", query = "SELECT t FROM Temperament t WHERE t.content = :content")})
public class Temperament implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtemperament")
    private Integer idtemperament;
    @Size(max = 45)
    @Column(name = "content")
    private String content;
    @ManyToMany(mappedBy = "temperamentCollection")
    private Collection<DogBreed> dogBreedCollection;

    public Temperament() {
    }

    public Temperament(Integer idtemperament) {
        this.idtemperament = idtemperament;
    }

    public Integer getIdtemperament() {
        return idtemperament;
    }

    public void setIdtemperament(Integer idtemperament) {
        this.idtemperament = idtemperament;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @XmlTransient
    public Collection<DogBreed> getDogBreedCollection() {
        return dogBreedCollection;
    }

    public void setDogBreedCollection(Collection<DogBreed> dogBreedCollection) {
        this.dogBreedCollection = dogBreedCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtemperament != null ? idtemperament.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Temperament)) {
            return false;
        }
        Temperament other = (Temperament) object;
        if ((this.idtemperament == null && other.idtemperament != null) || (this.idtemperament != null && !this.idtemperament.equals(other.idtemperament))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "thu.dtos.Temperament[ idtemperament=" + idtemperament + " ]";
    }
    
}
