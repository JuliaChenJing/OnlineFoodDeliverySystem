package com.grp11.Consumer;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grp11.Domain.ConsumerDomain;

@Service
@Transactional
public class ConsumerServiceImpl implements IConsumerService{
	
	@Autowired
	private IConsumerDAO consumerDAO;


	@Override
	public void addConsumer(ConsumerDomain user) {
		consumerDAO.save(user);	
		
	}

	@Override
	public ConsumerDomain update(ConsumerDomain user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConsumerDomain getUser(Long UserId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(Long UserId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ConsumerDomain> getAllUser() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
