package current;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


public class DateDemoTest {
	 
	@Test
	public void test() {
		DateDemo datedemo = new DateDemo();
		datedemo.inicializarDatos();
		assertTrue(datedemo.clasificarTransaccionComoCurrentONextDay_v2("0111", "0111104400").equals("CURRENT"));
	}
	
	@Test
	public void test2() {
		DateDemo datedemo = new DateDemo();
		datedemo.inicializarDatos();
		assertTrue(datedemo.clasificarTransaccionComoCurrentONextDay_v2("0110", "0109104400").equals("NEXT_DAY"));
	}

}
