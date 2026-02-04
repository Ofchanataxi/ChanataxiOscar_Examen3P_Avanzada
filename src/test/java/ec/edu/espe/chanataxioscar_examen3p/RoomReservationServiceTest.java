package ec.edu.espe.chanataxioscar_examen3p;

import ec.edu.espe.chanataxioscar_examen3p.dto.RoomReservationResponse;
import ec.edu.espe.chanataxioscar_examen3p.repository.ReservationRepository;
import ec.edu.espe.chanataxioscar_examen3p.repository.RoomReservationRepository;
import ec.edu.espe.chanataxioscar_examen3p.service.RoomReservationService;
import ec.edu.espe.chanataxioscar_examen3p.service.UserPolicyClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RoomReservationServiceTest {
    private RoomReservationService roomReservationService;
    private RoomReservationRepository roomReservationRepository;
    private UserPolicyClient userPolicyClient;
    private ReservationRepository reservationRepository;

    @BeforeEach
    public void setUp(){
        roomReservationRepository = Mockito.mock(RoomReservationRepository.class);
        userPolicyClient = Mockito.mock(UserPolicyClient.class);
        reservationRepository = Mockito.mock(ReservationRepository.class);
        roomReservationService = new RoomReservationService(reservationRepository, roomReservationRepository, userPolicyClient);
    }

    //Creación exitosa de una reserva con datos válidos.
    @Test
    void testCreateReservation_Success(){
        //Arrange
        String roomCode = "A101";
        String email = "oscar@espe.edu.ec";
        int hours = 2;

        when(reservationRepository.isReserved(roomCode)).thenReturn(false);
        when(userPolicyClient.isBlocked(email)).thenReturn(false);
        when(roomReservationRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);

        //Act
        RoomReservationResponse response = roomReservationService.createReservation(roomCode, email, hours);

        //Assert
        assertNotNull(response.getId());

        verify(reservationRepository).isReserved(roomCode);
        verify(userPolicyClient).isBlocked(email);
        verify(roomReservationRepository).save(Mockito.any());
    }

    //Error por correo electrónico inválido.
    @Test
    void createRoomReservation_invalidEmail_shouldThrow_andNotCallDependencies(){
        //Arrange
        String roomCode = "A101";
        String invalidEmail = "oscarespe.edu.ec";
        int hours = 2;

        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> roomReservationService.createReservation(roomCode, invalidEmail, hours));

        //No debe llamar a ninguna dependencia porque falla la validacion
        verifyNoInteractions(reservationRepository);
        verifyNoInteractions(userPolicyClient);
        verifyNoInteractions(roomReservationRepository);
    }

    //Error por número de horas fuera del rango permitido.
    @Test
    void createRoomReservation_invalidHours_shouldThrow_andNotCallDependencies(){
        //Arrange
        String roomCode = "A101";
        String email = "oscar@espe.edu.ec";
        int invalidHours = 10;

        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> roomReservationService.createReservation(roomCode, email, invalidHours));

        //No debe llamar a ninguna dependencia porque falla la validacion
        verifyNoInteractions(reservationRepository);
        verifyNoInteractions(userPolicyClient);
        verifyNoInteractions(roomReservationRepository);
    }

    //Error cuando la sala ya se encuentra reservada.
    @Test
    void createRoomReservation_roomAlreadyReserved_shouldThrow() {
        //Arrange
        String roomCode = "A101";
        String email = "oscar@espe.edu.ec";
        int hours = 2;

        when(reservationRepository.isReserved(roomCode)).thenReturn(true);

        //Act + Assert
        assertThrows(IllegalStateException.class, () -> roomReservationService.createReservation(roomCode, email, hours));

        verify(reservationRepository).isReserved(roomCode);
        verifyNoInteractions(userPolicyClient);
        verifyNoInteractions(roomReservationRepository);
    }
}
