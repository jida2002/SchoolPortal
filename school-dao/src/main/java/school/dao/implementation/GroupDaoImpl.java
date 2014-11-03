package school.dao.implementation;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import school.dao.GroupDao;
import school.model.Group;

@Repository
public class GroupDaoImpl extends BaseDaoImpl<Group, Long> implements GroupDao {

	public GroupDaoImpl() {
		super(Group.class);
	}

	public List<Group> findAllAdditional() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Group> findAllNotAdditional() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<Group> findByNumber(byte number) {
		try {
			return entityManager.createNamedQuery(Group.FIND_BY_NUMBER)
					.setParameter("number", number).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<Group> findByStartDate(Date startDate) {
		try {
			return entityManager.createNamedQuery(Group.FIND_BY_STARTDATE)
					.setParameter("startDate", startDate).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Transactional
	public Group findByTeacherId(long teacheIid) {
		try {
			return (Group) entityManager
					.createNamedQuery(Group.FIND_BY_TEACHER)
					.setParameter("teacheIid", teacheIid).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Transactional
	public Group findByNumberAndLetter(byte number, char letter) {
		try {
			return (Group) entityManager
					.createNamedQuery(Group.FIND_BY_NUMBER_LETTER)
					.setParameter("number", number)
					.setParameter("letter", letter).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<Group> findAllActiveGroups(Date actualDate) {
		try {
			return entityManager.createNamedQuery(Group.FIND_ALL_ACTIVE_GROUP)
					.setParameter("actualDate", actualDate).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}