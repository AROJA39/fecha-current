/**
 * 
 */
package current;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author andre
 *
 */
public class DateDemo {

	static String fechaYHoraCalendarioTransaccion = null;
	static String fechaCalendarioTransaccion = null;
	static String horaCalendarioTransaccion = null;
	static String fechaCompensacionTransaccion = null;
	static LocalDateTime fechaHoy = null;
	static Boolean esJornadaNormal = true; // En día hábil es 'true'.
	public static Boolean esDiaHabil = null; // AGREGAMOS 'PUBLIC'
	static final LocalTime FINAL_HOUR_FOR_NORMAL_JOURNEY = LocalTime.of(21, 00, 00);// Solamente aplica para día hábil
																					// (no sábados, ni domingos, ni
																					// festivos).
	static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMddHHmmss");
	static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
	static final Integer HORA_LIMITE_CIERRE = 0;
	static String estadoProceso = null;
	static String clasificacion;
	volatile static Boolean estaEnCierre = null;
	static Map<String, Object> diasFestivos = new HashMap<>();
	static Map<String, Object> diasDePrueba = new HashMap<>();
	static Map<String, DatosHashmap> datosTransaccionesPrueba = new HashMap<>();

	public static void inicializarDatos() {
		// Datos validos MMddHHmmss
		fechaYHoraCalendarioTransaccion = "0106110000";
		fechaCalendarioTransaccion = fechaYHoraCalendarioTransaccion.substring(0, 4);
		horaCalendarioTransaccion = fechaYHoraCalendarioTransaccion.substring(4, 10);
		// Datos válidos MMdd
		fechaCompensacionTransaccion = "0106";
		// Fecha del sistema
		fechaHoy = LocalDateTime.now();
		estaEnCierre = false;
		// Tipo de jornada hábil o no hábil
		esJornadaNormal = calcularSiEsJornadaNormal();
		// Indica si se trata de un día hábil
		esDiaHabil = true;
		// Estado del proceso
		estadoProceso = "";
		diasFestivos.put("0101", "Inicio de año (domingo)");
		diasFestivos.put("0109", "Reyes (lunes)");
		diasFestivos.put("0406", "Jueves santo");
		diasFestivos.put("0407", "Viernes santo");
		diasFestivos.put("0720", "Independencia (jueves)");
		diasFestivos.put("0807", "Batalla de Boyacá (lunes)");

		diasDePrueba.put("1230", "XFin de año BBOG (viernes)");
		diasDePrueba.put("0101", "Inicio de año (domingo)");
		diasDePrueba.put("1231", "Fin de año (sábado)");
		diasDePrueba.put("0102", "XPrimer día de 2023 (lunes)");
		diasDePrueba.put("0103", "XSegundo día de 2023 (martes)");
		diasDePrueba.put("0719", "XAntes del Día de la Independencia (miércoles)");
		diasDePrueba.put("0721", "XDespués del Día de la Independencia (viernes)");
		diasDePrueba.put("0806", "Antes del Día de la Batalla de Boyacá (domingo)");
		diasDePrueba.put("0808", "XDespués del Día de la Batalla de Boyacá (martes)");
	}

	public static final void armarDatosTransaccionesPrueba() {
		for (String llave : diasDePrueba.keySet()) {
			boolean esHabil = ((String) diasDePrueba.get(llave)).startsWith("X");
			DatosHashmap datos = new DatosHashmap();
			datos.llave = llave;
			datos.strHoraNormal = getHoraAleatoria("JORNADA_NORMAL");
			datos.strFechaCalendario = llave + datos.strHoraNormal;
			datos.strFechaCompensacion = llave;
			datos.esCierre = false;
			datos.esDiaHabil = esHabil;
			datosTransaccionesPrueba.put(datos.strFechaCalendario, datos);

			datos = new DatosHashmap();
			datos.llave = llave;
			datos.strHoraCierre = getHoraAleatoria("JORNADA_CIERRE");
			datos.strFechaCalendarioCierre = llave + datos.strHoraCierre;
			datos.strFechaCompensacionCierre = llave;
			datos.esCierre = true;
			datos.esDiaHabil = esHabil;
			datosTransaccionesPrueba.put(datos.strFechaCalendarioCierre, datos);
		}

		mostrardatosprueba();
	}

