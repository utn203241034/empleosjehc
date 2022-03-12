package com.jesus.enrique.herrera.campos.service;

import java.util.List;

import com.jesus.enrique.herrera.campos.model.Vacante;

public interface IntServiceVacantes {
	public List<Vacante> obtenerTodas();
	public void eliminar(Integer idVacante);
	public Vacante buscarPorId(Integer idVacante);
	public int numeroRegistros();
	public void guardar(Vacante vacante);
}
