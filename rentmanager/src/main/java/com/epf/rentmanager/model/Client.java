package com.epf.rentmanager.model;

import java.time.LocalDate;
import java.util.Objects;

public class Client {
    private long id;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate naissance;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Client(long id, String nom, String prenom, String email, LocalDate dateDeNaissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.naissance = dateDeNaissance;
    }

    public Client(String nom, String prenom, String email, LocalDate dateDeNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.naissance = dateDeNaissance;
    }

    public Client() { }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getNaissance() {
        return naissance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Double.compare(client.id, id) == 0 && Objects.equals(nom, client.nom) && Objects.equals(prenom, client.prenom) && Objects.equals(email, client.email) && Objects.equals(naissance, client.naissance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom, email, naissance);
    }

    public void setDateDeNaissance(LocalDate dateDeNaissance) {
        this.naissance = dateDeNaissance;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", naissance=" + naissance +
                '}';
    }


}
