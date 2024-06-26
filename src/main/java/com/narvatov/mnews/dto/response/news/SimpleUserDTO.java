package com.narvatov.mnews.dto.response.news;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.narvatov.mnews.model.user.Status;
import com.narvatov.mnews.model.user.User;
import lombok.Data;

@Data
public class SimpleUserDTO {

    private int id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private Status status;

    public SimpleUserDTO(User user) {
        id = user.getId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        status = user.getStatus();
    }

}
