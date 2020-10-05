package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    PetRepository petRepository;
    @Autowired
    CustomerService customerService;

    // Save pet to DB
    public Pet save(Pet pet){
        Pet newPet = petRepository.save(pet);
        if (pet.getCustomer() != null) {
            customerService.addPetToCustomer(newPet, newPet.getCustomer());
        }
        return petRepository.save(pet);
    }

    public List<Pet> findByOwner(Long ownerId) {
        List<Pet> pets = petRepository.findAllByCustomerId(ownerId);
        return pets;
    }

    public Pet findPetById(Long id){
        return petRepository.getOne(id);
    }

    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }
}
