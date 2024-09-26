package com.lvtn.notification.entity;


import com.lvtn.utils.dto.notification.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="notification")
public class Notification {
    @Id
    @SequenceGenerator(name = "notification_id_sequence",
            sequenceName = "notification_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_id_sequence")
    private Integer id;

    private String message;
    private String sender;
    private String toCustomerEmail;
    private Integer toCustomerId;
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @CreatedDate
    private LocalDateTime sendAt;

}
