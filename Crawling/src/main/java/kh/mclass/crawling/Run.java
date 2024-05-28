package kh.mclass.crawling;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.mclass.crawling.domain.CrawlingEntity;
import kh.mclass.crawling.domain.HotelFacilityEntity;
import kh.mclass.crawling.domain.HotelPicEntity;
import kh.mclass.crawling.model.repository.CrawlingRepository;


@Service
public class Run {
	private WebDriver webDriver;
	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
//	public static final String WEB_DRIVER_PATH = "/Crawling/chromedriver.exe";
	public static final String WEB_DRIVER_PATH = "C:\\workspace\\SpringBoot\\Crawling\\chromedriver.exe";
//"C:\\workspace\\SpringBoot\\Crawling\\chromedriver.exe"
//	"/Crawling/chromedriver.exe"
	
	@Autowired
	HotelPicEntity pic;
	
	@Autowired
	private CrawlingRepository crawlingRepository; 
	
	public List<CrawlingEntity> crawling() throws Exception {
		
		List<CrawlingEntity> data = new ArrayList<>();
		
		System.out.println("a=1");
//		String title = "";
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
		System.out.println("a=2");
		ChromeOptions options = new ChromeOptions();
		// 브라우저 띄우며 실행
//		options.setCapability("ignoreProtectedModeSettings", false);
		// 브라우저 띄우지 않고 실행
//		options.addArguments("headless");
//		options.addArguments("--lang=ko");
//		options.addArguments("--start-maximized");
        options.addArguments("--disable-popup-blocking");//팝업 무시
        System.out.println("a=3");
		webDriver = new ChromeDriver(options);
		System.out.println("a=4");
		//WebDriverWait wait = new WebDriverWait(webDriver, 10);  // TODO
		JavascriptExecutor executor = (JavascriptExecutor) webDriver;
		
		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		System.out.println("a=5");
		String url = "https://fit.ybtour.co.kr/hotel/hotel-search-result?countryCode=AE&regionCode=HKHKG&name=%ED%99%8D%EC%BD%A9&checkInDate=2024-06-11&checkOutDate=2024-06-14&roomList=2%5E0%5E";
		webDriver.get(url);
		System.out.println("a=7");
		
		//호텔 리스트에서 5개까지 뽑기
		for(int i = 1; i < 6; i++) {
			webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
			Thread.sleep(10000);
			CrawlingEntity hotelInfo = new CrawlingEntity();
			hotelInfo.setHotelCode(i);
			hotelInfo.setBusinessNum(i);
			hotelInfo.setHotelPcount(0);
			pic = new HotelPicEntity();
			HotelFacilityEntity hfe = new HotelFacilityEntity();
			
			//리스트 목록중 i번째 클릭
			WebElement link = webDriver.findElement(By.cssSelector("#__next > div:nth-child(1) > div.sc-7ddb7492-21.voftS > div.sc-b5d2a58c-3.buIBIM > div.sc-fd99526a-0.ddjdys > div > div.sc-7ddb7492-5.jzRuis > div.sc-7ddb7492-6.jmymcq > ul > li:nth-child("+i+") > a"));
			link.click();
			System.out.println("a=8");
			webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
			System.out.println("a=8-1");
			Thread.sleep(10000);
			System.out.println("a=8-2");
			//열려있는 탭 관리
			List<String> tabs = new ArrayList<String>(webDriver.getWindowHandles());
			System.out.println("a=9");
			//index 1번째 탭으로 핸들러 전환
			WebDriver webDriver1 = webDriver.switchTo().window(tabs.get(1));
			System.out.println("a=10");
			
			//호텔 한글 이름 hotelName
			String title_ko = webDriver1.findElement(By.cssSelector("#__next > div:nth-child(1) > div.sc-fd99526a-0.sc-aea556c0-0.ddjdys.dkYsOA > div.sc-fd99526a-2.sc-aea556c0-1.jRPrYk.ijKNBz > h2")).getText();
			title_ko = title_ko.replace(",","");
			hotelInfo.setHotelName(title_ko);
			System.out.println(title_ko);
			
			//호텔 영어 이름 hotelEng
			String title_en = webDriver.findElement(By.cssSelector("#__next > div:nth-child(1) > div.sc-fd99526a-0.sc-aea556c0-0.ddjdys.dkYsOA > div.sc-fd99526a-2.sc-aea556c0-1.jRPrYk.ijKNBz > span")).getText();
			title_en = title_en.replace(",","");
			hotelInfo.setHotelEng(title_en);
			System.out.println(title_en);
			
			//호텔 주소 hotelAddress
			String hotelAddress = webDriver.findElement(By.cssSelector("#hotelDetailComponent > div.sc-aea556c0-38.eAYAtz > dl:nth-child(1) > dd > ul > li:nth-child(2) > p")).getText();
			hotelInfo.setHotelAddress(hotelAddress);
			System.out.println(hotelAddress);
			
			//호텔 전화번호 hotelCall
			String hotelCall = webDriver.findElement(By.cssSelector("#hotelDetailComponent > div.sc-aea556c0-38.eAYAtz > dl:nth-child(1) > dd > ul > li:nth-child(3) > p")).getText();
			hotelInfo.setHotelCall(hotelCall);
			System.out.println(hotelCall);
			
			//호텔 소개  hotelIntro
			String hotelIntro = webDriver.findElement(By.cssSelector("#hotelDetailComponent > div.sc-aea556c0-38.eAYAtz > dl:nth-child(2) > dd > p")).getText();
			hotelInfo.setHotelIntro(hotelIntro);
			System.out.println(hotelIntro);
			
			//체크인 시간 hotelCheckIn
			String hotelCheckIn = webDriver.findElement(By.cssSelector("#hotelDetailComponent > div.sc-aea556c0-38.eAYAtz > dl:nth-child(3) > dd > ul > li:nth-child(1) > p")).getText();
			hotelInfo.setHotelCheckIn(hotelCheckIn);
			System.out.println(hotelCheckIn);
			
			//체크아웃 시간 hotelCheckIn
			String hotelCheckOut = webDriver.findElement(By.cssSelector("#hotelDetailComponent > div.sc-aea556c0-38.eAYAtz > dl:nth-child(3) > dd > ul > li:nth-child(2) > p")).getText();
			hotelInfo.setHotelCheckOut(hotelCheckOut);
			System.out.println(hotelCheckOut);
			
			//호텔 정책
			String hotelPolicy = webDriver.findElement(By.cssSelector("#hotelDetailComponent > div.sc-aea556c0-38.eAYAtz > dl:nth-child(6) > dd > p")).getText();
			hotelInfo.setHotelPolicy(hotelPolicy);
			System.out.println(hotelPolicy);
			
			crawlingRepository.insertHotel(hotelInfo);
			
			//호텔 편의시설
			for(int k = 1; k < 7; k++) {
				try {
					if(webDriver.findElement(By.cssSelector("#hotelDetailComponent > div.sc-aea556c0-38.eAYAtz > dl:nth-child(5) > dd > ul > li:nth-child("+k+") > span")) != null) {
						String hotelFacCat = webDriver.findElement(By.cssSelector("#hotelDetailComponent > div.sc-aea556c0-38.eAYAtz > dl:nth-child(5) > dd > ul > li:nth-child("+k+") > span")).getText();
						System.out.println(hotelFacCat);
						hfe.setHotelCode(i);
						hfe.setHotelFacCat(hotelFacCat);
						crawlingRepository.insertFac(hfe);
					}
				} catch (Exception e) {
					System.out.println(i+ "번 글의" + k + "번째 편의시설 요소 없음!");
				}
			}
			
			//호텔당 사진 10장까지 저장
			for(int j = 1; j<11; j++) {
				try {
					WebElement ele_image = webDriver.findElement(By.cssSelector("#__next > div:nth-child(1) > div.sc-fd99526a-0.sc-aea556c0-0.ddjdys.dkYsOA > div.sc-aea556c0-8.iudIa > div > div.swiper-wrapper > div:nth-child("+j+") > div > img"));
																		   
					String imgSRC = ele_image.getAttribute("src");
					System.out.println("imgSRC : " + imgSRC);
					
					//이미지의 URL만 따올 것이므로 아래 코드는 X
//					URL imgURL = new URL(imgSRC);
//					String image = ele_image.getText();
//					System.out.println("image : " + image);
					pic.setHotelCode(i);
					pic.setInnPic(imgSRC);
					crawlingRepository.insertPic(pic);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
			webDriver1.close();
			webDriver = webDriver.switchTo().window(tabs.get(0));
			
		}
		webDriver.close();
		webDriver.quit();
		
		return data;
	}
}
