package kh.mclass.crawling.domain;

import java.util.List;

import org.springframework.stereotype.Component;

import kh.mclass.crawling.domain.CrawlingEntity;
import lombok.Data;
import lombok.Getter;

@Data
@Component
@Getter
public class CrawlingEntity {
	private Integer hotelCode;
	private Integer businessNum;
	private String hotelName;
	private String hotelEng;
	private String hotelAddress;
	private String hotelCall;
	private String hotelCheckIn;
	private String hotelCheckOut;
	private String hotelPolicy;
	private String hotelIntro;
	private Integer hotelPcount;
}
