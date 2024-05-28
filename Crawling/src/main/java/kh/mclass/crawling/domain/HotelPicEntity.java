package kh.mclass.crawling.domain;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;

@Data
@Component
@Getter
public class HotelPicEntity {
	private String innPic;
	private Integer hotelCode;
}
