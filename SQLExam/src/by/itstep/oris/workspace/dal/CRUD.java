package by.itstep.oris.workspace.dal;

import by.itstep.oris.workspace.model.entity.Hospital;
import by.itstep.oris.workspace.model.entity.Patient;

public interface CRUD {
	void insert(Patient patient);
	void delete(int id);
	void update(int id, Patient patient);
	Patient get(int id);
	Hospital getAll();
}
