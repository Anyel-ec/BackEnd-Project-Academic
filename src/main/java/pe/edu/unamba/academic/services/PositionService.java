package pe.edu.unamba.academic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.Position;
import pe.edu.unamba.academic.repositories.PositionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PositionService {
    @Autowired
    private PositionRepository positionRepository;

    public List<Position> getAllCargos() {
        return positionRepository.findAll();
    }

    public Optional<Position> getPositionById(Long id) {
        return positionRepository.findById(id);
    }

    public Position savePosition(Position cargo) {
        return positionRepository.save(cargo);
    }

    public void deleteCPosition(Long id) {
        positionRepository.deleteById(id);
    }

    public Position updatePosition(Long id, Position cargoDetails) {
        Position cargo = positionRepository.findById(id).get();
        cargo.setName(cargoDetails.getName());
        return positionRepository.save(cargo);
    }
}