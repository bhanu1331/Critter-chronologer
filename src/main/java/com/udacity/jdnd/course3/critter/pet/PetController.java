package com.udacity.jdnd.course3.critter.pet;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService service;

    public PetController(PetService service) {
        this.service = service;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO dto) {
        Pet pet = new Pet();
        pet.setName(dto.getName());
        pet.setType(dto.getType());
        pet.setBirthDate(dto.getBirthDate());
        pet.setNotes(dto.getNotes());

        Pet saved = service.savePet(pet, dto.getOwnerId());
        return convertPet(saved);
    }

    @GetMapping("/{id}")
    public PetDTO getPet(@PathVariable Long id) {
        return convertPet(service.getPet(id));
    }

    @GetMapping
    public List<PetDTO> getPets() {
        List<PetDTO> result = new ArrayList<>();
        for (Pet pet : service.getAllPets()) {
            result.add(convertPet(pet));
        }
        return result;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable Long ownerId) {
        List<PetDTO> result = new ArrayList<>();
        for (Pet pet : service.getPetsByOwner(ownerId)) {
            result.add(convertPet(pet));
        }
        return result;
    }

    private PetDTO convertPet(Pet pet) {
        PetDTO dto = new PetDTO();
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setType(pet.getType());
        dto.setBirthDate(pet.getBirthDate());
        dto.setNotes(pet.getNotes());
        if (pet.getOwner() != null) {
            dto.setOwnerId(pet.getOwner().getId());
        }
        return dto;
    }
}
