/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuct.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "dog_breed")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DogBreed.findAll", query = "SELECT d FROM DogBreed d")
    , @NamedQuery(name = "DogBreed.findByIddogBreed", query = "SELECT d FROM DogBreed d WHERE d.iddogBreed = :iddogBreed")
    , @NamedQuery(name = "DogBreed.findByPhoto", query = "SELECT d FROM DogBreed d WHERE d.photo = :photo")
    , @NamedQuery(name = "DogBreed.findByName", query = "SELECT d FROM DogBreed d WHERE d.name = :name")
    , @NamedQuery(name = "DogBreed.findBySize", query = "SELECT d FROM DogBreed d WHERE d.size = :size")
    , @NamedQuery(name = "DogBreed.findByWeight", query = "SELECT d FROM DogBreed d WHERE d.weight = :weight")
    , @NamedQuery(name = "DogBreed.findByPuppy", query = "SELECT d FROM DogBreed d WHERE d.puppy = :puppy")
    , @NamedQuery(name = "DogBreed.findByLifeSpan", query = "SELECT d FROM DogBreed d WHERE d.lifeSpan = :lifeSpan")
    , @NamedQuery(name = "DogBreed.findByPrice", query = "SELECT d FROM DogBreed d WHERE d.price = :price")
    , @NamedQuery(name = "DogBreed.findByAdaptability", query = "SELECT d FROM DogBreed d WHERE d.adaptability = :adaptability")
    , @NamedQuery(name = "DogBreed.findByApartmentFriendly", query = "SELECT d FROM DogBreed d WHERE d.apartmentFriendly = :apartmentFriendly")
    , @NamedQuery(name = "DogBreed.findByBarkingTendency", query = "SELECT d FROM DogBreed d WHERE d.barkingTendency = :barkingTendency")
    , @NamedQuery(name = "DogBreed.findByCatFriendly", query = "SELECT d FROM DogBreed d WHERE d.catFriendly = :catFriendly")
    , @NamedQuery(name = "DogBreed.findByChildFriendly", query = "SELECT d FROM DogBreed d WHERE d.childFriendly = :childFriendly")
    , @NamedQuery(name = "DogBreed.findByDogFriendly", query = "SELECT d FROM DogBreed d WHERE d.dogFriendly = :dogFriendly")
    , @NamedQuery(name = "DogBreed.findByExerciseNeed", query = "SELECT d FROM DogBreed d WHERE d.exerciseNeed = :exerciseNeed")
    , @NamedQuery(name = "DogBreed.findByGrooming", query = "SELECT d FROM DogBreed d WHERE d.grooming = :grooming")
    , @NamedQuery(name = "DogBreed.findByHealthIssuse", query = "SELECT d FROM DogBreed d WHERE d.healthIssuse = :healthIssuse")
    , @NamedQuery(name = "DogBreed.findByIntelligence", query = "SELECT d FROM DogBreed d WHERE d.intelligence = :intelligence")
    , @NamedQuery(name = "DogBreed.findByPlayfulness", query = "SELECT d FROM DogBreed d WHERE d.playfulness = :playfulness")
    , @NamedQuery(name = "DogBreed.findBySheddingLevel", query = "SELECT d FROM DogBreed d WHERE d.sheddingLevel = :sheddingLevel")
    , @NamedQuery(name = "DogBreed.findByStrangerFriendly", query = "SELECT d FROM DogBreed d WHERE d.strangerFriendly = :strangerFriendly")
    , @NamedQuery(name = "DogBreed.findByTrainability", query = "SELECT d FROM DogBreed d WHERE d.trainability = :trainability")
    , @NamedQuery(name = "DogBreed.findByWatchdogAbility", query = "SELECT d FROM DogBreed d WHERE d.watchdogAbility = :watchdogAbility")})
