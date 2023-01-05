package current;

import java.time.LocalDate;


public class ValidateDate {
	public LocalDate getvalidatedate(String date) {
		LocalDate fecha = null;
		System.out.println("Entro " + date);
		try {
			LocalDate current_date = LocalDate.now();

			if (date.length() == 10) {
				System.out.println("Entro10 " + date);
				date=date.substring(0, date.length() - 6);
				System.out.println("Entro-4 " + date);
				
				
			} 
			if (date.length() == 2) {
				fecha = LocalDate.parse(current_date.getYear() + "-" // agrego el año
						+ ("0" + date.substring(0, 1) + "0"// agrego 0 del dia
								+ date.substring(1, 2))// agrego 0 del mes
								.replaceAll("(?s).{" + 2 + "}(?!$)", "$0" + "-"));// agrego - de la fecha

				System.out.println("Entro2 " + fecha);
				
			} else if (date.length() == 4) {
				System.out.println("Entro4 " + date);
				fecha = LocalDate.parse(current_date.getYear() + "-"// agrego el año
						+ date.replaceAll("(?s).{" + 2 + "}(?!$)", "$0" + "-"));// agrego - de la fecha

				System.out.println("Entro4 " + fecha);
			}

		} catch (Exception ex) {
			System.out.println("Error" + ex.getMessage());
		}
		return fecha;

	}// Cierre getvalidatedate
}
