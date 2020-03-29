import com.gojek.parking.model.Car;
import com.gojek.parking.model.ParkingLot;
import com.gojek.parking.model.Vehicle;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ParkingLotTest {

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
    public void shouldBeAbleToParkVehicle() {
        Vehicle polo = new Car( "KA-01-UU-67677", "White" );
        Vehicle beat = new Car( "KA-01-UU-67678", "blue" );
        underTest.park( polo );
        underTest.park( beat );
        assertThat( underTest.getParkingMap().keySet().size(), is( 2 ) );

    }
    @Test
    public void shouldBeAbleToLeaveAVehicle() {
        Vehicle polo = new Car( "KA-01-UU-67677", "White" );
        Vehicle beat = new Car( "KA-01-UU-67678", "blue" );
        Integer poloSlot = underTest.park( polo );
        underTest.park( beat );

        underTest.leave( poloSlot );

        assertThat( underTest.getParkingMap().keySet().size(), is( 1 ) );


    }



}
