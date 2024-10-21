package org.astroport;

import org.astroport.constants.DockType;
import org.astroport.dao.DockSpotDAO;
import org.astroport.dao.TicketDAO;
import org.astroport.model.DockSpot;
import org.astroport.model.Ticket;
import org.astroport.service.DockService;
import org.astroport.service.ShipService;
import org.astroport.service.TicketService;
import org.astroport.util.LanguageUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.ResourceBundle;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DockServiceTest {

    private MockedStatic<Main> mockedMain;
    private MockedStatic<AppConfig> mockedAppConfig;

    @Mock
    private DockSpotDAO mockDockSpotDAO;
    @Mock
    private TicketDAO mockTicketDAO;
    @Mock
    private ShipService mockShipService;
    @Mock
    private TicketService mockTicketService;


    private DockService dockService;


    @BeforeEach
    public void setUp() {
        // Initialise les mocks pour les méthodes statiques
        mockedAppConfig = mockStatic(AppConfig.class);
        mockedMain = mockStatic(Main.class);

        // Configure les mocks pour les méthodes statiques
        LanguageUtil mockLanguageUtil = mock(LanguageUtil.class);
        ResourceBundle mockMessages = mock(ResourceBundle.class);
        ResourceBundle mockErrors = mock(ResourceBundle.class);

        when(mockLanguageUtil.getMessages()).thenReturn(mockMessages);
        when(mockLanguageUtil.getErrors()).thenReturn(mockErrors);

        mockedAppConfig.when(AppConfig::getLanguageInterface).thenReturn(mockLanguageUtil);
        mockedMain.when(Main::resetApp).thenAnswer(invocation -> null);

        dockService = new DockService(mockDockSpotDAO, mockTicketDAO, mockShipService, mockTicketService);
    }

    @AfterEach
    public void tearDown() {
        // Ferme les mocks pour les méthodes statiques
        if (mockedAppConfig != null) {
            mockedAppConfig.close();
        }
        if (mockedMain != null) {
            mockedMain.close();
        }
    }

    @Test
    public void processIncomingShip_handlesIncomingShip() {
        when(mockShipService.getShipType()).thenReturn(DockType.CORVETTE);
        when(mockDockSpotDAO.getNextAvailableSlot(any(DockType.class))).thenReturn(Optional.of(1));
        when(mockShipService.getShipName()).thenReturn("Ship1");
        when(mockDockSpotDAO.updateDock(any(DockSpot.class))).thenReturn(true);

        dockService.processIncomingShip();

        verify(mockShipService).getShipName();
        verify(mockDockSpotDAO).updateDock(any(DockSpot.class));
        verify(mockTicketService).createAndSaveTicket(any(DockSpot.class), anyString());
    }


    @Test
    public void processIncomingShip_handlesNoAvailableDock() {
        when(mockDockSpotDAO.getNextAvailableSlot(any(DockType.class))).thenReturn(Optional.empty());

        dockService.processIncomingShip();

        verify(mockShipService, never()).getShipName();
        verify(mockDockSpotDAO, never()).updateDock(any(DockSpot.class));
        verify(mockTicketService, never()).createAndSaveTicket(any(DockSpot.class), anyString());
    }


    @Test
    public void processExitingShip_handlesExitingShip() {
        when(mockTicketDAO.getTicket(anyString())).thenReturn(Optional.of(new Ticket()));
        when(mockShipService.getShipName()).thenReturn("Ship1");

        dockService.processExitingShip();

        verify(mockTicketService).calculateAndSetFare(anyString(), any(Ticket.class));
        verify(mockTicketService).updateTicketAndDockSpot(any(Ticket.class));
    }

}
