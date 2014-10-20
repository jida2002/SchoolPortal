package school.dao;

import java.util.Date;
import java.util.List;

import school.model.Group;

public interface GroupDao extends BaseDao<Group>{

	List<Group> findAllAdditional();
	List<Group> findAllNotAdditional();
	List<Group> findByNumber(byte number);
	List<Group> findByStartDate(Date startDate);
	Group findByTeacherId(long teacheIid);
	Group findByNumberAndLetter(byte number, char letter);
	List<Group> findAllActiveGroups (Date actualDate);
	
}