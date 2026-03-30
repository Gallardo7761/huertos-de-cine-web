package net.miarma.backend.huertosdecine.service;

import net.miarma.backend.huertosdecine.dto.RequestDto;
import net.miarma.backend.huertosdecine.mapper.RequestMapper;
import net.miarma.backend.huertosdecine.model.Request;
import net.miarma.backend.huertosdecine.repository.RequestRepository;
import net.miarma.backlib.exception.ConflictException;
import net.miarma.backlib.exception.NotFoundException;
import net.miarma.backlib.util.UuidUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RequestService {
    private final RequestRepository repository;

    public RequestService(RequestRepository repository) { this.repository = repository; }

    public Request create(Request request) {
        if (repository.existsByUsernameAndStatus(request.getUsername(), (byte) 0)) { // 0 = PENDING
            throw new ConflictException("Ya existe una solicitud para este usuario");
        }
        request.setRequestId(UUID.randomUUID());
        return repository.save(request);
    }

    public List<Request> getAll() {
        return new ArrayList<>(repository.findAll());
    }

    public Request getById(UUID id) {
        byte[] idBytes = UuidUtil.uuidToBin(id);
        return repository.findById(idBytes)
                .orElseThrow(() -> new NotFoundException("Solicitud no encontrada"));
    }

    public Request updateStatus(UUID id, byte newStatus) {
        Request request = getById(id);
        if (request != null) {
            request.setStatus(newStatus);
            return repository.save(request);
        }
        return null;
    }
}