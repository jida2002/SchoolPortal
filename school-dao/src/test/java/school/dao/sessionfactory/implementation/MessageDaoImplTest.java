package school.dao.sessionfactory.implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import school.model.Message;
import school.model.User;

public class MessageDaoImplTest extends DBUnitConfig{

	public MessageDaoImplTest() {
		super("MessageDaoImplTest");
	}

	private User receiver;
	private User sender;
	private Message message;
	private MessageDaoImpl messageDaoImpl;
	
	@BeforeClass
	protected static void setUpBeforeClass() throws Exception {
	}
	
	@AfterClass
	protected static void tearDownAfterClass() throws Exception {
		HibernateSessionFactory.shutdown();
	}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		receiver = new User();
		receiver.setId(1L);
		sender = new User();
		sender.setId(2L);
		message = new Message();
		message.setId(1L);
		message.setText("Text1");
		message.setDate(new Date());
		message.setRead(true);
		message.setReceiverId(receiver);
		message.setSenderId(sender);
		messageDaoImpl = new MessageDaoImpl();
		Session session = HibernateSessionFactory.getSessionFactory().openSession();
		IDataSet messageDataSet = getDataSet();
		DatabaseOperation.CLEAN_INSERT.execute(this.getDatabaseTester().getConnection(), messageDataSet);
		session.close();
		
	}
	
	@After
	public void tearDown() throws Exception {
		Session session = HibernateSessionFactory.getSessionFactory().openSession();
		IDataSet messageDataSet = getDataSet();
		DatabaseOperation.DELETE_ALL.execute(this.getDatabaseTester().getConnection(), messageDataSet);
		session.close();
	}
	
	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSet(this.getClass().getResourceAsStream("/message.xml"));
	}
	
	@Test
	public void testfindAllMessagesByReceiver() {
		List<Message> set = messageDaoImpl.findAllMessagesByReceiver(receiver);
		int actual = set.size();
		int expected = 3;
		Assert.assertTrue(actual == expected);
	}
	
	@Test
	public void testfindAllMessagesBySender() {
		List<Message> set = messageDaoImpl.findAllMessagesBySender(sender);
		int actual = set.size();
		int expected = 2;
		Assert.assertTrue(actual == expected);
	}
	
	@Test
	public void testFindAllMessagesWithUsers() {
		List<User> twoUsers = new ArrayList<User>();
		twoUsers.add(receiver);
		twoUsers.add(sender);
		List<Message> set = messageDaoImpl.findAllMessagesWithUsers(twoUsers);
		int actual = set.size();
		int expected = 2;
		Assert.assertTrue(actual == expected);
	}
	
	@Test
	public void testSelectDistinctSendersForReceiver() {
		List<User> set = messageDaoImpl.selectDistinctSendersForReceiver(receiver);
		int expected = 3;
		int actual = set.size();
		Assert.assertTrue(actual == expected);
	}
	
	@Test
	public void testCountOfLettersWithUsers() {
		List<User> users = new ArrayList<User>();
		message = messageDaoImpl.findById(7L);
		receiver = message.getReceiverId();
		sender = message.getSenderId();
		users.add(receiver);
		users.add(sender);
		int countOfLetters = messageDaoImpl.countOfLettersWithUsers(users);
		int actual = countOfLetters;
		int expected = 2;
		Assert.assertTrue(actual == expected);
	}
	
	@Test
	public void testCountOfNewMessagesBetweenUsers() {
		List<User> users = new ArrayList<User>();
		message = messageDaoImpl.findById(7L);
		receiver = message.getReceiverId();
		sender = message.getSenderId();
		users.add(receiver);
		users.add(sender);
		int actual = messageDaoImpl.countOfNewMessagesBetweenUsers(users);
		int expected = 1;
		Assert.assertTrue(actual == expected);
	}
	
	@Test
	public void testFindById() {
		Message newMessage = messageDaoImpl.findById(1L);
		long actual = message.getId();
		long expected = newMessage.getId();
		Assert.assertEquals(actual, expected);
	}
	
	@Test
	public void testSave() {
		message.setId(11L);
		messageDaoImpl.save(message);
		List<Message> users = messageDaoImpl.findAll();
		Assert.assertTrue(users.size() == 11);
	}

	@Test
	public void testRemove() {
		messageDaoImpl.remove(message);
		Assert.assertNull(messageDaoImpl.findById(1L));
	}

	@Test
	public void testUpdate() {
		Message newMessage = messageDaoImpl.findById(1L);
		Assert.assertEquals(message.getText(), newMessage.getText());
		newMessage.setText("NewText");
		newMessage = messageDaoImpl.update(newMessage);
		String actual = message.getText();
		String notExpected = newMessage.getText();
		Assert.assertNotEquals(actual, notExpected);
	}

	@Test
	public void testFindAll() {
		List<Message> users = messageDaoImpl.findAll();
		int actual = users.size();
		int expected = 10;
		Assert.assertTrue(actual == expected);
	}
	
}