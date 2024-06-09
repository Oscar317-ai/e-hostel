package com.guava.E_HOSTELS.hostel.notifications;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private List<Notification> notifications = new ArrayList<>();

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public List<Notification> getNotificationsForTenant(Long tenantId) {
        return notifications.stream()
                .filter(notification -> notification.getTenantId().equals(tenantId))
                .collect(Collectors.toList());
    }
}