public class DogBreed implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddog_breed")
    private Integer iddogBreed;
    @Size(max = 255)
    @Column(name = "photo")
    private String photo;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Size(max = 45)
    @Column(name = "size")
    private String size;
    @Size(max = 45)
    @Column(name = "weight")
    private String weight;
    @Size(max = 45)
    @Column(name = "puppy")
    private String puppy;
    @Size(max = 45)
    @Column(name = "life_span")
    private String lifeSpan;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private Float price;
    @Column(name = "adaptability")
    private Float adaptability;
    @Column(name = "apartment_friendly")
    private Float apartmentFriendly;
    @Column(name = "barking_tendency")
    private Float barkingTendency;
    @Column(name = "cat_friendly")
    private Float catFriendly;
    @Column(name = "child_friendly")
    private Float childFriendly;
    @Column(name = "dog_friendly")
    private Float dogFriendly;
    @Column(name = "exercise_need")
    private Float exerciseNeed;
    @Column(name = "grooming")
    private Float grooming;
    @Column(name = "health_issuse")
    private Float healthIssuse;
    @Column(name = "intelligence")
    private Float intelligence;
    @Column(name = "playfulness")
    private Float playfulness;
    @Column(name = "shedding_level")
    private Float sheddingLevel;
    @Column(name = "stranger_friendly")
    private Float strangerFriendly;
    @Column(name = "trainability")
    private Float trainability;
    @Column(name = "watchdog_ability")
    private Float watchdogAbility;
    @JoinTable(name = "temperament_dog", joinColumns = {
        @JoinColumn(name = "iddog_breed", referencedColumnName = "iddog_breed")}, inverseJoinColumns = {
        @JoinColumn(name = "idtemperament", referencedColumnName = "idtemperament")})
    @ManyToMany
    private List<Temperament> temperamentList = new ArrayList<>();

    public DogBreed() {
    }

    public DogBreed(Integer iddogBreed) {
        this.iddogBreed = iddogBreed;
    }

    public Integer getIddogBreed() {
        return iddogBreed;
    }

    public void setIddogBreed(Integer iddogBreed) {
        this.iddogBreed = iddogBreed;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPuppy() {
        return puppy;
    }

    public void setPuppy(String puppy) {
        this.puppy = puppy;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(String lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getAdaptability() {
        return adaptability;
    }

    public void setAdaptability(Float adaptability) {
        this.adaptability = adaptability;
    }

    public Float getApartmentFriendly() {
        return apartmentFriendly;
    }

    public void setApartmentFriendly(Float apartmentFriendly) {
        this.apartmentFriendly = apartmentFriendly;
    }

    public Float getBarkingTendency() {
        return barkingTendency;
    }

    public void setBarkingTendency(Float barkingTendency) {
        this.barkingTendency = barkingTendency;
    }

    public Float getCatFriendly() {
        return catFriendly;
    }

    public void setCatFriendly(Float catFriendly) {
        this.catFriendly = catFriendly;
    }

    public Float getChildFriendly() {
        return childFriendly;
    }

    public void setChildFriendly(Float childFriendly) {
        this.childFriendly = childFriendly;
    }

    public Float getDogFriendly() {
        return dogFriendly;
    }

    public void setDogFriendly(Float dogFriendly) {
        this.dogFriendly = dogFriendly;
    }

    public Float getExerciseNeed() {
        return exerciseNeed;
    }

    public void setExerciseNeed(Float exerciseNeed) {
        this.exerciseNeed = exerciseNeed;
    }

    public Float getGrooming() {
        return grooming;
    }

    public void setGrooming(Float grooming) {
        this.grooming = grooming;
    }

    public Float getHealthIssuse() {
        return healthIssuse;
    }

    public void setHealthIssuse(Float healthIssuse) {
        this.healthIssuse = healthIssuse;
    }

    public Float getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(Float intelligence) {
        this.intelligence = intelligence;
    }

    public Float getPlayfulness() {
        return playfulness;
    }

    public void setPlayfulness(Float playfulness) {
        this.playfulness = playfulness;
    }

    public Float getSheddingLevel() {
        return sheddingLevel;
    }

    public void setSheddingLevel(Float sheddingLevel) {
        this.sheddingLevel = sheddingLevel;
    }

    public Float getStrangerFriendly() {
        return strangerFriendly;
    }

    public void setStrangerFriendly(Float strangerFriendly) {
        this.strangerFriendly = strangerFriendly;
    }

    public Float getTrainability() {
        return trainability;
    }

    public void setTrainability(Float trainability) {
        this.trainability = trainability;
    }

    public Float getWatchdogAbility() {
        return watchdogAbility;
    }

    public void setWatchdogAbility(Float watchdogAbility) {
        this.watchdogAbility = watchdogAbility;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddogBreed != null ? iddogBreed.hashCode() : 0);
        return hash;
    }

    @XmlTransient
    public List<Temperament> getTemperamentList() {
        return temperamentList;
    }

    public void setTemperamentList(List<Temperament> temperamentList) {
        this.temperamentList = temperamentList;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DogBreed)) {
            return false;
        }
        DogBreed other = (DogBreed) object;
        if ((this.iddogBreed == null && other.iddogBreed != null) || (this.iddogBreed != null && !this.iddogBreed.equals(other.iddogBreed))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "thuct.dtos.DogBreed[ iddogBreed=" + iddogBreed + " ]";
    }

}
