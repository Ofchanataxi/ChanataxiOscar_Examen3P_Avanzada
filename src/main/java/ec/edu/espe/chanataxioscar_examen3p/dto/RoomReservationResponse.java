package ec.edu.espe.chanataxioscar_examen3p.dto;

public class RoomReservationResponse {
    public String id;
    public String roomCode;
    public Integer hours;
    public String status;

    public RoomReservationResponse(String id, String roomCode, Integer hours, String status) {
        this.id = id;
        this.roomCode = roomCode;
        this.hours = hours;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public Integer getHours() {
        return hours;
    }

    public String getStatus() {
        return status;
    }
}
