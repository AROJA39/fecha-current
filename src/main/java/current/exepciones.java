package current;


public class exepciones {
	public boolean getexception(String fechaCompensacion) {

		Boolean b1 = false;

		if (fechaCompensacion != null) {

			System.out.println("Fecha invalida");

			b1 = true;

		}

		return b1;

	}

}
