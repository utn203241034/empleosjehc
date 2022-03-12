package com.jesus.enrique.herrera.campos.service;

import java.util.List;

import com.jesus.enrique.herrera.campos.model.Categoria;

public interface IntServiceCategorias {
	public List<Categoria> obtenerCategorias();
	public void guardar(Categoria categoria);
	public Categoria buscarPorId(Integer idCategoria);
	public void eliminar(Integer idCategoria);
	public Integer numeroCategorias();
}
