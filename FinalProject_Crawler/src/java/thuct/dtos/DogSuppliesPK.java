/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuct.dtos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author katherinecao
 */
@Embeddable
public class DogSuppliesPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "iddog_supplies")
    private int iddogSupplies;
    @Basic(optional = false)
    @NotNull
    @Column(name = "category")
    private int category;

    public DogSuppliesPK() {
    }

    public DogSuppliesPK(int iddogSupplies, int category) {
        this.iddogSupplies = iddogSupplies;
        this.category = category;
    }

    public int getIddogSupplies() {
        return iddogSupplies;
    }

    public void setIddogSupplies(int iddogSupplies) {
        this.iddogSupplies = iddogSupplies;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) iddogSupplies;
        hash += (int) category;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DogSuppliesPK)) {
            return false;
        }
        DogSuppliesPK other = (DogSuppliesPK) object;
        if (this.iddogSupplies != other.iddogSupplies) {
            return false;
        }
        if (this.category != other.category) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "thuct.dtos.DogSuppliesPK[ iddogSupplies=" + iddogSupplies + ", category=" + category + " ]";
    }
    
}
