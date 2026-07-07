package com.eComApp.user.service;

import com.eComApp.user.dto.UserRequestDTO;
import com.eComApp.user.dto.addressDto;
import com.eComApp.user.dto.userResponseDTO;
import com.eComApp.user.models.Address;
import com.eComApp.user.models.User;
import com.eComApp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<userResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapUserRes)
                .toList();
    }

    public void addUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        updateUserReqWithDto(user, userRequestDTO);
        userRepository.save(user);

        log.info("Saving User : {}" , user.getFirstName());
    }

    public Optional<userResponseDTO> getUserById(long id) {
        return userRepository.findById(id).stream()
                .map(this::mapUserRes)
                .findFirst();
    }

    public User updateUserById(long id, UserRequestDTO userRequestDTO) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    updateUserReqWithDto(existingUser, userRequestDTO);
                    return userRepository.save(existingUser);
                }).orElse(null);
    }

    // will update user with userRequestDTO data
    public void updateUserReqWithDto(User user , UserRequestDTO userRequestDTO){
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPhoneNumber(userRequestDTO.getPhoneNumber());

        if(userRequestDTO.getAddress() != null){
            Address address = new Address();
            address.setStreet(userRequestDTO.getAddress().getStreet());
            address.setCity(userRequestDTO.getAddress().getCity());
            address.setState(userRequestDTO.getAddress().getState());
            address.setCountry(userRequestDTO.getAddress().getCountry());
            address.setZipCode(userRequestDTO.getAddress().getZipCode());
            user.setAddress(address);
        }
    }

    // will convert user to userRequestDTO and return it
    public userResponseDTO mapUserRes(User user){
        userResponseDTO userRequestDTO = new userResponseDTO();
        userRequestDTO.setFirstName(user.getFirstName());
        userRequestDTO.setLastName(user.getLastName());
        userRequestDTO.setEmail(user.getEmail());
        userRequestDTO.setPhoneNumber(user.getPhoneNumber());
        userRequestDTO.setRole(user.getRole());

        if(user.getAddress() != null){
            addressDto addressDto = new addressDto();
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setState(user.getAddress().getState());
            addressDto.setCountry(user.getAddress().getCountry());
            addressDto.setZipCode(user.getAddress().getZipCode());
            userRequestDTO.setAddress(addressDto);
        }
        return userRequestDTO;
    }
}
