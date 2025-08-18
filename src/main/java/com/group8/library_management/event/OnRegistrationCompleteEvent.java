package com.group8.library_management.event;


import com.group8.library_management.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private final User user;

    public OnRegistrationCompleteEvent(User user) {
        super(user);
        this.user = user;
    }
}
