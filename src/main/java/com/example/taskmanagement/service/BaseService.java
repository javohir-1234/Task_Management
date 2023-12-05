package com.example.taskmanagement.service;

import com.example.taskmanagement.entity.BaseEntity;
import com.example.taskmanagement.exceptions.DataNotFound;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public abstract class BaseService <
        ENTITY extends BaseEntity,
        ID extends UUID,
        REP extends JpaRepository<ENTITY, ID>,
        RES,
        REQ>
{


    private REP repository;
    private final ModelMapper modelMapper;

    public RES create(REQ request){
        ENTITY entity = requestToEntity(request);
        repository.save(entity);
        return entityToResponse(entity);
    }

    public void delete(ID id){
        repository.deleteById(id);
    }

    public RES update(ID id, REQ request) throws DataNotFound {
        ENTITY entity = repository.findById(id)
                .orElseThrow(() -> new DataNotFound("Data Not Found"));
        modelMapper.map;
    }



    public abstract ENTITY requestToEntity(REQ request);
    public abstract RES entityToResponse(ENTITY entity);
}