	public static void mostrardatosprueba() {
		for (String llaveNavegacion : datosTransaccionesPrueba.keySet()) {
			DatosHashmap datos = datosTransaccionesPrueba.get(llaveNavegacion);
			StringBuilder data = new StringBuilder();
			data // .append("[").append(llaveNavegacion ).append( "]->")
					.append("[").append(datos.llave).append("]->").append("[").append(datos.strFechaCalendario)
					.append("]").append("[").append(datos.strFechaCompensacion).append("]").append("[")
					.append(datos.strHoraNormal).append("]").append("[").append(datos.strFechaCalendarioCierre)
					.append("]").append("[").append(datos.strFechaCompensacionCierre).append("]").append("[")
					.append(datos.strHoraCierre).append("]");
			System.out.println("PRUEBA[" + data.toString() + "]" + datos.esCierre);

			DateDemo.esDiaHabil = datos.esDiaHabil;
			if (datos.esCierre) {
				DateDemo.fechaCompensacionTransaccion = datos.strFechaCompensacionCierre;
				DateDemo.fechaCalendarioTransaccion = datos.strFechaCalendarioCierre;
				DateDemo.esJornadaNormal = false;
			} else {
				DateDemo.fechaCompensacionTransaccion = datos.strFechaCompensacion;
				DateDemo.fechaCalendarioTransaccion = datos.strFechaCalendario;
				DateDemo.esJornadaNormal = true;
			}
			/* clasificarTransaccionComoCurrentONextDay(); */
			clasificarTransaccionComoCurrentONextDay_v3(fechaCompensacionTransaccion, fechaCalendarioTransaccion,
					esJornadaNormal);

			System.out.println("Clasificación: " + clasificacion);
		}

	}

	public static final String getHoraAleatoria(LocalTime initialTime, LocalTime finalTime) {
		Random rand = new Random();
		int hours = rand.nextInt((finalTime.getHour() - initialTime.getHour()) + 1) + initialTime.getHour();
		int minutes = rand.nextInt((finalTime.getMinute() - initialTime.getMinute()) + 1) + initialTime.getMinute();
		int seconds = rand.nextInt((finalTime.getSecond() - initialTime.getSecond()) + 1) + initialTime.getSecond();
		LocalTime random = LocalTime.of(hours, minutes, seconds);
		return random.toString().replaceAll("[:]", "");
	}

	public static String getHoraAleatoria(String rango) {
		String aleatorioHora = "";
		switch (rango) {
		case "JORNADA_NORMAL":
			aleatorioHora = getHoraAleatoria(LocalTime.of(3, 0, 0), LocalTime.of(20, 00, 00));
			break;
		case "JORNADA_CIERRE":
			aleatorioHora = getHoraAleatoria(LocalTime.of(21, 0, 0), LocalTime.of(23, 59, 59));
			break;
		}
		return aleatorioHora;
	}

	static class DatosHashmap {
		boolean esDiaHabil = true;
		boolean esCierre = false;
		String llave = "N/A";
		String strFechaCalendario = "N/A";
		String strFechaCompensacion = "N/A";
		String strFechaCalendarioCierre = "N/A";
		String strFechaCompensacionCierre = "N/A";
		String strHoraNormal = "N/A";
		String strHoraCierre = "N/A";
	}

	// Método que recorra los días de prueba, y varíe la llaveNavegacion de
	// compensación, la llaveNavegacion de la transacción, y si está en jornada
	// normal encender o apagar el booleano esJornadaNormal.

	/*
	 * private static Boolean calcularSiEsDiaHabil( LocalDate date ){ ; }
	 */

	private static Boolean calcularSiEsJornadaNormal() {
		if (estaEnCierre) {
			return false;
		} else {
			return true;
		}
	}

	public static String clasificarTransaccionComoCurrentONextDay_v2(String fechaCompensacionString,
			String fechaCalendarioString) {
		if (!fechaCompensacionString.equals(fechaCalendarioString.substring(0, 4)) || isClosedJourney()) {
			clasificacion = "NEXT_DAY";
		} else {
			clasificacion = "CURRENT";
		}
		return clasificacion;
	}

	public synchronized static Boolean isClosedJourney() {
		LocalTime transactionHourReceived = LocalTime.now();
		if (isWorkingDay()) {
			if (transactionHourReceived.isAfter(FINAL_HOUR_FOR_NORMAL_JOURNEY) && !estaEnCierre) {
				estaEnCierre = true;
			}
		}
		return estaEnCierre;
	}

	public static String clasificarTransaccionComoCurrentONextDay_v3(String fechaCompensacionString,
			String fechaCalendarioString, Boolean isClosedJourney) {
		if (!fechaCompensacionString.equals(fechaCalendarioString.substring(0, 4)) || !isClosedJourney) {
			clasificacion = "NEXT_DAY";
		} else {
			clasificacion = "CURRENT";
		}
		return clasificacion;
	}

	public static Boolean isWorkingDay() {
		// esDiaHabil = false; // OJO. ESTÁ QUEMADO.
		return esDiaHabil;
	}

	public static void apagarCierre() {
		estaEnCierre = false;
	}

	public static void main(String args[]) {
		inicializarDatos();
		armarDatosTransaccionesPrueba();
	}
}
