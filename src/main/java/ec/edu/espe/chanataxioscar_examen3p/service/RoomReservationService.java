package ec.edu.espe.chanataxioscar_examen3p.service;

import ec.edu.espe.chanataxioscar_examen3p.dto.RoomReservationResponse;
import ec.edu.espe.chanataxioscar_examen3p.model.RoomReservation;
import ec.edu.espe.chanataxioscar_examen3p.repository.ReservationRepository;
import ec.edu.espe.chanataxioscar_examen3p.repository.RoomReservationRepository;

public class RoomReservationService {
    public final ReservationRepository reservationRepository;
    public final RoomReservationRepository roomReservationRepository;
    public final UserPolicyClient userPolicyClient;

    public RoomReservationService(ReservationRepository reservationRepository, RoomReservationRepository roomReservationRepository, UserPolicyClient userPolicyClient) {
        this.reservationRepository = reservationRepository;
        this.roomReservationRepository = roomReservationRepository;
        this.userPolicyClient = userPolicyClient;
    }

    public RoomReservationResponse createReservation(String roomCode, String email, int hours){
        //validaciones
        if(roomCode == null || roomCode.isEmpty()){
            throw new IllegalArgumentException("Room code is required");
        }
        if(email == null || email.isEmpty() || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }
        if(hours < 0 || hours >= 8) {
            throw new IllegalArgumentException("Hours must be between 0 and 8");
        }
        if(reservationRepository.isReserved(roomCode)){
            throw new IllegalStateException("Room is already reserved");
        }
        if(userPolicyClient.isBlocked(email)){
            throw new IllegalStateException("User is blocked from making reservations");
        }

        RoomReservation reservation = new RoomReservation(roomCode, email, hours);
        RoomReservation saved = roomReservationRepository.save(reservation);

        return new RoomReservationResponse(saved.getId(),saved.getRoomCode(), saved.getHours(), saved.getStatus());

    }
}
