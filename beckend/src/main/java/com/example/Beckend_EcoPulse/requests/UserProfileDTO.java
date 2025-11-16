package com.example.Beckend_EcoPulse.requests;

import com.example.Beckend_EcoPulse.models.StandardUser;
import com.example.Beckend_EcoPulse.models.User;
import lombok.Data;

@Data
public class UserProfileDTO {

    // Câmpuri din User.java
    private Long userId;
    private String email;
    private String username;

    // Câmpuri din StandardUser.java
    private String firstName;
    private String lastName;
    private String phone;
    private String standardUserRank;
    private Integer totalPoints;

    // Constructor care face maparea
    public UserProfileDTO(User user, StandardUser profile) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();

        this.firstName = profile.getFirstName();
        this.lastName = profile.getLastName();
        this.phone = profile.getPhone();
        this.standardUserRank = profile.getStandardUserRank();
        this.totalPoints = profile.getTotalPoints();
    }
}