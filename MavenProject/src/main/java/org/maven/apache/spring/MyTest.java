package org.maven.apache.spring;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.maven.apache.transaction.Transaction;
import org.maven.apache.MyLauncher;
import org.maven.apache.dateTransaction.DateTransaction;
import org.maven.apache.item.Item;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.UserMapper;
import org.maven.apache.service.DateTransaction.DateTransactionService;
import org.maven.apache.service.item.ItemService;
import org.maven.apache.service.mail.MailService;
import org.maven.apache.service.transaction.cachedTransactionService;
import org.maven.apache.service.user.UserService;
import org.maven.apache.user.User;
import org.maven.apache.utils.TransactionCachedUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.annotations.Test;

public class MyTest {

	@Test
	public void test1() {
		ApplicationContext context = new AnnotationConfigApplicationContext(MyBatisAutoConfiguration.class);
		ItemMapper itemMapper = (ItemMapper) (context.getBean("itemMapper"));
		UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
		User user = userMapper.selectById(3);
		User user2 = new User();
		user2.setPassword("123123134");
		user2.setUsername("Jerry2223333");
		user2.setName("asd");
		List<Item> items = itemMapper.selectAll();
		userMapper.add(user2);
		System.out.println(user);
		System.out.println(items);

	}

	@Test
	public void test2() {
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		ItemService itemService = context.getBean("itemService", ItemService.class);
		List<Item> items = itemService.selectAll();
		System.out.println(items);

	}

	@Test
	public void test3() {
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		UserService userService = context.getBean("userService", UserService.class);
		List<User> users = userService.selectAll();
		System.out.println(users);

	}

	@Test
	public void test4() {
		ExecutorService threadPoolExecutor = MyLauncher.context.getBean("threadPoolExecutor", ExecutorService.class);

		for (int i = 0; i < 14; i++) {
			threadPoolExecutor.execute(() -> System.out.println("123123"));
		}
	}

	@Test
	public void test5() throws InterruptedException {
		MailService mailService = MyLauncher.context.getBean("mailService", MailService.class);
		mailService.sendEmail("jamesgong0719@gmail.com", "123456999");
		System.out.println("woshisheiasd");
		System.out.println("woshisheiasd");
		System.out.println("woshisheiasd");
		System.out.println("woshisheiasd");
		System.out.println("woshisheiasd");
		System.out.println("woshisheiasd");
		Thread.sleep(5000);
	}

	@Test
	public void test6() {

		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		DateTransactionService dateTransactionservice = context.getBean("dateTransactionService",
				DateTransactionService.class);
//    dateTransactionservice.deleteById(3);
//    dateTransactionservice.IdGapInside();
//dateTransactionservice.

//        Calendar calendar= Calendar.getInstance();
//        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
//        System.out.println(dateFormat.format(calendar.getTime()));

		// List<DateTransaction> dateTransaction = dateTransactionservice.selectAll();
//DateTransaction dateTransaction = dateTransactionservice.selectById(1);
//dateTransaction.setAddUnit(999);
//dateTransactionservice.addUnitNumber(dateTransaction);
//        List<DateTransaction> date = dateTransactionservice.askedDate("2022-12-21");
//        System.out.println(date.get(0).getItemName());
//        System.out.println(date.get(0).getItemID());
		DateTransaction dateTransaction = new DateTransaction();
		System.out.println(dateTransactionservice.getCurrentTime());
//        dateTransaction.setAddUnit(20);
//        dateTransaction.setRemoveUnit(10);
//        dateTransaction.setCurrentUnit(dateTransaction.calculateCurrentUnit());
//        dateTransaction.setItemName("Vitamin");
//        dateTransaction.setStaffName("Mirror");
//        dateTransaction.setRecordTime(dateTransactionservice.getCurrentTime());

		// System.out.println(dateTransaction.calculateCurrentUnit());
//      //  System.out.println( "sdferhrtFRYGIHG:  "+ dateTransaction.getCurrentUnit());
		// dateTransactionservice.addTransaction(dateTransaction);
//

		// dateTransactionservice.addUnitNumber(999,3);
		// System.out.println(date);
	}

