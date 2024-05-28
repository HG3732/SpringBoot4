package kh.mclass.crawling.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kh.mclass.crawling.domain.CrawlingEntity;
import kh.mclass.crawling.domain.HotelFacilityEntity;
import kh.mclass.crawling.domain.HotelPicEntity;

@Mapper
public interface CrawlingRepository {
	
	public Integer insertHotel(CrawlingEntity dto);
	public Integer insertPic(HotelPicEntity url);
	public Integer insertFac(HotelFacilityEntity fac);

}
