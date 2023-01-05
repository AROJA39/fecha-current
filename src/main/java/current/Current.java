package current;

import java.time.LocalDate;

import io.javalin.Javalin;

public class Current {
	public static void main(String[] args) {

		Javalin app = Javalin.create().start(8000);
		app.get("/:fechaCompensacion/:fechaCalendario/:dia/:festivo/:jornada", ctx -> {
			try {
				
				ValidateDate validateDate=new ValidateDate();
				LocalDate fechaCompensacion=validateDate.getvalidatedate(ctx.pathParam("fechaCompensacion"));
				LocalDate fechaCalendario=validateDate.getvalidatedate(ctx.pathParam("fechaCalendario"));
				
				String dia = ctx.pathParam("dia");
				Boolean festivo = Boolean.parseBoolean(ctx.pathParam("festivo"));
				String jornada = ctx.pathParam("jornada");

				System.out.println("fechaCompensacion " + fechaCompensacion);
				System.out.println("fechaCalendarioD " + fechaCalendario);

				if (!dia.equals("Sabado") && !dia.equals("Domingo") && jornada.equals("Normal") && festivo == false
						&& (fechaCompensacion.compareTo(fechaCalendario) == 0)) {
					ctx.result("Current");
				} else {
					ctx.result("Nextday");
				}
			} catch (Exception e) {
				ctx.result("Error  " + e.toString());

			}

		});// Cierre del javalin
	}
}