	@Test
	public void test7() {
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		ItemService itemService = context.getBean("itemService", ItemService.class);
		itemService.deleteById(1);
		Item item = new Item();
		item.setItemName("head phone");
		item.setUnit(34);
		itemService.addNewItem(item);
		itemService.update(item);
	}

	@Test
	public void test8() {
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		DateTransactionService dateTransactionservice = context.getBean("dateTransactionService",
				DateTransactionService.class);
		List<DateTransaction> dateTransaction = dateTransactionservice.pageAskedNOOrder(1, 4);
		for (DateTransaction dare : dateTransaction) {
			System.out.println(dare.getItemID() + dare.getItemName()+dare.getAddUnit());
		}
	}

	@Test
	public void test9() {
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		DateTransactionService dateTransactionservice = context.getBean("dateTransactionService",
				DateTransactionService.class);
		List<DateTransaction> dateTransaction = dateTransactionservice.pageAskedDateAddUnitDescend();
		// List<DateTransaction> allDare = dateTransactionservice.selectAll();
		// System.out.println("jingdfe"+allDare.size());
		// System.out.println("shumu"+dateTransaction.size());
		System.out.println(dateTransaction.toString());
	}

	@Test
	public void test10() {
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		DateTransactionService dateTransactionservice = context.getBean("dateTransactionService",
				DateTransactionService.class);
		dateTransactionservice.IdGapInside();
	//	List<DateTransaction> dateTransaction = dateTransactionservice.pageAskedDateDescend(1,4);

		// List<DateTransaction> allDare = dateTransactionservice.selectAll();
		// System.out.println("jingdfe"+allDare.size());
		// System.out.println("shumu"+dateTransaction.size());
//		for (DateTransaction dare : dateTransaction) {
//			System.out.println(dare.getItemID() + " Name: " + dare.getItemName() + " AddUnit: " + dare.getAddUnit()
//					+ " removeUnit: " + dare.getAddUnit() + " CurrentUnit" + dare.getCurrentUnit() + " Time"
//					+ dare.getRecordTime());
//		}
	}

	@Test
	public void test11() {
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		ItemService itemService = context.getBean("itemService", ItemService.class);
		//List<Item> itemList = itemService.pageAskedItemIDAscend(1,4);
		List<Item> itemList = itemService.pageAskedUnitDescend(1,4);
		System.out.println(itemList);
	}

	@Test
	public  void test12(){
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		cachedTransactionService cachedTransactionService = context.getBean("cachedTransactionService",cachedTransactionService.class);
//		Transaction transaction = new Transaction();
//		transaction.setItemName("wood");
//		transaction.setStaffName("Tom");
//		transaction.setStatus("Restock");
//		transaction.setUnit(50);
//		transaction.setTransactionTime("2022-12-01");
//		cachedTransactionService.addNewTransaction(transaction);
//		Transaction transaction1=new Transaction();
//		transaction1.setItemName("Fan");
//		transaction1.setStaffName("Smith");
//		transaction1.setStatus("Restock");
//		transaction1.setUnit(100);
//		transaction1.setTransactionTime("2022-12-03");
//		cachedTransactionService.addNewTransaction(transaction1);
//		cachedTransactionService.IdGapInside();
		cachedTransactionService.updateAllCachedTransactionData();
		List<List<Transaction>> list=TransactionCachedUtils.getLists(TransactionCachedUtils.listType.AMOUNT_ASC_4);
		for(List<Transaction> first:list){
			for(Transaction transaction:first){
				System.out.println("TYGH" + transaction.getItemName()+transaction.getUnit());
			}
		}


	}

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        UserService userService = context.getBean("userService", UserService.class);
        List<User> users = userService.selectAll();
        System.out.println(users);
    }
}
