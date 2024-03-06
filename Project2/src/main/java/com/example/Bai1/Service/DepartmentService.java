package com.example.Bai1.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Bai1.dto.DepartmentDTO;
import com.example.Bai1.dto.SearchDTO;
import com.example.Bai1.entity.Department;
import com.example.Bai1.repository.DepartmentRepo;

@Service
public class DepartmentService {
	@Autowired
	DepartmentRepo departmentRepo;
	
	
	public Page<DepartmentDTO> getAll(SearchDTO listUserDTO){
		if(listUserDTO.getCurrentPage() == null)
			listUserDTO.setCurrentPage(0);
		if(listUserDTO.getSize() == null)
			listUserDTO.setSize(3);
		
		PageRequest pageRequest = PageRequest.of(listUserDTO.getCurrentPage(), listUserDTO.getSize());
		Page<Department> page = departmentRepo.findAll(pageRequest);
		
		Page<DepartmentDTO> page2 = page.map(department -> new ModelMapper().map(department, DepartmentDTO.class));
		return page2;
	}
	
	@Transactional
	@CacheEvict(cacheNames = "department-search", allEntries = true) //khi thêm mới dữ liệu cần xóa cacheSearch nếu không nó sẽ không lấy dữ liệu mới
	public void create(DepartmentDTO departmentDTO) {
		if(!departmentRepo.findById(departmentDTO.getId()).isPresent()) {
			Department department = new ModelMapper().map(departmentDTO, Department.class);
			departmentRepo.save(department);
		}
	}
	
	@Transactional
	@Caching(evict = {
			@CacheEvict (cacheNames = "department", key = "#departmentDTO.id"),
			@CacheEvict (cacheNames = "department-search", allEntries = true) //allEntries -> xóa tất cả phần tử
	}) 
	//@CachePut(cacheNames = "department", key="#departmentDTO.id" ) 
	//phương thức phải khác void và cùng kiểu dữu liệu trả về với getById
	public void update( DepartmentDTO departmentDTO) {
		Department department = departmentRepo.getById(departmentDTO.getId());
		if(department != null) {
			department.setName(departmentDTO.getName());
			departmentRepo.save(department);
		}
	}
	
	@Transactional
	@Caching(evict = {
			@CacheEvict (cacheNames = "department", key = "#id"),
			@CacheEvict (cacheNames = "department-search", allEntries = true) //allEntries -> xóa tất cả phần tử
	}) // xóa nhiều cache
	public void delete(int id) {
		departmentRepo.deleteById(id);
	}
	
	@Cacheable(cacheNames = "department", key = "#id", unless = "#result == null")// tối ưu hóa tốc độ , lưu vào trong bộ nhớ tạm, 
																					//result đại diện cho kết quả trả về.
	public DepartmentDTO getById(int id) {
		Department department = departmentRepo.getById(id);
		if(department != null) {
			DepartmentDTO departmentDTO = new ModelMapper().map(department, DepartmentDTO.class);
			return departmentDTO;
		}
		return null;
	}
	
	@Cacheable(cacheNames = "department-search")
	public List<DepartmentDTO> search(String name){
		List<Department> departments = departmentRepo.searchName("%" + name +"%");
		
		List<DepartmentDTO> departmentDTOs = departments.stream().map(department -> 
											new ModelMapper().map(department, DepartmentDTO.class)).collect(Collectors.toList());
		return departmentDTOs;
 	}
	
	
}
