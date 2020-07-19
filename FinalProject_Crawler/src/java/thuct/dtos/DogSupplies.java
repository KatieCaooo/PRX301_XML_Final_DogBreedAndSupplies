/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuct.dtos;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kloecao
 */
@Entity
@Table(name = "dog_supplies")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DogSupplies.findAll", query = "SELECT d FROM DogSupplies d")
    , @NamedQuery(name = "DogSupplies.findByIddogSupplies", query = "SELECT d FROM DogSupplies d WHERE d.dogSuppliesPK.iddogSupplies = :iddogSupplies")
    , @NamedQuery(name = "DogSupplies.findByPhoto", query = "SELECT d FROM DogSupplies d WHERE d.photo = :photo")
    , @NamedQuery(name = "DogSupplies.findByName", query = "SELECT d FROM DogSupplies d WHERE d.name = :name")
    , @NamedQuery(name = "DogSupplies.findByContent", query = "SELECT d FROM DogSupplies d WHERE d.content = :content")
    , @NamedQuery(name = "DogSupplies.findBySize", query = "SELECT d FROM DogSupplies d WHERE d.size = :size")
    , @NamedQuery(name = "DogSupplies.findByPrice", query = "SELECT d FROM DogSupplies d WHERE d.price = :price")
    , @NamedQuery(name = "DogSupplies.findByCategory", query = "SELECT d FROM DogSupplies d WHERE d.dogSuppliesPK.category = :category")})
public class DogSupplies implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DogSuppliesPK dogSuppliesPK;
    @Size(max = 255)
    @Column(name = "photo")
    private String photo;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Size(max = 255)
    @Column(name = "content")
    private String content;
    @Size(max = 100)
    @Column(name = "size")
    private String size;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private Float price;
    @JoinColumn(name = "category", referencedColumnName = "idcategory", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Category category1;

    public DogSupplies() {
    }

    public DogSupplies(DogSuppliesPK dogSuppliesPK) {
        this.dogSuppliesPK = dogSuppliesPK;
    }

    public DogSupplies(int iddogSupplies, int category) {
        this.dogSuppliesPK = new DogSuppliesPK(iddogSupplies, category);
    }

    public DogSuppliesPK getDogSuppliesPK() {
        return dogSuppliesPK;
    }

    public void setDogSuppliesPK(DogSuppliesPK dogSuppliesPK) {
        this.dogSuppliesPK = dogSuppliesPK;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Category getCategory1() {
        return category1;
    }

    public void setCategory1(Category category1) {
        this.category1 = category1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dogSuppliesPK != null ? dogSuppliesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DogSupplies)) {
            return false;
        }
        DogSupplies other = (DogSupplies) object;
        if ((this.dogSuppliesPK == null && other.dogSuppliesPK != null) || (this.dogSuppliesPK != null && !this.dogSuppliesPK.equals(other.dogSuppliesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "thuct.dtos.DogSupplies[ dogSuppliesPK=" + dogSuppliesPK + " ]";
    }
    
}
