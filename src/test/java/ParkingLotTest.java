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
    public void setUp(){
        underTest = new ParkingLot( 100 );
    }
    @Test
    public void shouldCreateAParkingLot(){
        assertThat(underTest.getCapacity(),is(100));
    }

    @Test
    public void shouldBeAbleToParkVehicle(){
        Vehicle car = new Car( "KA-01-UU-67677", "White" );
        underTest.park( car );
        assertThat( underTest.getParkingMap().keySet().size(),is(1)  );

    }
}