package com.humane.application;


import java.io.Serializable;
import java.time.LocalDate;

/**
 * A entity object, like in any other Java application. In a typical real world
 * application this could for example be a JPA entity.
 */
@SuppressWarnings("serial")
public class Animal implements Serializable, Cloneable {

    private Long id;

    private String name = "";

    private LocalDate birthDate;

    private AnimalStatus status;

    private AnimalType type;

    private DogBreed breed;

    private int weight;

    private Gender gender;

    private Color color;

    private Spayed spayed;

    


    public Animal() {


    }

    
    public Animal(Animal animal) {
        this.id = animal.getId();
        this.name = animal.getName();
        this.birthDate = animal.getBirthDate();
        this.status = animal.getStatus();
        this.breed = animal.getBreed();
        this.weight = animal.getWeight();
        this.gender = animal.getGender();
        this.color = animal.getColor();
        this.spayed = animal.getSpayed();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public AnimalStatus getStatus() {
        return status;
    }

    /**
     * Set the value of status
     *
     * @param status new value of status
     */
    public void setStatus(AnimalStatus status) {
        this.status = status;
    }

    /**
     * Get the value of birthDate
     *
     * @return the value of birthDate
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Set the value of birthDate
     *
     * @param birthDate new value of birthDate
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Get the value of lastName
     *
     * @return the value of lastName
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of lastName
     *
     * @param lastName new value of lastName
     */
    public void setName(String name) {
        this.name = name;
    }


    public AnimalType getType() {
        return this.type;
    }

    public void setType(AnimalType type) {
        this.type = type;
    }

    public DogBreed getBreed() {
        return this.breed;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Spayed getSpayed() {
        return this.spayed;
    }

    public void setSpayed(Spayed spayed) {
        this.spayed = spayed;
    }




    public void setBreed(DogBreed breed) {
        this.breed = breed;
    }

    public boolean isPersisted() {
        return id != null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (this.id == null) {
            return false;
        }

        if (obj instanceof Animal && obj.getClass().equals(getClass())) {
            return this.id.equals(((Animal) obj).id);
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + (id == null ? 0 : id.hashCode());
        return hash;
    }

    @Override
    public String toString() {
        return name;
    }
}