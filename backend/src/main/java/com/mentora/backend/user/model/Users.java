package com.mentora.backend.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    @NotNull
    private UUID id;

    @Column(name = "email", unique = true)
    @NotNull
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "current_streak", nullable = false, columnDefinition = "integer default 0")
    private Integer currentStreak = 0;

    @Column(name = "longest_streak", nullable = false, columnDefinition = "integer default 0")
    private Integer longestStreak = 0;

    @Column(name = "last_activity_date")
    private LocalDate lastActivityDate;

    @Column(name = "streak_freeze_count", nullable = false, columnDefinition = "integer default 0")
    private Integer streakFreezeCount = 5;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public void updateStreak() {
        LocalDate today = LocalDate.now();

        if (lastActivityDate == null) {
            currentStreak = 1;
            lastActivityDate = today;
        } else if (lastActivityDate.equals(today)) {
            return;
        } else if (lastActivityDate.equals(today.minusDays(1))) {
            currentStreak++;
            lastActivityDate = today;
        } else if (lastActivityDate.isBefore(today.minusDays(1))) {
            currentStreak = 1;
            lastActivityDate = today;
        }

        if (currentStreak > longestStreak) {
            longestStreak = currentStreak;
        }
    }

    public boolean canUseStreakFreeze() {
        return streakFreezeCount > 0;
    }

    public void useStreakFreeze() {
        if (canUseStreakFreeze()) {
            streakFreezeCount--;
            lastActivityDate = LocalDate.now();
        }
    }
}