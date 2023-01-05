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
				
				int hora=Integer.parseInt(ctx.pathParam("fechaCalendario").substring(4, ctx.pathParam("fechaCalendario").length() - 4));
				System.out.println("minutos " + hora);
				
				String dia = ctx.pathParam("dia");
				Boolean festivo = Boolean.parseBoolean(ctx.pathParam("festivo"));
				String jornada = ctx.pathParam("jornada");

				if (!dia.equals("Sabado") && !dia.equals("Domingo") && jornada.equals("Normal") && festivo == false
						&& (fechaCompensacion.compareTo(fechaCalendario) == 0)&& hora<21) {
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
