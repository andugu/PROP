package test.domini;

import static org.junit.Assert.*; 
import org.junit.Test;
import org.junit.runners.*;
import main.domini.*;
import java.lang.Object;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class EstadistiquesGeneralsTest {

	public static void main(String[] args) {}

	@Test
	public void getEstadistiques() {

		EstadistiquesGenerals e = new EstadistiquesGenerals();

		Object[] r = e.getEstadistiques("LZ78", true);
		// Ha de retornar estadistiques buides, 0.0, 0.0, 0.0
		assertEquals(0.0, (double) r[0], 0);
		assertEquals(0.0, (double) r[1], 0);
		assertEquals(0.0, (double) r[2], 0);

		r = e.getEstadistiques("LZSS", true);
		// Ha de retornar estadistiques buides, 0.0, 0.0, 0.0
		assertEquals(0.0, (double) r[0], 0);
		assertEquals(0.0, (double) r[1], 0);
		assertEquals(0.0, (double) r[2], 0);

		r = e.getEstadistiques("LZW", true);
		// Ha de retornar estadistiques buides, 0.0, 0.0, 0.0
		assertEquals(0.0, (double) r[0], 0);
		assertEquals(0.0, (double) r[1], 0);
		assertEquals(0.0, (double) r[2], 0);

		r = e.getEstadistiques("JPEG", true);
		// Ha de retornar estadistiques buides, 0.0, 0.0, 0.0
		assertEquals(0.0, (double) r[0], 0);
		assertEquals(0.0, (double) r[1], 0);
		assertEquals(0.0, (double) r[2], 0);

		r = e.getEstadistiques("LZ78", false); 
		// Ha de retornar estadistiques buides, 0.0, 0.0, 0.0
		assertEquals(0.0, (double) r[0], 0);
		assertEquals(0.0, (double) r[1], 0);
		assertEquals(0.0, (double) r[2], 0);

		r = e.getEstadistiques("LZSS", false);
		// Ha de retornar estadistiques buides, 0.0, 0.0, 0.0
		assertEquals(0.0, (double) r[0], 0);
		assertEquals(0.0, (double) r[1], 0);
		assertEquals(0.0, (double) r[2], 0);

		r = e.getEstadistiques("LZW", false);
		// Ha de retornar estadistiques buides, 0.0, 0.0, 0.0
		assertEquals(0.0, (double) r[0], 0);
		assertEquals(0.0, (double) r[1], 0);
		assertEquals(0.0, (double) r[2], 0);

		r = e.getEstadistiques("JPEG", false);
		// Ha de retornar estadistiques buides, 0.0, 0.0, 0.0
		assertEquals(0.0, (double) r[0], 0);
		assertEquals(0.0, (double) r[1], 0);
		assertEquals(0.0, (double) r[2], 0);
	}

	@Test
	public void assignarNovaEstadistica() {

		EstadistiquesGenerals e = new EstadistiquesGenerals();

		e.assignarNovaEstadistica(2.0, 2.0, (long) 3.0, "JPEG", true);
		e.assignarNovaEstadistica(1.0, 1.0, (long) 1.0, "JPEG", true);

		Object[] r = e.getEstadistiques("JPEG", true);

		assertEquals(1.5, (double) r[0], 0);
		assertEquals(1.5, (double) r[1], 0);
		assertEquals(2.0, (double) r[2], 0);

		e.assignarNovaEstadistica(4.0, 2.0, (long) 1.0, "LZ78", false);
		e.assignarNovaEstadistica(2.0, 2.0, (long) 0.0, "LZ78", false);

		r = e.getEstadistiques("LZ78", false);

		assertEquals(3.0, (double) r[0], 0);
		assertEquals(2.0, (double) r[1], 0);
		assertEquals(0.5, (double) r[2], 0);
	}
}