package pl.edu.agh.soa.jwt;


import java.security.Key;

public interface KeyGenerator {
    Key generateKey();
}
