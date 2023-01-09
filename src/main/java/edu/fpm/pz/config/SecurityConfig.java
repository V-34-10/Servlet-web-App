package edu.fpm.pz.config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class SecurityConfig {

    private final Set<String> protectedURLs = new HashSet<>();

    public SecurityConfig() {
        protectedURLs.add("/employee");
        protectedURLs.add("/position");
    }

    public Set<String> getProtectedURLs() {
        return Collections.unmodifiableSet(protectedURLs);
    }
}
