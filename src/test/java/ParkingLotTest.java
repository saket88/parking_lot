import com.gojek.parking.exception.DuplicateVehicleException;
import com.gojek.parking.model.Car;
import com.gojek.parking.model.ParkingLot;
import com.gojek.parking.model.Vehicle;
import org.junit.Before;
import org.junit.Test;

import javax.naming.SizeLimitExceededException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.apache.commons.lang3.builder.CompareToBuilder.reflectionCompare;
import static org.hamcrest.CoreMatchers.hasItems;
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
    public void shouldNotBeAbleToParkVehicleIfFull() throws DuplicateVehicleException, SizeLimitExceededException {
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
    public void shouldBeAbleToGetRegistrationsByColor() throws DuplicateVehicleException, SizeLimitExceededException {
        Vehicle polo = new Car( "KA-01-UU-67677", "White" );
        Vehicle beat = new Car( "KA-01-UU-67678", "Blue" );
        Vehicle santro = new Car( "KA-01-UU-676675", "White" );
        Vehicle jazz = new Car( "KA-01-UU-52345", "Red" );
        underTest.park( polo );
        underTest.park( beat );
        underTest.park( santro );
        underTest.park( jazz );

        List<Vehicle> slotsByColor = underTest.getRegistrationsByColor( "White" );

        List<Vehicle> expectedVehicles = new ArrayList<>(  );
        expectedVehicles.add( polo );
        expectedVehicles.add( santro );


        assertThat( reflectionCompare(slotsByColor, expectedVehicles),is( 0 ));


    }


    @Test
    public void shouldBeAbleToGetStatus() throws DuplicateVehicleException, SizeLimitExceededException {
        TestVehicles testVehicles = new TestVehicles().invoke();
        Vehicle polo = testVehicles.getPolo();
        Vehicle beat = testVehicles.getBeat();
        Vehicle santro = testVehicles.getSantro();
        Vehicle jazz = testVehicles.getJazz();

        Integer poloSlot =  underTest.park( polo );
        Integer santroSlot = underTest.park( santro );
        Integer beatSlot = underTest.park( beat );
        Integer jazzSlot = underTest.park( jazz );

        NewTestVehicles newTestVehicles = new NewTestVehicles( poloSlot, santroSlot, beatSlot, jazzSlot ).invoke();
        Vehicle newPolo = newTestVehicles.getNewPolo();
        Vehicle newBeat = newTestVehicles.getNewBeat();
        Vehicle newSantro = newTestVehicles.getNewSantro();
        Vehicle newJazz = newTestVehicles.getNewJazz();

        List<Vehicle> vehicles = underTest.getStatus();
        List<Vehicle> expectedVehicles = new ArrayList<>(  );
        expectedVehicles.add( newPolo );
        expectedVehicles.add( newBeat );
        expectedVehicles.add( newSantro );
        expectedVehicles.add( newJazz );

        assertThat( reflectionCompare(vehicles, expectedVehicles),is( 0 ));

    }

    @Test
    public void shouldBeAbleToGetTheImmediateSlotAvailableForParking() throws DuplicateVehicleException, SizeLimitExceededException {
        Vehicle polo = new Car( "KA-01-UU-67677", "White" );
        Vehicle beat = new Car( "KA-01-UU-67678", "blue" );
        Vehicle mercedes = new Car( "KA-01-UU-67679", "magenta" );
        Vehicle vento = new Car( "KA-01-UU-67272", "yellow" );
        Vehicle santro = new Car( "KA-01-UU-84785", "blue" );
        underTest.park( polo );
        Integer beatSlot = underTest.park( beat );
        underTest.park( mercedes );
        underTest.park( vento );
        underTest.park( santro );

        underTest.leave( beatSlot );

        Vehicle duster = new Car( "KA-01-UU-35268", "Silver" );
        Integer dusterSlot = underTest.park( duster );

        assertThat( dusterSlot, is( beatSlot ) );


    }


    private class TestVehicles {
        private Vehicle polo;
        private Vehicle beat;
        private Vehicle santro;
        private Vehicle jazz;

        public Vehicle getPolo() {
            return polo;
        }

        public Vehicle getBeat() {
            return beat;
        }

        public Vehicle getSantro() {
            return santro;
        }

        public Vehicle getJazz() {
            return jazz;
        }

        public TestVehicles invoke() {
            polo = new Car( "KA-01-UU-67677", "White" );
            beat = new Car( "KA-01-UU-67678", "Blue" );
            santro = new Car( "KA-01-UU-676675", "White" );
            jazz = new Car( "KA-01-UU-52345", "Red" );
            return this;
        }
    }

    private class NewTestVehicles {
        private Integer poloSlot;
        private Integer santroSlot;
        private Integer beatSlot;
        private Integer jazzSlot;
        private Vehicle newPolo;
        private Vehicle newBeat;
        private Vehicle newSantro;
        private Vehicle newJazz;

        public NewTestVehicles( Integer poloSlot, Integer santroSlot, Integer beatSlot, Integer jazzSlot ) {
            this.poloSlot = poloSlot;
            this.santroSlot = santroSlot;
            this.beatSlot = beatSlot;
            this.jazzSlot = jazzSlot;
        }

        public Vehicle getNewPolo() {
            return newPolo;
        }

        public Vehicle getNewBeat() {
            return newBeat;
        }

        public Vehicle getNewSantro() {
            return newSantro;
        }

        public Vehicle getNewJazz() {
            return newJazz;
        }

        public NewTestVehicles invoke() {
            newPolo = new Car( "KA-01-UU-67677", "White", poloSlot );
            newBeat = new Car( "KA-01-UU-67678", "Blue", beatSlot );
            newSantro = new Car( "KA-01-UU-676675", "White", santroSlot );
            newJazz = new Car( "KA-01-UU-52345", "Red", jazzSlot );
            return this;
        }
    }
}
