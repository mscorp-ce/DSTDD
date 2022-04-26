package com.devsuperior.bds02.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exception.ResourceNotFoundException;

@Service
public class EventService {
	
	@Autowired
	private EventRepository repository;

	@Transactional
	public EventDTO update(Long id, EventDTO dto) {

		try {
			Event event = repository.getOne(id);
			copyDtoToEntity(dto, event);

			event = repository.save(event);
			return new EventDTO(event);
		}
		catch(EntityNotFoundException e){
			throw new ResourceNotFoundException("Id not found "+ id);
		}
	}
	
	private void copyDtoToEntity(EventDTO dto, Event event) {
		event.setName(dto.getName());
		event.setDate(dto.getDate());
		event.setCity(new City(dto.getCityId(), null));
		event.setUrl(dto.getUrl());
	}

}
