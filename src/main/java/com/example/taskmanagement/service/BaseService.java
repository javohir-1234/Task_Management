package com.example.taskmanagement.service;

import com.example.taskmanagement.entity.BaseEntity;
import com.example.taskmanagement.exceptions.DataNotFound;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public abstract class BaseService<
        ENTITY extends BaseEntity,
        ID extends UUID,
        REP extends JpaRepository<ENTITY, ID>,
        RES,
        REQ> {


    public abstract ModelMapper modelMapper();

    public abstract REP repository();

    public RES create(REQ request) throws DataNotFound {
        ENTITY entity = requestToEntity(request);
        repository().save(entity);
        return entityToResponse(entity);
    }

    public RES getById(ID id) throws DataNotFound {
        ENTITY entity = repository().findById(id)
                .orElseThrow(() -> new DataNotFound("Data Not Found"));
        if (entity.isActive()) {
            return entityToResponse(entity);
        }

        throw new DataNotFound("Data Not Found");
    }

    public void delete(ID id) throws DataNotFound {
        ENTITY entity = repository().findById(id)
                .orElseThrow(() -> new DataNotFound("Data Not Found"));
        entity.setActive(false);
    }

    public RES update(ID id, REQ request) throws DataNotFound {
        ENTITY entity = repository().findById(id)
                .orElseThrow(() -> new DataNotFound("Data Not Found"));
        if (entity.isActive()) {
            modelMapper().map(request, entity);
            repository().save(entity);
            return entityToResponse(entity);
        }

        throw new DataNotFound("Data Not Found");
    }

    public List<RES> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository().findAll(pageable)
                .stream().map(e -> entityToResponse(e))
                .collect(Collectors.toList());
    }


    public abstract ENTITY requestToEntity(REQ request);

    public abstract RES entityToResponse(ENTITY entity);
}
