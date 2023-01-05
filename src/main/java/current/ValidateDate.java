package current;

import java.time.LocalDate;

public class ValidateDate {
	public LocalDate getvalidatedate(String date) {
		LocalDate fecha = null;
		try {
			LocalDate current_date = LocalDate.now();

			if (date.length() == 10) {
				date = date.substring(0, date.length() - 6);
			}
			if (date.length() == 2) {
				fecha = LocalDate.parse(current_date.getYear() + "-" // agrego el año
						+ ("0" + date.substring(0, 1) + "0"// agrego 0 del dia
								+ date.substring(1, 2))// agrego 0 del mes
								.replaceAll("(?s).{" + 2 + "}(?!$)", "$0" + "-"));// agrego - de la fecha
			} else if (date.length() == 4) {
				fecha = LocalDate.parse(current_date.getYear() + "-"// agrego el año
						+ date.replaceAll("(?s).{" + 2 + "}(?!$)", "$0" + "-"));// agrego - de la fecha
			}

		} catch (Exception ex) {
			System.out.println("Error" + ex.getMessage());
		}
		return fecha;

	}// Cierre getvalidatedate
}
