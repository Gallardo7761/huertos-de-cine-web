package net.miarma.backend.huertosdecine.mapper;

import net.miarma.backend.huertosdecine.dto.RequestDto;
import net.miarma.backend.huertosdecine.model.Request;

public class RequestMapper {

    public static RequestDto.Response toResponse(Request entity) {
        if (entity == null) return null;

        RequestDto.Response dto = new RequestDto.Response();
        dto.setRequestId(entity.getRequestId());
        dto.setDisplayName(entity.getDisplayName());
        dto.setUsername(entity.getUsername());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    public static Request toEntity(RequestDto.Request dto) {
        if (dto == null) return null;

        Request entity = new Request();
        entity.setDisplayName(dto.getDisplayName());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setStatus(dto.getStatus());
        return entity;
    }
}