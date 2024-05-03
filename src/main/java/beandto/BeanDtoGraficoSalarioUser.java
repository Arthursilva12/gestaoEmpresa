package beandto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BeanDtoGraficoSalarioUser implements Serializable {

	private static final long serialVersionUID = 1L;

	List<Double> salarios = new ArrayList<>();
	List<String> perfils = new ArrayList<>();

	public List<Double> getSalarios() {
		return salarios;
	}

	public void setSalarios(List<Double> salarios) {
		this.salarios = salarios;
	}

	public List<String> getPerfils() {
		return perfils;
	}

	public void setPerfils(List<String> perfil) {
		this.perfils = perfil;
	}

}
