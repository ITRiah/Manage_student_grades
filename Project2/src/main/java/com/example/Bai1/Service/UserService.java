package com.example.Bai1.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Bai1.dto.SearchDTO;
import com.example.Bai1.dto.UserDTO;
import com.example.Bai1.entity.User;
import com.example.Bai1.repository.UserRepo;

import jakarta.persistence.NoResultException;

@Service //tự tạo một đối tượng UserService và lưu lại
public class UserService implements UserDetailsService { // UserDetailsService: đối tượng trong securiry
	
	@Autowired
	UserRepo userRepo; //đối tương db
	
	public Page<UserDTO> getAll(SearchDTO listUserDTO) {
		
		if(listUserDTO.getCurrentPage() == null)
			listUserDTO.setCurrentPage(0);
		if(listUserDTO.getSize() == null)
			listUserDTO.setSize(3);
		
		Sort sortBy = Sort.by("age").descending();
		
		PageRequest pageRequest = PageRequest.of(listUserDTO.getCurrentPage(), listUserDTO.getSize(), sortBy);
		
		Page<User> userPage = userRepo.findAll(pageRequest);
		
		//ModelMapper
		Page<UserDTO> userDTOPage = userPage.map(user -> new ModelMapper().map(user, UserDTO.class));
		return userDTOPage;
	}
	
	@Transactional
	public void creat(UserDTO userDTO) {
		if(!userRepo.findById(userDTO.getId()).isPresent())
		{
			
			User user = new ModelMapper().map(userDTO, User.class);
			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword())); // nên convert khi lưu db
			userRepo.save(user);
		}
	}
	
	public User getByID(int id) {
		return userRepo.getById(id);
	}
	
	@Transactional
	public void delete(int id) {
		userRepo.deleteById(id);
	}
	
	@Transactional
	public void update(UserDTO userDTO) {
		User current = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException :: new);
		
		if(userDTO.getFile() != null)
		{
			current.setAvatarURL(userDTO.getAvatarURL());
			userRepo.save(current);
		}
	}
	
	public List<UserDTO> searchByName(String name){
		List<User> userList = userRepo.searchByName("%" + name + "%");
		List<UserDTO> userDTOList = userList.stream().map(user -> new ModelMapper().map(user, UserDTO.class)).collect(Collectors.toList());
		return userDTOList;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user  = userRepo.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("Not found");
		}
		
		//convert user -> userdetails
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		//chuyển role về quyền
		for (String role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		
		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
	}
}
