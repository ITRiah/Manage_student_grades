package com.example.Bai1.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Bai1.dto.ResponseDTO;


@RestController
@RequestMapping("/cache")
public class CacheController { //Xóa cache bằng tay.
	@Autowired
	CacheManager cacheManager;
	
	@GetMapping("/")
	public ResponseDTO<List<String>>  getList(){
		List<String> caches =  cacheManager.getCacheNames().stream() //java 8
					.collect(Collectors.toList());
		
		return ResponseDTO.<List<String>>builder()
				.status(200)
				.data(caches)
				.msg("ok")
				.build();
	}
	
	@DeleteMapping("/")
	public ResponseDTO<Void> delete(@RequestParam("name") String name){
		Cache cache =cacheManager.getCache(name);
		if(cache != null) {
			cache.clear();
		}
		return ResponseDTO.<Void>builder()
				.status(200)
				.msg("ok")
				.build();
	}
	
}
