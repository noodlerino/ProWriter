package dao;

import java.util.ArrayList;

import model.Proyecto;

public interface ProyectoDAO {
	
	public Integer addProyecto(Proyecto proyecto);
	
	public Proyecto getProyeto(Integer idProyecto);
	
	public void updateProyecto(Proyecto updatedProyecto);
	
	public Integer removeProyecto(Integer idProyecto);
	
	public ArrayList<Proyecto> getProyectos();

}
