package com.mentora.backend.user;

import com.mentora.backend.user.model.Users;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * Service layer for User-related business logic.
 * Handles streak management and user operations with proper transaction management.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Updates the user's streak based on their activity.
     * This method is transactional - changes are automatically persisted.
     *
     * @param userId the ID of the user
     * @throws NoSuchElementException if user not found
     */
    @Transactional
    public void updateUserStreak(String userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + userId));

        user.updateStreak();
    }

    /**
     * Uses a streak freeze for the user.
     * This method is transactional - changes are automatically persisted.
     *
     * @param userId the ID of the user
     * @throws NoSuchElementException if user not found
     * @throws IllegalStateException if user has no streak freezes available
     */
    @Transactional
    public void useStreakFreeze(String userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + userId));

        if (!user.canUseStreakFreeze()) {
            throw new IllegalStateException("No streak freezes available");
        }

        user.useStreakFreeze();
    }

    /**
     * Gets the current streak count for a user.
     *
     * @param userId the ID of the user
     * @return the current streak count
     * @throws NoSuchElementException if user not found
     */
    public Integer getCurrentStreak(String userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + userId));

        return user.getCurrentStreak();
    }

    /**
     * Gets the longest streak count for a user.
     *
     * @param userId the ID of the user
     * @return the longest streak count
     * @throws NoSuchElementException if user not found
     */
    public Integer getLongestStreak(String userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + userId));

        return user.getLongestStreak();
    }

    /**
     * Gets the remaining streak freeze count for a user.
     *
     * @param userId the ID of the user
     * @return the number of streak freezes available
     * @throws NoSuchElementException if user not found
     */
    public Integer getStreakFreezeCount(String userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + userId));

        return user.getStreakFreezeCount();
    }
}