package current;

import java.time.LocalDate;

import io.javalin.Javalin;

public class Current {
	public static void main(String[] args) {

		Javalin app = Javalin.create().start(8000);
		app.get("/:fechaCompensacion/:fechaCalendario/:dia/:festivo/:jornada", ctx -> {

			LocalDate fechaCompensacion = null;
			LocalDate fechaCalendario = null;
			String dia = null;
			Boolean festivo = false;
			int hora = 0;
			String jornada = null;

			try {

				ValidateDate validateDate = new ValidateDate();
				fechaCompensacion = validateDate.getvalidatedate(ctx.pathParam("fechaCompensacion"));
				fechaCalendario = validateDate.getvalidatedate(ctx.pathParam("fechaCalendario"));

				hora = Integer.parseInt(
						ctx.pathParam("fechaCalendario").substring(4, ctx.pathParam("fechaCalendario").length() - 4));

				dia = ctx.pathParam("dia");
				festivo = Boolean.parseBoolean(ctx.pathParam("festivo"));
				jornada = ctx.pathParam("jornada");

				if (!dia.equals("Sabado") && !dia.equals("Domingo") && jornada.equals("Normal") && festivo == false
						&& (fechaCompensacion.compareTo(fechaCalendario) == 0) && hora < 21) {
					ctx.result("Current");
				} else {
					ctx.result("Nextday");
				}
			} catch (Exception e) {
				ctx.result("Error2  " + e.toString());
				if (fechaCompensacion == null) {
					ctx.result("Fecha Compensacion invalida(MMDD)");
				} 
				if (fechaCalendario == null) {
					ctx.result("Fecha Calendario invalida(MMDDHHMMSS)");
				}
				if (dia == null) {
					ctx.result("Dia invalida(Sabado)");
				}
			}

		});// Cierre del javalin
	}
}
