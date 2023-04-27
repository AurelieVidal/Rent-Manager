package com.epf.rentmanager.model;

import com.epf.rentmanager.exception.InvalidClientException;
import com.epf.rentmanager.exception.InvalidReservationException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Reservation {

    private long id;
    private Client client;
    private Vehicle vehicle;
    private LocalDate debut;
    private LocalDate fin;

    public Reservation(long id, Client client, Vehicle vehicle, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.client = client;
        this.vehicle = vehicle;
        this.debut = debut;
        this.fin = fin;
    }

    public Reservation(Client client, Vehicle vehicle, LocalDate debut, LocalDate fin) {
        this.client = client;
        this.vehicle = vehicle;
        this.debut = debut;
        this.fin = fin;
    }

    public Reservation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id && Objects.equals(client, that.client) && Objects.equals(vehicle, that.vehicle) && Objects.equals(debut, that.debut) && Objects.equals(fin, that.fin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, vehicle, debut, fin);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", client=" + client +
                ", vehicle=" + vehicle +
                ", debut=" + debut +
                ", fin=" + fin +
                '}';
    }




    public String nomComplet (){
        String nom = this.client.getNom();
        String prenom = this.client.getPrenom();

        return prenom+" "+nom;
    }

    public String modeleComplet () {
        String marque = this.vehicle.getConstructeur();
        String modele = this.vehicle.getModele();

        return marque+" "+modele;
    }



    public static boolean checkDuration (Reservation reservation) {
        long duree = ChronoUnit.DAYS.between(reservation.getDebut(), reservation.getFin());
        if (duree >7 || duree <0){
            return false;
        }
        else {
            return true;
        }
    }




    public static void ValidReservation (Reservation reservation) throws InvalidReservationException {
        if (!checkDuration(reservation)) {
            throw new InvalidReservationException();
        }
    }


}
