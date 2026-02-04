package ec.edu.espe.chanataxioscar_examen3p.model;

import java.util.UUID;

public class RoomReservation {
    private String id;
    private String roomCode;
    private String reservedByEmail;
    private Integer hours;
    //(CREATED, CONFIRMED)
    private String status;

    public RoomReservation(String roomCode, String reservedByEmail, Integer hours) {
        this.id = UUID.randomUUID().toString();
        this.roomCode = roomCode;
        this.reservedByEmail = reservedByEmail;
        this.hours = hours;
        this.status = "CREATED";
    }

    public String getId() {
        return id;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public String getReservedByEmail() {
        return reservedByEmail;
    }

    public Integer getHours() {
        return hours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
