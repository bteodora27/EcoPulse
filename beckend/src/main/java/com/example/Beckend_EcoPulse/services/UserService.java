//package com.example.Beckend_EcoPulse.services;
//
//import com.example.Beckend_EcoPulse.models.Organization;
//import com.example.Beckend_EcoPulse.models.StandardUser;
//import com.example.Beckend_EcoPulse.models.User;
//import com.example.Beckend_EcoPulse.exception.InsufficientPermissionsException;
//import com.example.Beckend_EcoPulse.repositories.OrganizationRepository;
//import com.example.Beckend_EcoPulse.repositories.StandardUserRepository;
//import com.example.Beckend_EcoPulse.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private StandardUserRepository standardUserRepository;
//
//    @Autowired
//    private OrganizationRepository organizationRepository;
//
//    public void validateEventCreationPermission(Long userID) {
//        User user = userRepository.findById(userID)
//                .orElseThrow(() -> new InsufficientPermissionsException("User not found"));
//
//        // 1. Verifică dacă e StandardUser
//        if (standardUserRepository.findByUser(user).isPresent()) {
//            StandardUser standardUser = standardUserRepository.findByUser(user).get();
//            if (!Boolean.TRUE.equals(standardUser.getCanCreateEvent())) {
//                throw new InsufficientPermissionsException("Standard user not allowed to create events");
//            }
//            return;
//        }
//
//        // 2. Verifică dacă e Organization
//        if (organizationRepository.findByUser(user).isPresent()) {
//            Organization org = organizationRepository.findByUser(user).get();
//            if (!Boolean.TRUE.equals(org.getCanCreateEvents())) {
//                throw new InsufficientPermissionsException("Organization not allowed to create events");
//            }
//            return;
//        }
//
//        // 3. Dacă nu e nici StandardUser nici Organization
//        throw new InsufficientPermissionsException("User has no permission to create events");
//    }
//}
