package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PetService {

    private final PetRepository petRepo;
    private final CustomerRepository customerRepo;

    public PetService(PetRepository petRepo, CustomerRepository customerRepo) {
        this.petRepo = petRepo;
        this.customerRepo = customerRepo;
    }

    public Pet savePet(Pet pet, Long ownerId) {
        Customer owner = customerRepo.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));
        pet.setOwner(owner);
        return petRepo.save(pet);
    }

    public Pet getPet(Long id) {
        return petRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));
    }

    public List<Pet> getAllPets() {
        return petRepo.findAll();
    }

    public List<Pet> getPetsByOwner(Long ownerId) {
        return petRepo.findByOwner_Id(ownerId);
    }
}
