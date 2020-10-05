package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public void addPetToCustomer(Pet pet, Customer customer) {
        List<Pet>  pets = customer.getPets();
        if (pets == null) {
            pets = new ArrayList<>();
        }
        pets.add(pet);
        customer.setPets(pets);
    }

    public Customer getForPet(Long petId){
        return customerRepository.findByPetsId(petId);
    }

    public Customer getById(long ownerId) {
        return customerRepository.getOne(ownerId);
    }
}
