package service;

import java.util.List;

import entity.MedicineBrief;
import entity.MedicineDetail;

public interface MedService {
	
	List<MedicineBrief> getMedListByType(int t_id,int sort_way);
	
	MedicineDetail getMedDetailById(int m_id);
	
	List<MedicineBrief> searchMedByKeyword(String keyword,int sort_way);
	
	List<MedicineBrief> getTopSalesnumMedList();
}
