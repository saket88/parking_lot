import com.gojek.parking.exception.DuplicateVehicleException;
import com.gojek.parking.model.Car;
import com.gojek.parking.model.ParkingLot;
import com.gojek.parking.model.Vehicle;
import org.junit.Before;
import org.junit.Test;

import javax.naming.SizeLimitExceededException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class b  {

    ParkingLot underTest;

    @Before
    public void setUp() {
        underTest = new ParkingLot( 100 );
    }

    @Test
    public void shouldCreateAParkingLot() {
        assertThat( underTest.getCapacity(), is( 100 ) );
    }

    @Test
    public void shouldBeAbleToParkVehicle() throws DuplicateVehicleException, SizeLimitExceededException {
        Vehicle polo = new Car( "KA-01-UU-67677", "White" );
        Vehicle beat = new Car( "KA-01-UU-67678", "blue" );
        underTest.park( polo );
        underTest.park( beat );
        assertThat( getSize(), is( 2 ) );

    }

    @Test(expected = DuplicateVehicleException.class)
    public void shouldNotAllowToParkSameVehicleTwice() throws DuplicateVehicleException, SizeLimitExceededException {
        Vehicle polo = new Car( "KA-01-UU-67677", "White" );
        underTest.park( polo );
        underTest.park( polo );

    }

    @Test
    public void shouldBeAbleToLeaveAVehicle() throws DuplicateVehicleException, SizeLimitExceededException {
        Vehicle polo = new Car( "KA-01-UU-67677", "White" );
        Vehicle beat = new Car( "KA-01-UU-67678", "blue" );
        Integer poloSlot = underTest.park( polo );
        underTest.park( beat );

        underTest.leave( poloSlot );

        assertThat( getSize(), is( 1 ) );


    }

    @Test(expected = NoSuchElementException.class)
    public void shouldBeAbleToLeaveAVehicleNotParked() throws DuplicateVehicleException, SizeLimitExceededException {
        Vehicle polo = new Car( "KA-01-UU-67677", "White" );
        underTest.park( polo );

        underTest.leave( 2 );


    }

    private int getSize() {
        return underTest.getParkingMap().keySet().size();
    }

    @Test(expected = SizeLimitExceededException.class)
    public void shouldBeAbleToParkVehicleOneFull() throws DuplicateVehicleException, SizeLimitExceededException {
        underTest = new ParkingLot( 1 );
        Vehicle polo = new Car( "KA-01-UU-67677", "White" );
        Vehicle beat = new Car( "KA-01-UU-67678", "blue" );
        underTest.park( polo );

        underTest.park( beat );

    }

    @Test
    public void shouldBeAbleToGetSlotNumbersByColor() throws DuplicateVehicleException, SizeLimitExceededException {
        Vehicle polo = new Car( "KA-01-UU-67677", "White" );
        Vehicle beat = new Car( "KA-01-UU-67678", "Blue" );
        Vehicle santro = new Car( "KA-01-UU-676675", "White" );
        Vehicle jazz = new Car( "KA-01-UU-52345", "Red" );
        Integer poloSlot = underTest.park( polo );
        Integer santroSlot = underTest.park( santro );
        underTest.park( beat );
        underTest.park( jazz );

        List<Integer> slotsByColor = underTest.getSlotsByColor( "White" );

        assertThat( slotsByColor, hasItems( poloSlot, santroSlot ) );

    }

    @Test
    public void shouldBeAbleToGetSlotFromRegistrationNumber() throws DuplicateVehicleException, SizeLimitExceededException {
        Vehicle polo = new Car( "KA-01-UU-67677", "White" );
        Integer poloSlot = underTest.park( polo );

        Integer actualSlot = underTest.getSlotByRegistration( "KA-01-UU-67677" );

        assertThat( poloSlot, is( equalTo( actualSlot ) ) );

    }


}
