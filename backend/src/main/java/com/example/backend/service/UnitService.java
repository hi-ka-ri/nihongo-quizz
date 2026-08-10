package com.example.backend.service;

import com.example.backend.dto.response.UnitDto;
import com.example.backend.entity.Unit;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnitService {
    
    private final UnitRepository unitRepository;

    public List<UnitDto> getAllUnits() {
        return unitRepository.findAllByOrderByOrderIndexAsc().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public UnitDto getUnitById(Integer id) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unit not found with id: " + id));
        return mapToDto(unit);
    }

    private UnitDto mapToDto(Unit unit) {
        return UnitDto.builder()
                .id(unit.getId())
                .title(unit.getTitle())
                .description(unit.getDescription())
                .orderIndex(unit.getOrderIndex())
                .imageUrl(unit.getImageUrl())
                .build();
    }
}
